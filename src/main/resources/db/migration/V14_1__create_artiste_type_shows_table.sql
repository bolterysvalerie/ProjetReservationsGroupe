CREATE TABLE IF NOT EXISTS artiste_type_shows
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

ALTER TABLE artiste_type_shows
    ADD COLUMN artiste_type_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER artiste_type_id;

ALTER TABLE artiste_type_shows
    ADD CONSTRAINT fk_artiste_type_artiste_type FOREIGN KEY (artiste_type_id)
        REFERENCES artiste_types (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_type_shows
    ADD CONSTRAINT fk_artiste_type_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;
