package me.pythonchik.tableplays.managers.modifiers;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Random Custom Model Data Plus. Adds a random number from 0 to a specified bound (exclusive) to the custom model data.
 * 
 * Modifier usage: {@code RCMDP#} (where # is the bound)
 */
public class RandomCMDModifier implements BaseModifier {

    @Override
    public boolean apply(ModifierContext context, String modifier, List<String> allModifiers) {
        /*
        When called, adds a random value to the custom_model_data. from 0 to 'bound'
         */
        AtomicBoolean flag = new AtomicBoolean(false);
        context.getItemStack().ifPresent(stack -> {
            if (stack.getItemMeta() != null) {
                int bound = Integer.parseInt(modifier.substring(5));
                int number = new Random().nextInt(0, bound);
                ItemMeta meta = stack.getItemMeta();
                meta.setCustomModelData(meta.getCustomModelData() + number);
                stack.setItemMeta(meta);
                flag.set(true);
            }
        });
        return flag.get();
    }
}
