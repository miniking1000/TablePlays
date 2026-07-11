package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import me.pythonchik.tableplays.managers.Util;
import me.pythonchik.tableplays.managers.ValuesManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Reveal Subtype. Restores the original subtype hidden by HIDESUB#, resets the custom model data, and updates the display entity.
 * 
 * Modifier usage: {@code REVSUB}
 */
public class RevealSubModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        // REVSUB
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getItemStack().ifPresent(stack -> {
            //ItemStack stack = old_stack.clone();
            if (stack.getItemMeta() != null && stack.getItemMeta().getPersistentDataContainer().has(Util.ItemTags.ExtraData.getValue(), PersistentDataType.STRING)) {

                ItemMeta meta = stack.getItemMeta();
                String[] extras = Objects.requireNonNull(meta.getPersistentDataContainer().get(Util.ItemTags.ExtraData.getValue(), PersistentDataType.STRING)).split(";");

                for (String extra : extras) {
                    String[] split = extra.split(":");
                    if (split[0].equals("subtype") && split.length >= 2) {
                        // put subtype back
                        meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, Integer.parseInt(split[1]));
                        break;
                    }
                }


                stack.setItemMeta(meta);
                meta.setCustomModelData(ValuesManager.getBaseCMD(stack));
                stack.setItemMeta(meta);
                String issuer = context.getIssuer();
                if (issuer.equals(Util.Callers.Ground.getValue()) && context.getClickedInteraction().isPresent() && context.getClickedInteraction().get().getVehicle() != null && Objects.requireNonNull(context.getClickedInteraction().get().getVehicle()).getType().equals(EntityType.ITEM_DISPLAY)) {
                    ((ItemDisplay) Objects.requireNonNull(context.getClickedInteraction().get().getVehicle())).setItemStack(stack);
                }
                flag.set(true);
                //if (context.getClickedInteraction().isPresent() && context.getClickedInteraction().get().getVehicle() != null && context.getClickedInteraction().get().getVehicle().getType().equals(EntityType.ITEM_DISPLAY)) {
            }
        });
        return flag.get();
    }
}
