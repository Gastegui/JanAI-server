-- First you should run the DB Creation .sql
-- Use the database
USE JanAI;

-- Insert data into food table
INSERT INTO food VALUES
  (1, 'Pizza', 11.0, 26.0, 10.0, 2.5, 100),
  (2, 'Burger', 14.0, 24.0, 12.0, 1.8, 200),
  (3, 'Salad', 2.0, 3.0, 0.2, 1.5, 300),
  (4, 'Pasta', 5.0, 31.0, 1.2, 1.7, 400),
  (5, 'Ice Cream', 3.0, 23.0, 10.0, 0.4, 500);

-- Insert data into foodClass table
INSERT INTO foodClass VALUES
  (0, ' '),
  (1, 'Sea food'),
  (2, 'Healthy Food'),
  (3, 'Dessert');

-- Insert data into foodType table
INSERT INTO foodType VALUES
  (0, 0, ' '),
  (1, 1, 'Fish'),
  (2, 2, 'Vegan'),
  (3, 3, 'Sweet');

-- Insert data into foodGroup table
INSERT INTO foodGroup VALUES
  (0, 0, ' '),
  (1, 1, 'Blue fish'),
  (2, 2, 'Vegetables'),
  (3, 3, 'Ice Creams');

-- Insert data into ingredients table
INSERT INTO ingredients VALUES
  (0, ' ', 0),
  (1, 'Tuna', 1),
  (2, 'Lettuce', 2),
  (3, 'Vanilla', 3);

-- Insert data into administrator table
INSERT INTO administrator (uname, surname, username, email, userPass) VALUES
  ('John', 'Doe', 'johndoe', 'john.doe@example.com', 'securePass123'),
  ('Jane', 'Smith', 'janesmith', 'jane.smith@example.com', 'strongPass456');

-- Insert data into campaign table
INSERT INTO campaign VALUES
  (1, 'Summer Sale', 1, 'New York', 'Food Corp'),
  (2, 'Vegan Promotion', 2, 'Los Angeles', 'Green Foods');

-- Insert data into ingredientsInCampaign table
INSERT INTO ingredientsInCampaign VALUES
  (1, 1, '2024-01-01', '2024-06-30'),
  (2, 2, '2024-02-01', '2024-08-31');

-- Insert data into hasIngredients table
INSERT INTO hasIngredients VALUES
  (1, 1),
  (2, 2),
  (3, 3);

-- Insert data into user_data table
INSERT INTO userData VALUES
(1, 'Luken', 'Iriondo', 'M', 20, '183', 'iamLudok', 'luken.iriondo@alumni.mondragon.edu', 'angry', 'Light', false, 
'Lose weight', 30, 90, 22, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null),
(2, 'Anje', 'Maiztegi', 'M', 19, '173', 'anje13', 'ange.maiztegui@alumni.mondragon.edu', 'angry', 'Active', false, 
'Lose weight', 30, 90, 22, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null);

-- Insert data into foodList table
INSERT INTO foodList VALUES
  (3, 1, '2024-12-01', 'Lunch'),
  (3, 1, '2024-12-12', 'Lunch'),
  (3, 1, '2024-12-12', 'Dinner'),
  (3, 1, '2024-12-13', 'Lunch'),
  (2, 2, '2024-12-02', 'Dinner');

-- Insert data into weightGoals table
INSERT INTO weightGoals VALUES
  (1, 1, 85, 80, 18, CURRENT_DATE()),
  (2, 2, 75, 80, 18, CURRENT_DATE());

-- Insert data into restrictions table
INSERT INTO restrictions VALUES
  (1, 1, 1, 1, 1, 1),
  (2, 2, 3, 3, 3, 3);

select * from food;
select * from foodClass;
select * from foodType;
select * from foodGroup;
select * from ingredients;
select * from administrator;
select * from campaign;
select * from ingredientsInCampaign;
select * from hasIngredients;
select * from userData;
select * from foodList;
select * from weightGoals;
select * from restrictions;