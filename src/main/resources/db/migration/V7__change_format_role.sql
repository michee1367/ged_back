CREATE TABLE IF NOT EXISTS utilisateur_roles  (
    utilisateur_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_utilisateur_roles_utilisateurs FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id_utilisateur),
    CONSTRAINT pk_utlisateur PRIMARY KEY (utilisateur_id, role)
);

INSERT INTO utilisateur_roles (utilisateur_id, role)
SELECT id_utilisateur, COALESCE(role, 'AGENT') FROM utilisateurs;

ALTER TABLE utilisateurs DROP COLUMN role;

