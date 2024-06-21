DROP SCHEMA IF EXISTS `music-room-reservation`;

CREATE SCHEMA `music-room-reservation`;

USE `music-room-reservation`;

CREATE TABLE user (
    userId INT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE music_room (
    musicRoomId INT PRIMARY KEY AUTO_INCREMENT,
    musicRoomStatus ENUM('available', 'occupied', 'under maintenance') NOT NULL,
    name VARCHAR(100) NOT NULL,
    building VARCHAR(100) NOT NULL,
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
    FOREIGN KEY (musicRoomId) REFERENCES music_rmusic_roomoom(musicRoomId),
    FOREIGN KEY (userId) REFERENCES User(userId)
);

INSERT INTO time_slot (startTime, endTime) VALUES
('08:30:00', '10:20:00'),
('10:30:00', '12:20:00'),
('12:30:00', '14:20:00'),
('14:30:00', '16:20:00');
