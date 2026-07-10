package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Entity Center Grid. Similar to CGRID, but snaps relative to the clicked interaction entity's width and position instead of a block.
 * 
 * Modifier usage: {@code ECGRID#} (where # is the number of divisions)
 */
public class ECGridModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        //modifier looks like this: ECGRID10
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getLocation().ifPresent(spawn_loc -> {
            context.getClickedInteraction().ifPresent(clickedInteraction -> {
                context.getVector().ifPresent(clicked_pos -> {
                    int divisions = Integer.parseInt(modifier.substring(6));
                    if (divisions <= 0) return;
                    float width = clickedInteraction.getInteractionWidth();
                    Location interactionLocation = clickedInteraction.getLocation();
                    double regionSize = width / divisions;
                    double adjustedX = clicked_pos.getX() + width/2;
                    double adjustedZ = clicked_pos.getZ() + width/2;
                    spawn_loc.setX(interactionLocation.getX() - width/2 + regionSize * (((int) (adjustedX / regionSize)) + 0.5));
                    spawn_loc.setZ(interactionLocation.getZ() - width/2 + regionSize * (((int) (adjustedZ / regionSize)) + 0.5));
                    flag.set(true);
                });
            });
        });
        return flag.get();
    }
}
