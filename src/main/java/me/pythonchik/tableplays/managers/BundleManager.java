package me.pythonchik.tableplays.managers;

import me.pythonchik.tableplays.TablePlays;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class BundleManager {
    public static boolean addToBundle(ItemStack bundle, ItemStack item) {
        if (!isValidBundle(bundle)) return false;
        PersistentDataContainer container = bundle.getItemMeta().getPersistentDataContainer();
        String[] types = container.get(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING).split(",");
        String[] meta = container.get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).split(";");
        if (!Arrays.asList(types).contains(item.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.Item.getValue(), PersistentDataType.STRING))) {
            return false; // bundle does not accept that type
        }
        ArrayList<ItemStack> currentItems = getBundleItems(bundle);
        if (currentItems.size() >= Integer.parseInt(meta[1])) return false; // max items reached
        currentItems.add(item);
        saveItemsToBundle(bundle, currentItems);
        return true;
    }
    public static ArrayList<ItemStack> getBundleItems(ItemStack bundle) {
        if (!isValidBundle(bundle)) return new ArrayList<>();
        String data = bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING);
        if (data.matches("default.*")) return (ArrayList<ItemStack>) TablePlays.defaults.get(data).clone(); // return a copy of default pack, so it will not affect next defaults
        if (TablePlays.data.getString(data) != null) return Util.getItemsFromBase64(TablePlays.data.getString(data));
        return Util.getItemsFromBase64(data);
    }

    public static Integer getBundleCurrentItems(ItemStack bundle) {
        if (!isValidBundle(bundle)) return -1;
        String data = bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING);
        if (data.matches("default.*")) return ((ArrayList<ItemStack>) TablePlays.defaults.get(data).clone()).size(); // return a copy of default pack, so it will not affect next defaults
        if (TablePlays.data.getString(data) != null) return Util.getItemStackCountFromString(TablePlays.data.getString(data));
        return Util.getItemStackCountFromString(data);
    }

    public static int getMaxItems(ItemStack bundle) {
        if (!isValidBundle(bundle)) return -1;
        return Integer.parseInt(bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).split(";")[1]);
    }

    public static boolean saveItemsToBundle(ItemStack bundle, ArrayList<ItemStack> items) {
        return saveItemsToBundle(bundle, Util.convertItemsToBase64(items));
    }

    public static boolean saveItemsToBundle(ItemStack bundle, String base64) {
        String type = bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).split(";")[0];
        if (type.equals("uuid")) {
            // so you can't write anything to default.
            if (bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING).matches("default.*")) {
                String uuid = UUID.randomUUID().toString();
                ItemMeta metaamphetamine = bundle.getItemMeta();
                metaamphetamine.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, uuid);
                bundle.setItemMeta(metaamphetamine);
                TablePlays.data.set(uuid, base64);
            } else {
                TablePlays.data.set(bundle.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING), base64);
            }
        } else {
            ItemMeta meta = bundle.getItemMeta();
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, base64);
            bundle.setItemMeta(meta);
        }
        updateBundle(bundle);
        return true;
    }

    public static boolean isValidBundle(ItemStack bundle) {
        if (bundle == null) return false; // nothing is not a valid bundle
        if (bundle.getItemMeta() == null) return false; // item with no meta is not
        PersistentDataContainer container = bundle.getItemMeta().getPersistentDataContainer();
        if (!container.has(Util.ItemTags.Item.getValue(), PersistentDataType.STRING)) return false; // it's not my item
        if (!container.get(Util.ItemTags.Item.getValue(), PersistentDataType.STRING).equals(Util.ItemTypes.Bundle.getValue())) return false; //it's not a bundle
        if (!container.has(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING)) return false; // it's bundle but not bundle?
        if (!container.has(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING)) return false; // does not have necessary meta
        if (!container.has(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING)) return false; // does not have necessary data
        if (!container.get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).matches("(data|uuid);[0-9]\\d*;(queue|stack|random)")) return false; // meta is in wrong format
        return true;
    }

    public static void updateBundle(ItemStack bundle) {
        ItemMeta meta = bundle.getItemMeta();
        meta.setLore(translationManager.instance.getLore(bundle));
        bundle.setItemMeta(meta);
        //update lore and etc of bundle.
    }

}
