package com.example.campusconnect.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.campusconnect.MainActivity;
import com.example.campusconnect.R;
import com.example.campusconnect.model.User;
import com.example.campusconnect.utils.ValidationUtils;
import com.example.campusconnect.viewmodel.AuthViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilFullName;
    private TextInputEditText etFullName;
    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private TextInputLayout tilCourse;
    private AutoCompleteTextView actvCourse;
    private TextInputLayout tilDepartment;
    private AutoCompleteTextView actvDepartment;
    private TextInputLayout tilSemester;
    private AutoCompleteTextView actvSemester;
    private TextInputLayout tilRollNumber;
    private TextInputEditText etRollNumber;
    private TextInputLayout tilPassword;
    private TextInputEditText etPassword;
    private TextInputLayout tilConfirmPassword;
    private TextInputEditText etConfirmPassword;
    private MaterialButton btnRegister;
    private ProgressBar progressBar;
    private TextView tvSignIn;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupDropdownAdapters();
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setupListeners();
    }

    private void initViews() {
        tilFullName = findViewById(R.id.tilFullName);
        etFullName = findViewById(R.id.etFullName);
        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);
        tilCourse = findViewById(R.id.tilCourse);
        actvCourse = findViewById(R.id.actvCourse);
        tilDepartment = findViewById(R.id.tilDepartment);
        actvDepartment = findViewById(R.id.actvDepartment);
        tilSemester = findViewById(R.id.tilSemester);
        actvSemester = findViewById(R.id.actvSemester);
        tilRollNumber = findViewById(R.id.tilRollNumber);
        etRollNumber = findViewById(R.id.etRollNumber);
        tilPassword = findViewById(R.id.tilPassword);
        etPassword = findViewById(R.id.etPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void setupDropdownAdapters() {
        // Degree program courses (B.Tech = 8 Semesters, MBA Tech = 10 Semesters)
        String[] courses = new String[]{"B.Tech", "MBA Tech"};
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, courses);
        actvCourse.setAdapter(courseAdapter);

        // Departments including Artificial Intelligence (AI)
        String[] departments = new String[]{
                "Computer Science",
                "Artificial Intelligence",
                "Information Technology",
                "Electronics & Communication",
                "Mechanical Engineering",
                "Civil Engineering",
                "Business Administration"
        };
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, departments);
        actvDepartment.setAdapter(deptAdapter);

        // Default sem options (8 Semesters for B.Tech)
        updateSemesterOptions(8);

        actvCourse.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCourse = (String) parent.getItemAtPosition(position);
            if ("MBA Tech".equals(selectedCourse)) {
                updateSemesterOptions(10);
            } else {
                updateSemesterOptions(8);
            }
        });
    }

    private void updateSemesterOptions(int maxSemesters) {
        String[] semesters = new String[maxSemesters];
        for (int i = 0; i < maxSemesters; i++) {
            semesters[i] = "Semester " + (i + 1);
        }
        ArrayAdapter<String> semAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, semesters);
        actvSemester.setAdapter(semAdapter);
        actvSemester.setText("", false);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> attemptRegister());

        tvSignIn.setOnClickListener(v -> finish());
    }

    private void attemptRegister() {
        clearErrors();

        String fullName = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String course = actvCourse.getText() != null ? actvCourse.getText().toString().trim() : "";
        String department = actvDepartment.getText() != null ? actvDepartment.getText().toString().trim() : "";
        String semesterStr = actvSemester.getText() != null ? actvSemester.getText().toString().trim() : "";
        String rollNumber = etRollNumber.getText() != null ? etRollNumber.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String confirmPassword = etConfirmPassword.getText() != null ? etConfirmPassword.getText().toString().trim() : "";

        boolean isNameValid = ValidationUtils.isNonEmpty(fullName);
        boolean isEmailValid = ValidationUtils.isValidEmail(email);
        boolean isCourseValid = ValidationUtils.isNonEmpty(course);
        boolean isDeptValid = ValidationUtils.isNonEmpty(department);
        boolean isSemValid = ValidationUtils.isNonEmpty(semesterStr);
        boolean isRollValid = ValidationUtils.isNonEmpty(rollNumber);
        boolean isPasswordValid = ValidationUtils.isValidPassword(password);
        boolean doPasswordsMatch = java.util.Objects.equals(password, confirmPassword);

        if (!isNameValid) {
            tilFullName.setError(getString(R.string.error_empty_name));
        }
        if (!isEmailValid) {
            tilEmail.setError(getString(R.string.error_invalid_email));
        }
        if (!isCourseValid) {
            tilCourse.setError(getString(R.string.error_empty_course));
        }
        if (!isDeptValid) {
            tilDepartment.setError(getString(R.string.error_empty_department));
        }
        if (!isSemValid) {
            tilSemester.setError(getString(R.string.error_empty_semester));
        }
        if (!isRollValid) {
            tilRollNumber.setError(getString(R.string.error_empty_roll_number));
        }
        if (!isPasswordValid) {
            tilPassword.setError(getString(R.string.error_short_password));
        }
        if (!doPasswordsMatch) {
            tilConfirmPassword.setError(getString(R.string.error_password_mismatch));
        }

        if (!isNameValid || !isEmailValid || !isCourseValid || !isDeptValid || !isSemValid || !isRollValid || !isPasswordValid || !doPasswordsMatch) {
            return;
        }

        int semesterNum = parseSemesterNumber(semesterStr);

        User newUser = new User(null, fullName, email, course, department, semesterNum, rollNumber);

        authViewModel.register(newUser, password).observe(this, resource -> {
            if (resource == null) return;

            switch (resource.getStatus()) {
                case LOADING:
                    btnRegister.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, resource.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void clearErrors() {
        tilFullName.setError(null);
        tilEmail.setError(null);
        tilCourse.setError(null);
        tilDepartment.setError(null);
        tilSemester.setError(null);
        tilRollNumber.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);
    }

    private int parseSemesterNumber(String semesterStr) {
        if (semesterStr != null && semesterStr.contains(" ")) {
            try {
                return Integer.parseInt(semesterStr.split(" ")[1]);
            } catch (NumberFormatException e) {
                return 1;
            }
        }
        return 1;
    }
}
