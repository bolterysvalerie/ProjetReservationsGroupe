# CREATE TABLE show_tag (
#                           show_id BIGINT NOT NULL,
#                           tag_id  BIGINT NOT NULL,
#                           PRIMARY KEY (show_id, tag_id),
#                           CONSTRAINT fk_show_tag_show
#                               FOREIGN KEY (show_id) REFERENCES shows(id)
#                                   ON UPDATE CASCADE ON DELETE RESTRICT,
#                           CONSTRAINT fk_show_tag_tag
#                               FOREIGN KEY (tag_id)  REFERENCES tag(id)
#                                   ON UPDATE CASCADE ON DELETE RESTRICT
# );

CREATE TABLE show_tag (
                          show_id BIGINT NOT NULL,
                          tag_id  BIGINT NOT NULL,
                          PRIMARY KEY (show_id, tag_id),
                          CONSTRAINT fk_show_tag_show
                              FOREIGN KEY (show_id) REFERENCES shows(id)
                                  ON UPDATE CASCADE
                                  ON DELETE RESTRICT,
                          CONSTRAINT fk_show_tag_tag
                              FOREIGN KEY (tag_id)  REFERENCES tag(id)
                                  ON UPDATE CASCADE
                                  ON DELETE RESTRICT
) ;

#     ENGINE=InnoDB
#   DEFAULT CHARSET=utf8mb4
#   COLLATE=utf8mb4_general_ci;