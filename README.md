# University-Student-Planner

breif overview of what can be found in the following repo
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
`public void someMethodName(){
variableName.methodName();
}`

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
(can implement MySQL with a JDBC[https://www.geeksforgeeks.org/establishing-jdbc-connection-in-java/] note: will have to reference this if we use its code)

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
| 12 | Database Structure Creation   | Design and create necessary database (.db) files for the application. | High  | - | Incomplete |
| 13 | Database Integration          | Integrate the created database files with the application’s backend. | High  | - | Incomplete |
| 14 | User Interface Development    | Develop an intuitive and user-friendly GUI for the application. | High  | - | Incomplete |

---
# Testing
## Demo Video

## UI/UX navigation 
(show different screens and explain whats happening)
## Backend Logical tests
(if backend can be unit tested to confirm logic put here)
---
# Appendix
(links used to aid in project development)
---
