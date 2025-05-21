CREATE TABLE IF NOT EXISTS troupes
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) ,
    logo_url  VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY name_unique_troupe (name)
    );

