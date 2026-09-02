# Database Schema Specification — Campus Connect

## 1. Firebase Cloud Firestore Collections

### Collection 1: `users/{userId}`
Stores student profile and academic details.
```json
{
  "userId": "string (Matches Firebase Auth UID)",
  "fullName": "string",
  "email": "string",
  "department": "string (e.g., Computer Science)",
  "semester": "int (1 to 8)",
  "rollNumber": "string",
  "profileImageUrl": "string (Nullable)",
  "fcmToken": "string",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Collection 2: `timetables/{timetableId}`
Stores weekly class schedule entries.
```json
{
  "timetableId": "string (Auto-id)",
  "department": "string",
  "semester": "int",
  "dayOfWeek": "string (MONDAY, TUESDAY, etc.)",
  "subjectCode": "string",
  "subjectName": "string",
  "professorName": "string",
  "roomNumber": "string",
  "startTime": "string (e.g., 09:00)",
  "endTime": "string (e.g., 10:00)"
}
```

### Collection 3: `attendance/{attendanceId}`
Tracks student attendance records per subject.
```json
{
  "attendanceId": "string (Auto-id)",
  "userId": "string",
  "subjectCode": "string",
  "subjectName": "string",
  "classesAttended": "int",
  "totalClasses": "int",
  "lastUpdated": "timestamp"
}
```

### Collection 4: `assignments/{assignmentId}`
Stores homework and project deadlines.
```json
{
  "assignmentId": "string (Auto-id)",
  "userId": "string",
  "subjectName": "string",
  "title": "string",
  "description": "string",
  "dueDate": "timestamp",
  "priority": "string (HIGH, MEDIUM, LOW)",
  "isCompleted": "boolean",
  "createdAt": "timestamp"
}
```

### Collection 5: `notes/{noteId}`
Shared study material and notes.
```json
{
  "noteId": "string (Auto-id)",
  "uploaderId": "string",
  "uploaderName": "string",
  "subjectName": "string",
  "title": "string",
  "description": "string",
  "fileUrl": "string (Firebase Storage URL)",
  "fileType": "string (PDF, IMAGE)",
  "downloadsCount": "int",
  "createdAt": "timestamp"
}
```

### Collection 6: `events/{eventId}`
Campus and club events.
```json
{
  "eventId": "string (Auto-id)",
  "title": "string",
  "organizer": "string (e.g., Coding Club)",
  "description": "string",
  "eventDate": "timestamp",
  "venue": "string",
  "bannerImageUrl": "string",
  "rsvpCount": "int",
  "createdAt": "timestamp"
}
```

### Collection 7: `lost_found/{itemId}`
Lost and found item postings.
```json
{
  "itemId": "string (Auto-id)",
  "userId": "string",
  "userName": "string",
  "title": "string",
  "description": "string",
  "category": "string (LOST / FOUND)",
  "location": "string",
  "imageUrl": "string (Nullable)",
  "contactPhone": "string",
  "isResolved": "boolean",
  "createdAt": "timestamp"
}
```

### Collection 8: `clubs/{clubId}`
Campus club profiles and information.
```json
{
  "clubId": "string (Auto-id)",
  "clubName": "string",
  "category": "string (TECHNICAL, CULTURAL, SPORTS)",
  "description": "string",
  "logoUrl": "string",
  "leadName": "string",
  "leadContact": "string",
  "memberCount": "int"
}
```

### Collection 9: `announcements/{announcementId}`
Official campus notices.
```json
{
  "announcementId": "string (Auto-id)",
  "title": "string",
  "content": "string",
  "category": "string (EXAM, GENERAL, HOLIDAY, URGENT)",
  "author": "string (e.g., Dean Office)",
  "postedAt": "timestamp"
}
```

### Collection 10: `complaints/{complaintId}`
Grievances and infrastructure reports.
```json
{
  "complaintId": "string (Auto-id)",
  "userId": "string",
  "userName": "string",
  "category": "string (HOSTEL, WI-FI, ACADEMICS, INFRASTRUCTURE)",
  "subject": "string",
  "description": "string",
  "status": "string (SUBMITTED, IN_REVIEW, RESOLVED)",
  "createdAt": "timestamp",
  "updatedAt": "timestamp"
}
```

### Collection 11: `notifications/{notificationId}`
User-specific notifications.
```json
{
  "notificationId": "string (Auto-id)",
  "userId": "string",
  "title": "string",
  "message": "string",
  "type": "string (CLASS, ASSIGNMENT, COMPLAINT, ANNOUNCEMENT)",
  "isRead": "boolean",
  "timestamp": "timestamp"
}
```

---

## 2. Room Local Database Schema (Offline Cache)

To enable offline caching for fast startup and poor network connectivity, Room caches key student datasets.

### Room Entity: `UserEntity` (`users_table`)
- `userId` (Primary Key, String)
- `fullName` (String)
- `email` (String)
- `department` (String)
- `semester` (Int)
- `rollNumber` (String)

### Room Entity: `TimetableEntity` (`timetable_table`)
- `timetableId` (Primary Key, String)
- `dayOfWeek` (String)
- `subjectName` (String)
- `professorName` (String)
- `roomNumber` (String)
- `startTime` (String)
- `endTime` (String)

### Room Entity: `AttendanceEntity` (`attendance_table`)
- `attendanceId` (Primary Key, String)
- `subjectCode` (String)
- `subjectName` (String)
- `classesAttended` (Int)
- `totalClasses` (Int)

### Room Entity: `AssignmentEntity` (`assignment_table`)
- `assignmentId` (Primary Key, String)
- `title` (String)
- `subjectName` (String)
- `dueDate` (Long, Epoch Millis)
- `priority` (String)
- `isCompleted` (Boolean)
