-- Table: artiste_type
CREATE TABLE IF NOT EXISTS artiste_type (
    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

-- Table: artiste_type_show
CREATE TABLE IF NOT EXISTS artiste_type_show (
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Table: locations
CREATE TABLE IF NOT EXISTS locations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    phone VARCHAR(30),
    designation VARCHAR(60),
    slug VARCHAR(60) NOT NULL,
    address VARCHAR(255),
    website VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY locations_slug_key (slug)
);

-- Table: representation_reservation
CREATE TABLE IF NOT EXISTS representation_reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quantity TINYINT(4),
    PRIMARY KEY (id)
);

-- Table: representations
CREATE TABLE IF NOT EXISTS representations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    schedule TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

-- Table: reservations
CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    booking_date TIMESTAMP NULL DEFAULT NULL,
    status VARCHAR(60),
    PRIMARY KEY (id)
);

-- Table: reviews
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    stars SMALLINT,
    validated BOOLEAN,
    create_at TIMESTAMP NULL DEFAULT NULL,
    update_ad TIMESTAMP NULL DEFAULT NULL,
    review TEXT,
    PRIMARY KEY (id)
);

-- Table: role_user
CREATE TABLE IF NOT EXISTS role_user (
    id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Table: shows
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
