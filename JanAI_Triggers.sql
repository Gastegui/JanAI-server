-- First you should run the DB Creation .sql
USE JANAI;

DELIMITER $$

CREATE TRIGGER calculate_calorie_values_trigger
AFTER INSERT ON user_data
FOR EACH ROW
BEGIN
    DECLARE v_bmrMifflin FLOAT;
    DECLARE v_bmrHarrisBenedict FLOAT;
    DECLARE v_bmrKatchMcArdle FLOAT;
    DECLARE v_tdeeMifflin FLOAT;
    DECLARE v_tdeeHarrisBenedict FLOAT;
    DECLARE v_tdeeKatchMcArdle FLOAT;
    DECLARE v_body_fat FLOAT;
    DECLARE v_totalWeightLoss FLOAT;
    DECLARE v_weeklyDeficit FLOAT;
    DECLARE v_dailyCalorieIntakeMifflin FLOAT;
    DECLARE v_dailyCalorieIntakeHarrisBenedict FLOAT;
    DECLARE v_dailyCalorieIntakeKatchMcArdle FLOAT;

    -- Call the procedure to calculate the value
    CALL calculate_calorie_values(
        NEW.gender,
        NEW.weight,
        NEW.height,
        NEW.age,
        NEW.waist,
        NEW.neck,
        NEW.hips,
        NEW.bodyFat,
        NEW.activityLevel,
        NEW.goalWeight,
        NEW.durationToAchieveGoalWeight,
        v_bmrMifflin,
        v_bmrHarrisBenedict,
        v_bmrKatchMcArdle,
        v_tdeeMifflin,
        v_tdeeHarrisBenedict,
        v_tdeeKatchMcArdle,
        v_body_fat,
        v_totalWeightLoss,
		v_weeklyDeficit,
        v_dailyCalorieIntakeMifflin,
        v_dailyCalorieIntakeHarrisBenedict,
        v_dailyCalorieIntakeKatchMcArdle
    );

    -- Insert the results in the temporal table (you can't change the same table that the trigger is reading)
    INSERT INTO user_data_temp (userID, bmrMifflin, bmrHarrisBenedict, bmrKatchMcArdle, tdeeMifflin, tdeeHarrisBenedict, tdeeKatchMcArdle, bodyFat,
    totalWeightLoss, weeklyDeficit, dailyCalorieIntakeMifflin, dailyCalorieIntakeHarrisBenedict, dailyCalorieIntakeKatchMcArdle)
    VALUES (NEW.userID, v_bmrMifflin, v_bmrHarrisBenedict, v_bmrKatchMcArdle, v_tdeeMifflin, v_tdeeHarrisBenedict, v_tdeeKatchMcArdle, v_body_fat, 
    v_totalWeightLoss, v_weeklyDeficit, v_dailyCalorieIntakeMifflin, v_dailyCalorieIntakeHarrisBenedict, v_dailyCalorieIntakeKatchMcArdle);
END $$

DELIMITER ;