INSERT INTO `reservations` (`id`, `booking_date`, `user_id`, `status`,`representation_id`)
VALUES
    (1, '2025-01-06 10:00:00', 1, 'Confirmed',1),
    (2, '2025-01-06 11:00:00', 2, 'Pending',1),
    (3, '2025-01-06 12:00:00', 1, 'Cancelled',2),
    (4, '2025-01-06 13:00:00', 2, 'Confirmed',2),
    (5, '2025-01-06 14:00:00', 1, 'Pending',3),
    (6, '2025-01-06 15:00:00', 1, 'Confirmed',3),
    (7, '2025-01-06 16:00:00', 1, 'Cancelled',3),
    (8, '2025-01-06 17:00:00', 2, 'Pending',4),
    (9, '2025-01-06 18:00:00', 2, 'Confirmed',4),
    (10, '2025-01-06 19:00:00', 1, 'Pending',4);