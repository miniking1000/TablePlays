package me.pythonchik.tableplays.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for creating action strings easily.
 * Format: "int:action,int:action"
 */
// this will be reworked soon™
public class ActionBuilder {
    private final List<String> actions = new ArrayList<>();

    public ActionBuilder addAction(String action, Util.ActionTag... tags) {
        Util.ActionTagSet tagSet = new Util.ActionTagSet();
        for (Util.ActionTag tag : tags) {
            tagSet.add(tag);
        }
        actions.add(tagSet.getInt() + ":" + action);
        return this;
    }

    public ActionBuilder addAction(Util.ActionTagSet tags, String action) {
        actions.add(tags.getInt() + ":" + action);
        return this;
    }

    public ActionBuilder addAction(int tagValue, String action) {
        actions.add(tagValue + ":" + action);
        return this;
    }

    public String build() {
        return String.join(",", actions);
    }
}
