drop database if exists JanAI;
create database if not exists JanAI;
use JanAI;

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
	ingredientID bigint auto_increment,
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

create table users(
	userID bigint auto_increment,
    uname char(255) NOT NULL,
    secondName char(255),
    gender enum("MALE","FEMALE") NOT NULL,
    age bigint NOT NULL,
    height int NOT NULL,
    username char(255) UNIQUE NOT NULL,
    email char(255) UNIQUE NOT NULL,
    userPass char(255) NOT NULL,
    activity enum("1-2","3-4","5-6","7"),
    premium boolean NOT NULL,
    objective enum("Lose weight", "Gain weight", "Keep fit"),
    neck double,
    waist double,
    hips double,
    PRIMARY KEY (userID)
);

create table foodList(
	foodID bigint,
    userID bigint,
    consumption_date date,
    meal char(255),
    PRIMARY KEY (foodID, userID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (foodID) REFERENCES food(foodID)
);

create table weightGoals(
	weightGoalsID bigint,
    userID bigint, # FOREIGN KEY TO USER TABLE
    cur_weight double NOT NULL,
    goal_weight double NOT NULL,
    goalDate date,
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

#select * from users;
#delete from users;
