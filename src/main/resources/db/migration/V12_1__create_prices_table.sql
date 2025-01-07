CREATE TABLE IF NOT EXISTS prices (
    id BIGINT NOT NULL AUTO_INCREMENT,
    end_date DATE,
    price FLOAT NOT NULL,
    start_date DATE,
    type VARCHAR(30),
    PRIMARY KEY (id)
);