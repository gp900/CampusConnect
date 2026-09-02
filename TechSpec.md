# Technical Architecture Specification — Campus Connect

## 1. Architecture Pattern
Campus Connect follows a clean **MVVM (Model-View-ViewModel)** architecture tailored for Java + XML:

```
┌─────────────────────────────────────────────────────────────┐
│                       VIEW LAYER                            │
│  (Activities, Fragments, RecyclerView Adapters, XML Layouts)│
└──────────────────────────────┬──────────────────────────────┘
                               │ Observes LiveData / State
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                    VIEWMODEL LAYER                          │
│          (Maintains UI State, handles events)               │
└──────────────────────────────┬──────────────────────────────┘
                               │ Requests Data
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                    REPOSITORY LAYER                         │
│     (Single Source of Truth: Data fetch/merge logic)        │
└──────────────┬──────────────────────────────┬───────────────┘
               │                              │
               ▼                              ▼
┌──────────────────────────────┐┌──────────────────────────────┐
│     REMOTE DATA SOURCE       ││      LOCAL DATA SOURCE       │
│(Firebase Auth, Firestore,    ││(Room Database, SharedPrefs)  │
│ Firebase Storage, FCM)       ││                              │
└──────────────────────────────┘└──────────────────────────────┘
```

---

## 2. Directory Structure

```
app/src/main/java/com/example/campusconnect/
├── adapter/
│   ├── OnboardingAdapter.java
│   ├── TimetableAdapter.java
│   ├── AttendanceAdapter.java
│   ├── AssignmentAdapter.java
│   ├── NotesAdapter.java
│   ├── EventsAdapter.java
│   ├── LostFoundAdapter.java
│   ├── ClubsAdapter.java
│   ├── AnnouncementAdapter.java
│   └── ComplaintAdapter.java
├── database/
│   ├── AppDatabase.java
│   ├── dao/
│   │   ├── UserDao.java
│   │   ├── TimetableDao.java
│   │   ├── AttendanceDao.java
│   │   └── AssignmentDao.java
│   └── entity/
│       ├── UserEntity.java
│       ├── TimetableEntity.java
│       ├── AttendanceEntity.java
│       └── AssignmentEntity.java
├── model/
│   ├── User.java
│   ├── OnboardingItem.java
│   ├── TimetableItem.java
│   ├── AttendanceItem.java
│   ├── Assignment.java
│   ├── Note.java
│   ├── Event.java
│   ├── LostFoundItem.java
│   ├── Club.java
│   ├── Announcement.java
│   ├── Complaint.java
│   └── NotificationItem.java
├── repository/
│   ├── AuthRepository.java
│   ├── StudentRepository.java
│   ├── AcademicRepository.java
│   └── CampusRepository.java
├── ui/
│   ├── auth/
│   │   ├── LoginActivity.java
│   │   └── RegisterActivity.java
│   ├── onboarding/
│   │   └── OnboardingActivity.java
│   ├── splash/
│   │   └── SplashActivity.java
│   ├── main/
│   │   ├── MainActivity.java
│   │   ├── HomeFragment.java
│   │   ├── AcademicsFragment.java
│   │   ├── CampusFragment.java
│   │   └── ProfileFragment.java
│   └── academics/
│       ├── TimetableFragment.java
│       ├── AttendanceFragment.java
│       ├── AssignmentsFragment.java
│       └── NotesFragment.java
├── viewmodel/
│   ├── AuthViewModel.java
│   ├── DashboardViewModel.java
│   ├── AcademicViewModel.java
│   └── CampusViewModel.java
└── utils/
    ├── Constants.java
    ├── DateTimeUtils.java
    ├── ValidationUtils.java
    └── SharedPrefManager.java
```

---

## 3. Core Android Components & Libraries

### Android Jetpack Dependencies
- `androidx.appcompat:appcompat:1.7.1` — Backward compatibility & Material activity support.
- `com.google.android.material:material:1.14.0` — Material Design 3 UI components.
- `androidx.constraintlayout:constraintlayout:2.2.1` — Responsive layout engine.
- `androidx.recyclerview:recyclerview:1.4.0` — Efficient list renderings.
- `androidx.viewpager2:viewpager2` — Swipeable onboarding and tabbed view adapters.
- `androidx.room:room-runtime:2.6.1` — Local SQLite caching layer.
- `androidx.lifecycle:lifecycle-viewmodel:2.8.7` & `lifecycle-livedata:2.8.7` — ViewModel & reactive UI observers.

### Firebase Dependencies
- `com.google.firebase:firebase-auth` — User authentication.
- `com.google.firebase:firebase-firestore` — Cloud NoSQL database.
- `com.google.firebase:firebase-storage` — Document and image file storage.
- `com.google.firebase:firebase-messaging` — Push notification service.

---

## 4. State Management & Data Flow
1. **Views** observe `LiveData` exposed by `ViewModel` instances using `observe(getViewLifecycleOwner(), observer)`.
2. **ViewModel** calls methods on `Repository` and posts state updates to `MutableLiveData<Resource<T>>` where `Resource` represents `Loading`, `Success`, or `Error`.
3. **Repository** checks Room cache first; if stale or empty, fetches from Firestore and updates local DB cache.
