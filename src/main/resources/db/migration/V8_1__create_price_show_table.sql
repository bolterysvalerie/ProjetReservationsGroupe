CREATE TABLE price_show (
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

ALTER TABLE price_show
    ADD COLUMN price_id BIGINT NOT NULL AFTER id,
    ADD COLUMN show_id BIGINT NOT NULL AFTER price_id;

ALTER TABLE price_show
    ADD CONSTRAINT fk_price_show_price FOREIGN KEY (price_id)
        REFERENCES prices (id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE price_show
    ADD CONSTRAINT fk_price_show_show FOREIGN KEY (show_id)
        REFERENCES shows (id) ON UPDATE CASCADE ON DELETE CASCADE;
