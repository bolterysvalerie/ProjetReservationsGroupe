CREATE TABLE IF NOT EXISTS artists
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(60),
    lastname  VARCHAR(60),
    PRIMARY KEY (id)
);

ALTER TABLE artists
    ADD COLUMN troupe_id BIGINT NOT NULL AFTER lastname;

ALTER TABLE artists
    ADD CONSTRAINT fk_artist_troupe FOREIGN KEY (troupe_id)
        REFERENCES troupes (id) ON UPDATE CASCADE ON DELETE CASCADE;