-- ============================================================
-- Sales seed data for jmarod_inventory — batch 3
-- 50 sales for the last 7 days: 2026-06-17 to 2026-06-23
-- Products repeat frequently (aceites, pedales, descarrilador, etc.)
-- NOTE: Does NOT decrement stock or create inventory movements
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ─── 2026-06-17 (7 ventas) ────────────────────────────────────

-- Sale 61: 2026-06-17 — 2x Aceite Yamalube 10w-40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 08:45:00', 98000.00, 'Venta semilla #61', NULL, '2026-06-17 08:45:00');
SET @s61 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s61, 1, 2, 49000.00, 98000.00);

-- Sale 62: 2026-06-17 — 1x Aceite Bajaj BGO + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 09:30:00', 48000.00, 'Venta semilla #62', NULL, '2026-06-17 09:30:00');
SET @s62 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s62, 2, 1, 38000.00, 38000.00),
  (@s62, 9, 1, 10000.00, 10000.00);

-- Sale 63: 2026-06-17 — 2x Aceite Castrol 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 10:15:00', 88000.00, 'Venta semilla #63', NULL, '2026-06-17 10:15:00');
SET @s63 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s63, 14, 2, 44000.00, 88000.00);

-- Sale 64: 2026-06-17 — 1x Aceite Honda Semi-4t + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 11:00:00', 74000.00, 'Venta semilla #64', NULL, '2026-06-17 11:00:00');
SET @s64 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s64, 3, 1, 44000.00, 44000.00),
  (@s64, 11, 1, 30000.00, 30000.00);

-- Sale 65: 2026-06-17 — 3x Pedales + 2x Descarrilador Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 12:20:00', 42000.00, 'Venta semilla #65', NULL, '2026-06-17 12:20:00');
SET @s65 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s65, 9, 3, 10000.00, 30000.00),
  (@s65, 4, 2, 6000.00, 12000.00);

-- Sale 66: 2026-06-17 — 1x Yamalube + 1x Bajaj + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 14:00:00', 131000.00, 'Venta semilla #66', NULL, '2026-06-17 14:00:00');
SET @s66 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s66, 1, 1, 49000.00, 49000.00),
  (@s66, 2, 1, 38000.00, 38000.00),
  (@s66, 3, 1, 44000.00, 44000.00);

-- Sale 67: 2026-06-17 — 1x Aceite Mobil 10W40 + 1x Aceite Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-17 16:10:00', 86000.00, 'Venta semilla #67', NULL, '2026-06-17 16:10:00');
SET @s67 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s67, 12, 1, 42000.00, 42000.00),
  (@s67, 14, 1, 44000.00, 44000.00);

-- ─── 2026-06-18 (7 ventas) ────────────────────────────────────

-- Sale 68: 2026-06-18 — 2x Bajaj + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 08:30:00', 86000.00, 'Venta semilla #68', NULL, '2026-06-18 08:30:00');
SET @s68 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s68, 2, 2, 38000.00, 76000.00),
  (@s68, 9, 1, 10000.00, 10000.00);

-- Sale 69: 2026-06-18 — 1x Yamalube + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 09:15:00', 79000.00, 'Venta semilla #69', NULL, '2026-06-18 09:15:00');
SET @s69 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s69, 1, 1, 49000.00, 49000.00),
  (@s69, 11, 1, 30000.00, 30000.00);

-- Sale 70: 2026-06-18 — 3x Aceite Honda Semi-4t
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 10:45:00', 132000.00, 'Venta semilla #70', NULL, '2026-06-18 10:45:00');
SET @s70 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s70, 3, 3, 44000.00, 132000.00);

-- Sale 71: 2026-06-18 — 1x Castrol + 2x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 11:30:00', 56000.00, 'Venta semilla #71', NULL, '2026-06-18 11:30:00');
SET @s71 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s71, 14, 1, 44000.00, 44000.00),
  (@s71, 4, 2, 6000.00, 12000.00);

-- Sale 72: 2026-06-18 — 4x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 13:00:00', 40000.00, 'Venta semilla #72', NULL, '2026-06-18 13:00:00');
SET @s72 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s72, 9, 4, 10000.00, 40000.00);

-- Sale 73: 2026-06-18 — 2x Yamalube + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 14:30:00', 142000.00, 'Venta semilla #73', NULL, '2026-06-18 14:30:00');
SET @s73 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s73, 1, 2, 49000.00, 98000.00),
  (@s73, 14, 1, 44000.00, 44000.00);

-- Sale 74: 2026-06-18 — 1x Aceite Mobil 10W40 + 1x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-18 15:45:00', 80000.00, 'Venta semilla #74', NULL, '2026-06-18 15:45:00');
SET @s74 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s74, 12, 1, 42000.00, 42000.00),
  (@s74, 2, 1, 38000.00, 38000.00);

-- ─── 2026-06-19 (7 ventas) ────────────────────────────────────

-- Sale 75: 2026-06-19 — 1x Yamalube + 2x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 08:50:00', 125000.00, 'Venta semilla #75', NULL, '2026-06-19 08:50:00');
SET @s75 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s75, 1, 1, 49000.00, 49000.00),
  (@s75, 2, 2, 38000.00, 76000.00);

-- Sale 76: 2026-06-19 — 3x Pedales + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 09:40:00', 46000.00, 'Venta semilla #76', NULL, '2026-06-19 09:40:00');
SET @s76 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s76, 9, 3, 10000.00, 30000.00),
  (@s76, 6, 1, 16000.00, 16000.00);

-- Sale 77: 2026-06-19 — 2x Castrol + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 10:30:00', 132000.00, 'Venta semilla #77', NULL, '2026-06-19 10:30:00');
SET @s77 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s77, 14, 2, 44000.00, 88000.00),
  (@s77, 3, 1, 44000.00, 44000.00);

-- Sale 78: 2026-06-19 — 1x Aceite Mobil 10W30 + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 11:20:00', 72000.00, 'Venta semilla #78', NULL, '2026-06-19 11:20:00');
SET @s78 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s78, 13, 1, 42000.00, 42000.00),
  (@s78, 11, 1, 30000.00, 30000.00);

-- Sale 79: 2026-06-19 — 5x Cuña + 3x Tuerca Trasera 3/8
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 12:00:00', 8000.00, 'Venta semilla #79', NULL, '2026-06-19 12:00:00');
SET @s79 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s79, 5, 5, 1000.00, 5000.00),
  (@s79, 7, 3, 1000.00, 3000.00);

-- Sale 80: 2026-06-19 — 1x Yamalube + 1x Castrol + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 14:15:00', 137000.00, 'Venta semilla #80', NULL, '2026-06-19 14:15:00');
SET @s80 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s80, 1, 1, 49000.00, 49000.00),
  (@s80, 14, 1, 44000.00, 44000.00),
  (@s80, 3, 1, 44000.00, 44000.00);

-- Sale 81: 2026-06-19 — 2x Aceite Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-19 16:00:00', 84000.00, 'Venta semilla #81', NULL, '2026-06-19 16:00:00');
SET @s81 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s81, 12, 2, 42000.00, 84000.00);

-- ─── 2026-06-20 (7 ventas) ────────────────────────────────────

-- Sale 82: 2026-06-20 — 1x Honda + 1x Pedales + 1x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 08:20:00', 60000.00, 'Venta semilla #82', NULL, '2026-06-20 08:20:00');
SET @s82 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s82, 3, 1, 44000.00, 44000.00),
  (@s82, 9, 1, 10000.00, 10000.00),
  (@s82, 4, 1, 6000.00, 6000.00);

-- Sale 83: 2026-06-20 — 2x Yamalube + 2x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 09:10:00', 174000.00, 'Venta semilla #83', NULL, '2026-06-20 09:10:00');
SET @s83 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s83, 1, 2, 49000.00, 98000.00),
  (@s83, 2, 2, 38000.00, 76000.00);

-- Sale 84: 2026-06-20 — 1x Castrol + 1x Mobil 20W50 + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 10:30:00', 84000.00, 'Venta semilla #84', NULL, '2026-06-20 10:30:00');
SET @s84 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s84, 14, 1, 44000.00, 44000.00),
  (@s84, 11, 1, 30000.00, 30000.00),
  (@s84, 9, 1, 10000.00, 10000.00);

-- Sale 85: 2026-06-20 — 3x Descarrilador + 2x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 11:45:00', 50000.00, 'Venta semilla #85', NULL, '2026-06-20 11:45:00');
SET @s85 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s85, 4, 3, 6000.00, 18000.00),
  (@s85, 6, 2, 16000.00, 32000.00);

-- Sale 86: 2026-06-20 — 1x Bajaj + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 13:00:00', 82000.00, 'Venta semilla #86', NULL, '2026-06-20 13:00:00');
SET @s86 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s86, 2, 1, 38000.00, 38000.00),
  (@s86, 3, 1, 44000.00, 44000.00);

-- Sale 87: 2026-06-20 — 3x Pedales + 1x Yamalube
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 14:20:00', 79000.00, 'Venta semilla #87', NULL, '2026-06-20 14:20:00');
SET @s87 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s87, 9, 3, 10000.00, 30000.00),
  (@s87, 1, 1, 49000.00, 49000.00);

-- Sale 88: 2026-06-20 — 2x Castrol + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-20 15:30:00', 130000.00, 'Venta semilla #88', NULL, '2026-06-20 15:30:00');
SET @s88 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s88, 14, 2, 44000.00, 88000.00),
  (@s88, 12, 1, 42000.00, 42000.00);

-- ─── 2026-06-21 (7 ventas) ────────────────────────────────────

-- Sale 89: 2026-06-21 — 1x Yamalube + 1x Mobil 20W50 + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 08:40:00', 123000.00, 'Venta semilla #89', NULL, '2026-06-21 08:40:00');
SET @s89 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s89, 1, 1, 49000.00, 49000.00),
  (@s89, 11, 1, 30000.00, 30000.00),
  (@s89, 14, 1, 44000.00, 44000.00);

-- Sale 90: 2026-06-21 — 2x Bajaj + 3x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 09:30:00', 106000.00, 'Venta semilla #90', NULL, '2026-06-21 09:30:00');
SET @s90 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s90, 2, 2, 38000.00, 76000.00),
  (@s90, 9, 3, 10000.00, 30000.00);

-- Sale 91: 2026-06-21 — 1x Honda + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 10:50:00', 86000.00, 'Venta semilla #91', NULL, '2026-06-21 10:50:00');
SET @s91 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s91, 3, 1, 44000.00, 44000.00),
  (@s91, 12, 1, 42000.00, 42000.00);

-- Sale 92: 2026-06-21 — 4x Pedales + 2x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 11:40:00', 52000.00, 'Venta semilla #92', NULL, '2026-06-21 11:40:00');
SET @s92 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s92, 9, 4, 10000.00, 40000.00),
  (@s92, 4, 2, 6000.00, 12000.00);

-- Sale 93: 2026-06-21 — 3x Aceite Yamalube
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 13:15:00', 147000.00, 'Venta semilla #93', NULL, '2026-06-21 13:15:00');
SET @s93 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s93, 1, 3, 49000.00, 147000.00);

-- Sale 94: 2026-06-21 — 1x Castrol + 1x Bajaj + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 14:00:00', 98000.00, 'Venta semilla #94', NULL, '2026-06-21 14:00:00');
SET @s94 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s94, 14, 1, 44000.00, 44000.00),
  (@s94, 2, 1, 38000.00, 38000.00),
  (@s94, 6, 1, 16000.00, 16000.00);

-- Sale 95: 2026-06-21 — 2x Aceite Mobil 10W30 + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-21 15:50:00', 128000.00, 'Venta semilla #95', NULL, '2026-06-21 15:50:00');
SET @s95 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s95, 13, 2, 42000.00, 84000.00),
  (@s95, 3, 1, 44000.00, 44000.00);

-- ─── 2026-06-22 (8 ventas) ────────────────────────────────────

-- Sale 96: 2026-06-22 — 1x Yamalube + 2x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 08:10:00', 137000.00, 'Venta semilla #96', NULL, '2026-06-22 08:10:00');
SET @s96 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s96, 1, 1, 49000.00, 49000.00),
  (@s96, 3, 2, 44000.00, 88000.00);

-- Sale 97: 2026-06-22 — 3x Pedales + 3x Cuña
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 09:00:00', 33000.00, 'Venta semilla #97', NULL, '2026-06-22 09:00:00');
SET @s97 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s97, 9, 3, 10000.00, 30000.00),
  (@s97, 5, 3, 1000.00, 3000.00);

-- Sale 98: 2026-06-22 — 2x Bajaj + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 10:20:00', 120000.00, 'Venta semilla #98', NULL, '2026-06-22 10:20:00');
SET @s98 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s98, 2, 2, 38000.00, 76000.00),
  (@s98, 14, 1, 44000.00, 44000.00);

-- Sale 99: 2026-06-22 — 1x Aceite Mobil 10W40 + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 11:10:00', 72000.00, 'Venta semilla #99', NULL, '2026-06-22 11:10:00');
SET @s99 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s99, 12, 1, 42000.00, 42000.00),
  (@s99, 11, 1, 30000.00, 30000.00);

-- Sale 100: 2026-06-22 — 1x Honda + 1x Yamalube + 1x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 12:00:00', 131000.00, 'Venta semilla #100', NULL, '2026-06-22 12:00:00');
SET @s100 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s100, 3, 1, 44000.00, 44000.00),
  (@s100, 1, 1, 49000.00, 49000.00),
  (@s100, 2, 1, 38000.00, 38000.00);

-- Sale 101: 2026-06-22 — 5x Descarrilador Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 13:30:00', 30000.00, 'Venta semilla #101', NULL, '2026-06-22 13:30:00');
SET @s101 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s101, 4, 5, 6000.00, 30000.00);

-- Sale 102: 2026-06-22 — 2x Castrol + 2x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 14:45:00', 108000.00, 'Venta semilla #102', NULL, '2026-06-22 14:45:00');
SET @s102 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s102, 14, 2, 44000.00, 88000.00),
  (@s102, 9, 2, 10000.00, 20000.00);

-- Sale 103: 2026-06-22 — 1x Aceite Mobil 10W30 + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-22 16:00:00', 58000.00, 'Venta semilla #103', NULL, '2026-06-22 16:00:00');
SET @s103 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s103, 13, 1, 42000.00, 42000.00),
  (@s103, 6, 1, 16000.00, 16000.00);

-- ─── 2026-06-23 (7 ventas) ────────────────────────────────────

-- Sale 104: 2026-06-23 — 2x Yamalube + 1x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 08:30:00', 136000.00, 'Venta semilla #104', NULL, '2026-06-23 08:30:00');
SET @s104 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s104, 1, 2, 49000.00, 98000.00),
  (@s104, 2, 1, 38000.00, 38000.00);

-- Sale 105: 2026-06-23 — 1x Castrol + 3x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 09:20:00', 74000.00, 'Venta semilla #105', NULL, '2026-06-23 09:20:00');
SET @s105 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s105, 14, 1, 44000.00, 44000.00),
  (@s105, 9, 3, 10000.00, 30000.00);

-- Sale 106: 2026-06-23 — 1x Honda + 1x Mobil 20W50 + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 10:15:00', 116000.00, 'Venta semilla #106', NULL, '2026-06-23 10:15:00');
SET @s106 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s106, 3, 1, 44000.00, 44000.00),
  (@s106, 11, 1, 30000.00, 30000.00),
  (@s106, 12, 1, 42000.00, 42000.00);

-- Sale 107: 2026-06-23 — 4x Descarrilador + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 11:00:00', 40000.00, 'Venta semilla #107', NULL, '2026-06-23 11:00:00');
SET @s107 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s107, 4, 4, 6000.00, 24000.00),
  (@s107, 6, 1, 16000.00, 16000.00);

-- Sale 108: 2026-06-23 — 1x Bajaj + 2x Cuña
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 12:30:00', 40000.00, 'Venta semilla #108', NULL, '2026-06-23 12:30:00');
SET @s108 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s108, 2, 1, 38000.00, 38000.00),
  (@s108, 5, 2, 1000.00, 2000.00);

-- Sale 109: 2026-06-23 — 3x Yamalube + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 14:00:00', 191000.00, 'Venta semilla #109', NULL, '2026-06-23 14:00:00');
SET @s109 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s109, 1, 3, 49000.00, 147000.00),
  (@s109, 14, 1, 44000.00, 44000.00);

-- Sale 110: 2026-06-23 — 1x Aceite Mobil 10W30 + 2x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-06-23 15:10:00', 62000.00, 'Venta semilla #110', NULL, '2026-06-23 15:10:00');
SET @s110 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s110, 13, 1, 42000.00, 42000.00),
  (@s110, 9, 2, 10000.00, 20000.00);

SET FOREIGN_KEY_CHECKS = 1;
