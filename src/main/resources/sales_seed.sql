-- ============================================================
-- Sales seed data for jmarod_inventory
-- 10 sample sales for development/testing
-- NOTE: Does NOT decrement stock or create inventory movements
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- Sale 1: 2026-05-01 — 1x Aceite Yamalube 10w-40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-01 09:30:00', 49000.00, 'Venta semilla #1', NULL, '2026-05-01 09:30:00');
SET @s1 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s1, 1, 1, 49000.00, 49000.00);

-- Sale 2: 2026-05-02 — 2x Aceite Bajaj BGO 20w-50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-02 10:15:00', 76000.00, 'Venta semilla #2', NULL, '2026-05-02 10:15:00');
SET @s2 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s2, 2, 2, 38000.00, 76000.00);

-- Sale 3: 2026-05-03 — 1x Aceite Honda Semi-4t + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-03 11:00:00', 74000.00, 'Venta semilla #3', NULL, '2026-05-03 11:00:00');
SET @s3 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s3, 3, 1, 44000.00, 44000.00),
  (@s3, 11, 1, 30000.00, 30000.00);

-- Sale 4: 2026-05-05 — 2x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-05 14:20:00', 20000.00, 'Venta semilla #4', NULL, '2026-05-05 14:20:00');
SET @s4 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s4, 9, 2, 10000.00, 20000.00);

-- Sale 5: 2026-05-06 — 1x Aceite Mobil 10W40 + 1x Aceite Castrol 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-06 09:00:00', 86000.00, 'Venta semilla #5', NULL, '2026-05-06 09:00:00');
SET @s5 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s5, 12, 1, 42000.00, 42000.00),
  (@s5, 14, 1, 44000.00, 44000.00);

-- Sale 6: 2026-05-08 — 2x Aceite Yamalube + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-08 10:45:00', 114000.00, 'Venta semilla #6', NULL, '2026-05-08 10:45:00');
SET @s6 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s6, 1, 2, 49000.00, 98000.00),
  (@s6, 6, 1, 16000.00, 16000.00);

-- Sale 7: 2026-05-09 — 3x Descarrilador Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-09 15:30:00', 18000.00, 'Venta semilla #7', NULL, '2026-05-09 15:30:00');
SET @s7 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s7, 4, 3, 6000.00, 18000.00);

-- Sale 8: 2026-05-12 — 1x Aceite Honda Semi-4t + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-12 11:20:00', 54000.00, 'Venta semilla #8', NULL, '2026-05-12 11:20:00');
SET @s8 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s8, 3, 1, 44000.00, 44000.00),
  (@s8, 9, 1, 10000.00, 10000.00);

-- Sale 9: 2026-05-14 — 2x Aceite Mobil 20W50 + 1x Aceite Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-14 08:50:00', 102000.00, 'Venta semilla #9', NULL, '2026-05-14 08:50:00');
SET @s9 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s9, 11, 2, 30000.00, 60000.00),
  (@s9, 12, 1, 42000.00, 42000.00);

-- Sale 10: 2026-05-16 — 1x Aceite Castrol + 1x Aceite Yamalube + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-16 13:00:00', 103000.00, 'Venta semilla #10', NULL, '2026-05-16 13:00:00');
SET @s10 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s10, 14, 1, 44000.00, 44000.00),
  (@s10, 1, 1, 49000.00, 49000.00),
  (@s10, 9, 1, 10000.00, 10000.00);

SET FOREIGN_KEY_CHECKS = 1;
