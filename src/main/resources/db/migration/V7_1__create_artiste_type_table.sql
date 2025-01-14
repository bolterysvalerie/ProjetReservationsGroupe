CREATE TABLE IF NOT EXISTS artiste_types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

ALTER TABLE artiste_types
    ADD COLUMN artist_id BIGINT NOT NULL AFTER id,
    ADD COLUMN type_id BIGINT NOT NULL AFTER artist_id;

ALTER TABLE artiste_types
    ADD CONSTRAINT fk_artiste_type_type FOREIGN KEY (type_id)
        REFERENCES types (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_types
    ADD CONSTRAINT fk_artiste_type_artist FOREIGN KEY (artist_id)
        REFERENCES artists (id) ON UPDATE CASCADE ON DELETE CASCADE;