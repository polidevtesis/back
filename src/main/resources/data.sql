-- Seed admin user if not already present
-- Password: polidevtesis2034*   (bcrypt hash)
INSERT INTO users (username, password, created_at)
SELECT 'admin',
       '$2a$12$7QJ8n3p6V5mK2wL9rT4eXeGHkNqZ1sYdA8bC3fE0jM5uP7vW6xI.G',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
