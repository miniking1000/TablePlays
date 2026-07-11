package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Flips the pitch of the clicked interaction's display entity (between 90 and -90 degrees).
 * 
 * Modifier usage: {@code EFLIP}
 */
public class EntityFlipModifier implements BaseModifier {


    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getClickedInteraction().ifPresent(interaction -> {
            //interaction here is a spawned interaction, I need to get its itemDisplay, and rotate pitch to 180
            if (interaction.getVehicle() != null) {
                Entity display = interaction.getVehicle();
                display.setRotation(display.getLocation().getYaw(), display.getLocation().getPitch() == -90 ? 90 : -90);
                flag.set(true);
            }
        });
        return flag.get();
    }
}
