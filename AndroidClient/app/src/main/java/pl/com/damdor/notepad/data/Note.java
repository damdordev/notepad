package pl.com.damdor.notepad.data;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class Note implements Cloneable {

    private int mId;
    private String mTitle = "";
    private String mContent = "";
    private Calendar mCreateTime = Calendar.getInstance();
    private Calendar mLastUpdateTime = Calendar.getInstance();

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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

    public Calendar getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(Calendar createTime) {
        mCreateTime = createTime;
    }

    public Calendar getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(Calendar lastUpdateTime) {
        mLastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (mId != note.mId) return false;
        if (!mTitle.equals(note.mTitle)) return false;
        if (!mContent.equals(note.mContent)) return false;
        if (!mCreateTime.equals(note.mCreateTime)) return false;
        return mLastUpdateTime.equals(note.mLastUpdateTime);
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + mTitle.hashCode();
        result = 31 * result + mContent.hashCode();
        result = 31 * result + mCreateTime.hashCode();
        result = 31 * result + mLastUpdateTime.hashCode();
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
