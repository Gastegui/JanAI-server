-- First you should run the DB Creation .sql
USE JANAI;

DELIMITER $$

CREATE PROCEDURE calculate_calorie_values(
    IN p_gender CHAR(1),
    IN p_weight FLOAT,
    IN p_height FLOAT,
    IN p_age INT,
    IN p_waist FLOAT,     
    IN p_neck FLOAT,      
    IN p_hips FLOAT,      
    IN p_body_fat_percentage FLOAT, 
    IN p_activityLevel CHAR(255),
    IN p_goalWeight FLOAT,
    IN p_durationToAchieveGoalWeight INT,
    OUT p_bmrMifflin FLOAT,    
    OUT p_bmrHarrisBenedict FLOAT, 
    OUT p_bmrKatchMcArdle FLOAT, 
    OUT p_tdeeMifflin FLOAT,
    OUT p_tdeeHarrisBenedict FLOAT,
    OUT p_tdeeKatchMcArdle FLOAT,
    OUT p_body_fat FLOAT,
    OUT p_totalWeightLoss FLOAT,
    OUT p_weeklyDeficit FLOAT,
    OUT p_dailyCalorieIntakeMifflin FLOAT,
    OUT p_dailyCalorieIntakeHarrisBenedict FLOAT,
    OUT p_dailyCalorieIntakeKatchMcArdle FLOAT
)
BEGIN
    -- Body Fat 
    IF p_gender = 'M' THEN
        SET p_body_fat = 100 * ((4.95 / (1.0324 - 0.19077 * LOG10(p_waist - p_neck) + 0.15456 * LOG10(p_height))) - 4.5);
    ELSE
        SET p_body_fat = 100 * ((4.95 / (1.29579 - 0.35004 * LOG10(p_waist + p_hips - p_neck) + 0.221 * LOG10(p_height))) - 4.5);
    END IF;

    -- Mifflin-St Jeor Equation (BMR)
    IF p_gender = 'M' THEN
        SET p_bmrMifflin = 10 * p_weight + 6.25 * p_height - 5 * p_age + 5;
    ELSE
        SET p_bmrMifflin = 10 * p_weight + 6.25 * p_height - 5 * p_age - 161;
    END IF;

    -- Revised Harris-Benedict Equation (BMR)
    IF p_gender = 'M' THEN
        SET p_bmrHarrisBenedict = 13.397 * p_weight + 4.799 * p_height - 5.677 * p_age + 88.362;
    ELSE
        SET p_bmrHarrisBenedict = 9.247 * p_weight + 3.098 * p_height - 4.330 * p_age + 447.593;
    END IF;

    -- Katch-McArdle Formula (BMR)
    SET p_bmrKatchMcArdle = 370 + 21.6 * (1 - p_body_fat / 100) * p_weight;
    
    -- Total Daily Energy Expenditure (TDEE)
    IF p_activityLevel = 'Sedentary' THEN
		SET p_tdeeMifflin = p_bmrMifflin * 1.2;
		SET p_tdeeHarrisBenedict = p_bmrHarrisBenedict * 1.2;
        SET p_tdeeKatchMcArdle = p_bmrKatchMcArdle * 1.2;
	ELSEIF p_activityLevel = 'Light' THEN
		SET p_tdeeMifflin = p_bmrMifflin * 1.375;
		SET p_tdeeHarrisBenedict = p_bmrHarrisBenedict * 1.375;
        SET p_tdeeKatchMcArdle = p_bmrKatchMcArdle * 1.375;
    ELSEIF p_activityLevel = 'Moderate' THEN
		SET p_tdeeMifflin = p_bmrMifflin * 1.55;
		SET p_tdeeHarrisBenedict = p_bmrHarrisBenedict * 1.55;
        SET p_tdeeKatchMcArdle = p_bmrKatchMcArdle * 1.55;
    ELSEIF p_activityLevel = 'Active' THEN
		SET p_tdeeMifflin = p_bmrMifflin * 1.725;
		SET p_tdeeHarrisBenedict = p_bmrHarrisBenedict * 1.725;
        SET p_tdeeKatchMcArdle = p_bmrKatchMcArdle * 1.725;
    ELSE
		SET p_tdeeMifflin = p_bmrMifflin * 1.9;
		SET p_tdeeHarrisBenedict = p_bmrHarrisBenedict * 1.9;
        SET p_tdeeKatchMcArdle = p_bmrKatchMcArdle * 1.9;
    END IF;
    
    -- Total Weight Loss
    SET p_totalWeightLoss = p_weight - p_goalWeight;
    
    -- Weekly Deficit
	SET p_weeklyDeficit = (p_totalWeightLoss * 7700) / p_durationToAchieveGoalWeight; # 1kg = ~7700 kcal
    
    -- Daily Calorie Intake
	SET p_dailyCalorieIntakeMifflin = p_tdeeMifflin - (p_weeklyDeficit / 7);
    SET p_dailyCalorieIntakeHarrisBenedict = p_tdeeHarrisBenedict - (p_weeklyDeficit / 7);
    SET p_dailyCalorieIntakeKatchMcArdle = p_tdeeKatchMcArdle - (p_weeklyDeficit / 7);
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE update_calorie_values()
BEGIN
    -- Update the user_data table using the temporal table values
    UPDATE user_data u
    JOIN user_data_temp t ON u.userID = t.userID
    SET 
        u.bmrMifflin = t.bmrMifflin,
        u.bmrHarrisBenedict = t.bmrHarrisBenedict,
        u.bmrKatchMcArdle = t.bmrKatchMcArdle,
        u.tdeeMifflin = t.tdeeMifflin,
        u.tdeeHarrisBenedict = t.tdeeHarrisBenedict,
        u.tdeeKatchMcArdle = t.tdeeKatchMcArdle,
        u.bodyFat = t.bodyFat,
        u.totalWeightLoss = t.totalWeightLoss,
		u.weeklyDeficit = t.weeklyDeficit,
        u.dailyCalorieIntakeMifflin = t.dailyCalorieIntakeMifflin,
        u.dailyCalorieIntakeHarrisBenedict = t.dailyCalorieIntakeHarrisBenedict,
        u.dailyCalorieIntakeKatchMcArdle = t.dailyCalorieIntakeKatchMcArdle;
        
    -- Delete the content of the temporal table
    DELETE FROM user_data_temp;
END $$

DELIMITER ;