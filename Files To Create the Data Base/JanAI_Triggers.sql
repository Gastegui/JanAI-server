-- First you should run the DB Creation .sql
USE JANAI;

DELIMITER $$

CREATE TRIGGER calculate_calorie_values
AFTER INSERT ON weightGoals
FOR EACH ROW
BEGIN
    DECLARE v_body_fat FLOAT;
    DECLARE v_bmrMifflin FLOAT;
    DECLARE v_bmrHarrisBenedict FLOAT;
    DECLARE v_bmrKatchMcArdle FLOAT;
    DECLARE v_tdeeMifflin FLOAT;
    DECLARE v_tdeeHarrisBenedict FLOAT;
    DECLARE v_tdeeKatchMcArdle FLOAT;
    DECLARE v_totalWeightLoss FLOAT;
    DECLARE v_weeklyDeficit FLOAT;
    DECLARE v_dailyCalorieIntakeMifflin FLOAT;
    DECLARE v_dailyCalorieIntakeHarrisBenedict FLOAT;
    DECLARE v_dailyCalorieIntakeKatchMcArdle FLOAT;

    -- Obtener datos del usuario desde userData
    DECLARE v_gender CHAR(1);
    DECLARE v_height FLOAT;
    DECLARE v_age INT;
    DECLARE v_waist FLOAT;
    DECLARE v_neck FLOAT;
    DECLARE v_hips FLOAT;
    DECLARE v_bodyFatPercentage FLOAT;
    DECLARE v_activityLevel CHAR(255);
    DECLARE v_dailyWaterIntake FLOAT;

    SELECT gender, height, age, waist, neck, hips, bodyFat, activityLevel
    INTO v_gender, v_height, v_age, v_waist, v_neck, v_hips, v_bodyFatPercentage, v_activityLevel
    FROM userData
    WHERE userID = NEW.userID;

    -- Cálculo del porcentaje de grasa corporal
    IF v_gender = 'M' THEN
        SET v_body_fat = 100 * ((4.95 / (1.0324 - 0.19077 * LOG10(v_waist - v_neck) + 0.15456 * LOG10(v_height))) - 4.5);
    ELSE
        SET v_body_fat = 100 * ((4.95 / (1.29579 - 0.35004 * LOG10(v_waist + v_hips - v_neck) + 0.221 * LOG10(v_height))) - 4.5);
    END IF;

    -- Cálculo de BMR usando Mifflin-St Jeor
    IF v_gender = 'M' THEN
        SET v_bmrMifflin = 10 * NEW.weight + 6.25 * v_height - 5 * v_age + 5;
    ELSE
        SET v_bmrMifflin = 10 * NEW.weight + 6.25 * v_height - 5 * v_age - 161;
    END IF;

    -- Cálculo de BMR usando Harris-Benedict
    IF v_gender = 'M' THEN
        SET v_bmrHarrisBenedict = 13.397 * NEW.weight + 4.799 * v_height - 5.677 * v_age + 88.362;
    ELSE
        SET v_bmrHarrisBenedict = 9.247 * NEW.weight + 3.098 * v_height - 4.330 * v_age + 447.593;
    END IF;

    -- Cálculo de BMR usando Katch-McArdle
    SET v_bmrKatchMcArdle = 370 + 21.6 * (1 - v_body_fat / 100) * NEW.weight;

    -- Cálculo de TDEE
    CASE v_activityLevel
        WHEN 'Sedentary' THEN
            SET v_tdeeMifflin = v_bmrMifflin * 1.2;
            SET v_tdeeHarrisBenedict = v_bmrHarrisBenedict * 1.2;
            SET v_tdeeKatchMcArdle = v_bmrKatchMcArdle * 1.2;
        WHEN 'Light' THEN
            SET v_tdeeMifflin = v_bmrMifflin * 1.375;
            SET v_tdeeHarrisBenedict = v_bmrHarrisBenedict * 1.375;
            SET v_tdeeKatchMcArdle = v_bmrKatchMcArdle * 1.375;
        WHEN 'Moderate' THEN
            SET v_tdeeMifflin = v_bmrMifflin * 1.55;
            SET v_tdeeHarrisBenedict = v_bmrHarrisBenedict * 1.55;
            SET v_tdeeKatchMcArdle = v_bmrKatchMcArdle * 1.55;
        WHEN 'Active' THEN
            SET v_tdeeMifflin = v_bmrMifflin * 1.725;
            SET v_tdeeHarrisBenedict = v_bmrHarrisBenedict * 1.725;
            SET v_tdeeKatchMcArdle = v_bmrKatchMcArdle * 1.725;
        ELSE
            SET v_tdeeMifflin = v_bmrMifflin * 1.9;
            SET v_tdeeHarrisBenedict = v_bmrHarrisBenedict * 1.9;
            SET v_tdeeKatchMcArdle = v_bmrKatchMcArdle * 1.9;
    END CASE;

    -- Cálculo de la pérdida total de peso y el déficit semanal
    SET v_totalWeightLoss = NEW.weight - NEW.goalWeight;
    SET v_weeklyDeficit = (v_totalWeightLoss * 7700) / NEW.durationToAchieveGoalWeight; -- 1kg = ~7700 kcal

    -- Ingesta calórica diaria
    SET v_dailyCalorieIntakeMifflin = v_tdeeMifflin - (v_weeklyDeficit / 7);
    SET v_dailyCalorieIntakeHarrisBenedict = v_tdeeHarrisBenedict - (v_weeklyDeficit / 7);
    SET v_dailyCalorieIntakeKatchMcArdle = v_tdeeKatchMcArdle - (v_weeklyDeficit / 7);
    
    -- Calcular la ingesta diaria de agua
    SET v_dailyWaterIntake = NEW.weight * 0.033;

    -- Actualizar los datos del usuario directamente en userData
    UPDATE userData
    SET
        waterIntake = v_dailyWaterIntake,
        bodyFat = v_body_fat,
        bmrMifflin = v_bmrMifflin,
        bmrHarrisBenedict = v_bmrHarrisBenedict,
        bmrKatchMcArdle = v_bmrKatchMcArdle,
        tdeeMifflin = v_tdeeMifflin,
        tdeeHarrisBenedict = v_tdeeHarrisBenedict,
        tdeeKatchMcArdle = v_tdeeKatchMcArdle,
        totalWeightLoss = v_totalWeightLoss,
        weeklyDeficit = v_weeklyDeficit,
        dailyCalorieIntakeMifflin = v_dailyCalorieIntakeMifflin,
        dailyCalorieIntakeHarrisBenedict = v_dailyCalorieIntakeHarrisBenedict,
        dailyCalorieIntakeKatchMcArdle = v_dailyCalorieIntakeKatchMcArdle
    WHERE userID = NEW.userID;
END $$

DELIMITER ;