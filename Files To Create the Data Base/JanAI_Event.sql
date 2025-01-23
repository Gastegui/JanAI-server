SET GLOBAL event_scheduler = ON;
CREATE EVENT ResetWaterCounter
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_DATE + INTERVAL 1 DAY -- Comienza a la medianoche del próximo día
DO
UPDATE userData
SET waterCounter = 0;
SHOW EVENTS;