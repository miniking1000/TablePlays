package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Rotates the current interaction's display entity's yaw to the next multiple of a specified number of degrees.
 * 
 * Modifier usage: {@code ROT#} (where # is the degrees, e.g., ROT90)
 */
public class RotateModifier implements BaseModifier {
    //rot#
    //rot90 - rotate item to the nearest multiple of 90 degrees
    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getInteraction().ifPresent(interaction -> {
            //interaction here is a spawned interaction, I need to get its itemDisplay, and rotate pitch to 180
            if (interaction.getVehicle() != null) {
                Entity display = interaction.getVehicle();
                int alignTo = Integer.parseInt(modifier.substring(3));
                float yaw = display.getLocation().getYaw()+0.01f-((float) alignTo / 2); // from 0 to 360 (0.01 for the case when its already aligned to rotate still, and /2 to make center, and not corner
                float nextMultiple = ((float) Math.ceil(yaw / alignTo)) * alignTo;
                if (nextMultiple > 360) {
                    nextMultiple = alignTo;
                }
                // if it's the same still (e.g. I want to rotate already centered object -> do just one rotation
                if (nextMultiple == ((float) Math.ceil(display.getLocation().getYaw() / alignTo)) * alignTo) {
                    yaw = display.getLocation().getYaw()+0.01f; // from 0 to 360 (0.01 for the case when it's already aligned to rotate still, and /2 to make center, and not corner
                    nextMultiple = ((float) Math.ceil(yaw / alignTo)) * alignTo;
                    if (nextMultiple > 360) {
                        nextMultiple = alignTo;
                    }
                }
                display.setRotation(nextMultiple, display.getLocation().getPitch());
                flag.set(true);
            }
        });
        return flag.get();
    }
}
