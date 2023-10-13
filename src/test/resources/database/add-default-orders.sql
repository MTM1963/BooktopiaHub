INSERT INTO USER (username, password) VALUES ('user1', '$2a$10$ajtJ7Q0bQgBmHxw5Zt84lupudTOyWROzQ9/LPep6vJ1ssAJ9JkgXS'), ('user2', '$2a$10$ajtJ7Q0bQgBmHxw5Zt84lupudTOyWROzQ9/LPep6vJ1ssAJ9JkgXS');


INSERT INTO ORDERS (user_id, total, status, order_date, shipping_address) VALUES
                                                                                  (1, 50.0, 1, '2023-10-11', 'Kyiv, Ukraine'),
                                                                                  (2, 60.0, 1, '2023-10-11', 'Lviv, Ukraine');
