DROP SCHEMA IF EXISTS `music_room_reservation`;

CREATE SCHEMA `music_room_reservation`;

USE `music_room_reservation`;

-- Create table script
CREATE TABLE user (
    userId INT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    is_admin boolean NOT NULL
);

CREATE TABLE music_room (
    musicRoomId INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('available', 'reserved', 'out of service') NOT NULL,
    name VARCHAR(100) NOT NULL,
	floor VARCHAR(10) NOT NULL,
    building VARCHAR(50) NOT NULL,
    musicalInstrument VARCHAR(255),
    minCapacity INT NOT NULL,
    maxCapacity INT NOT NULL,
    feature VARCHAR(255)
);

CREATE TABLE time_slot (
    timeSlotId INT PRIMARY KEY AUTO_INCREMENT,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL
);

CREATE TABLE reservation (
    reservationId INT PRIMARY KEY AUTO_INCREMENT,
    phoneNumber VARCHAR(20) NOT NULL,
    date DATE NOT NULL,
    createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    timeSlotId INT,
    musicRoomId INT,
    userId INT,
    FOREIGN KEY (timeSlotId) REFERENCES time_slot(timeSlotId),
    FOREIGN KEY (musicRoomId) REFERENCES music_room(musicRoomId),
    FOREIGN KEY (userId) REFERENCES user(userId)
);

-- ---------------------------------------------------------------------
-- Insert script
CREATE TABLE `user` (
    `oid` char(36) NOT NULL UNIQUE,
    `name` varchar(100) NOT NULL,
    `username` varchar(50) NOT NULL,
    `email` varchar(50) NOT NULL,
    `password` varchar(100) NOT NULL,
    `role` enum('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`oid`)
);

INSERT INTO `user` (`oid`, `name`, `username`, `email`, `password`, `role`) 
VALUES ('0712334f-4982-4d26-a7ef-4ad0ff53cb18', 'ITBKK PICHET', 'itbkk.pichet', 
'itbkk.pichet@ad.sit.kmutt.ac.th', 
'$argon2id$v=19$m=16384,t=2,p=1$aFNjdDg2S2hSWVg1OE9uaw$4dBU2LykSRgNJKL4s5IqHmIiQr+b3HDAn0xPruz6l8Y',
 'ADMIN');
 INSERT INTO `user` (`oid`, `name`, `username`, `email`, `password`, `role`) 
VALUES ('995a830b-6c62-45e6-ab89-1077dff55a72', 'ITBKK SIAM', 'itbkk.siam', 
'itbkk.siam@ad.sit.kmutt.ac.th', 
'$argon2id$v=19$m=16384,t=2,p=1$ZExDM1kxbTJmZllHMjBEZQ$dEDcThzMQkNx/J7bqN6klo73+/b+GzMj1st/UgbsTJg',
 'ADMIN');
 INSERT INTO `user` (`oid`, `name`, `username`, `email`, `password`, `role`) 
VALUES ('e12b6e98-7d1b-4b98-bb7d-56b7c5f84f1e', 'John Doe', 'john.doe', 
'john.doe@example.com', 

CREATE USER 'user'@'%' IDENTIFIED BY 'bm(B3hrkET';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'GZ4ueQS*CM';

-- Grant privileges
GRANT INSERT, DELETE ON music_room_reservation.reservation TO 'user'@'%';
GRANT ALL PRIVILEGES ON music_room_reservation.* TO 'admin'@'localhost';

--- Insert music rooms with floor information
INSERT INTO music_room (status, name, floor, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Melody Haven', '1', 'A', 'Upright piano', 1, 3, 'Music stand, adjustable stool, soundproofing'),
('available', 'Harmony Hub', '1', 'A', 'Digital piano', 3, 5, 'Several music stands, four chairs, soundproofing'),
('available', 'Percussion Paradise', '5', 'E', 'Acoustic drum set, electronic drum kit', 1, 3, 'Soundproofing, recording equipment'),
('available', 'Pianist\'s Pinnacle', '5', 'E', 'Two grand pianos', 1, 3, 'Acoustic treatment, recording capabilities'),
('available', 'Synth Symphony', '5', 'E', 'Synthesizers, computers with DAWs, MIDI controllers', 3, 5, 'Studio monitors, soundproofing'),
('available', 'Theory Throne', '5', 'E', NULL, 3, 5, 'Whiteboards, projector, desks, audio playback system'),
('available', 'History Hall', '5', 'E', NULL, 10, 15, 'Multimedia setup with large screen, audio system, desks'),
('available', 'Maestro’s Masterclass', '5', 'E', 'Grand piano', 10, 30, 'Seating for 30, projector, sound system');

-- Rehearsal rooms
INSERT INTO music_room (status, name, floor, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Rhythm Retreat', '2', 'B', 'Drum set, keyboard', 6, 10, 'Amps for guitars and bass, PA system, music stands, seating for up to 10 musicians'),
('available', 'Lullaby Melody', '2', 'B', 'Grand piano', 10, 15, 'Risers for choir, music stands, acoustic treatment');

-- Recording studios 
INSERT INTO music_room (status, name, floor, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Echo Chamber', '3', 'C', 'Basic recording setup', 3, 5, 'Audio interface, studio monitors, condenser microphones, soundproofing'),
('available', 'Sonic Sanctuary', '3', 'C', 'High-end recording equipment', 6, 10, 'Mixing console, isolation booth, high-end microphones, outboard gear, soundproofing');

-- Performance spaces
INSERT INTO music_room (status, name, floor, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Recital Royale', '4', 'D', 'Grand piano', 30, 100, 'Stage, seating for 100, sound and lighting system'),
('available', 'Concert Coliseum', '4', 'D', 'Multiple instruments for orchestra', 100, 500, 'Large stage, seating for 500, orchestra pit, advanced acoustics, sound and lighting system');

INSERT INTO time_slot (startTime, endTime) VALUES ('08:30:00', '10:20:00');
INSERT INTO time_slot (startTime, endTime) VALUES ('10:30:00', '12:20:00');
INSERT INTO time_slot (startTime, endTime) VALUES ('12:30:00', '14:20:00');
INSERT INTO time_slot (startTime, endTime) VALUES ('14:30:00', '16:20:00');

INSERT INTO reservation (phoneNumber, date, timeSlotId, musicRoomId, userId) VALUES 
('1234567890', '2024-08-04', 1, 1, 1001),
('0987654321', '2024-08-05', 2, 2, 1002),
('1122334455', '2024-08-06', 3, 3, 1003);
