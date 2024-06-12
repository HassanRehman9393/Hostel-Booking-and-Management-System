
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    contactNo VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    userType VARCHAR(50) NOT NULL
);
CREATE TABLE hostel_advertisements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_name VARCHAR(255) NOT NULL,
    facilities VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    number_of_rooms INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    hostel_image_url VARCHAR(255) NOT NULL
);
CREATE TABLE Room (
    roomId INT AUTO_INCREMENT PRIMARY KEY,
    hostelId INT,
    roomType VARCHAR(255) NOT NULL,
    availableRooms INT NOT NULL,
    isAvailable BOOLEAN NOT NULL,
    FOREIGN KEY (hostelId) REFERENCES hostel_advertisements(id)
);

SELECT r.roomId
FROM Room r
JOIN hostel_advertisements ha ON r.hostelId = ha.id
WHERE ha.hostel_name = 'Sunshine';
SELECT r.roomId
FROM Room r
JOIN hostel_advertisements ha ON r.hostelId = ha.id
WHERE ha.hostel_name = 'Sunshine' AND r.roomType = 'single';

CREATE TABLE Booking (
    bookingId INT AUTO_INCREMENT PRIMARY KEY,
    hostelName VARCHAR(255) NOT NULL,
    roomId INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    email VARCHAR(255) NOT NULL,
    contactNo VARCHAR(255) NOT NULL,
    roomType VARCHAR(255) NOT NULL,
    FOREIGN KEY (roomId) REFERENCES Room(roomId)
);
CREATE TABLE complains (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    sender TEXT NOT NULL,
    recipient TEXT NOT NULL,
    complain TEXT NOT NULL,
    description TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE leave_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resident_id INT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    requested_date DATE NOT NULL,
    leave_date DATE NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    forwarding_address VARCHAR(255) NOT NULL,
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    approval_date DATE,
    approval_comments TEXT,
    approval_status VARCHAR(10) DEFAULT 'Pending'
);
CREATE TABLE Feedback (
    id SERIAL PRIMARY KEY,
    userName VARCHAR(255) NOT NULL,
    ratings DOUBLE PRECISION NOT NULL,
    feedback TEXT NOT NULL,
    hostelName VARCHAR(255) NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);