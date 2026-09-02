# 🎓 Campus Connect

> **"One App. Everything for Students."**

Campus Connect is a comprehensive, portfolio-grade Android application designed to centralize student academic tracking, campus communication, productivity tools, and community activities into a single, cohesive mobile ecosystem.

---

## 📌 Features & Core Modules

Campus Connect consolidates 14 core student life workflows:

1. **👤 Student Profile:** Comprehensive academic profile displaying department, semester, roll number, and academic stats.
2. **📊 Dashboard:** Central student hub featuring a "Next Class" countdown timer, attendance overview, pending assignments, and quick actions.
3. **📅 Timetable:** Daily and weekly class schedule breakdown organized by day of the week, room numbers, and professors.
4. **📈 Attendance Tracker:** Dynamic percentage tracker with safety margin calculations ("Safe to bunk" vs. "Danger zone" warnings) and quick logging.
5. **📝 Assignment Manager:** Homework and project deadline tracker with pending/completed filters and priority badges (High, Medium, Low).
6. **📚 Notes Sharing:** Repository for uploading, searching, and downloading peer-shared study materials and PDFs.
7. **🎉 Campus Events:** Campus event discovery, RSVP tracking, venue details, and schedule integration.
8. **🔍 Lost & Found:** Community bulletin board for posting lost and found campus items with images and direct contact options.
9. **🏆 Clubs Directory:** Directory of campus technical, cultural, and sports clubs with executive details and activity updates.
10. **📢 Campus Announcements:** Official campus broadcasts categorized by urgency (Notice, Exam, Urgent, Holiday).
11. **🛠️ Complaint System:** Formal student grievance lodging platform with real-time status tracking (Submitted, In Review, Resolved).
12. **🔔 Notification Center:** Real-time push and local notifications for upcoming lectures, assignment deadlines, and administrative alerts.
13. **🔐 Authentication:** Secure Firebase Authentication (Email/Password) with academic profile creation and session persistence.
14. **⚙️ Settings:** Preference management including Dark Mode toggle, notification customization, and account security.

---

## 🛠️ Technology Stack

Campus Connect is built specifically to demonstrate clean, maintainable, native Android development:

- **Language:** Java 11+
- **UI Framework:** Android Native XML Layouts (Strictly **No Jetpack Compose**)
- **Design System:** Material Design 3 (`com.google.android.material`)
- **Layout Engines:** `ConstraintLayout`, `RecyclerView`, `CardView`, `ViewPager2`
- **Architecture:** Clean **MVVM (Model-View-ViewModel)** Architecture
- **State Management:** `LiveData` & `ViewModel`
- **Backend Services:**
  - **Firebase Authentication** — User identity & session management
  - **Cloud Firestore** — Real-time NoSQL database
  - **Firebase Storage** — Notes documents and Lost & Found image uploads
  - **Firebase Cloud Messaging (FCM)** — Push notification delivery
- **Local Persistence:** **Room Database** (SQLite caching layer for offline reliability)

---

## 🏗️ Architecture & Project Structure

Campus Connect adopts a strict MVVM directory layout designed for scalability and readability:

```
app/src/main/java/com/example/campusconnect/
├── adapter/        # RecyclerView & ViewPager2 adapters
├── database/       # Room database, Entities, and DAOs
├── model/          # Data models and domain entities
├── repository/     # Data repositories (Single Source of Truth)
├── ui/             # Activities, Fragments, and View Binding logic
│   ├── academics/  # Timetable, Attendance, Assignments, Notes
│   ├── auth/       # Login, Register, Forgot Password
│   ├── main/       # MainActivity, Home, Campus, Profile tabs
│   ├── onboarding/ # Onboarding slider
│   └── splash/     # Splash screen
├── viewmodel/      # Architecture ViewModels & LiveData
└── utils/          # Constants, Preference managers, Date helpers
```

---

## 🎨 Design Tokens & System

Built on a standardized **8dp Spacing Grid** and a modern campus color palette:

- **Primary Blue (`#2563EB`):** Key interactive components and branding
- **Secondary Purple (`#7C3AED`):** Accent badges and secondary actions
- **Background Slate (`#F8FAFC`):** Low-contrast slate background for card separation
- **Surface White (`#FFFFFF`):** Material 3 card backgrounds and dialog containers
- **Success (`#16A34A`):** Safe attendance status and resolved items
- **Error (`#DC2626`):** Attendance danger warnings and overdue tasks

---

## 📄 Engineering Documentation Suite

Campus Connect follows a strict **Documentation-First Workflow**. You can explore the full specification suite below:

- 📋 [PRD.md](PRD.md) — Product Requirements Document (User Personas, Core Requirements, User Stories)
- 📐 [Rules.md](Rules.md) — Engineering Standards & Coding Guidelines
- 🎨 [Design.md](Design.md) — Complete Material 3 Design System & UI Specs
- 🗺️ [AppFlow.md](AppFlow.md) — Screen Map & Navigation Architecture
- 🗄️ [Schema.md](Schema.md) — Firestore & Room Database Schemas
- ⚙️ [TechSpec.md](TechSpec.md) — Technical Architecture Specification
- 🚀 [ImplementationPlan.md](ImplementationPlan.md) — 18-Milestone Development Roadmap
- 📌 [Tracker.md](Tracker.md) — Feature & Milestone Status Matrix

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (2024.1+ recommended)
- **Android SDK** Minimum API 23 (Android 6.0 Marshmallow) | Target API 36
- **Java JDK 11+**

### Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/gp900/CampusConnect.git
   ```
2. Open the project in **Android Studio**.
3. Allow Gradle to sync dependencies.
4. Run the application on an Android Emulator or connected physical device.

---

## 📜 License & Author

Developed as a portfolio-grade Android application by **Gaurav Puri**.
Designed for educational clarity and native Android software engineering best practices.
