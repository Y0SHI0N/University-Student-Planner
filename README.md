# University-Student-Planner

## Project Brief: QUT Study Planner

### Problem Space:
Current QUT Student Planner Services either have inadequate UI design or lack essential features that students value. QUT students prioritise:
- Minimising the time spent searching for key information.
- Reducing the number of hyperlinks required to navigate to desired pages.
- Clear and concise textual information for quick and efficient reading.

### Resolution:
The "QUT Study Planner" app was developed to provide students with a study planning service that features a user-friendly UI, enhancing ease of use while incorporating innovative functionalities aligned with students' needs.

### Scope:
To achieve its objectives, the application will leverage Jira, an industry-recognised product management tool, to track and document all project requirements. The development process will be structured within designated sprints over 11 weeks to ensure systematic progress and project completion.

### Key Features & Justification:
1. **User-Friendly Interface for Easy Navigation** – Ensures that students can quickly access the information they need without unnecessary complexity, reducing cognitive load and time spent searching for essential resources.
2. **AI-Generated Study-Based Quotes** – Provides motivational content tailored to students based on current timetable, helping to maintain engagement and a positive study mindset.
3. **AI-Generated Weekly Overview Based on Real-Time Timetable Data** – Automatically presents a personalised weekly overview allowing students to better manage their time and workload efficiently.
4. **Study Goals and Tracking** – Enables students to set, track, and achieve their academic goals which promotes accountability and structured study habits.
5. **Campus Map with Real-Time Busy Location Indicators** – Assists students in locating less crowded study areas, improving study efficiency and campus navigation during peak times.

## Contents Table
1) [Getting Started](#getting-Started)
   - [Setting up work environment](#setting-up-work-environment)
2) [Collaboration](#collaboration)
   - [Workflow](#workflow)
   - [Contributing to the repository](#contributing-to-the-repo)
3) [Planning](#planning)
   - [Database Structuring](#database-structuring)
   - [Project Requirements](#project-requirements)
4) [Appendix](#appendix)
---
# Getting Started

## Setting up the work environment
### Dependencies (Amazon corretto and potentially more)

### how to setup IntelliJ 
1. Download the IntelliJ IDEA Community Edition .exe file from [JetBrains.com](https://www.jetbrains.com/idea/download/?section=windows)
2. Select Installation location
3. Select Installation options
4. chose a start menu folder
#### IntelliJ should now be correctly installed.

### how to setup Amazon Corretto on IntelliJ
1. Select correct operating system and download Amazon Corretto Version 21 .msi file from [Corretto.aws](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html)
2. When a new project is created in IntelliJ select Amazon Corretto version 21 as the JDK 
#### The project will utilise Amazon Corretto as its JDK because it is Free, has multiplatform support and has optimised memory management compared to other JDK's

### how to setup a JavaFX project on IntelliJ
1. download a SDK for JavaFX from [JavaFX](https://gluonhq.com/products/javafx/)
2. while in IntelliJ navigate to modify run configuration
3. select Modify options and navigate to add VM options
4. Input the JavaFX SDK file directory
#### JavaFX will be responsible for the GUI for the project
### how to setup Scenebuilder
---
# Collaboration

## Workflow 
### Coding Practices
- when contributing code to this repository ensure camelcase is used for variable and method naming
- when contributing to this repository ensure variable and method names are given meaning in respect to the application
##### Example Coding stucture
```java
public void someMethodName() {
    variableName.methodName();
}
```

## Contributing to the repo
1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to your branch (`git push origin feature-name`)
5. Open a Pull Request
---
# Planning
## database structuring 
### User Signup Data
| Column          | Type    | Description                                |
|----------------|--------|--------------------------------------------|
| `Student_Number`          | VARCHAR | Primary key, Allocated Student id     |
| `First_Name`   | CHAR    | Users first name                          |
| `Last_Name` | CHAR    | Users last name                   |
| `Email`    | VARCHAR    | Users Email (example@outlook.com)      |
| `Phone_Number` | VARCHAR | Users Phone Number (+61 41234567 or 0412345678) |
| `Login_Password`      | VARCHAR    | Users Password to login |
### User app usage Data
| Column          | Type    | Description                                |
|----------------|--------|--------------------------------------------|
| `Student_Number`          | VARCHAR | Part of **composite primary key**, Allocated Student id     |
| `date_Modified`          | VARCHAR | Part of **composite primary key**, datetime of data entry     |
| `GPA`          | INT | Users actual GPA KPI     |
| `GPA_GOAL`   | INT    | Users GPA Goal                          |
| `HOURS_STUDIED` | TIME    | Users actual study time KPI                   |
| `HOURS_STUDIED_GOAL`    | TIME    | Users study time goal       |
| `ATTENDANCE_RATE` | FLOAT | Users actual attendance rate KPI stored as a float to be represented as a percentage |
| `ATTENDANCE_RATE_GOAL`      | FLOAT    | Users attendance rate goal |
| `UNITS_ENROLLED`      | VARCHAR    | a list of the users enrolled Units |
> **Primary Key**: (`Student_Number`, `date_Modified`)

### User Timetable Data
| Column          | Type    | Description                                |
|----------------|--------|--------------------------------------------|
| `event_ID`          | INT | Part of **composite primary key**, Allocated event id     |
| `event_Name`          | VARCHAR | Primary key, Allocated events name     |
| `Student_Number`          | VARCHAR | Part of **composite primary key**, Allocated Student id     |
| `EVENT_TYPE`          | VARCHAR | what type of event it is (study,work,unit lecture)     |
| `EVENT_START_DATETIME`   | DATETIME    | Expected event start time                          |
| `EVENT_END_DATETIME` | DATETIME    | Expected event end time                   |
| `EVENT_Location` | VARCHAR    | the location the event was attended                   |
| `EVENT_ATTENDANCE`    | INT    | 1 represents did attend and 0 represents did not attend       |
> **Primary Key**: (`Student_Number`, `event_ID`)

## Project Requirements
| ID  | Name                        | Description                                                                                   | Importance | Status      |
|-----|-----------------------------|-----------------------------------------------------------------------------------------------|------------|-------------|
| 1   | View Personal Information   | Enable students to view their personal information in the system.                            | Medium     | Completed   |
| 2   | Edit Personal Information   | Allow students to edit their personal information from their profile page.                   | Medium     | Completed   |
| 3   | View Study Progress         | Display study progress details on the home page for students to monitor academic progress.   | Medium     | In Progress |
| 4   | Edit Study Progress         | Allow students to edit their study progress details directly from the home page.             | Medium     | In Progress |
| 5   | Add Calendar Event Types    | Let students add different event types (e.g., sessions, exams) to manage schedules.          | Medium     | Completed   |
| 6   | Edit Calendar Events        | Enable students to edit calendar events via a popup without navigating away.                 | Medium     | Completed   |
| 7   | Delete Calendar Events      | Allow students to delete incorrect or outdated events from their calendar.                   | High       | Completed   |
| 8   | Weekly Overview             | Provide students with AI-generated insights into their study habits and areas for improvement.| High      | Completed   |
| 9   | AI Heatmap Overview         | Display an AI-based overview of class heatmap and attendance data.                           | High       | In Progress |
| 10  | Input Class Schedule        | Enable students to manually input their class schedule for personalised planning.            | High       | Completed   |
| 11  | Navigation Bar              | Add UI control elements (e.g., scroll bar, combo box) to improve interface usability.         | Low       | Completed   |
---
# Appendix
### Documentation
- JavaFx UI/UX components via [Oracle](https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/ui_controls.htm#JFXUI336)
- JDBC Documentation via [Geeks for Geeks](https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/)
- JavaDoc Documentation via [Geeks for Geeks](https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/)
---
