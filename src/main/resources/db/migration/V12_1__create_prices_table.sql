CREATE TABLE IF NOT EXISTS prices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    end_date DATE,
    price FLOAT NOT NULL,
    start_date DATE,
    type VARCHAR(30),
    PRIMARY KEY (id)
);

ALTER TABLE price_shows
    ADD CONSTRAINT fk_price_show_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE price_shows
    ADD CONSTRAINT fk_price_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE representation_reservations
    ADD CONSTRAINT fk_representation_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;
