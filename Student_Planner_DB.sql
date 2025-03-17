CREATE Database if not exists Student_Planner_DB;
USE Student_Planner_DB;

create table if not exists User_Signup_Data(
StudentNumber VARCHAR(10) UNIQUE NOT NULL,
Firstname CHAR(20) NOT NULL,
LastName CHAR(20) NOT NULL,
Email VARCHAR(225) NOT NULL,
PhoneNumber CHAR(10),
LoginPassword VARCHAR(15) NOT NULL,
PRIMARY KEY(StudentNumber)
);
create table if not exists User_Collected_Data(
StudentNumber VARCHAR(10) UNIQUE NOT NULL,
GPA INT,
GPAGoal INT,
HoursStudied TIME,
HoursStudiedGoal TIME,  
AttendanceRate FLOAT,
AttendanceRateGoal FLOAT,
UnitsEnrolled VARCHAR(225),
PRIMARY KEY(StudentNumber)
);
create table if not exists User_Timetable_Data(
StudentNumber VARCHAR(10) UNIQUE NOT NULL,
EventType VARCHAR(20) NOT NULL,
EventStartDate DATETIME NOT NULL,
EventEndDate DATETIME NOT NULL,
EventLocation VARCHAR(20) NOT NULL,
EventAttendance INT,
PRIMARY KEY(StudentNumber)
);

CREATE USER if not exists 'student_user'@'localhost' IDENTIFIED BY 'SecurePass123';
GRANT ALL PRIVILEGES ON student_planner_db.* TO 'student_user'@'localhost';
FLUSH PRIVILEGES;
