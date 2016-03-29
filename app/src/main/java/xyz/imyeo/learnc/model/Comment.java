package xyz.imyeo.learnc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
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
}
