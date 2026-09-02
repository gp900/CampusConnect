# App Flow & Navigation Architecture — Campus Connect

## 1. High-Level User Journey

```
[ Splash Screen ]
       │
       ▼ (Check Auth State & First-Run)
┌───────────────┬────────────────────────┐
│               │                        │
▼ (First Run)   ▼ (Not Authenticated)    ▼ (Authenticated)
[ Onboarding ]  [ Login Screen ]         [ Main Container ]
       │                │                        │
       └────────►───────┴────────────────────────┤
                         │                       │
                         ▼                       ▼
                   [ Register ]           ┌──────────────┐
                         │                │ MainActivity │
                         └──────►─────────┤ (BottomNav)  │
                                          └──────┬───────┘
                                                 │
      ┌──────────────────┬───────────────────────┼──────────────────────┐
      ▼                  ▼                       ▼                      ▼
 [ Home Tab ]    [ Academics Tab ]       [ Campus Tab ]          [ Profile Tab ]
```

---

## 2. Screen & Navigation Flow Breakdown

### Flow 1: Onboarding & Authentication
1. **Splash Screen (`SplashActivity`):**
   - Displays Campus Connect branding logo, title, and tagline.
   - Checks Firebase Auth session and `SharedPreferences` (first launch flag).
   - Routes automatically after 2 seconds to `OnboardingActivity`, `LoginActivity`, or `MainActivity`.

2. **Onboarding Screen (`OnboardingActivity`):**
   - `ViewPager2` containing 3 onboarding slides:
     - *Slide 1:* "Everything on Campus" — One app for all your college needs.
     - *Slide 2:* "Stay Organized" — Track timetables, attendance, and assignment deadlines.
     - *Slide 3:* "Connect with Campus" — Events, clubs, announcements, and notes sharing.
   - Page indicator dots + "Skip" and "Next/Get Started" buttons.
   - Navigates to `LoginActivity`.

3. **Authentication Screens:**
   - **Login Screen (`LoginActivity`):** Email & Password inputs, "Remember Me", "Forgot Password?", "Login" button, link to Register.
   - **Register Screen (`RegisterActivity`):** Full Name, College Email, Password, Confirm Password, Department dropdown, Semester dropdown, "Create Account" button.
   - **Forgot Password Dialog/Screen:** Password reset link dispatched via Firebase Auth.

---

### Flow 2: Main Application Architecture (`MainActivity`)
`MainActivity` houses a `BottomNavigationView` with 4 major sections:

#### Tab 1: Home Dashboard (`HomeFragment`)
- **Header:** User greeting ("Hello, Alex!"), profile thumbnail, notification bell icon.
- **Hero Card:** "Next Class" countdown timer, subject name, room number, professor.
- **Stats Row:** Attendance summary percentage card, pending assignments badge card.
- **Upcoming Events Banner:** Horizontal scroll view of upcoming campus events.
- **Quick Action Grid:** Quick buttons for "Mark Attendance", "View Notes", "Lodge Complaint", "Lost & Found".

#### Tab 2: Academics Hub (`AcademicsFragment`)
Contains a top `TabLayout` with `ViewPager2` supporting 4 academic modules:
1. **Timetable Sub-tab:** Day selector (Mon-Fri/Sat), chronological class list cards.
2. **Attendance Sub-tab:** Overall percentage meter, subject list with "+ Present / - Absent" quick logs, goal status.
3. **Assignments Sub-tab:** Tabbed view ("Pending", "Completed"), priority filters, "Add Assignment" FloatingActionButton.
4. **Notes Sub-tab:** Subject search, category filters, PDF/image notes cards with download & rating options, "Upload Note" FAB.

#### Tab 3: Campus Hub (`CampusFragment`)
Contains a top `TabLayout` with `ViewPager2` supporting 5 campus life modules:
1. **Events Sub-tab:** Featured events carousel, event cards with date, venue, RSVP count, "Register/RSVP" button.
2. **Lost & Found Sub-tab:** Filter by "Lost" / "Found", item cards with photo, location, date, contact button, "Post Item" FAB.
3. **Clubs Sub-tab:** Club directory cards (Technical, Cultural, Sports), executive team info, "Join Club" button, club event feeds.
4. **Announcements Sub-tab:** Searchable list of official notices with urgency badges (Urgent, Notice, Exam).
5. **Complaints Sub-tab:** Active complaints list with status badges (Submitted, In Review, Resolved), "Lodge Complaint" FAB.

#### Tab 4: Profile & Settings (`ProfileFragment`)
- **Profile Header:** Large avatar, student name, email, department, semester, roll number, "Edit Profile" button.
- **Academic Summary Card:** Completed credits, overall attendance, active memberships.
- **Settings List:**
  - Dark Mode Toggle (`Switch`).
  - Notification Preferences.
  - My Submitted Notes / My Complaints history.
  - Terms of Service & Privacy Policy.
  - **Logout Button:** Logs out from Firebase Auth and navigates back to `LoginActivity`.
