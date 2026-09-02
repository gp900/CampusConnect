# Product Requirements Document (PRD) — Campus Connect

## 1. Product Vision
**"One App. Everything for Students."**
Campus Connect is designed to be the definitive digital ecosystem for college and university students. It consolidates scattered campus utilities—schedules, attendance, coursework, campus news, clubs, lost-and-found, and student grievances—into a single, modern, and accessible Android application.

---

## 2. Problem Statement
College students currently rely on fragmented channels for campus information:
- WhatsApp/Telegram groups for class notes and lost items.
- Physical notice boards or clunky web portals for announcements.
- Paper registers or separate apps for tracking personal attendance.
- Manual alarm clocks or scattered calendars for assignment deadlines.

This fragmentation leads to missed deadlines, poor attendance tracking, lack of campus engagement, and frustration. Campus Connect solves this by centralizing all academic and student life workflows under a unified, user-friendly mobile experience.

---

## 3. Target Audience & User Personas

### Target Audience
Undergraduate and graduate university/college students, campus club managers, class representatives, and campus administrators.

### User Personas

#### Persona 1: Alex — The Busy Undergraduate
- **Age:** 20 | **Year:** Junior (Computer Science)
- **Goals:** Keep track of class schedules, monitor attendance to avoid shortfalls, never miss assignment submission deadlines, and quickly access lecture notes.
- **Pain Points:** Forgets which room the next class is in; struggles to calculate attendance percentages manually.

#### Persona 2: Sarah — The Campus Club Leader & Event Organizer
- **Age:** 21 | **Year:** Senior (Business Administration)
- **Goals:** Promote club events, broadcast campus announcements, recruit new members, and organize student activities.
- **Pain Points:** Social media broadcasts get buried under non-college noise; hard to verify event RSVPs.

---

## 4. Goals & Non-Goals

### Product Goals
1. Provide a central student dashboard with real-time class, attendance, and assignment updates.
2. Enable seamless academic tracking (timetable, attendance calculation, assignment deadlines).
3. Facilitate peer-to-peer campus engagement (notes sharing, lost & found, events, clubs).
4. Provide an official grievance/complaint lodging system for campus issues.
5. Work reliably online and cache essential data for offline access.

### Non-Goals
1. Financial transaction processing (e.g., fee payments, canteen billing) in initial releases.
2. Video/Audio streaming for live lectures.
3. Chat/Direct Messaging system between individual students (focus remains on structured communication).

---

## 5. Core Modules & Feature Breakdown

1. **Student Profile:** Personal info, college email, department, semester, roll number, avatar, academic stats.
2. **Dashboard:** High-level overview: greeting, next class countdown, attendance summary, upcoming assignments, campus feed snippet, quick action buttons.
3. **Timetable:** Daily/weekly schedule breakdown with time slots, subject names, room numbers, and professor names.
4. **Attendance Tracker:** Subject-wise percentage tracker with target attendance goals, "bunk/present/absent" logging, and safety margin calculator.
5. **Assignment Manager:** List of active and completed assignments, deadline dates, submission status, priority badges, and reminder notifications.
6. **Notes Sharing:** Subject-categorized PDF/image notes upload and download, user ratings, and search.
7. **Events:** Campus event discovery, date filter, registration/RSVP, venue details, and calendar integration.
8. **Lost & Found:** Item postings with image, location, status (Lost/Found/Claimed), and contact details.
9. **Clubs:** Club directory, executive member info, joining requests, club updates, and upcoming club activities.
10. **Campus Announcements:** Official broadcasts tagged by urgency (Notice, Urgent, Exam, Holiday).
11. **Complaint System:** Lodge campus infrastructure/academic complaints, track resolution status (Submitted, In Review, Resolved).
12. **Notifications:** Real-time push and local notifications for classes, deadlines, announcements, and complaint status updates.
13. **Authentication:** Firebase Auth with email/password, registration with academic details, password reset, and session persistence.
14. **Settings:** Profile edit, Dark Mode toggle, notification preferences, terms, privacy policy, and logout.

---

## 6. Functional & Non-Functional Requirements

### Functional Requirements
- **FR-01:** Users must be able to register with a valid college email, password, full name, department, and semester.
- **FR-02:** User authentication state must persist across app launches until explicitly logged out.
- **FR-03:** Timetable must display classes filtered by the student's department and semester.
- **FR-04:** Attendance tracker must calculate overall and per-subject attendance percentage dynamically `(Attended / Total) * 100`.
- **FR-05:** Students must receive notifications 15 minutes prior to their next scheduled class.
- **FR-06:** Assignment manager must allow adding, marking as done, and sorting by due date.
- **FR-07:** Lost & Found items must support posting with optional photo upload to Firebase Storage.
- **FR-08:** Complaints lodged must generate a unique tracking ID and update status in real-time.

### Non-Functional Requirements
- **NFR-01 Performance:** Screen transition latency < 200ms; initial dashboard load < 1.5s on 4G connections.
- **NFR-02 Reliability & Offline Mode:** Cached timetable and attendance data available offline via Room DB.
- **NFR-03 Usability:** Adherence to Material Design 3 guidelines; minimum touch target size of 48dp x 48dp.
- **NFR-04 Security:** No raw passwords stored in code or plain text; Firestore security rules strictly enforcing data ownership.
- **NFR-05 Scalability:** Database schema structured to support thousands of active campus users.

---

## 7. User Stories

| ID | As a/an... | I want to... | So that... |
|---|---|---|---|
| US-01 | Student | register with my college email and academic info | I can access customized schedules and updates. |
| US-02 | Student | view my dashboard on launch | I can quickly see my next class and urgent tasks. |
| US-03 | Student | check my daily timetable | I know which room to go to for each lecture. |
| US-04 | Student | mark my attendance daily for each subject | I can ensure I meet the minimum required percentage. |
| US-05 | Student | view pending assignments sorted by deadline | I can prioritize urgent homework and projects. |
| US-06 | Student | download and view notes shared by classmates | I can prepare effectively for exams. |
| US-07 | Student | post a lost item with a description and image | Someone who finds it can reach out to me. |
| US-08 | Student | browse upcoming campus events and RSVP | I can participate in extracurricular campus life. |
| US-09 | Student | submit a campus complaint (e.g. broken Wi-Fi) | Administration can resolve the issue. |
| US-10 | Student | edit my profile and toggle dark mode | I can personalize my app experience. |

---

## 8. Success Criteria & Metrics
- **User Engagement:** Daily Active Users (DAU) > 60% of total registered students.
- **Academic Utility:** Average student attendance tracking frequency > 4 times per week.
- **App Quality:** Zero fatal crashes on major Android API levels (API 23 to API 36).
- **Performance:** App startup time < 2 seconds.
