package me.pythonchik.tableplays.managers;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for creating modifier strings easily.
 * Format: "int:modifier,int:modifier" (or "*:modifier")
 */
// this will be reworked soon™
public class ModifierBuilder {
    private final List<String> modifiers = new ArrayList<>();

    public ModifierBuilder addModifier(String modifier, Util.ActionTag... tags) {
        Util.ActionTagSet tagSet = new Util.ActionTagSet();
        for (Util.ActionTag tag : tags) {
            tagSet.add(tag);
        }
        modifiers.add(tagSet.getInt() + ":" + modifier);
        return this;
    }

    public ModifierBuilder addModifier(Util.ActionTagSet tags, String modifier) {
        modifiers.add(tags.getInt() + ":" + modifier);
        return this;
    }

    public ModifierBuilder addModifier(int tagValue, String modifier) {
        modifiers.add(tagValue + ":" + modifier);
        return this;
    }

    public ModifierBuilder addGlobalModifier(String modifier) {
        modifiers.add("*:" + modifier);
        return this;
    }

    public String build() {
        return String.join(",", modifiers);
    }
}
