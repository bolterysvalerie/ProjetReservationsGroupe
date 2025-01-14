ALTER TABLE artiste_type
    ADD COLUMN show_id BIGINT NOT NULL AFTER id;

ALTER TABLE artiste_type
    ADD CONSTRAINT fk_artiste_type_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;