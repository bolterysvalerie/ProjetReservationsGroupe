CREATE TABLE show_tags (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           show_id BIGINT NOT NULL,  -- Clé étrangère vers SHOWS
                           tag_id BIGINT NOT NULL,   -- Clé étrangère vers TAGS
                           created_at DATETIME NOT NULL DEFAULT NOW(),

    -- Définition des clés étrangères
                           CONSTRAINT fk_show FOREIGN KEY (show_id) REFERENCES shows (id) ON DELETE CASCADE,
                           CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
) CHARSET=utf8;