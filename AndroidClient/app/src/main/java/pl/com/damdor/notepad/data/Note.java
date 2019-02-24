package pl.com.damdor.notepad.data;

import java.util.Calendar;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class Note {

    private String mTitle;
    private String mContent;
    private Calendar mCreateTime;
    private Calendar mLastUpdateTime;

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
}
