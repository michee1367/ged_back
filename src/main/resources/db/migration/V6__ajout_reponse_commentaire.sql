CREATE TABLE IF NOT EXISTS reponses (
    id BIGSERIAL PRIMARY KEY,
    contenu TEXT NOT NULL,
    dossier_id BIGINT,
    CONSTRAINT fk_dossiers_reponses FOREIGN KEY (dossier_id) REFERENCES dossiers(id)
);

CREATE TABLE IF NOT EXISTS commentaires (
    id BIGSERIAL PRIMARY KEY,
    contenu TEXT NOT NULL,
    dossier_id BIGINT,
    contributeur_id BIGINT,
    CONSTRAINT fk_dossiers_commentaires FOREIGN KEY (dossier_id) REFERENCES dossiers(id),
    CONSTRAINT fk_contributeur_commentaires FOREIGN KEY (contributeur_id) REFERENCES utilisateurs(id_utilisateur)
)