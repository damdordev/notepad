package pl.com.damdor.notepad.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-03-07.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {
    private static class Holder {
        public final TextView title;
        public final TextView shortContent;

        public Holder(View view){
            title = view.findViewById(R.id.item_note_title);
            shortContent = view.findViewById(R.id.item_note_short_content);
        }
    }

    public NoteListAdapter(@NonNull Context context,
                           @NonNull List<Note> objects) {
        super(context, -1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        Holder holder = null;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();

        }

        holder.title.setText(note.getTitle());
        holder.shortContent.setText(note.getContent());

        return convertView;
    }

}
