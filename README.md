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
   - [User Stories](#user-stories)
   - [UML Class Diagram](#uml-class-diagram)
   - [Database Structuring](#database-structuring)
   - [Project Requirements](#project-requirements)
4) [Testing](#testing)
   - [Demo Video](#demo-video)
   - [UI/UX Navigation](#uiux-navigation)
   - [Backend Logic Testing](#backend-logical-tests)
6) [Appendix](#appendix)
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
## User Stories

## UML class diagram
![Alt text](/UML_Diagram.png)
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
| `Student_Number`          | VARCHAR | Primary key, Allocated Student id     |
| `GPA`          | INT | Users actual GPA KPI     |
| `GPA_GOAL`   | INT    | Users GPA Goal                          |
| `HOURS_STUDIED` | TIME    | Users actual study time KPI                   |
| `HOURS_STUDIED_GOAL`    | TIME    | Users study time goal       |
| `ATTENDANCE_RATE` | FLOAT | Users actual attendance rate KPI stored as a float to be represented as a percentage |
| `ATTENDANCE_RATE_GOAL`      | FLOAT    | Users attendance rate goal |
| `UNITS_ENROLLED`      | VARCHAR    | a list of the users enrolled Units |
### User Timetable Data
| Column          | Type    | Description                                |
|----------------|--------|--------------------------------------------|
| `Student_Number`          | VARCHAR | Primary key, Allocated Student id     |
| `EVENT_TYPE`          | VARCHAR | what type of event it is (study,work,unit lecture)     |
| `EVENT_START_DATETIME`   | DATETIME    | Expected event start time                          |
| `EVENT_END_DATETIME` | DATETIME    | Expected event end time                   |
| `EVENT_ATTENDANCE`    | INT    | 1 represents did attend and 0 represents did not attend       |

## Project Requirements
| ID  | Name                          | Description  | Importance  | Expected End Date | Status  |
|----|------------------------------|-----------------------------------------------|-------------|----------------|-----------|
| 1  | Live Heatmap                 | Enable real-time display of university heatmap data. | High  | - | Incomplete |
| 2  | Predictive Heatmap            | Implement AI-based predictions for heatmap trends based on past bookings and usage patterns. | High  | - | Incomplete |
| 3  | Student KPI Dashboard         | Display key performance indicators (KPIs) for students in a clear and interactive format. | Medium | - | Incomplete |
| 4  | Performance Goal Management   | Allow users to set custom goals or receive AI-generated recommendations to enhance performance. | High  | - | Incomplete |
| 5  | AI-Generated Study Quotes     | Provide AI-curated motivational or relevant study quotes based on the user’s workload. | Low | - | Incomplete |
| 6  | Weekly Performance Report     | Generate automated weekly performance reports based on KPIs and goals, with AI-driven suggestions for improvement. | High | - | Incomplete |
| 7  | Assignment Deadline Tracker   | Display upcoming assignment details, including name, class, weighting, and due date. | High  | - | Incomplete |
| 8  | Calendar Event Management     | Allow users to add and manage events in their personal calendar. | Medium | - | Incomplete |
| 9  | Assignment Management         | Enable users to create, edit, and manage their assignments efficiently. | High  | - | Incomplete |
| 10 | Timetable Filtering           | Implement filtering options for timetables based on unit codes, event types (e.g., study sessions), and tags. | Medium | - | Incomplete |
| 11 | Timetable View Modes          | Provide options to switch between weekly, monthly, and widget-based timetable views. | Medium | - | Incomplete |
| 12 | Database Structure Creation   | Design and create necessary database (.db) files for the application. | High  | - | Complete |
| 13 | Database Integration          | Integrate the created database files with the application’s backend. | High  | - | Complete |
| 14 | User Interface Development    | Develop an intuitive and user-friendly GUI for the application. | High  | - | Complete |
| 15 | Goal Progress Line Chart      | Show how the goal has changed over time represented via a line chart. | Medium | - | Incomplete |
| 16 | Profile Page                  | Have a profile page that allows easy information reading and updates. | Medium | - | Incomplete |

---
# Testing
## Demo Video

## UI/UX navigation 
(show different screens and explain whats happening)
## Backend Logical tests
(if backend can be unit tested to confirm logic put here)
---
# Appendix
### Documentation
- JavaFx UI/UX components via [Oracle](https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/ui_controls.htm#JFXUI336)
- JDBC Documentation via [Geeks for Geeks](https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/)
- Hakari Documentation via [baeldung](https://www.baeldung.com/hikaricp)
- JavaDoc Documentation via [Geeks for Geeks](https://www.geeksforgeeks.org/what-is-javadoc-tool-and-how-to-use-it/)
- OpenAi API Repository via [Github](https://github.com/openai/openai-java)
---
