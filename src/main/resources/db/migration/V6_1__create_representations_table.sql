















CREATE TABLE IF NOT EXISTS representations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    schedule TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE representations
    ADD COLUMN location_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER location_id;

ALTER TABLE representations
    ADD CONSTRAINT fk_representations_location_id FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;