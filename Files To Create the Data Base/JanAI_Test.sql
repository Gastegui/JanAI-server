-- First you should run the DB Creation .sql
-- Use the database
USE JanAI;

-- Insert data into food table
-- Macros of the food taken in: 
	-- https://www.fatsecret.com/Default.aspx
    -- https://www.nutritionix.com/
INSERT INTO food(foodID, foodName, proteins, carbs, fats, fiber, calories) VALUES
  (1, 'deviled_eggs', 11.57, 201, 16.23, 0, 16.23),  
  (2, 'gyoza', 7.5, 22.2, 7, 0, 185),  
  (3, 'escargots', 16.1, 2, 1.4, 0, 90),  
  (4, 'fried_calamari', 15.13, 9.9, 2.17, 0.5, 125),  
  (5, 'mussels', 11.9, 3.69, 2.24, 0, 86),  
  (6, 'oysters', 7.05, 3.91, 2.46, 0, 68),  
  (7, 'sashimi', 21.62, 0, 5.93, 0, 146),  
  (8, 'scallops', 18.14, 10.49, 10.96, 0.5, 217),  
  (9, 'tuna_tartare', 4.58, 17.09, 3, 0.76, 266),  
  (10, 'eggs_benedict', 11.96, 8.32, 21.6, 0.6, 276),  
  (11, 'huevos_rancheros', 6.89, 8.53, 6.66, 1.5, 119),  
  (12, 'omelette', 10.62, 0.69, 12.02, 0, 153),  
  (13, 'creme_brulee', 4.4, 24, 11.6, 0.5, 219),  
  (14, 'ice_cream', 3.52, 24.4, 10.72, 0.7, 201),  
  (15, 'panna_cotta', 3.3, 21, 11, 0, 201),  
  (16, 'cheesecake', 5.5, 25.5, 22.5, 0.4, 321),  
  (17, 'tiramisu', 4.77, 24.41, 18.2, 0.9, 283),  
  (18, 'cannoli', 8.7, 28.84, 11.04, 0.7, 254),  
  (19, 'frozen_yogurt', 4.7, 19.62, 1.47, 0.3, 107),  
  (20, 'shrimp_and_grits', 20.31, 0.91, 1.73, 0, 106),  
  (21, 'chicken_curry', 11.47, 4.74, 6.67, 0.9, 124),  
  (22, 'chicken_quesadilla', 17.15, 20.14, 17.14, 1.3, 306),  
  (23, 'chicken_wings', 26.64, 0, 19.3, 0, 288),  
  (24, 'peking_duck', 18.66, 0, 20.61, 0, 265),  
  (25, 'club_sandwich', 16.2, 12.2, 13.3, 0.9, 236.9),  
  (26, 'breakfast_burrito', 10.72, 11.89, 12.3, 0.8, 202),  
  (27, 'beef_carpaccio', 22.31, 0, 2.62, 0, 119),  
  (28, 'beef_tartare', 17.78, 0.63, 14.56, 0.1, 210),  
  (29, 'beet_salad', 1.8, 14, 0, 0.7, 67),  
  (30, 'bibimbap', 4.2, 18, 2.6, 0.7, 112.8),  
  (31, 'filet_mignon', 21.97, 0, 8.41, 0, 170),  
  (32, 'hamburger', 15.3, 18, 12.2, 0, 243),  
  (33, 'prime_rib', 25.91, 0, 17.32, 0, 266),  
  (34, 'steak', 27.29, 0, 15.01, 0, 252),  
  (35, 'foie_gras', 11.4, 4.67, 43.84, 0, 462),  
  (36, 'croque_madame', 10.3, 15.8, 10.6, 0.8, 200.4),  
  (37, 'baby_back_ribs', 16.12, 0, 23.58, 0, 282),  
  (38, 'pork_chop', 27.91, 0, 14.57, 0, 250),  
  (39, 'hot_dog', 10.6, 18.4, 14.84, 0, 247),  
  (40, 'crab_cakes', 22.23, 0.67, 7.98, 0.1, 168),  
  (41, 'ceviche', 11.5, 3.68, 0.79, 0.5, 69),  
  (42, 'fish_and_chips', 30, 14, 12.4, 0, 288),  
  (43, 'grilled_salmon', 23.97, 0.49, 7.56, 0, 171),  
  (44, 'macaroni_and_cheese', 7.94, 21.46, 9.4, 1.1, 203),  
  (45, 'ravioli', 10.79, 19.04, 7.18, 0.7, 188),  
  (46, 'spaghetti_carbonara', 8.05, 25.72, 5.31, 1.5, 191),  
  (47, 'lasagna', 9.96, 17.2, 6.01, 1.3, 163),  
  (48, 'spaghetti_bolognese', 5.3, 12.6, 3.3, 1.7, 100.1),  
  (49, 'pulled_pork_sandwich', 10.8, 30.9, 4.5, 0, 207),  
  (50, 'lobster_roll_sandwich', 16.8, 13.2, 8.8, 0.8, 202.8),  
  (51, 'cheese_plate', 22.21, 4.71, 26.91, 0, 350),  
  (52, 'grilled_cheese_sandwich', 11.04, 33.56, 19, 1.5, 350),  
  (53, 'takoyaki', 7.8, 15.1, 6.5, 0.6, 150.8),  
  (54, 'clam_chowder', 3.62, 7.79, 2.39, 0.8, 67),  
  (55, 'lobster_bisque', 8.07, 5.11, 5.15, 0.1, 100),  
  (56, 'dumplings', 3.3, 20.22, 3.21, 0.7, 124),  
  (57, 'garlic_bread', 7.75, 45.62, 12.78, 2.7, 330),  
  (58, 'samosa', 4.67, 32.21, 17.86, 1.7, 308),
  (59, 'spring_rolls', 3.1, 11.63, 4.43, 1, 98),
  (60, 'french_toast', 8.59, 30.8, 9.43, 1.2, 244),
  (61, 'pancakes', 6.4, 28.3, 9.7, 0, 227),
  (62, 'waffles', 7.3, 48.84, 9.55, 2.5, 310),
  (63, 'strawberry_shortcake', 2.3, 18.2, 10.1, 1.4, 169.8),
  (64, 'churros', 3.6, 30, 1.5, 5.2, 151),
  (65, 'cup_cakes', 2.84, 67.64, 10.93, 1.1, 10.93),
  (66, 'donuts', 5.7, 47, 22.85, 1.4, 412),
  (67, 'macarons', 9.9, 52.8, 21, 0, 445),
  (68, 'chocolate_cake', 4.1, 54.6, 16.4, 2.8, 367),
  (69, 'chocolate_mousse', 4.16, 16.1, 15.11, 0.6, 209),
  (70, 'red_velvet_cake', 6.81, 46.81, 17.18, 1.4, 367),
  (71, 'carrot_cake', 3.86, 52.96, 20.88, 1.1, 408),
  (72, 'guacamole', 1.96, 8.51, 14.31, 6.6, 157),
  (73, 'hummus', 4.86, 20.12, 8.59, 4, 177),
  (74, 'apple_pie', 2.4, 37.1, 12.5, 0, 265),
  (75, 'bread_pudding', 5.27, 23.26, 4.74, 0.9, 153),
  (76, 'pizza', 12.33, 30.33, 11.74, 1.9, 276),
  (77, 'fried_rice', 6.3, 21.06, 6.23, 0.7, 168),
  (78, 'paella', 9.52, 16.68, 5.44, 0.6, 158),
  (79, 'risotto', 6.87, 21.69, 6.43, 0.3, 174),
  (80, 'sushi', 4.3, 29.9, 0.42, 0.8, 143),
  (81, 'pad_thai', 5.79, 22.49, 6.38, 1.5, 170),
  (82, 'tacos', 12.08, 15.63, 12.02, 0, 216),
  (83, 'gnocchi', 2.36, 17.04, 6.24, 1, 133),
  (84, 'greek_salad', 6.66, 3.22, 6.93, 0.9, 101),
  (85, 'caesar_salad', 5.03, 6.52, 14.17, 1.5, 170),
  (86, 'seaweed_salad', 2.98, 13.91, 2.1, 1, 81),
  (87, 'caprese_salad', 10.5, 3.51, 13.7, 1, 177),
  (88, 'nachos', 8.05, 32.15, 16.77, 0, 306),
  (89, 'edamame', 12.9, 9.7, 5.6, 5.6, 131.6),
  (90, 'falafel', 13.31, 31.84, 17.8, 0, 333),
  (91, 'baklava', 6.7, 37.62, 29.03, 2.6, 428),
  (92, 'bruschetta', 9.2, 49, 24.1, 4.8, 458),
  (93, 'french_fries', 3.48, 35.66, 14.06, 3.3, 274),
  (94, 'onion_rings', 4.46, 37.74, 18.69, 0, 332),
  (95, 'poutine', 7, 16.5, 14.3, 1.3, 221.5),
  (96, 'pho', 5.77, 5.24, 2.3, 0.3, 67),
  (97, 'ramen', 9.3, 65.5, 17.1, 2.4, 453),
  (98, 'hot_and_sour_soup', 6.19, 2.03, 3.24, 0.1, 67),
  (99, 'miso_soup', 2.51, 3.24, 1.4, 0.8, 35),
  (100, 'french_onion_soup', 6.6, 16.32, 6.86, 1.2, 153),
  (101, 'beignets', 6.6, 44, 9.4, 1.4, 288.2);

-- Insert data into foodClass table
INSERT INTO foodClass(classID, className) VALUES
  (0, ' '),
  (1, 'Animal'),
  (2, 'Plant-based');

-- Insert data into foodType table
INSERT INTO foodType(typeID, classID, typeName) VALUES
  (0, 0, ' '),
  (1, 1, 'Appetizer'), # Animal
  (2, 1, 'Dessert'), # Animal
  (3, 1, 'Meat'), # Animal
  (4, 1, 'Fish'), # Animal
  (5, 2, 'Dip'), # Plant-based
  (6, 2, 'Fruit'), # Plant-based
  (7, 2, 'Grain'), # Plant-based
  (8, 2, 'Vegetables'), # Plant-based
  (9, 2, 'Sweetener'); # Plant-based

-- Insert data into foodGroup table
INSERT INTO foodGroup(groupID, typeID, groupName) VALUES
  (0, 0, ' '),
  (1, 1, 'Protein'), # Appetizer
  (2, 3, 'Red Meat'), # Meat
  (3, 4, 'Seafood'), # Fish
  (4, 2, 'Dairy'), # Dessert
  (5, 3, 'Poultry'), # Meat
  (6, 7, 'Grains'), # Grain
  (7, 8, 'Vegetables'), # Vegetables
  (8, 6, 'Fruits'), # Fruit
  (9, 2, 'Nuts'), # Dessert
  (10, 9, 'Sugars'), # Sweetener
  (11, 5, 'Legumes'); # Dip

-- Insert data into ingredients table
INSERT INTO ingredients(ingredientID, ingName, groupID) VALUES
  (0, ' ', 0),
  (1, 'Eggs', 1), # Protein
  (2, 'Pork', 2), # Red Meat
  (3, 'Snails', 3), # Seafood
  (4, 'Squid', 3), # Seafood
  (5, 'Mussels', 3), # Seafood
  (6, 'Oysters', 3), # Seafood
  (7, 'Fish', 3), # Seafood
  (8, 'Scallops', 3), # Seafood
  (9, 'Tuna', 3), # Seafood
  (10, 'Cream', 4), # Dairy
  (11, 'Cream Cheese', 4), # Dairy
  (12, 'Mascarpone', 4), # Dairy
  (13, 'Ricotta Cheese', 4), # Dairy
  (14, 'Yogurt', 4), # Dairy
  (15, 'Shrimp', 3), # Seafood
  (16, 'Chicken', 5), # Poultry
  (17, 'Duck', 5), # Poultry
  (18, 'Turkey', 5), # Poultry
  (19, 'Beef', 2), # Red meat
  (20, 'Duck Liver', 2), # Red meat
  (21, 'Ham', 2), # Red meat
  (22, 'Pork', 2), # Red meat
  (23, 'Sausage', 2), # Red meat
  (24, 'Crab', 3), # Seafood
  (25, 'Fish', 3), # Seafood
  (26, 'Salmon', 3), # Seafood
  (27, 'Cheese', 4), # Dairy
  (28, 'Beef', 2), # Red meat
  (29, 'Lobster', 3), # Seafood
  (30, 'Octopus', 3), # Seafood
  (31, 'Clams', 3), # Seafood
  (32, 'Lobster', 3), # Seafood
  (33, 'Dough', 6), # Grains
  (34, 'Bread', 6), # Grains
  (35, 'Potatoes', 7), # Vegetables
  (36, 'Vegetables', 7), # Vegetables
  (37, 'Flour', 6), # Grains
  (38, 'Strawberries', 8), # Fruits
  (39, 'Almond Flour', 9), # Nuts
  (40, 'Chocolate', 10), # Sugars
  (41, 'Cocoa', 10), # Sugars
  (42, 'Carrots', 7), # Vegetables
  (43, 'Avocado', 8), # Fruits
  (44, 'Chickpeas', 11), # Legumes
  (45, 'Apple', 8), # Fruits
  (46, 'Rice', 6), # Grains
  (47, 'Rice Noodles', 6), # Grains
  (48, 'Tortilla', 6), # Grains
  (49, 'Cucumbers', 7), # Vegetables
  (50, 'Lettuce', 7), # Vegetables
  (51, 'Seaweed', 7), # Vegetables
  (52, 'Tomatoes', 7), # Vegetables
  (53, 'Tortilla Chips', 6), # Grains
  (54, 'Soybeans', 11), # Legumes
  (55, 'Chickpeas', 11), # Legumes
  (56, 'Walnuts', 9), # Nuts
  (57, 'Onions', 7), # Vegetables
  (58, 'Wheat Noodles', 6), # Grains
  (59, 'Tofu', 1), # Protein
  (60, 'Sugar', 10); # Sugars;

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
INSERT INTO hasIngredients(foodID, ingredientID) VALUES
  (1, 1),  
  (2, 2),  
  (3, 3),  
  (4, 4),  
  (5, 5),  
  (6, 6),  
  (7, 7),  
  (8, 8),  
  (9, 9),  
  (10, 1),  
  (11, 1),  
  (12, 1), 
  (13, 10),  
  (14, 10),  
  (15, 10),  
  (16, 11),  
  (17, 12),  
  (18, 13),  
  (19, 14),  
  (20, 15),  
  (21, 16),  
  (22, 16),  
  (23, 16),  
  (24, 17),  
  (25, 18),  
  (26, 1),  
  (27, 19),  
  (28, 19),  
  (29, 19),  
  (30, 19),  
  (31, 19),  
  (32, 19),  
  (33, 19),  
  (34, 19),  
  (35, 20),  
  (36, 21),  
  (37, 22),  
  (38, 22),  
  (39, 23),  
  (40, 24),  
  (41, 25),  
  (42, 25),  
  (43, 26),  
  (44, 27),  
  (45, 27),  
  (46, 1),  
  (47, 28),  
  (48, 28),  
  (49, 2),  
  (50, 29),  
  (51, 27),  
  (52, 27),  
  (53, 30),  
  (54, 31),  
  (55, 32),  
  (56, 33),  
  (57, 34),  
  (58, 35),  
  (59, 36),  
  (60, 34),  
  (61, 37),  
  (62, 37),  
  (63, 38),  
  (64, 37),  
  (65, 37),  
  (66, 37),  
  (67, 39),  
  (68, 40),  
  (69, 40),  
  (70, 41),  
  (71, 42),  
  (72, 43),  
  (73, 44),  
  (74, 45),  
  (75, 34),  
  (76, 33),  
  (77, 46),  
  (78, 46),  
  (79, 46),  
  (80, 46),  
  (81, 47),  
  (82, 48),  
  (83, 35),  
  (84, 49),  
  (85, 50), 
  (86, 51), 
  (87, 52),  
  (88, 53),  
  (89, 54),  
  (90, 55),  
  (91, 56),  
  (92, 52),  
  (93, 35),  
  (94, 57),  
  (95, 35),  
  (96, 47),  
  (97, 58),  
  (98, 59),  
  (99, 59),  
  (100, 57),  
  (101, 60);

-- Insert data into user_data table
INSERT INTO userData VALUES
(1, 'Luken', 'Iriondo', 'M', 20, '183', 'iamLudok', 'luken.iriondo@alumni.mondragon.edu', 'angry', 'Light', false, 
'Lose weight', 30, 90, 22, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null),
(2, 'Anje', 'Maiztegi', 'M', 19, '173', 'anje13', 'ange.maiztegui@alumni.mondragon.edu', 'angry', 'Active', false, 
'Lose weight', 30, 90, 22, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null);

-- Insert data into foodList table
INSERT INTO foodList VALUES
  (3, 1, '2025-01-18', 'Lunch', 20),
  (3, 1, '2024-12-12', 'Lunch', 20),
  (3, 1, '2024-12-12', 'Dinner', 20),
  (3, 1, '2024-12-13', 'Lunch', 20),
  (6, 1, '2025-01-14', 'Dinner', 20),
  (2, 2, '2024-12-02', 'Dinner', 20);

-- Insert data into weightGoals table
INSERT INTO weightGoals VALUES
  (1, 1, 85, 80, 18, CURRENT_DATE()),
  (2, 2, 75, 80, 18, CURRENT_DATE());

-- Insert data into restrictions table
INSERT INTO restrictions(restrictionID, userID, groupID, typeID, classID, ingredientID) VALUES
  (1, 1, 1, 1, 1, 1),
  (2, 2, 2, 2, 2, 2);

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