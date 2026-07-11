package me.pythonchik.tableplays.managers.modifiers;

import me.pythonchik.tableplays.managers.ValuesManager;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Resets custom model data of the item to the base model from the config (what you get from craft).
 * 
 * Modifier usage: {@code RESETCMD}
 */
public class ResetCMDModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getItemStack().ifPresent(stack -> {
            if (stack.getItemMeta() != null) {
                ItemMeta meta = stack.getItemMeta();
                meta.setCustomModelData(ValuesManager.getBaseCMD(stack));
                stack.setItemMeta(meta);
                flag.set(true);
            }
        });
        return flag.get();
    }
}
