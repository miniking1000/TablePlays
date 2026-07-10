package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import me.pythonchik.tableplays.managers.Util;
import me.pythonchik.tableplays.managers.ValuesManager;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Stacks the item on top of other existing items at the interaction location by checking their hitboxes and pushing it up.
 * 
 * Modifier usage: {@code PUSH}
 */
public class PushModifier implements BaseModifier {
    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getItemStack().ifPresent(stack -> {
            List<Float> hitbox = ValuesManager.getItemHitbox(stack);
            context.getInteraction().ifPresent(interaction -> {
                if (interaction.getVehicle() == null) return;
                Location finalLoc = interaction.getVehicle().getLocation().clone();
                boolean adjusted;
                int maxIterations = 100; // I doubt you will have 100 items stacked on top of each other and you will click on the bottom one.
                do {
                    Collection<Entity> nearbyEntities = finalLoc.getWorld().getNearbyEntities(finalLoc, hitbox.get(0)*0.499, hitbox.get(1) * 1.5, hitbox.get(0)*0.499, entity -> entity.getType().equals(EntityType.ITEM_DISPLAY));
                    adjusted = false;
                    double currentY = finalLoc.getY();
                    double highestEntityTop = -Double.MAX_VALUE;
                    for (Entity entity : nearbyEntities) {
                        if (entity.getPersistentDataContainer().has(Util.ItemTags.Entity.getValue()) && ((ItemDisplay) entity).getItemStack() != null) {
                            if (entity.equals(interaction.getVehicle())) continue;
                            List<Float> entityHitbox = ValuesManager.getItemHitbox(((ItemDisplay) entity).getItemStack());
                            double entityTop = entity.getLocation().getY() + entityHitbox.get(1); //to account for imperfections in float calculations
                            if (entityTop > currentY) {
                                highestEntityTop = Math.max(highestEntityTop, entityTop);
                            }
                        }
                    }
                    if (highestEntityTop != -Double.MAX_VALUE) {
                        finalLoc.setY(highestEntityTop);
                        adjusted = true;
                    } else {
                        finalLoc.setY(currentY + 0.0001);
                        break;
                    }
                    maxIterations--;
                } while (adjusted && maxIterations > 0);
                Entity vehicle = interaction.getVehicle();
                vehicle.removePassenger(interaction);
                vehicle.teleport(finalLoc);
                vehicle.addPassenger(interaction);
                flag.set(true);

            });
        });
        return flag.get();
    }
}
