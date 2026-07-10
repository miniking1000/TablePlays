package me.pythonchik.tableplays.managers.modifiers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Sets a random yaw (0 to 359 degrees) for the item's placement location.
 * 
 * Modifier usage: {@code RANDYAW}
 */
public class RandYawModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getLocation().ifPresent(spawn_loc -> {
            spawn_loc.setYaw(new Random().nextInt(360));
            flag.set(true);
        });
        return flag.get();
    }
}
