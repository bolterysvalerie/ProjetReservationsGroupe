CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    booking_date TIMESTAMP NULL DEFAULT NULL,
    status VARCHAR(60),
    PRIMARY KEY (id)
);

ALTER TABLE reservations
    ADD COLUMN user_id BIGINT NOT NULL AFTER id;

ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;