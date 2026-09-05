package com.mich.ged.domain.interfaces.out;

public interface DocumentStoragePort {
    String stockerFichier(String nomFichier, byte[] contenu);
    byte[] telechargerFichier(String cheminStockage);
}
