-- Clean up existing tables
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS historique_transmissions CASCADE;
DROP TABLE IF EXISTS documents CASCADE;
DROP TABLE IF EXISTS dossiers CASCADE;
DROP TABLE IF EXISTS utilisateurs CASCADE;
DROP TABLE IF EXISTS services CASCADE;

-- 1. Table Services
CREATE TABLE services (
    id_service BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE
);

-- 2. Table Utilisateurs
CREATE TABLE utilisateurs (
    id_utilisateur BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    post_nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    actif BOOLEAN NOT NULL DEFAULT TRUE,
    service_id BIGINT,
    CONSTRAINT fk_user_service FOREIGN KEY (service_id) REFERENCES services(id_service) ON DELETE SET NULL
);

-- 3. Table Dossiers
CREATE TABLE dossiers (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(255) NOT NULL UNIQUE,
    objet VARCHAR(255) NOT NULL,
    date_reception TIMESTAMP NOT NULL,
    echeance DATE NOT NULL,
    priorite VARCHAR(50) NOT NULL DEFAULT 'NORMALE',
    statut VARCHAR(50) NOT NULL DEFAULT 'RECU',
    agent_enregistreur_id BIGINT NOT NULL,
    service_destinataire_id BIGINT,
    CONSTRAINT fk_dossier_agent FOREIGN KEY (agent_enregistreur_id) REFERENCES utilisateurs(id_utilisateur),
    CONSTRAINT fk_dossier_service FOREIGN KEY (service_destinataire_id) REFERENCES services(id_service) ON DELETE SET NULL
);

-- 4. Table Documents
CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    chemin_fichier VARCHAR(255) NOT NULL,
    date_ajout TIMESTAMP NOT NULL,
    dossier_id BIGINT NOT NULL,
    CONSTRAINT fk_document_dossier FOREIGN KEY (dossier_id) REFERENCES dossiers(id) ON DELETE CASCADE
);

-- 5. Table Historique Transmissions
CREATE TABLE historique_transmissions (
    id BIGSERIAL PRIMARY KEY,
    motif TEXT,
    date_transmission TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    action_effectuee VARCHAR(255) NOT NULL,
    commentaire VARCHAR(255) NOT NULL,
    statut_apres_action VARCHAR(50),
    dossier_id BIGINT NOT NULL,
    expediteur_user_id BIGINT NOT NULL,
    destinataire_user_id BIGINT NOT NULL,
    service_expediteur_id BIGINT,
    service_destinataire_id BIGINT,
    CONSTRAINT fk_hist_dossier FOREIGN KEY (dossier_id) REFERENCES dossiers(id) ON DELETE CASCADE,
    CONSTRAINT fk_hist_exp_user FOREIGN KEY (expediteur_user_id) REFERENCES utilisateurs(id_utilisateur),
    CONSTRAINT fk_hist_dest_user FOREIGN KEY (destinataire_user_id) REFERENCES utilisateurs(id_utilisateur),
    CONSTRAINT fk_hist_exp_service FOREIGN KEY (service_expediteur_id) REFERENCES services(id_service) ON DELETE SET NULL,
    CONSTRAINT fk_hist_dest_service FOREIGN KEY (service_destinataire_id) REFERENCES services(id_service) ON DELETE SET NULL
);

-- 6. Table Notifications
CREATE TABLE notifications (
    id_notification BIGSERIAL PRIMARY KEY,
    service_destinataire_id BIGINT,
    message TEXT NOT NULL,
    date_envoi TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lue BOOLEAN NOT NULL DEFAULT FALSE,
    destinataire_id BIGINT NOT NULL,
    CONSTRAINT fk_notif_user FOREIGN KEY (destinataire_id) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE
);

-- Index pour optimiser la recherche de dossiers
CREATE INDEX idx_dossiers_numero ON dossiers(numero);
CREATE INDEX idx_dossiers_statut ON dossiers(statut);
CREATE INDEX idx_hist_dossier_id ON historique_transmissions(dossier_id);