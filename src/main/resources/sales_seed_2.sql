-- ============================================================
-- Sales seed data for jmarod_inventory — batch 2
-- 50 sample sales spread across March–May 2026
-- NOTE: Does NOT decrement stock or create inventory movements
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- Sale 11: 2026-03-03 — 2x Yamalube + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-03 09:10:00', 142000.00, 'Venta semilla #11', NULL, '2026-03-03 09:10:00');
SET @s11 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s11, 1, 2, 49000.00, 98000.00),
  (@s11, 14, 1, 44000.00, 44000.00);

-- Sale 12: 2026-03-04 — 3x Aceite Bajaj BGO
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-04 10:30:00', 114000.00, 'Venta semilla #12', NULL, '2026-03-04 10:30:00');
SET @s12 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s12, 2, 3, 38000.00, 114000.00);

-- Sale 13: 2026-03-05 — 1x Honda Semi-4t + 2x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-05 11:45:00', 64000.00, 'Venta semilla #13', NULL, '2026-03-05 11:45:00');
SET @s13 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s13, 3, 1, 44000.00, 44000.00),
  (@s13, 9, 2, 10000.00, 20000.00);

-- Sale 14: 2026-03-06 — 5x Tuerca Trasera 3/8
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-06 08:20:00', 5000.00, 'Venta semilla #14', NULL, '2026-03-06 08:20:00');
SET @s14 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s14, 7, 5, 1000.00, 5000.00);

-- Sale 15: 2026-03-09 — 1x Yamalube + 1x Aceite Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-09 09:00:00', 79000.00, 'Venta semilla #15', NULL, '2026-03-09 09:00:00');
SET @s15 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s15, 1, 1, 49000.00, 49000.00),
  (@s15, 11, 1, 30000.00, 30000.00);

-- Sale 16: 2026-03-10 — 2x Aceite Castrol 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-10 14:00:00', 88000.00, 'Venta semilla #16', NULL, '2026-03-10 14:00:00');
SET @s16 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s16, 14, 2, 44000.00, 88000.00);

-- Sale 17: 2026-03-11 — 1x Honda Semi-4t + 1x Aceite Bajaj BGO
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-11 10:15:00', 82000.00, 'Venta semilla #17', NULL, '2026-03-11 10:15:00');
SET @s17 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s17, 3, 1, 44000.00, 44000.00),
  (@s17, 2, 1, 38000.00, 38000.00);

-- Sale 18: 2026-03-12 — 3x Descarrilador + 2x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-12 11:30:00', 50000.00, 'Venta semilla #18', NULL, '2026-03-12 11:30:00');
SET @s18 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s18, 4, 3, 6000.00, 18000.00),
  (@s18, 6, 2, 16000.00, 32000.00);

-- Sale 19: 2026-03-13 — 4x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-13 15:00:00', 40000.00, 'Venta semilla #19', NULL, '2026-03-13 15:00:00');
SET @s19 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s19, 9, 4, 10000.00, 40000.00);

-- Sale 20: 2026-03-16 — 1x Mobil 10W40 + 1x Castrol + 1x Yamalube
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-16 09:45:00', 135000.00, 'Venta semilla #20', NULL, '2026-03-16 09:45:00');
SET @s20 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s20, 12, 1, 42000.00, 42000.00),
  (@s20, 14, 1, 44000.00, 44000.00),
  (@s20, 1, 1, 49000.00, 49000.00);

-- Sale 21: 2026-03-17 — 2x Aceite Mobil 10W30
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-17 10:00:00', 84000.00, 'Venta semilla #21', NULL, '2026-03-17 10:00:00');
SET @s21 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s21, 13, 2, 42000.00, 84000.00);

-- Sale 22: 2026-03-18 — 1x Yamalube + 3x Cable Freno Trasero
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-18 13:20:00', 52000.00, 'Venta semilla #22', NULL, '2026-03-18 13:20:00');
SET @s22 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s22, 1, 1, 49000.00, 49000.00),
  (@s22, 17, 3, 1000.00, 3000.00);

-- Sale 23: 2026-03-19 — 5x Cuña + 2x Tuerca Trasera
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-19 08:40:00', 7000.00, 'Venta semilla #23', NULL, '2026-03-19 08:40:00');
SET @s23 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s23, 5, 5, 1000.00, 5000.00),
  (@s23, 7, 2, 1000.00, 2000.00);

-- Sale 24: 2026-03-20 — 1x Honda + 1x Mobil 20W50 + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-20 11:00:00', 84000.00, 'Venta semilla #24', NULL, '2026-03-20 11:00:00');
SET @s24 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s24, 3, 1, 44000.00, 44000.00),
  (@s24, 11, 1, 30000.00, 30000.00),
  (@s24, 9, 1, 10000.00, 10000.00);

-- Sale 25: 2026-03-23 — 2x Aceite Bajaj + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-23 10:30:00', 92000.00, 'Venta semilla #25', NULL, '2026-03-23 10:30:00');
SET @s25 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s25, 2, 2, 38000.00, 76000.00),
  (@s25, 6, 1, 16000.00, 16000.00);

-- Sale 26: 2026-03-24 — 1x Castrol + 2x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-24 14:50:00', 56000.00, 'Venta semilla #26', NULL, '2026-03-24 14:50:00');
SET @s26 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s26, 14, 1, 44000.00, 44000.00),
  (@s26, 4, 2, 6000.00, 12000.00);

-- Sale 27: 2026-03-25 — 3x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-25 09:15:00', 30000.00, 'Venta semilla #27', NULL, '2026-03-25 09:15:00');
SET @s27 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s27, 9, 3, 10000.00, 30000.00);

-- Sale 28: 2026-03-26 — 1x Yamalube + 1x Aceite Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-26 11:00:00', 87000.00, 'Venta semilla #28', NULL, '2026-03-26 11:00:00');
SET @s28 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s28, 1, 1, 49000.00, 49000.00),
  (@s28, 2, 1, 38000.00, 38000.00);

-- Sale 29: 2026-03-27 — 2x Aceite Honda Semi-4t
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-27 10:00:00', 88000.00, 'Venta semilla #29', NULL, '2026-03-27 10:00:00');
SET @s29 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s29, 3, 2, 44000.00, 88000.00);

-- Sale 30: 2026-03-30 — 1x Mobil 10W40 + 1x Mobil 20W50
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-03-30 15:10:00', 72000.00, 'Venta semilla #30', NULL, '2026-03-30 15:10:00');
SET @s30 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s30, 12, 1, 42000.00, 42000.00),
  (@s30, 11, 1, 30000.00, 30000.00);

-- Sale 31: 2026-04-01 — 1x Castrol + 1x Yamalube + 2x Cuña
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-01 09:00:00', 95000.00, 'Venta semilla #31', NULL, '2026-04-01 09:00:00');
SET @s31 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s31, 14, 1, 44000.00, 44000.00),
  (@s31, 1, 1, 49000.00, 49000.00),
  (@s31, 5, 2, 1000.00, 2000.00);

-- Sale 32: 2026-04-02 — 4x Descarrilador + 3x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-02 10:45:00', 54000.00, 'Venta semilla #32', NULL, '2026-04-02 10:45:00');
SET @s32 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s32, 4, 4, 6000.00, 24000.00),
  (@s32, 9, 3, 10000.00, 30000.00);

-- Sale 33: 2026-04-03 — 2x Aceite Bajaj + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-03 11:20:00', 120000.00, 'Venta semilla #33', NULL, '2026-04-03 11:20:00');
SET @s33 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s33, 2, 2, 38000.00, 76000.00),
  (@s33, 14, 1, 44000.00, 44000.00);

-- Sale 34: 2026-04-06 — 1x Honda + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-06 08:50:00', 86000.00, 'Venta semilla #34', NULL, '2026-04-06 08:50:00');
SET @s34 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s34, 3, 1, 44000.00, 44000.00),
  (@s34, 12, 1, 42000.00, 42000.00);

-- Sale 35: 2026-04-07 — 3x Cable de Cambios + 2x Zapata Freno MTB Varilla
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-07 16:00:00', 7000.00, 'Venta semilla #35', NULL, '2026-04-07 16:00:00');
SET @s35 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s35, 20, 3, 1000.00, 3000.00),
  (@s35, 21, 2, 2000.00, 4000.00);

-- Sale 36: 2026-04-08 — 1x Yamalube + 1x Bajaj + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-08 09:30:00', 131000.00, 'Venta semilla #36', NULL, '2026-04-08 09:30:00');
SET @s36 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s36, 1, 1, 49000.00, 49000.00),
  (@s36, 2, 1, 38000.00, 38000.00),
  (@s36, 3, 1, 44000.00, 44000.00);

-- Sale 37: 2026-04-09 — 2x Mobil 20W50 + 2x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-09 14:00:00', 144000.00, 'Venta semilla #37', NULL, '2026-04-09 14:00:00');
SET @s37 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s37, 11, 2, 30000.00, 60000.00),
  (@s37, 12, 2, 42000.00, 84000.00);

-- Sale 38: 2026-04-13 — 1x Castrol + 3x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-13 10:10:00', 74000.00, 'Venta semilla #38', NULL, '2026-04-13 10:10:00');
SET @s38 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s38, 14, 1, 44000.00, 44000.00),
  (@s38, 9, 3, 10000.00, 30000.00);

-- Sale 39: 2026-04-14 — 5x Graduador Freno Pequeño + 5x Tuerca Delantera
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-14 09:00:00', 8000.00, 'Venta semilla #39', NULL, '2026-04-14 09:00:00');
SET @s39 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s39, 8, 5, 800.00, 4000.00),
  (@s39, 15, 5, 800.00, 4000.00);

-- Sale 40: 2026-04-15 — 2x Yamalube + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-15 11:30:00', 114000.00, 'Venta semilla #40', NULL, '2026-04-15 11:30:00');
SET @s40 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s40, 1, 2, 49000.00, 98000.00),
  (@s40, 6, 1, 16000.00, 16000.00);

-- Sale 41: 2026-04-16 — 1x Honda + 2x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-16 08:30:00', 120000.00, 'Venta semilla #41', NULL, '2026-04-16 08:30:00');
SET @s41 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s41, 3, 1, 44000.00, 44000.00),
  (@s41, 2, 2, 38000.00, 76000.00);

-- Sale 42: 2026-04-17 — 3x Aceite Mobil 10W30
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-17 13:40:00', 126000.00, 'Venta semilla #42', NULL, '2026-04-17 13:40:00');
SET @s42 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s42, 13, 3, 42000.00, 126000.00);

-- Sale 43: 2026-04-20 — 1x Castrol + 1x Mobil 20W50 + 1x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-20 09:00:00', 84000.00, 'Venta semilla #43', NULL, '2026-04-20 09:00:00');
SET @s43 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s43, 14, 1, 44000.00, 44000.00),
  (@s43, 11, 1, 30000.00, 30000.00),
  (@s43, 9, 1, 10000.00, 10000.00);

-- Sale 44: 2026-04-21 — 4x Cable Freno Delantero + 2x Cable de Cambios
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-21 15:20:00', 5200.00, 'Venta semilla #44', NULL, '2026-04-21 15:20:00');
SET @s44 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s44, 18, 4, 800.00, 3200.00),
  (@s44, 20, 2, 1000.00, 2000.00);

-- Sale 45: 2026-04-22 — 2x Yamalube + 2x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-22 10:00:00', 186000.00, 'Venta semilla #45', NULL, '2026-04-22 10:00:00');
SET @s45 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s45, 1, 2, 49000.00, 98000.00),
  (@s45, 3, 2, 44000.00, 88000.00);

-- Sale 46: 2026-04-23 — 1x Bajaj + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-23 11:15:00', 80000.00, 'Venta semilla #46', NULL, '2026-04-23 11:15:00');
SET @s46 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s46, 2, 1, 38000.00, 38000.00),
  (@s46, 12, 1, 42000.00, 42000.00);

-- Sale 47: 2026-04-24 — 3x Descarrilador + 1x Tensor Corriente
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-24 14:30:00', 34000.00, 'Venta semilla #47', NULL, '2026-04-24 14:30:00');
SET @s47 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s47, 4, 3, 6000.00, 18000.00),
  (@s47, 6, 1, 16000.00, 16000.00);

-- Sale 48: 2026-04-27 — 2x Castrol + 1x Yamalube
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-27 09:20:00', 137000.00, 'Venta semilla #48', NULL, '2026-04-27 09:20:00');
SET @s48 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s48, 14, 2, 44000.00, 88000.00),
  (@s48, 1, 1, 49000.00, 49000.00);

-- Sale 49: 2026-04-28 — 1x Mobil 20W50 + 2x Pedales
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-28 10:50:00', 50000.00, 'Venta semilla #49', NULL, '2026-04-28 10:50:00');
SET @s49 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s49, 11, 1, 30000.00, 30000.00),
  (@s49, 9, 2, 10000.00, 20000.00);

-- Sale 50: 2026-04-29 — 5x Cuña + 3x Tuerca Trasera + 2x Tornillo Marco
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-29 16:10:00', 10000.00, 'Venta semilla #50', NULL, '2026-04-29 16:10:00');
SET @s50 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s50, 5, 5, 1000.00, 5000.00),
  (@s50, 7, 3, 1000.00, 3000.00),
  (@s50, 16, 2, 1000.00, 2000.00);

-- Sale 51: 2026-04-30 — 2x Honda + 1x Bajaj
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-04-30 09:00:00', 126000.00, 'Venta semilla #51', NULL, '2026-04-30 09:00:00');
SET @s51 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s51, 3, 2, 44000.00, 88000.00),
  (@s51, 2, 1, 38000.00, 38000.00);

-- Sale 52: 2026-05-04 — 1x Castrol + 1x Mobil 10W40
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-04 10:30:00', 86000.00, 'Venta semilla #52', NULL, '2026-05-04 10:30:00');
SET @s52 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s52, 14, 1, 44000.00, 44000.00),
  (@s52, 12, 1, 42000.00, 42000.00);

-- Sale 53: 2026-05-07 — 3x Pedales + 2x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-07 11:00:00', 42000.00, 'Venta semilla #53', NULL, '2026-05-07 11:00:00');
SET @s53 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s53, 9, 3, 10000.00, 30000.00),
  (@s53, 4, 2, 6000.00, 12000.00);

-- Sale 54: 2026-05-11 — 1x Yamalube + 1x Mobil 20W50 + 1x Mobil 10W30
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-11 09:40:00', 121000.00, 'Venta semilla #54', NULL, '2026-05-11 09:40:00');
SET @s54 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s54, 1, 1, 49000.00, 49000.00),
  (@s54, 11, 1, 30000.00, 30000.00),
  (@s54, 13, 1, 42000.00, 42000.00);

-- Sale 55: 2026-05-13 — 2x Bajaj + 2x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-13 14:15:00', 164000.00, 'Venta semilla #55', NULL, '2026-05-13 14:15:00');
SET @s55 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s55, 2, 2, 38000.00, 76000.00),
  (@s55, 3, 2, 44000.00, 88000.00);

-- Sale 56: 2026-05-15 — 1x Castrol + 1x Pedales + 3x Cable de Cambios
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-15 10:00:00', 57000.00, 'Venta semilla #56', NULL, '2026-05-15 10:00:00');
SET @s56 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s56, 14, 1, 44000.00, 44000.00),
  (@s56, 9, 1, 10000.00, 10000.00),
  (@s56, 20, 3, 1000.00, 3000.00);

-- Sale 57: 2026-05-17 — 3x Tensor Corriente + 2x Descarrilador
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-17 11:45:00', 60000.00, 'Venta semilla #57', NULL, '2026-05-17 11:45:00');
SET @s57 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s57, 6, 3, 16000.00, 48000.00),
  (@s57, 4, 2, 6000.00, 12000.00);

-- Sale 58: 2026-05-18 — 2x Yamalube + 1x Castrol
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-18 08:55:00', 142000.00, 'Venta semilla #58', NULL, '2026-05-18 08:55:00');
SET @s58 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s58, 1, 2, 49000.00, 98000.00),
  (@s58, 14, 1, 44000.00, 44000.00);

-- Sale 59: 2026-05-19 — 1x Mobil 10W40 + 1x Mobil 20W50 + 1x Honda
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-19 10:20:00', 116000.00, 'Venta semilla #59', NULL, '2026-05-19 10:20:00');
SET @s59 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s59, 12, 1, 42000.00, 42000.00),
  (@s59, 11, 1, 30000.00, 30000.00),
  (@s59, 3, 1, 44000.00, 44000.00);

-- Sale 60: 2026-05-19 — 4x Pedales + 1x Yamalube
INSERT INTO sales (sale_date, total_amount, notes, deleted_at, created_at) VALUES
  ('2026-05-19 15:30:00', 89000.00, 'Venta semilla #60', NULL, '2026-05-19 15:30:00');
SET @s60 = LAST_INSERT_ID();
INSERT INTO sale_items (sale_id, product_id, quantity, unit_price, subtotal) VALUES
  (@s60, 9, 4, 10000.00, 40000.00),
  (@s60, 1, 1, 49000.00, 49000.00);

SET FOREIGN_KEY_CHECKS = 1;
