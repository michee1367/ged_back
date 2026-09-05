-- 1. Services
INSERT INTO services (id_service, nom, code) VALUES
(1, 'Cabinet du Ministre', 'CAB'),
(2, 'Secrétariat Général', 'SG'),
(3, 'Direction des Ressources Humaines', 'DRH'),
(4, 'Direction Informatique', 'DIRINF');

-- 2. Utilisateurs
INSERT INTO utilisateurs (id_utilisateur, nom, post_nom, prenom, email, phone_number, mot_de_passe, role, actif, service_id) VALUES
(1, 'KABANGA', 'MUKENDI', 'Jean', 'j.kabanga@gouv.cd', '+243810000001', 'hash_pass_1', 'MINISTRE', true, 1),
(2, 'MUKENDI', 'TCHAMALA', 'Sarah', 's.mukendi@gouv.cd', '+243810000002', 'hash_pass_2', 'SECRETAIRE_GENERAL', true, 2),
(3, 'ILUNGA', 'KASSONGO', 'Patrick', 'p.ilunga@gouv.cd', '+243810000003', 'hash_pass_3', 'DIRECTEUR', true, 3),
(4, 'KASSONGO', 'MBUYI', 'Alain', 'a.kassongo@gouv.cd', '+243810000004', 'hash_pass_4', 'AGENT', true, 3);