package com.example.campusconnect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campusconnect.R;
import com.example.campusconnect.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final List<Note> notes;
    private final OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onDownloadClicked(Note note);
    }

    public NotesAdapter(List<Note> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(notes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivFileType;
        private final TextView tvNoteTitle;
        private final TextView tvNoteSubject;
        private final TextView tvNoteMeta;
        private final View ivDownload;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFileType = itemView.findViewById(R.id.ivFileType);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteSubject = itemView.findViewById(R.id.tvNoteSubject);
            tvNoteMeta = itemView.findViewById(R.id.tvNoteMeta);
            ivDownload = itemView.findViewById(R.id.ivDownload);
        }

        public void bind(Note item, OnNoteClickListener listener) {
            tvNoteTitle.setText(item.getTitle());
            tvNoteSubject.setText(item.getSubjectName());
            
            String metaText = "By " + item.getUploaderName() + " • " + 
                    itemView.getContext().getString(R.string.downloads_format, item.getDownloadsCount());
            tvNoteMeta.setText(metaText);

            if ("IMG".equalsIgnoreCase(item.getFileType())) {
                ivFileType.setImageResource(R.drawable.ic_image);
            } else {
                ivFileType.setImageResource(R.drawable.ic_pdf);
            }

            ivDownload.setOnClickListener(v -> {
                if (listener != null) listener.onDownloadClicked(item);
            });
        }
    }
}
