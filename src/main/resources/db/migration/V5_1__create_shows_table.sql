CREATE TABLE IF NOT EXISTS shows (
    id BIGINT NOT NULL AUTO_INCREMENT,
    bookable TINYINT(1),
    duration SMALLINT(5) UNSIGNED,
    created_in TIMESTAMP NULL DEFAULT NULL,
    slug VARCHAR(60) NOT NULL,
    poster_url VARCHAR(255),
    title VARCHAR(255),
    UNIQUE KEY show_slug_key (slug),
    PRIMARY KEY (id)
);

ALTER TABLE shows
    ADD COLUMN location_id BIGINT NOT NULL AFTER id;

ALTER TABLE shows
    ADD CONSTRAINT fk_shows_location FOREIGN KEY (location_id)
        REFERENCES locations (id) ON UPDATE CASCADE ON DELETE CASCADE;