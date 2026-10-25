package com.example.campusconnect.ui.academics;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.adapter.NotesAdapter;
import com.example.campusconnect.model.Note;
import com.example.campusconnect.viewmodel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements NotesAdapter.OnNoteClickListener {

    private TextInputEditText etSearch;
    private RecyclerView rvNotes;
    private ProgressBar progressBar;
    private LinearLayout layoutEmptyState;
    private ExtendedFloatingActionButton fabUploadNote;

    private NotesViewModel notesViewModel;
    private NotesAdapter notesAdapter;
    private List<Note> allNotes = new ArrayList<>();
    
    private ActivityResultLauncher<String> filePickerLauncher;
    private TextView tvSelectedFileName;
    private boolean isFileSelected = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                isFileSelected = true;
                if (tvSelectedFileName != null) {
                    // Simple mock for actual file name extraction
                    String path = uri.getPath();
                    String filename = path != null ? path.substring(path.lastIndexOf('/') + 1) : "document.pdf";
                    tvSelectedFileName.setText(filename);
                }
            }
        });

        setupListeners();
        observeNotes();
    }

    private void initViews(View view) {
        etSearch = view.findViewById(R.id.etSearch);
        rvNotes = view.findViewById(R.id.rvNotes);
        progressBar = view.findViewById(R.id.progressBar);
        layoutEmptyState = view.findViewById(R.id.layoutEmptyState);
        fabUploadNote = view.findViewById(R.id.fabUploadNote);
    }

    private void setupListeners() {
        fabUploadNote.setOnClickListener(v -> showAddNoteDialog());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void observeNotes() {
        notesViewModel.getNotes().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    rvNotes.setVisibility(View.GONE);
                    layoutEmptyState.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    if (resource.getData() != null) {
                        allNotes = resource.getData();
                        String currentQuery = etSearch.getText() != null ? etSearch.getText().toString() : "";
                        filterNotes(currentQuery);
                    }
                    break;
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void filterNotes(String query) {
        List<Note> filteredList = new ArrayList<>();
        if (query.trim().isEmpty()) {
            filteredList.addAll(allNotes);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Note note : allNotes) {
                if (note.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        note.getSubjectName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(note);
                }
            }
        }

        if (filteredList.isEmpty()) {
            rvNotes.setVisibility(View.GONE);
            layoutEmptyState.setVisibility(View.VISIBLE);
        } else {
            layoutEmptyState.setVisibility(View.GONE);
            rvNotes.setVisibility(View.VISIBLE);
            notesAdapter = new NotesAdapter(filteredList, this);
            rvNotes.setAdapter(notesAdapter);
        }
    }

    @Override
    public void onDownloadClicked(Note note) {
        Toast.makeText(getContext(), "Downloading: " + note.getTitle(), Toast.LENGTH_SHORT).show();
        // Here you would normally trigger Android DownloadManager to fetch note.getFileUrl()
    }

    private void showAddNoteDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_add_note, null);
        dialog.setContentView(view);

        TextInputEditText etTitle = view.findViewById(R.id.etTitle);
        TextInputEditText etSubject = view.findViewById(R.id.etSubject);
        AutoCompleteTextView actvFileType = view.findViewById(R.id.actvFileType);
        LinearLayout layoutFilePicker = view.findViewById(R.id.layoutFilePicker);
        tvSelectedFileName = view.findViewById(R.id.tvSelectedFileName);
        MaterialButton btnUpload = view.findViewById(R.id.btnUpload);
        
        isFileSelected = false; // reset for new dialog

        String[] fileTypes = {"PDF", "IMG"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, fileTypes);
        actvFileType.setAdapter(adapter);

        layoutFilePicker.setOnClickListener(v -> filePickerLauncher.launch("*/*"));

        btnUpload.setOnClickListener(v -> {
            String title = etTitle.getText() != null ? etTitle.getText().toString().trim() : "";
            String subject = etSubject.getText() != null ? etSubject.getText().toString().trim() : "";
            String type = actvFileType.getText() != null ? actvFileType.getText().toString().trim() : "PDF";

            if (title.isEmpty() || subject.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            
            if (!isFileSelected) {
                Toast.makeText(getContext(), "Please select a file to upload", Toast.LENGTH_SHORT).show();
                return;
            }

            Note newNote = new Note(
                    "n_" + System.currentTimeMillis(),
                    title, subject, "Alex Student", type, 0, "Just now"
            );
            notesViewModel.addNote(newNote);
            dialog.dismiss();
            Toast.makeText(getContext(), "Note Uploaded Successfully!", Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
}
