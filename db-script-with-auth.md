``` DROP SCHEMA IF EXISTS `music-room-reservation`;

CREATE SCHEMA `music-room-reservation`;

USE `music-room-reservation`;

-- Create table script
CREATE TABLE user_type (
    userTypeId INT PRIMARY KEY AUTO_INCREMENT,
    typeName VARCHAR(50) NOT NULL
);

CREATE TABLE user (
    userId INT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    user_type INT NOT NULL,
    FOREIGN KEY (userTypeId) REFERENCES user_type(userTypeId)
);

CREATE TABLE music_room (
    musicRoomId INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('available', 'reserved', 'out of service') NOT NULL,
    name VARCHAR(255) NOT NULL,
    building VARCHAR(50) NOT NULL,
    musicalInstrument VARCHAR(255),
    minCapacity INT NOT NULL,
    maxCapacity INT NOT NULL,
    feature VARCHAR(255)
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

CREATE TABLE time_slot (
    timeSlotId INT PRIMARY KEY AUTO_INCREMENT,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL
);

-- ---------------------------------------------------------------------
-- Insert script
INSERT INTO user_type (typeName) VALUES
('user'),
('admin');

INSERT INTO User (userId, email, password, userTypeId) VALUES
(1001, 'kobe.mc@cpu.ac.th', MD5('test'), 1), 
(1002, 'jimmy.mc@cpu.ac.th', MD5('test'), 1), 
(1003, 'lauren.mc@cpu.ac.th', MD5('test'), 1), 
(9999, 'peekie.mc@cpu.ac.th', MD5('test'), 2); 

GRANT INSERT ON reservation TO 'user'@'localhost';

GRANT ALL PRIVILEGES ON reservation TO 'admin'@'localhost';

-- practice rooms 
INSERT INTO music_room (status, name, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Melody Haven', 'A', 'Upright piano', 1, 3, 'Music stand, adjustable stool, soundproofing'),
('available', 'Harmony Hub', 'A', 'Digital piano', 3, 5, 'Several music stands, four chairs, soundproofing'),
('available', 'Percussion Paradise', 'E', 'Acoustic drum set, electronic drum kit', 1, 3, 'Soundproofing, recording equipment'),
('available', 'Pianist\'s Pinnacle', 'E', 'Two grand pianos', 1, 3, 'Acoustic treatment, recording capabilities'),
('available', 'Synth Symphony', 'E', 'Synthesizers, computers with DAWs, MIDI controllers', 3, 5, 'Studio monitors, soundproofing'),
('available', 'Theory Throne', 'E', NULL, 3, 5, 'Whiteboards, projector, desks, audio playback system'),
('available', 'History Hall', 'E', NULL, 10, NULL, 'Multimedia setup with large screen, audio system, desks'),
('available', 'Maestro’s Masterclass', 'E', 'Grand piano', 10, NULL, 'Seating for 30, projector, sound system');

-- rehearsal rooms
INSERT INTO music_room (status, name, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Rhythm Retreat', 'B', 'Drum set, keyboard', 6, 10, 'Amps for guitars and bass, PA system, music stands, seating for up to 10 musicians'),
('available', 'Lullaby Melody', 'B', 'Grand piano', 10, NULL, 'Risers for choir, music stands, acoustic treatment');

-- recording studios 
INSERT INTO music_room (status, name, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Echo Chamber', 'C', 'Basic recording setup', 3, 5, 'Audio interface, studio monitors, condenser microphones, soundproofing'),
('available', 'Sonic Sanctuary', 'C', 'High-end recording equipment', 6, 10, 'Mixing console, isolation booth, high-end microphones, outboard gear, soundproofing');

-- performance spaces
INSERT INTO music_room (status, name, building, musicalInstrument, minCapacity, maxCapacity, feature) VALUES
('available', 'Recital Royale', 'D', 'Grand piano', 10, NULL, 'Stage, seating for 100, sound and lighting system'),
('available', 'Concert Coliseum', 'D', 'Multiple instruments for orchestra', 10, NULL, 'Large stage, seating for 500, orchestra pit, advanced acoustics, sound and lighting system');

INSERT INTO time_slot (startTime, endTime) VALUES
('08:30:00', '10:20:00'),
('10:30:00', '12:20:00'),
('12:30:00', '14:20:00'),
('14:30:00', '16:20:00');

INSERT INTO reservation (phoneNumber, date, timeSlotId, musicRoomId, userId) VALUES
('1234567890', '2024-06-22', 1, 1, 1001),  -- User: kobe.mc@cpu.ac.th, Time Slot: 08:30-10:20, Room: Melody Haven
('2345678901', '2024-06-23', 2, 3, 1002),  -- User: jimmy.mc@cpu.ac.th, Time Slot: 10:30-12:20, Room: Percussion Paradise
('2345678901', '2024-06-23', 4, 12, 1002), -- User: jimmy.mc@cpu.ac.th, Time Slot: 08:30-10:20, Room: Echo Chamber
('3456789012', '2024-06-24', 3, 6, 1003),  -- User: lauren.mc@cpu.ac.th, Time Slot: 12:30-14:20, Room: Theory Throne
('4567890123', '2024-06-25', 4, 9, 1001);  -- User: kobe.mc@cpu.ac.th, Time Slot: 14:30-16:20, Room: Recital Royale ```
