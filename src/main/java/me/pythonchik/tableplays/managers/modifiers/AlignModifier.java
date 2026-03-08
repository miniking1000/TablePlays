package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.Location;
import org.bukkit.entity.Interaction;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlignModifier implements BaseModifier {
    /*
    Applied on spawned item, and alines
    */
    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (context.getClickedInteraction().isPresent() && context.getLocation().isPresent()) {
            Location spawnloc = context.getLocation().get();
            Interaction clicked_interaction = context.getClickedInteraction().get();
            spawnloc.setX(clicked_interaction.getLocation().getX());
            spawnloc.setZ(clicked_interaction.getLocation().getZ());
            flag.set(true);
        }
        context.getVector().ifPresent(clicked_pos -> {

        });
        return flag.get();
    }
}
