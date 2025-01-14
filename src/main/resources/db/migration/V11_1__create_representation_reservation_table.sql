CREATE TABLE IF NOT EXISTS representation_reservations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quantity TINYINT(4),
    PRIMARY KEY (id)
);

ALTER TABLE representation_reservations
    ADD COLUMN price_id BIGINT NOT NULL AFTER id,
    ADD COLUMN representation_id BIGINT NOT NULL AFTER price_id,
    ADD COLUMN reservation_id BIGINT NOT NULL AFTER representation_id;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_reservation_representation FOREIGN KEY (representation_id)
        REFERENCES representations (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_reservation FOREIGN KEY (reservation_id)
        REFERENCES reservations (id) ON UPDATE CASCADE ON DELETE CASCADE;