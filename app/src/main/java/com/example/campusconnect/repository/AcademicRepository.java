package com.example.campusconnect.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.model.Note;
import com.example.campusconnect.model.TimetableItem;
import com.example.campusconnect.utils.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AcademicRepository {

    private static AcademicRepository instance;
    private final AcademicDao academicDao;
    private final ExecutorService executor;

    private AcademicRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.academicDao = db.academicDao();
        this.executor = Executors.newSingleThreadExecutor();
        seedInitialData();
    }

    public static synchronized AcademicRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AcademicRepository(context);
        }
        return instance;
    }

    private void seedInitialData() {
        executor.execute(() -> {
            // Seed Timetable
            if (academicDao.getTimetable().getValue() == null || academicDao.getTimetable().getValue().isEmpty()) {
                List<TimetableItem> defaultTimetable = new ArrayList<>();
                defaultTimetable.add(new TimetableItem("tt1", "MONDAY", "CS-301", "Data Structures & Algorithms", "Dr. Robert Vance", "Room 302", "09:00 AM", "10:00 AM", "COMPLETED"));
                defaultTimetable.add(new TimetableItem("tt2", "MONDAY", "CS-302", "Operating Systems", "Prof. Jane Smith", "Room 304", "10:30 AM", "11:30 AM", "ONGOING"));
                defaultTimetable.add(new TimetableItem("tt3", "MONDAY", "CS-303", "Database Management Systems", "Dr. Alan Turing", "Lab 4", "12:00 PM", "02:00 PM", "UPCOMING"));
                defaultTimetable.add(new TimetableItem("tt4", "MONDAY", "CS-304", "Computer Networks", "Prof. Vint Cerf", "Room 101", "02:30 PM", "04:00 PM", "UPCOMING"));
                academicDao.insertTimetable(defaultTimetable);
            }

            // Seed Attendance
            if (academicDao.getAttendance().getValue() == null || academicDao.getAttendance().getValue().isEmpty()) {
                List<AttendanceItem> defaultAttendance = new ArrayList<>();
                defaultAttendance.add(new AttendanceItem("at1", "CS-301", "Data Structures", 32, 40));
                defaultAttendance.add(new AttendanceItem("at2", "CS-302", "Operating Systems", 28, 40));
                defaultAttendance.add(new AttendanceItem("at3", "CS-303", "Database Systems", 35, 38));
                defaultAttendance.add(new AttendanceItem("at4", "CS-304", "Computer Networks", 18, 30));
                academicDao.insertAttendance(defaultAttendance);
            }

            // Seed Assignments
            if (academicDao.getAssignments().getValue() == null || academicDao.getAssignments().getValue().isEmpty()) {
                List<Assignment> defaultAssignments = new ArrayList<>();
                defaultAssignments.add(new Assignment("as1", "Chapter 4 Exercise Questions", "Data Structures", "Tomorrow, 11:59 PM", "HIGH", false));
                defaultAssignments.add(new Assignment("as2", "Build a custom Thread Pool", "Operating Systems", "Oct 12, 10:00 AM", "MEDIUM", false));
                defaultAssignments.add(new Assignment("as3", "Normalize the given ER Diagram", "Database Systems", "Oct 15, 05:00 PM", "HIGH", false));
                defaultAssignments.add(new Assignment("as4", "Packet tracer lab submission", "Computer Networks", "Last Week", "LOW", true));
                academicDao.insertAssignments(defaultAssignments);
            }

            // Seed Notes
            if (academicDao.getNotes().getValue() == null || academicDao.getNotes().getValue().isEmpty()) {
                List<Note> defaultNotes = new ArrayList<>();
                defaultNotes.add(new Note("n1", "Chapter 4: Trees and Graphs", "Data Structures", "Alex Student", "PDF", 45, "2 hours ago"));
                defaultNotes.add(new Note("n2", "Normal Forms & Relational Algebra", "Database Systems", "Sarah Connor", "PDF", 128, "Yesterday"));
                defaultNotes.add(new Note("n3", "Midterm Cheat Sheet", "Operating Systems", "John McCarthy", "IMG", 302, "Last Week"));
                defaultNotes.add(new Note("n4", "Gradient Descent Notes", "Machine Learning", "Linus Torvalds", "PDF", 12, "2 days ago"));
                academicDao.insertNotes(defaultNotes);
            }
        });
    }

    // ==========================================
    // TIMETABLE MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<TimetableItem>>> getTimetableForDay(String day) {
        MutableLiveData<Resource<List<TimetableItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        academicDao.getTimetable().observeForever(items -> {
            if (items != null) {
                // Filter by day manually for now, or you could add a Query in DAO
                List<TimetableItem> filtered = new ArrayList<>();
                for (TimetableItem item : items) {
                    if (item.getDayOfWeek().equalsIgnoreCase(day)) {
                        filtered.add(item);
                    }
                }
                result.setValue(Resource.success(filtered));
            }
        });
        return result;
    }

    // ==========================================
    // ATTENDANCE MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<AttendanceItem>>> getAttendance() {
        MutableLiveData<Resource<List<AttendanceItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        academicDao.getAttendance().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void markAttendance(String subjectId, boolean isPresent) {
        executor.execute(() -> {
            // Need a way to fetch a single item to increment, or simply let the UI pass the current total
        });
    }
    
    // Quick hack for UI simulation since we don't have single fetch in DAO right now
    public void markAttendanceObj(AttendanceItem item, boolean isPresent) {
        executor.execute(() -> {
            item.setTotalClasses(item.getTotalClasses() + 1);
            if (isPresent) {
                item.setClassesAttended(item.getClassesAttended() + 1);
            }
            academicDao.insertAttendanceItem(item);
        });
    }

    public void addAttendanceSubject(AttendanceItem item) {
        executor.execute(() -> academicDao.insertAttendanceItem(item));
    }
    
    public void deleteAttendance(String id) {
        executor.execute(() -> academicDao.deleteAttendance(id));
    }

    // ==========================================
    // ASSIGNMENT MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Assignment>>> getAssignments() {
        MutableLiveData<Resource<List<Assignment>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        academicDao.getAssignments().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void addAssignment(Assignment assignment) {
        executor.execute(() -> academicDao.insertAssignment(assignment));
    }
    
    public void updateAssignmentCompletion(Assignment assignment) {
        executor.execute(() -> academicDao.updateAssignmentStatus(assignment.getId(), assignment.isCompleted()));
    }
    
    public void deleteAssignment(String id) {
        executor.execute(() -> academicDao.deleteAssignment(id));
    }

    // ==========================================
    // NOTES MODULE LOGIC
    // ==========================================

    public LiveData<Resource<List<Note>>> getNotes() {
        MutableLiveData<Resource<List<Note>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());
        academicDao.getNotes().observeForever(items -> {
            if (items != null) result.setValue(Resource.success(items));
        });
        return result;
    }

    public void addNote(Note note) {
        executor.execute(() -> academicDao.insertNote(note));
    }
    
    public void deleteNote(String id) {
        executor.execute(() -> academicDao.deleteNote(id));
    }
}
