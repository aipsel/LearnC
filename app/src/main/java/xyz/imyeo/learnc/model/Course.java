package xyz.imyeo.learnc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Course")
public class Course extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public Course setTitle(String title) {
        put("title", title);
        return this;
    }

    public String getIntro() {
        return getString("intro");
    }

    public Course setIntro(String intro) {
        put("intro", intro);
        return this;
    }

    public int getProgress() {
        return getInt("progress");
    }

    public Course setProgress(int progress) {
        if (progress >= 0 && progress <= 100)
            put("progress", progress);
        return this;
    }
}
