# CREATE TABLE IF NOT EXISTS artists
# (
#     id        BIGINT NOT NULL AUTO_INCREMENT,
#     firstname VARCHAR(60),
#     lastname  VARCHAR(60),
#     PRIMARY KEY (id)
# );

CREATE TABLE artists (
                         id         BIGINT NOT NULL AUTO_INCREMENT,
                         firstname  VARCHAR(60),
                         lastname   VARCHAR(60),
                         troupe_id  BIGINT           NULL,   -- ← la nouvelle colonne
                         PRIMARY KEY (id),
                         CONSTRAINT fk_artists_troupe
                             FOREIGN KEY (troupe_id)
                                 REFERENCES troupes(id)
                                 ON UPDATE CASCADE
                                 ON DELETE RESTRICT
);