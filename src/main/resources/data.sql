-- Seed admin user if not already present
-- Password: 1234   (bcrypt hash)
INSERT INTO users (username, password, created_at)
SELECT 'admin',
       '$2a$12$86c18JzkbvgnWySGFSkscu2WZ527to0OCCJMgJbhOSUBRhAt.j232',
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');
