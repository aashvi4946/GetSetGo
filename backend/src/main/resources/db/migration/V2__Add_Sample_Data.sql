-- V2__Add_Sample_Data.sql

-- INSERT INTO users (id, first_name, last_name, email, password)
-- VALUES (99, 'Admin', 'User', 'admin@example.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqR2e5dOKJhakzF9wVJ9vNsVsc8S')
-- ON CONFLICT (email) DO NOTHING;

-- -- Assumes role id for ADMIN is 2
-- INSERT INTO user_roles (user_id, role_id)
-- SELECT 99, r.id FROM roles r WHERE r.name = 'ROLE_ADMIN'
-- ON CONFLICT (user_id, role_id) DO NOTHING;


-- INSERT INTO users (id, first_name, last_name, email, password)
-- VALUES (100, 'John', 'Doe', 'john.doe@example.com', '$2a$10$l.V.8.p3a3d5e6f7g8h9i0j1k2l3m4n5o6p7q8r9s0t1u2')
-- ON CONFLICT (email) DO NOTHING;

-- -- Assumes role id for USER is 1
-- INSERT INTO user_roles (user_id, role_id)
-- SELECT 100, r.id FROM roles r WHERE r.name = 'ROLE_USER'
-- ON CONFLICT (user_id, role_id) DO NOTHING;

INSERT INTO tours (name, description, destination, start_date, end_date, price, max_capacity)
VALUES
('Parisian Dream', 'Explore the romantic city of Paris, visit the Eiffel Tower, Louvre Museum, and enjoy a Seine river cruise.', 'Paris, France', '2025-10-15', '2025-10-22', 1999.99, 20),
('Ancient Rome Discovery', 'Walk through history in Rome. Visit the Colosseum, Roman Forum, and Vatican City.', 'Rome, Italy', '2025-11-05', '2025-11-12', 1750.00, 25),
('Tokyo Neon Nights', 'Experience the vibrant culture of Tokyo, from ancient temples to the bustling streets of Shibuya.', 'Tokyo, Japan', '2026-04-10', '2026-04-18', 2500.50, 15)
ON CONFLICT (name) DO NOTHING;