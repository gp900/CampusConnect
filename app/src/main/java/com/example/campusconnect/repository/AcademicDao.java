package com.example.campusconnect.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.campusconnect.model.Assignment;
import com.example.campusconnect.model.AttendanceItem;
import com.example.campusconnect.model.Note;
import com.example.campusconnect.model.TimetableItem;

import java.util.List;

@Dao
public interface AcademicDao {

    // Timetable
    @Query("SELECT * FROM timetable")
    LiveData<List<TimetableItem>> getTimetable();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTimetable(List<TimetableItem> items);

    @Query("DELETE FROM timetable")
    void clearTimetable();

    // Attendance
    @Query("SELECT * FROM attendance")
    LiveData<List<AttendanceItem>> getAttendance();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendance(List<AttendanceItem> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendanceItem(AttendanceItem item);

    @Query("UPDATE attendance SET classesAttended = :attended WHERE id = :id")
    void updateAttendance(String id, int attended);

    @Query("DELETE FROM attendance")
    void clearAttendance();

    // Assignments
    @Query("SELECT * FROM assignments ORDER BY dueDate ASC")
    LiveData<List<Assignment>> getAssignments();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignment(Assignment assignment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignments(List<Assignment> assignments);

    @Query("UPDATE assignments SET isCompleted = :isCompleted WHERE id = :id")
    void updateAssignmentStatus(String id, boolean isCompleted);

    @Query("DELETE FROM assignments")
    void clearAssignments();

    // Notes
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(List<Note> notes);

    @Query("DELETE FROM notes")
    void clearNotes();
}
