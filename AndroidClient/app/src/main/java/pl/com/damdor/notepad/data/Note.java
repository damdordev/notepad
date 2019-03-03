package pl.com.damdor.notepad.data;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class Note implements Cloneable {
    public static final long UNINITIALIZED_ID = 0;

    private long mId = UNINITIALIZED_ID;
    private String mTitle = "";
    private String mContent = "";

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (mId != note.mId) return false;
        if (!mTitle.equals(note.mTitle)) return false;
        return mContent.equals(note.mContent);
    }

    @Override
    public int hashCode() {
        int result = (int) mId;
        result = 31 * result + mTitle.hashCode();
        result = 31 * result + mContent.hashCode();
        return result;
    }

    @Override
    public Note clone() {
        try {
            return (Note) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
