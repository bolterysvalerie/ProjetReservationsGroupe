INSERT INTO `users`(`id`, `language`, `login`, `firstname`, `lastname`, `email`, `password`, `role`, `created_at`)
VALUES (1, 'fr', 'bob', 'Bob', 'Sull', 'bob@sull.com','12345678','admin',NOW()),
       (2, 'en', 'anna', 'Anna', 'Lyse', 'anna.lyse@sull.com','12345678','member',NOW());