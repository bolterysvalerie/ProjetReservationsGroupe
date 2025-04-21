# CREATE TABLE tag (
#                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                      tag VARCHAR(30) NOT NULL,
#                      UNIQUE (tag)
# );

CREATE TABLE tag (
                     id   BIGINT          NOT NULL AUTO_INCREMENT,
                     tag  VARCHAR(30)     NOT NULL,
                     PRIMARY KEY (id),
                     UNIQUE KEY uk_tag_tag (tag)
) ;


#     ENGINE=InnoDB
#   DEFAULT CHARSET=utf8mb4
#   COLLATE=utf8mb4_general_ci;

