# Design System & UI Specifications — Campus Connect

## 1. Brand Identity & Visual Style
**Campus Connect** features a modern, clean, minimal, student-centric design system built on **Material Design 3**. It offers high legibility, accessible touch targets, crisp card elevation, and smooth transitions.

---

## 2. Color Palette

### Light Theme
- **Primary Blue (`#2563EB`):** Main branding color, primary buttons, active tab indicators, key callouts.
- **Secondary Purple (`#7C3AED`):** Accent highlights, special badges, quick action icons.
- **Background (`#F8FAFC`):** Neutral slate background providing contrast for surface cards.
- **Surface (`#FFFFFF`):** Card backgrounds, bottom sheets, dialogs, top app bars.
- **Text Primary (`#0F172A`):** Deep charcoal for primary headings, titles, and body text.
- **Text Secondary (`#64748B`):** Muted slate for captions, metadata, timestamps, and subtitles.
- **Success (`#16A34A`):** Safe attendance status (>= 75%), resolved complaints, completed tasks.
- **Error (`#DC2626`):** Low attendance (< 75%), overdue assignments, urgent warnings, error messages.
- **Warning (`#F59E0B`):** Impending deadlines, pending status, warning alerts.

### Dark Theme Mappings
- **Primary Blue (`#60A5FA`):** Lighter blue for dark surfaces.
- **Secondary Purple (`#A78BFA`):** Soft violet accent.
- **Background (`#0F172A`):** Dark slate background.
- **Surface (`#1E293B`):** Dark surface elevation cards.
- **Text Primary (`#F8FAFC`):** Off-white text.
- **Text Secondary (`#94A3B8`):** Light slate secondary text.

---

## 3. Typography Scale

| Style Name | Font Weight | Size (sp) | Line Height | Application |
|---|---|---|---|---|
| Screen Title | Bold | 28sp – 30sp | 36sp | Top header on primary screens |
| Section Header | SemiBold / Bold | 20sp – 22sp | 28sp | Card titles, section headers |
| Subtitle / Title Medium | Medium | 18sp | 24sp | Subheaders, list item titles |
| Body Main | Regular | 16sp | 22sp | Main content, inputs, list body |
| Secondary Body | Regular | 14sp | 20sp | Descriptions, metadata, field labels |
| Caption / Tag | Medium | 12sp | 16sp | Badges, timestamps, small chips |

---

## 4. Spacing System (8dp Grid)
- **4dp (`spacing_tiny`):** Micro gaps between icon and text, dense list item inner padding.
- **8dp (`spacing_small`):** Standard element gap, inner card padding component gap.
- **16dp (`spacing_medium`):** Screen edge margins, card padding, view group spacing.
- **24dp (`spacing_large`):** Major section separators, header-to-content distance.
- **32dp (`spacing_xlarge`):** Header top padding, hero section gaps.
- **48dp (`spacing_xxlarge`):** Minimum touch target height for buttons and interactive items.

---

## 5. UI Component Library

### Material Buttons
- **Primary Filled Button:** Rounded corners (12dp radius), Primary Blue background, white bold text, 48dp minimum height.
- **Outlined Button:** 1.5dp stroke in Primary Blue, transparent background, used for secondary actions (e.g., "Cancel", "Filter").
- **Text Button:** Borderless button for inline actions (e.g., "Forgot Password?", "See All").

### Input Text Fields (`TextInputLayout` + `TextInputEditText`)
- Material 3 Outlined Style with floating label.
- Corner radius: 12dp.
- Active stroke color: Primary Blue (`#2563EB`).
- Error state: Red stroke (`#DC2626`) with explicit helper text below.

### Material Cards (`MaterialCardView`)
- Background: Surface White (`#FFFFFF`).
- Elevation: 2dp resting elevation, 6dp on hover/press.
- Corner Radius: 16dp.
- Stroke: 1dp border (`#E2E8F0`) for crisp separation on slate background.

### Top App Bar & Bottom Navigation
- **MaterialToolbar:** Clean surface white background, centered title or left-aligned title with user avatar on the right.
- **BottomNavigationView:** Container elevation 8dp, 4 primary tabs (Home, Academics, Campus, Profile) with active indicator pill in Primary Blue.

### Badges & Chips
- **Status Chips:** Small 12sp chips with soft background tint (e.g., Success Light `#DCFCE7` with `#16A34A` text for High Attendance).
- **Priority Badges:** High (Red), Medium (Yellow), Low (Blue) for assignment deadlines.

---

## 6. Layout States & UX Guidelines

### Loading State
- Smooth `ProgressBar` or Shimmer placeholder layouts matching card dimensions.

### Empty State
- Centered icon/illustration, bold empty title (e.g., "No Classes Today!"), descriptive caption, and an action button (e.g., "Add Subject").

### Error State
- Error icon, friendly clear error message, and a primary "Try Again" button.
