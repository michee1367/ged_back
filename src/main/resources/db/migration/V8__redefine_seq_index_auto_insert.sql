SELECT setval(
    pg_get_serial_sequence('utilisateurs', 'id_utilisateur'), 
    COALESCE((SELECT MAX(id_utilisateur) FROM utilisateurs), 1)
);

SELECT setval(
    pg_get_serial_sequence('services', 'id_service'), 
    COALESCE((SELECT MAX(id_utilisateur) FROM utilisateurs), 1)
);