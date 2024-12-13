-- Table: artists
CREATE TABLE IF NOT EXISTS artists (
    id BIGINT NOT NULL AUTO_INCREMENT,
    birthdate DATE,
    firstname VARCHAR(60),
    lastname VARCHAR(60),
    PRIMARY KEY (id)
);

-- Table: localities
CREATE TABLE IF NOT EXISTS localities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    postal_code VARCHAR(6),
    locality VARCHAR(60),
    PRIMARY KEY (id)
);

-- Table: prices
CREATE TABLE IF NOT EXISTS prices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    end_date DATE,
    price FLOAT NOT NULL,
    start_date DATE,
    type VARCHAR(30),
    PRIMARY KEY (id)
);

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role VARCHAR(30),
    PRIMARY KEY (id)
);

-- Table: types
CREATE TABLE IF NOT EXISTS types (
    id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(60),
    PRIMARY KEY (id)
);

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    language VARCHAR(2),
    login VARCHAR(30) NOT NULL,
    firstname VARCHAR(60) NOT NULL,
    lastname VARCHAR(60) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY users_login_key (login)
);