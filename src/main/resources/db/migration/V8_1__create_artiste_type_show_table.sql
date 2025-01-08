CREATE TABLE IF NOT EXISTS artiste_type_show (
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE artiste_type_show
    ADD COLUMN artiste_type_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER artiste_type_id;

ALTER TABLE artiste_type_show
    ADD CONSTRAINT fk_artiste_type_show_artist_type FOREIGN KEY (artiste_type_id)
        REFERENCES artiste_type (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE artiste_type_show
    ADD CONSTRAINT fk_artiste_type_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;