package xyz.imyeo.learnc.model;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

public class Comment extends ParseObject {

    public ParseUser getCreator() {
        return getParseUser("creator");
    }

    public void setCreator(ParseUser creator) {
        put("creator", creator);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content", content);
    }

    public List<String> getTags() {
        return getList("tags");
    }

    public void addTags(String tag) {
        getList("tags").add(tag);
    }

    public void removeTags(String tag) {
        getList("tags").remove(tag);
    }
}
