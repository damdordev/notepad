package pl.com.damdor.notepad.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.annimon.stream.function.Consumer;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-03-07.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {

    private static class Holder {
        private Note mNote;

        final TextView title;
        final TextView shortContent;
        final ImageButton deleteButton;

        Holder(View view){
            title = view.findViewById(R.id.item_note_title);
            shortContent = view.findViewById(R.id.item_note_short_content);
            deleteButton = view.findViewById(R.id.item_note_delete_button);
        }

        public void updateModel(Note note){
            mNote = note;
        }

        public Note getNote(){
            return mNote;
        }

    }

    private Consumer<Note> mOnDeleteNoteListener;
    private Consumer<Note> mOnEditNoteListener;

    public NoteListAdapter(@NonNull Context context,
                           @NonNull List<Note> objects) {
        super(context, -1, objects);
    }

    public void setOnDeleteNoteListener(Consumer<Note> listener){
        mOnDeleteNoteListener = listener;
    }

    public void setOnEditNoteListener(Consumer<Note> listener) { mOnEditNoteListener = listener; }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        Holder holder;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);

            holder.deleteButton.setTag(holder);
            holder.deleteButton.setOnClickListener(this::onDeleteButtonClicked);
            convertView.setOnClickListener(this::onEditButtonClicked);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.updateModel(note);
        holder.title.setText(Objects.requireNonNull(note).getTitle());
        holder.shortContent.setText(note.getContent());

        return convertView;
    }

    private void onDeleteButtonClicked(View view) {
        if(mOnDeleteNoteListener != null){
            Holder holder = (Holder) view.getTag();
            mOnDeleteNoteListener.accept(holder.mNote);
        }
    }

    private void onEditButtonClicked(View view) {
        if(mOnEditNoteListener != null){
            Holder holder = (Holder) view.getTag();
            mOnEditNoteListener.accept(holder.mNote);
        }
    }

}
