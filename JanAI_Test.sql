-- First you should run the DB Creation .sql
USE JANAI;

INSERT INTO user_data VALUES(1, 'Luken', 'Iriondo', 'M', 20, '183', 'iamLudok', 'luken.iriondo@alumni.mondragon.edu',
	'angry', 'Light', false, 'Lose weight', 30, 90, null, null, null, null, null, null, null, null, null, null, null,
    null, null, null);
INSERT INTO user_data VALUES(2, 'Anje', 'Maiztegi', 'M', 19, '173', 'anje13', 'ange.maiztegui@alumni.mondragon.edu',
	'angry', 'Active', false, 'Lose weight', 30, 90, null, null, null, null, null, null, null, null, null, null, null,
    null, null, null);
    
INSERT INTO weightGoals VALUES(1, 1, 85, 80, 18, CURRENT_DATE());
INSERT INTO weightGoals VALUES(2, 2, 75, 80, 18, CURRENT_DATE());

select * from user_data;