package xyz.imyeo.learnc.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Conversation")
public class Conversation extends ParseObject {

    public static final int TYPE_QUESTION = 1;
    public static final int TYPE_SHARE = 2;
    public static final int TYPE_DISCUSS = 3;

    public ParseUser getCreator() {
        return getParseUser("creator");
    }

    public Conversation setCreator(ParseUser creator) {
        put("creator", creator);
        return this;
    }

    public String getTitle() {
        return getString("title");
    }

    public Conversation setTitle(String mTitle) {
        put("title", mTitle);
        return this;
    }

    public String getDescription() {
        return getString("description");
    }

    public Conversation setDescription(String description) {
        put("description", description);
        return this;
    }

    public int getType() {
        return getInt("type");
    }

    public Conversation setType(int type) {
        put("type", type);
        return this;
    }

    public int getViews() {
        return getInt("views");
    }

    public Conversation setViews(int views) {
        put("views", views);
        return this;
    }

    public int getCommentNumber() {
        return getInt("commentNumber");
    }

    public List<CharSequence> getTags() {
        return getList("tags");
    }

    public Conversation addTag(CharSequence tag) {
        List<CharSequence> tags = getList("tags");
        if (tags == null) {
            tags = new ArrayList<>();
            put("tags", tags);
        }
        tags.add(tag);
        return this;
    }

    public Conversation addTags(List<CharSequence> tag) {
        List<CharSequence> tags = getList("tags");
        if (tags == null) {
            put("tags", tag);
        } else {
            tags.addAll(tag);
        }
        return this;
    }

    public void removeTag(CharSequence tag) {
        getList("tags").remove(tag);
    }
}
