CREATE TABLE IF NOT EXISTS troupes
(
    id          BIGINT NOT NULL AUTO_INCREMENT, -- Clé primaire
    name        VARCHAR(255) NOT NULL,
    logo_url   VARCHAR(1024),
    PRIMARY KEY (id) -- Ajout de la clé primaire
) CHARSET=utf8;

