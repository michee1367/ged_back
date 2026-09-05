package com.mich.ged.adapters.out.persistence.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mich.ged.domain.interfaces.out.DocumentStoragePort;

@Component
public class FileSystemStorageAdapter implements DocumentStoragePort {

    private final Path rootLocation;

    public FileSystemStorageAdapter(@Value("${app.storage.location:uploads}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation).normalize();
        initStorage();
    }

    private void initStorage() {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'initialiser le dossier de stockage", e);
        }
    }

    @Override
    public String stockerFichier(String nomFichier, byte[] contenu) {
        try {
            // Génération d'un nom unique pour éviter le télescopage de fichiers
            String extension = "";
            int i = nomFichier.lastIndexOf('.');
            if (i > 0) {
                extension = nomFichier.substring(i);
            }

            String nomFichierUnique = UUID.randomUUID().toString() + extension;
            Path destinationFile = this.rootLocation.resolve(nomFichierUnique).normalize().toAbsolutePath();

            System.out.println("ROOT            = " + this.rootLocation.toAbsolutePath());
            System.out.println("DESTINATION     = " + destinationFile);
            System.out.println("DESTINATION PARENT = " + destinationFile.getParent());
            System.out.println("EQUALS          = " + destinationFile.getParent().equals(this.rootLocation.toAbsolutePath()));

            // Sécurité : Vérification que le fichier reste dans le dossier cible
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new SecurityException("Tentative de stockage en dehors du répertoire autorisé.");
            }

            Files.write(destinationFile, contenu, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Échec du stockage du fichier " + nomFichier, e);
        }
    }

    @Override
    public byte[] telechargerFichier(String cheminStockage) {
        try {
            Path file = Paths.get(cheminStockage);
            if (!Files.exists(file) || !Files.isReadable(file)) {
                throw new RuntimeException("Le fichier n'existe pas ou est inaccessible : " + cheminStockage);
            }
            return Files.readAllBytes(file);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + cheminStockage, e);
        }
    }
}