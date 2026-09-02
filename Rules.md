# Engineering Guidelines & Rules — Campus Connect

## 1. Technology & Language Constraints
- **Language:** Java 11+ ONLY. Do not use Kotlin code or Kotlin-specific constructs in production source files.
- **UI Toolkit:** Android XML Layouts ONLY. **Jetpack Compose is strictly prohibited.**
- **Design System:** Material Design 3 (`com.google.android.material`) components must be used for buttons, cards, text fields, top bars, bottom navigation, and dialogs.

---

## 2. Code Structure & Clean Architecture Rules
- Follow the **MVVM (Model-View-ViewModel)** architectural pattern.
- Keep **Activities and Fragments lean**. Activities/Fragments should strictly handle UI initialization, view binding, and listening to ViewModel state.
- **Business Logic** must reside in ViewModels or Domain Repositories, never in Activity/Fragment classes.
- Use **Repositories** as a single source of truth to abstract data fetching between Firebase Firestore/Storage and Room DB.
- Use **RecyclerView** with custom ViewHolders and Adapters for all list displays. Do not use ListView.

---

## 3. Resource Management & Naming Conventions

### Resource Rules
- **ZERO hardcoded strings** in Java files or XML layouts. All text must be defined in `res/values/strings.xml`.
- **ZERO hardcoded colors** in XML layouts or Java code. All colors must use semantic color definitions from `res/values/colors.xml`.
- **ZERO hardcoded dimensions** for padding/margins. Use standard 8dp spacing values defined in `res/values/dimens.xml`.

### Naming Conventions
- **Java Classes:** `PascalCase` (e.g., `SplashActivity`, `TimetableAdapter`, `UserViewModel`).
- **Java Variables / Methods:** `camelCase` (e.g., `userEmail`, `fetchTimetable()`).
- **Constants:** `UPPER_SNAKE_CASE` (e.g., `KEY_USER_ID`, `PREF_NAME`).
- **XML Layout Files:** `snake_case` prefixed by type:
  - Activities: `activity_*.xml` (e.g., `activity_splash.xml`, `activity_main.xml`)
  - Fragments: `fragment_*.xml` (e.g., `fragment_home.xml`)
  - List Items: `item_*.xml` (e.g., `item_assignment.xml`, `item_onboarding.xml`)
  - Dialogs: `dialog_*.xml` (e.g., `dialog_add_assignment.xml`)
- **XML View IDs:** `snake_case` with explicit component prefix:
  - Button: `btn_*` (e.g., `btn_login`)
  - TextView: `tv_*` (e.g., `tv_title`, `tv_subtitle`)
  - EditText: `et_*` (e.g., `et_email`)
  - ImageView: `iv_*` (e.g., `iv_avatar`)
  - RecyclerView: `rv_*` (e.g., `rv_timetable`)
  - CardView: `card_*` (e.g., `card_attendance`)
  - TextInputLayout: `til_*` (e.g., `til_email`)
  - Toolbar: `toolbar_*` (e.g., `toolbar_main`)
  - BottomNavigationView: `bottom_nav_*` (e.g., `bottom_navigation`)

---

## 4. UI/UX & Layout Guidelines
- **Responsive Layouts:** Always prefer `ConstraintLayout` for flat, performant, and screen-agnostic layouts. Avoid deep layout nesting.
- **Touch Target Size:** All clickable elements (buttons, icons, list items) must maintain a minimum touch target area of **48dp x 48dp**.
- **State Handling:** Every list or data screen MUST handle 4 core states:
  1. **Loading State:** Progress bar / Shimmer animation while fetching data.
  2. **Success State:** Populated RecyclerView or Card views.
  3. **Empty State:** User-friendly illustration/text when no data exists.
  4. **Error State:** Friendly error message with a "Retry" button.

---

## 5. Security & Credentials
- **NEVER** put passwords, API tokens, Firebase private keys, or service account JSON files in source control.
- Ensure `google-services.json` is listed in `.gitignore` if sensitive or environment-specific.
- Always use `TextInputLayout` with `passwordToggleEnabled="true"` for password fields.
- Apply Firestore Security Rules to restrict document read/write access based on `request.auth.uid`.

---

## 6. Input Validation & Error Handling
- Validate all user input fields (email format, non-empty text, password length >= 6 characters) before initiating network/auth requests.
- Display input errors directly on `TextInputLayout` using `setError()`.
- Gracefully handle network offline states using local caching or displaying network alert snackbars.

---

## 7. Version Control & Git Discipline
- Commit early and often with clear, structured commit messages following Conventional Commits:
  - `chore: ...` (setup, config, gradle updates)
  - `docs: ...` (PRD, specs, documentation)
  - `feat: ...` (new feature or UI screen)
  - `fix: ...` (bug fixes)
  - `refactor: ...` (code restructuring without functional changes)
- Never mix unrelated features in a single commit.
