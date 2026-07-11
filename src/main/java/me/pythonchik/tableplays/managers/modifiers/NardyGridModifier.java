package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Nardy Grid. Snaps the placement location to a 12-spot layout (6 spots per side, separated by a center border)
 * on a 64-pixel mapped texture.
 * 
 * Modifier usage: {@code NARDYGRID}
 */
public class NardyGridModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getLocation().ifPresent(spawn_loc -> {
            context.getClickedInteraction().ifPresent(clickedInteraction -> {
                context.getVector().ifPresent(clicked_pos -> {
                    float width = clickedInteraction.getInteractionWidth();
                    Location interactionLocation = clickedInteraction.getLocation();

                    float yaw;
                    if (clickedInteraction.getVehicle() != null) {
                        yaw = clickedInteraction.getVehicle().getLocation().getYaw();
                    } else {
                        yaw = interactionLocation.getYaw();
                    }
                    yaw = (yaw % 360 + 360) % 360;
                    
                    boolean isXAxis;
                    if ((yaw >= 315 || yaw < 45) || (yaw >= 135 && yaw < 225)) {
                        isXAxis = true; // Looking Z+ or Z-, split is on Z axis, spots run along X
                    } else {
                        isXAxis = false; // Looking X+ or X-, split is on X axis, spots run along Z
                    }

                    // Calculate the position relative to the bottom-left of the interaction using clicked_pos
                    double localPos;
                    if (isXAxis) {
                        localPos = clicked_pos.getX() + width / 2;
                    } else {
                        localPos = clicked_pos.getZ() + width / 2;
                    }

                    double pixelSize = width / 64.0;
                    double currentPixel = localPos / pixelSize;

                    double snappedPixel;
                    if (currentPixel < 32) {
                        // Left/Top side (pixels 1 to 31)
                        int spot = (int) Math.floor((currentPixel - 1) / 5.0);
                        if (spot < 0) spot = 0;
                        if (spot > 5) spot = 5;
                        snappedPixel = 1 + (spot * 5) + 2.5;
                    } else {
                        // Right/Bottom side (pixels 33 to 63)
                        int spot = (int) Math.floor((currentPixel - 33) / 5.0);
                        if (spot < 0) spot = 0;
                        if (spot > 5) spot = 5;
                        snappedPixel = 33 + (spot * 5) + 2.5;
                    }

                    double finalOffset = snappedPixel * pixelSize;

                    if (isXAxis) {
                        spawn_loc.setX((interactionLocation.getX() - width / 2) + finalOffset);
                    } else {
                        spawn_loc.setZ((interactionLocation.getZ() - width / 2) + finalOffset);
                    }

                    flag.set(true);

                });
            });
        });
        return flag.get();
    }
}
