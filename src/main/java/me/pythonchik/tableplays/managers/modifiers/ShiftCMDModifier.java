package me.pythonchik.tableplays.managers.modifiers;

import me.pythonchik.tableplays.managers.ValuesManager;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Toggles the item's custom model data. Adds a specified amount to the base CMD, or reverts to the base CMD if it's already shifted.
 * 
 * Modifier usage: {@code SHIFT#} (where # is the amount to shift)
 */
public class ShiftCMDModifier implements BaseModifier {
    //shift12332
    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getItemStack().ifPresent(stack -> {
            ItemMeta meta = stack.getItemMeta();
            int basecmd = ValuesManager.getBaseCMD(stack);
            if (basecmd == stack.getItemMeta().getCustomModelData()) {
                meta.setCustomModelData(basecmd+Integer.parseInt(modifier.substring(5)));
            } else {
                meta.setCustomModelData(basecmd);
            }
            stack.setItemMeta(meta);
            flag.set(true);
        });
        return flag.get();
    }
}
