-- First you should run the DB Creation .sql
-- Use the database
USE JanAI;

-- Insert data into food table
INSERT INTO food VALUES
  (1, 'Pizza'),
  (2, 'Burger'),
  (3, 'Salad'),
  (4, 'Pasta'),
  (5, 'Ice Cream');

-- Insert data into foodClass table
INSERT INTO foodClass VALUES
  (1, 'Fast Food'),
  (2, 'Healthy Food'),
  (3, 'Dessert');

-- Insert data into foodType table
INSERT INTO foodType VALUES
  (1, 1, 'Snacks'),
  (2, 2, 'Vegan'),
  (3, 3, 'Sweet');

-- Insert data into foodGroup table
INSERT INTO foodGroup VALUES
  (1, 1, 'Fried Food'),
  (2, 2, 'Vegetables'),
  (3, 3, 'Ice Creams');

-- Insert data into ingredients table
INSERT INTO ingredients VALUES
  (1, 'Tomato Sauce', 1),
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
'Lose weight', 30, 90, null, null, null, null, null, null, null, null, null, null, null, null, null, null),
(2, 'Anje', 'Maiztegi', 'M', 19, '173', 'anje13', 'ange.maiztegui@alumni.mondragon.edu', 'angry', 'Active', false, 
'Lose weight', 30, 90, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- Insert data into foodList table
INSERT INTO foodList VALUES
  (1, 1, '2024-12-01', 'Lunch'),
  (2, 2, '2024-12-02', 'Dinner');

-- Insert data into weightGoals table
INSERT INTO weightGoals VALUES
  (1, 1, 85, 80, 18, CURRENT_DATE()),
  (2, 2, 75, 80, 18, CURRENT_DATE());

-- Insert data into restrictions table
INSERT INTO restrictions VALUES
  (1, 'Meat', 1, 1, 1, 1, 1),
  (2, 'Ice Cream', 2, 3, 3, 3, 3);

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