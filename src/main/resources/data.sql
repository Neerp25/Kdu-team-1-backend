INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (1, 'GRANDD', 'Grand Deluxe', 'A Promo for Grand Deluxe Rooms', '2024-03-20', '2024-04-30', 0.5);

INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (2, 'SUPERD', 'Super Deluxe', 'A Promo for Super Deluxe Rooms', '2024-03-20', '2024-04-30', 0.5);

INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (3, 'FAMILYD', 'Family Deluxe', 'A Promo for Family Deluxe Rooms', '2024-03-20', '2024-04-30', 0.5);

INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (4, 'COUPLES', 'Couple Suite', 'A Promo for Couple Suites', '2024-03-20', '2024-04-30', 0.5);

INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (5, 'GARDENS', 'Garden Suite', 'A Promo for Garden Suites', '2024-03-20', '2024-04-30', 0.5);

INSERT
INTO
  promo_codes
  (room_type_id, promo_code, promo_title, promo_desc, start_date, end_date, price_factor)
VALUES
  (6, 'STANDARDS', 'Standard Suite', 'A Promo for Standard Suites', '2024-03-20', '2024-04-30', 0.5);

--CREATE OR REPLACE FUNCTION check_overlap()
--RETURNS TRIGGER AS $$
--BEGIN
--    IF EXISTS (
--        SELECT 1
--        FROM pre_booking_table
--        WHERE room_id = NEW.room_id
--        AND (start_date, end_date) OVERLAPS (NEW.start_date, NEW.end_date)
--    ) THEN
--        RAISE EXCEPTION 'Overlapping dates for room ID %', NEW.room_id;
--    END IF;
--    RETURN NEW;
--END;
--$$ LANGUAGE plpgsql;
--
--CREATE TRIGGER overlapping_dates_trigger
--BEFORE INSERT OR UPDATE ON pre_booking_table
--FOR EACH ROW
--EXECUTE FUNCTION check_overlap();
--
--DROP FUNCTION check_overlap();

