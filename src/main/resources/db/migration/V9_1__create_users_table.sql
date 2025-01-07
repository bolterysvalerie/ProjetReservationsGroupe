CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    language VARCHAR(2),
    login VARCHAR(30) NOT NULL,
    firstname VARCHAR(60) NOT NULL,
    lastname VARCHAR(60) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role enum('ADMIN','AFFILIATE','MEMBER','PRESS','PRODUCER') DEFAULT NULL,
    created_at datetime NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY users_login_key (login)
    );


