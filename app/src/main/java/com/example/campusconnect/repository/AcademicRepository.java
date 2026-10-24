package com.example.campusconnect.repository;

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

public class AcademicRepository {

    private static AcademicRepository instance;

    private AcademicRepository() {
    }

    public static synchronized AcademicRepository getInstance() {
        if (instance == null) {
            instance = new AcademicRepository();
        }
        return instance;
    }

    public LiveData<Resource<List<TimetableItem>>> getTimetableForDay(String dayOfWeek) {
        MutableLiveData<Resource<List<TimetableItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            List<TimetableItem> list = new ArrayList<>();

            if ("SUNDAY".equalsIgnoreCase(dayOfWeek)) {
                result.setValue(Resource.success(list));
                return;
            }

            if ("SATURDAY".equalsIgnoreCase(dayOfWeek)) {
                list.add(new TimetableItem("t101", "SATURDAY", "CS-305", "Lab - Data Structures", "Prof. Alan Turing", "Lab 2", "10:00 AM", "12:00 PM", "UPCOMING"));
                result.setValue(Resource.success(list));
                return;
            }

            list.add(new TimetableItem("t1", dayOfWeek, "CS-301", "Data Structures & Algorithms", "Dr. Robert Vance", "Room 302", "09:00 AM", "10:00 AM", "COMPLETED"));
            list.add(new TimetableItem("t2", dayOfWeek, "CS-302", "Database Management Systems", "Prof. Sarah Connor", "Room 304", "10:15 AM", "11:15 AM", "ONGOING"));
            list.add(new TimetableItem("t3", dayOfWeek, "AI-303", "Machine Learning Foundations", "Dr. John McCarthy", "Room 105", "11:30 AM", "12:30 PM", "UPCOMING"));
            list.add(new TimetableItem("t4", dayOfWeek, "CS-304", "Operating Systems", "Dr. Linus Torvalds", "Room 201", "02:00 PM", "03:00 PM", "UPCOMING"));

            result.setValue(Resource.success(list));
        }, 400);

        return result;
    }

    // ==========================================
    // ATTENDANCE MODULE LOGIC
    // ==========================================

    private List<AttendanceItem> mockAttendanceList;

    public LiveData<Resource<List<AttendanceItem>>> getAttendanceList() {
        MutableLiveData<Resource<List<AttendanceItem>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockAttendanceList == null) {
                mockAttendanceList = new ArrayList<>();
                mockAttendanceList.add(new AttendanceItem("a1", "CS-301", "Data Structures & Algorithms", 33, 40));
                mockAttendanceList.add(new AttendanceItem("a2", "CS-302", "Database Management Systems", 28, 30));
                mockAttendanceList.add(new AttendanceItem("a3", "AI-303", "Machine Learning Foundations", 18, 25));
                mockAttendanceList.add(new AttendanceItem("a4", "CS-304", "Operating Systems", 15, 22));
                mockAttendanceList.add(new AttendanceItem("a5", "MA-201", "Applied Mathematics", 12, 20));
            }
            result.setValue(Resource.success(mockAttendanceList));
        }, 300);

        return result;
    }

    public void markAttendancePresent(String itemId) {
        if (mockAttendanceList == null) return;
        for (AttendanceItem item : mockAttendanceList) {
            if (item.getId().equals(itemId)) {
                item.setClassesAttended(item.getClassesAttended() + 1);
                item.setTotalClasses(item.getTotalClasses() + 1);
                break;
            }
        }
    }

    public void markAttendanceAbsent(String itemId) {
        if (mockAttendanceList == null) return;
        for (AttendanceItem item : mockAttendanceList) {
            if (item.getId().equals(itemId)) {
                item.setTotalClasses(item.getTotalClasses() + 1);
                break;
            }
        }
    }

    // ==========================================
    // ASSIGNMENT MODULE LOGIC
    // ==========================================

    private List<Assignment> mockAssignmentList;

    public LiveData<Resource<List<Assignment>>> getAssignments() {
        MutableLiveData<Resource<List<Assignment>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockAssignmentList == null) {
                mockAssignmentList = new ArrayList<>();
                mockAssignmentList.add(new Assignment("as1", "Chapter 4 Exercise Questions", "Data Structures", "Tomorrow, 11:59 PM", "HIGH", false));
                mockAssignmentList.add(new Assignment("as2", "SQL Query Optimization Lab", "Database Systems", "Friday, 10:00 AM", "MEDIUM", false));
                mockAssignmentList.add(new Assignment("as3", "Read AI Research Paper", "Machine Learning", "Next Monday", "LOW", false));
                mockAssignmentList.add(new Assignment("as4", "Setup Android Studio Environment", "Mobile Dev", "Last Week", "HIGH", true));
            }
            result.setValue(Resource.success(new ArrayList<>(mockAssignmentList)));
        }, 300);

        return result;
    }

    public void toggleAssignmentCompletion(String assignmentId, boolean isCompleted) {
        if (mockAssignmentList == null) return;
        for (Assignment item : mockAssignmentList) {
            if (item.getId().equals(assignmentId)) {
                item.setCompleted(isCompleted);
                break;
            }
        }
    }

    public void addAssignment(Assignment assignment) {
        if (mockAssignmentList == null) {
            mockAssignmentList = new ArrayList<>();
        }
        mockAssignmentList.add(0, assignment);
    }

    // ==========================================
    // NOTES MODULE LOGIC
    // ==========================================

    private List<Note> mockNotesList;

    public LiveData<Resource<List<Note>>> getNotes() {
        MutableLiveData<Resource<List<Note>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (mockNotesList == null) {
                mockNotesList = new ArrayList<>();
                mockNotesList.add(new Note("n1", "Chapter 4: Trees and Graphs", "Data Structures", "Alex Student", "PDF", 45, "2 hours ago"));
                mockNotesList.add(new Note("n2", "Normal Forms & Relational Algebra", "Database Systems", "Sarah Connor", "PDF", 128, "Yesterday"));
                mockNotesList.add(new Note("n3", "Midterm Cheat Sheet", "Operating Systems", "John McCarthy", "IMG", 302, "Last Week"));
                mockNotesList.add(new Note("n4", "Gradient Descent Notes", "Machine Learning", "Linus Torvalds", "PDF", 12, "2 days ago"));
            }
            result.setValue(Resource.success(new ArrayList<>(mockNotesList)));
        }, 300);

        return result;
    }

    public void addNote(Note note) {
        if (mockNotesList == null) {
            mockNotesList = new ArrayList<>();
        }
        mockNotesList.add(0, note);
    }
}
