package xyz.imyeo.learnc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("Conversation")
public class Conversation extends ParseObject {

    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_SHARE = 2;
    public static final int TYPE_DISCUSS = 3;

    public ParseUser getCreator() {
        return getParseUser("creator");
    }

    public void setCreator(ParseUser creator) {
        put("creator", creator);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String mTitle) {
        put("title", mTitle);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public int getType() {
        return getInt("type");
    }

    public void setType(int type) {
        put("type", type);
    }

    public int getViews() {
        return getInt("views");
    }

    public void setViews(int views) {
        put("views", views);
    }

    public ParseRelation<Comment> getComment() {
        return getRelation("comments");
    }

    public void addComment(Comment comment) {
        getRelation("comments").add(comment);
    }

    public void deleteComment(Comment comment) {
        getRelation("comments").remove(comment);
    }
}
