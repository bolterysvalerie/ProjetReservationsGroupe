
CREATE TABLE IF NOT EXISTS troupes (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       name VARCHAR(60) NOT NULL UNIQUE,
                                       logo_url VARCHAR(255),
                                       PRIMARY KEY (id)
);

# -- ajout de la colonne troupe_id et de la FK
# ALTER TABLE artists
#     ADD COLUMN troupe_id BIGINT NULL,
#     ADD CONSTRAINT fk_artists_troupe
#         FOREIGN KEY (troupe_id)
#             REFERENCES troupes(id)
#             ON UPDATE CASCADE
#             ON DELETE RESTRICT;