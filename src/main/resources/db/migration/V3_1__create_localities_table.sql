CREATE TABLE IF NOT EXISTS localities (
    id BIGINT NOT NULL AUTO_INCREMENT,
    postal_code VARCHAR(6),
    locality VARCHAR(60),
    PRIMARY KEY (id)
);