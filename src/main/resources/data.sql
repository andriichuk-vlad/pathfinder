INSERT INTO users (name, email, role) VALUES
('Shepard', 'shepard@example.com', 'DRIVER');
INSERT INTO users (name, email, role) VALUES
('Garrus', 'garrus@example.com', 'DRIVER');
INSERT INTO users (name, email, role) VALUES
('Liara', 'liara@example.com', 'PASSENGER');
INSERT INTO users (name, email, role) VALUES
('Tali', 'tali@example.com', 'PASSENGER');

INSERT INTO rides (from_city, to_city, departure_time, total_seats, available_seats, price, driver_id, vehicle_name) VALUES
('Poznan', 'Warsaw', '2026-12-01 10:00:00', 5, 4, 50.00, 1, 'M35 Mako');
INSERT INTO rides (from_city, to_city, departure_time, total_seats, available_seats, price, driver_id, vehicle_name) VALUES
('Gdansk', 'Wroclaw', '2026-12-02 14:00:00', 3, 3, 75.00, 2, 'M35 Mako');

INSERT INTO ride_intermediate_cities (ride_id, intermediate_cities) VALUES
(1, 'Konin'),
(1, 'Lodz'),
(2, 'Bydgoszcz'),
(2, 'Poznan');


