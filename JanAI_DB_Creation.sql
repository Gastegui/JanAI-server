DROP DATABASE IF EXISTS JANAI;
CREATE DATABASE IF NOT EXISTS JANAI;
USE JANAI;

create table food(
	foodID bigint,
    foodName varchar(255),
    PRIMARY KEY (foodID)
);

create table foodClass(
	classID bigint,
    className varchar(255),
    PRIMARY KEY (classID)
);

create table foodType(
	typeID bigint,
    classID bigint,
    typeName varchar(255),
    PRIMARY KEY (typeID),
    FOREIGN KEY (classID) REFERENCES foodClass(classID)
);

create table foodGroup(
	groupID bigint,
    typeID bigint,
    groupName varchar(255),
    PRIMARY KEY (groupID),
    FOREIGN KEY (typeID) REFERENCES foodType(typeID)
);

create table ingredients(
	ingredientID bigint,
    ingName char(255),
    groupID bigint,
    PRIMARY KEY (ingredientID),
    FOREIGN KEY (groupID) REFERENCES foodGroup(groupID)
);

create table hasIngredients(
	foodID bigint,
    ingredientID bigint,
    FOREIGN KEY (foodID) REFERENCES food(foodID),
    FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID),
    PRIMARY KEY (foodID, ingredientID)
);

create table users(
	userID bigint PRIMARY KEY,
    uname char(255) NOT NULL,
    secondName char(255),
    gender enum("MALE","FEMALE") NOT NULL,
    age int NOT NULL, -- years
    height int NOT NULL, -- cm
    username char(255) UNIQUE NOT NULL,
    email char(255) UNIQUE NOT NULL,
    userPass char(255) NOT NULL,
    activityLevel enum("Sedentary","Light","Moderate","Active","Very Active"),
    premium boolean NOT NULL,
    objective enum("Lose weight", "Gain weight", "Keep fit"),
    neck float, -- cm
    waist float, -- cm
    hips float, -- cm
    finalDailyCalorieIntake float, -- kcal/day
    -- Variables only for the LLM
    bmrMifflin float, -- kcal
    bmrHarrisBenedict float, -- kcal
    bmrKatchMcArdle float, -- kcal
    tdeeMifflin float, -- kcal
    tdeeHarrisBenedict float, -- kcal
    tdeeKatchMcArdle float, -- kcal
    bodyFat float, -- %
    totalWeightLoss float, -- kg
    weeklyDeficit float, -- kcal
    dailyCalorieIntakeMifflin float, -- kcal/day
    dailyCalorieIntakeHarrisBenedict float, -- kcal/day
    dailyCalorieIntakeKatchMcArdle float -- kcal/day
);
INSERT INTO user_data VALUES(1, 'Luken', 'M', 20, 183, 85, 90, 30, null, 80, 18, 'Light', 'lose weight', null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO user_data VALUES(2, 'Anje', 'M', 20, 173, 75, 90, 30, null, 80, 18, 'Active', 'lose weight', null, null, null, null, null, null, null, null, null, null, null, null, null);

CREATE TABLE user_data_temp (
    userID BIGINT PRIMARY KEY,
    bmrMifflin FLOAT, -- kcal
    bmrHarrisBenedict FLOAT, -- kcal
    bmrKatchMcArdle FLOAT, -- kcal
    tdeeMifflin FLOAT, -- kcal
    tdeeHarrisBenedict FLOAT, -- kcal
    tdeeKatchMcArdle FLOAT, -- kcal
    bodyFat FLOAT, -- %
    totalWeightLoss FLOAT, -- kg
    weeklyDeficit FLOAT, -- kcal
    dailyCalorieIntakeMifflin FLOAT, -- kcal/day
    dailyCalorieIntakeHarrisBenedict FLOAT, -- kcal/day
    dailyCalorieIntakeKatchMcArdle FLOAT -- kcal/day
);

create table weightGoals(
	weightGoalsID bigint,
    userID bigint, # FOREIGN KEY TO USER TABLE
    weight float NOT NULL, -- kg
    goalWeight float NOT NULL, -- kg
    durationToAchieveGoalWeight int, -- weeks
    registerDate date,
    PRIMARY KEY (weightGoalsID),
    FOREIGN KEY (userID) REFERENCES users(userID)
);

create table restrictions(
	restrictionID bigint,
    restrictedFood varchar(255),
    userID bigint,
    groupID bigint,
    typeID bigint,
    classID bigint,
    ingredientID bigint,
    PRIMARY KEY (restrictionID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (groupID) REFERENCES foodGroup(groupID),
    FOREIGN KEY (typeID) REFERENCES foodType(typeID),
    FOREIGN KEY (classID) REFERENCES foodClass(classID),
    FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID)
);

/*
select * from user_data;
select * from user_data_temp;
CALL update_calorie_values();
*/