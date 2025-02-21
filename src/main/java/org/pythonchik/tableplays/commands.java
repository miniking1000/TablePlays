package org.pythonchik.tableplays;

import org.pythonchik.tableplays.managers.ItemCreator;
import org.pythonchik.tableplays.managers.Util;
import org.pythonchik.tableplays.managers.Util.ItemTags;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.pythonchik.tableplays.managers.ValuesManager;

import java.util.ArrayList;
import java.util.List;

public class commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equals("base64")) {
                Player player = (Player) sender;
                ItemStack stack = player.getInventory().getItemInMainHand();
                String items = Util.convertItemsToBase64(new ArrayList<>(List.of(stack)));
                player.getInventory().addItem(Util.getItemsFromBase64(items).getFirst());
            } else {
                Player player = (Player) sender;
                player.getInventory().addItem(ItemCreator.getDice());
            }
        }
        return true;
    }
}
