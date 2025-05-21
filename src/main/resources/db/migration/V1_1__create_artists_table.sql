CREATE TABLE IF NOT EXISTS artists
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(60),
    lastname  VARCHAR(60),
    troupe_id   BIGINT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_troupe FOREIGN KEY (troupe_id) REFERENCES troupes(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

