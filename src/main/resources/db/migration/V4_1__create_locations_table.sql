CREATE TABLE IF NOT EXISTS locations
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    phone       VARCHAR(30),
    designation VARCHAR(60),
    slug        VARCHAR(60) NOT NULL,
    address     VARCHAR(255),
    website     VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY locations_slug_key (slug)
);

ALTER TABLE locations
    ADD COLUMN locality_id BIGINT NOT NULL AFTER id;

# ALTER TABLE locations
#     ADD CONSTRAINT locations_localities UNIQUE (locality_id);

-- Ajout de la clé étrangère vers localities
ALTER TABLE locations
    ADD CONSTRAINT fk_locations_localities FOREIGN KEY (locality_id)
        REFERENCES localities(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE;

# ALTER TABLE locations
#     ADD CONSTRAINT locations_id UNIQUE (id);