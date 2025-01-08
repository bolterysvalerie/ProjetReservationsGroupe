CREATE TABLE IF NOT EXISTS artists (
    id BIGINT NOT NULL AUTO_INCREMENT,
    birthdate DATE,
    firstname VARCHAR(60),
    lastname VARCHAR(60),
    PRIMARY KEY (id)
);