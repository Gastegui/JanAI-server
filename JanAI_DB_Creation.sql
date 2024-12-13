drop database if exists JanAI;
create database if not exists JanAI;
use JanAI;

create table food(
	foodID bigint auto_increment,
    foodName varchar(255),
    proteins float,
    carbs float,
    fats float,
    fiber float,
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

create table administrator(
	adminID bigint auto_increment,
    uname char(255),
    surname char(255),
    username char(255) UNIQUE NOT NULL,
    email char(255) UNIQUE NOT NULL,
    userPass char(255) NOT NULL,
    PRIMARY KEY (adminID)
);

create table campaign(
	campaignID bigint,
    campName char(255),
    adminID bigint,
    town char(255),
    company char(255),
    PRIMARY KEY (campaignID),
    FOREIGN KEY (adminID) REFERENCES administrator(adminID)
);

create table ingredientsInCampaign(
	campaignID bigint,
    ingredientID bigint,
    init_date date,
    end_date date,
    PRIMARY KEY (campaignID, ingredientID),
    FOREIGN KEY (campaignID) REFERENCES campaign(campaignID),
    FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID)
);

create table hasIngredients(
	foodID bigint,
    ingredientID bigint,
    FOREIGN KEY (foodID) REFERENCES food(foodID),
    FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID),
    PRIMARY KEY (foodID, ingredientID)
);

create table userData(
	userID bigint auto_increment PRIMARY KEY,
    uname char(255) NOT NULL,
    secondName char(255),
    gender enum("M","F") NOT NULL,
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

create table foodList(
	foodID bigint,
    userID bigint,
    consumption_date date,
    meal char(255),
    PRIMARY KEY (foodID, userID),
    FOREIGN KEY (userID) REFERENCES userData(userID),
    FOREIGN KEY (foodID) REFERENCES food(foodID)
);

create table weightGoals(
	weightGoalsID bigint auto_increment,
    userID bigint, # FOREIGN KEY TO USER TABLE
    weight float NOT NULL, -- kg
    goalWeight float NOT NULL, -- kg
    durationToAchieveGoalWeight int, -- weeks
    registerDate date,
    /*recommendedCalories float,
    recommendedProteins float,
    recommendedCarbs float,
    recommendedFats float,
    recommendedFiber float,*/
    PRIMARY KEY (weightGoalsID),
    FOREIGN KEY (userID) REFERENCES userData(userID)
);

create table restrictions(
	restrictionID bigint,
    restrictedName varchar(255),
    userID bigint,
    groupID bigint,
    typeID bigint,
    classID bigint,
    ingredientID bigint,
    PRIMARY KEY (restrictionID),
    FOREIGN KEY (userID) REFERENCES userData(userID),
    FOREIGN KEY (groupID) REFERENCES foodGroup(groupID),
    FOREIGN KEY (typeID) REFERENCES foodType(typeID),
    FOREIGN KEY (classID) REFERENCES foodClass(classID),
    FOREIGN KEY (ingredientID) REFERENCES ingredients(ingredientID)
);

#insert into food values (1, 'Tortilla', 12.4, 13.4, 75.4, 98.6);

#select * from food;

#delete from food;
#select * from userData;
#select * from food;
select * from weightGoals;
delete from weightGoals;