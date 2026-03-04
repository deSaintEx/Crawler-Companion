-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_details CASCADE;
DROP TABLE IF EXISTS player_character CASCADE;
DROP TABLE IF EXISTS plot CASCADE;
DROP TABLE IF EXISTS user_character CASCADE;
DROP TABLE IF EXISTS party CASCADE;
DROP TABLE IF EXISTS campaign CASCADE;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_users PRIMARY KEY (user_id)
);

CREATE TABLE user_details(
	full_name varchar(100) NOT NULL,
	user_id int NOT NULL,
	in_a_party boolean NOT NULL,
	in_a_campaign  boolean NOT NULL,
	has_character boolean NOT NULL,
	CONSTRAINT PK_user_details PRIMARY KEY (user_id),
	CONSTRAINT FK_user_details FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE player_character(
	character_id serial NOT NULL,
	character_name varchar(100) NOT NULL UNIQUE,
	character_level int NOT NULL,
	species varchar(100) NOT NULL,
	character_class varchar(100) NOT NULL,
	spells varchar(100),
	weapons varchar(100),
	description varchar(500) NOT NULL,
	stats varchar(100),
	CONSTRAINT PK_character_id PRIMARY KEY (character_id)
);

CREATE TABLE user_character(
	user_id int NOT NULL,
	character_id int NOT NULL,
	CONSTRAINT PK_user_character PRIMARY KEY (user_id, character_id),
	CONSTRAINT FK_users FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	CONSTRAINT FK_player_character FOREIGN KEY (character_id) REFERENCES player_character(character_id)
);

CREATE TABLE plot(
	plot_id serial NOT NULL,
	plot_title varchar(100) NOT NULL UNIQUE,
	description varchar(500) NOT NULL,
	CONSTRAINT PK_plot_id PRIMARY KEY (plot_id)
);

CREATE TABLE party(
	party_id serial NOT NULL,
	party_name varchar(100) NOT NULL UNIQUE,
	CONSTRAINT PK_party_id PRIMARY KEY (party_id)
);

CREATE TABLE party_characters(
	party_id int NOT NULL,
	character_id int NOT NULL,
	CONSTRAINT PK_party_character PRIMARY KEY (party_id, character_id),
	CONSTRAINT FK_party FOREIGN KEY (party_id) REFERENCES party(party_id),
	CONSTRAINT FK_characters FOREIGN KEY (character_id) REFERENCES player_character(character_id)
);

CREATE TABLE campaign(
	campaign_id serial NOT NULL,
	campaign_name varchar(100) NOT NULL,
	description varchar(500) NOT NULL,
	party_id int NOT NULL,
	plot_id int NOT NULL,
	CONSTRAINT PK_campaign_id PRIMARY KEY (campaign_id),
	CONSTRAINT FK_plot_id FOREIGN KEY (plot_id) REFERENCES plot(plot_id)
);

ALTER TABLE campaign
ADD CONSTRAINT FK_party_id FOREIGN KEY (party_id) REFERENCES party(party_id);


-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN'),
	('untadaike', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
	('poorplayer', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
	('deSaintEx', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_ADMIN');
	
INSERT INTO
	user_details (full_name, user_id, in_a_party, in_a_campaign, has_character)
VALUES
	('Unnamed User', 1, true, true, true),
	('Unnamed Admin', 2, false, false, false),
	('Galen Molk', 3, true, true, true),
	('Julian Remulla', 4, true, true, true),
	('Benjamin Bonenfant', 5, false, false, false);
	
INSERT INTO 
	player_character (character_name, character_level, species, character_class, spells, weapons, description, stats)
VALUES
	('Userus Maximus', 1, 'human', 'barbarian', NULL, 'battleaxe, claymore, and mace', 'A warrior through and through--Userus has never been defeated in single combat.', 'STR: 10, INT: 5, DEX: 7'),
	('Floki the Minstrel', 1, 'human', 'bard', 'Charm Enemy', 'shortsword, blowgun, and lute', 'A classical player full of Viking lore and equipped with a strong voice, but he is not afraid to use his instrument to fight.', 'STR: 8, INT: 9, DEX: 6'),
	('Voronwe', 1, 'elf', 'rogue', 'Walk in Shadow', 'rapier and dagger', 'He is capable of being neither heard nor seen, but his attacks are deadly whether in stealth or in the open', 'STR: 7, INT: 6, DEX: 10');
	
INSERT INTO
	plot (plot_title, description)
VALUES 
	('Banning the Bandits', 'A gang of thugs has taken over the town. Your party is called upon to confront them and restore order. You will be handsomely rewarded.');

INSERT INTO
	party (party_name)
VALUES
	('The Plebs');
	
INSERT INTO
	party_characters (party_id, character_id)
VALUES
	(1, 2),
	(1, 3);
	
INSERT INTO
	campaign (campaign_name, party_id, plot_id, description)
VALUES
	('The Plebs Save the Town', 1, 1, 'Floki and Voronwe come to the rescue when the town is beset with bandits.');


COMMIT TRANSACTION;
