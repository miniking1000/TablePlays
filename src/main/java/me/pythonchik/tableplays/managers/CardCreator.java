package me.pythonchik.tableplays.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class CardCreator {

    public static ItemStack getEmpty36bundle() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 0);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Card.getValue());
            String saveType = ValuesManager.getSaveType(36); // magical 36 because it's a 36 card deck
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";36;random"); // save in data, as its the smallest deck, 36 cards max, get randomly.
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card36"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card36"); // generate new bundle
            }
             */
            bundle.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(bundle));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));

            translationManager manager = translationManager.getInstance();
            meta.setLore(manager.getLore(bundle));
            meta.setDisplayName(manager.getName(bundle));
        }
        bundle.setItemMeta(meta);
        return bundle;
    }

    public static ItemStack getEmpty52bundle() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 0); // 0, cards bundle, may just be null for this case
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Card.getValue());
            String saveType = ValuesManager.getSaveType(52); // magical 52 because it's a 52 card deck
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";52;random"); // save in data, as it's the smallest deck, 36 cards max, get randomly.
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card52"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card52"); // generate new bundle
            }
            */
            bundle.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(bundle));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));

            translationManager manager = translationManager.getInstance();
            meta.setLore(manager.getLore(bundle));
            meta.setDisplayName(manager.getName(bundle));
        }
        bundle.setItemMeta(meta);
        return bundle;
    }

    public static ItemStack getEmpty54bundle() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 0);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Card.getValue());
            String saveType = ValuesManager.getSaveType(54); // magical 54 because it's a 54 card deck
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";54;random"); // save in data, as its the smallest deck, 54 cards max, get randomly.
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card54"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.card54"); // generate new bundle
            }
             */
            bundle.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(bundle));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));

            translationManager manager = translationManager.getInstance();
            meta.setLore(manager.getLore(bundle));
            meta.setDisplayName(manager.getName(bundle));
        }
        bundle.setItemMeta(meta);
        return bundle;
    }


    //were used to generate default.cardXX, not useless. (maybe now, but I won't remove them)

    public static ArrayList<ItemStack> get36Deck() {
        ArrayList<ItemStack> cards = new ArrayList<>();
        for (int j = 0; j<4;j++) {
            for (int i = 4; i < 13; i++) {
                cards.add(getCard(j*20+i));
            }
        }
        return cards;
    }

    public static ArrayList<ItemStack> get52Deck() {
        ArrayList<ItemStack> cards = new ArrayList<>();
        for (int j = 0; j<4;j++) {
            for (int i = 0; i < 13; i++) {
                cards.add(getCard(j*20+i));
            }
        }
        return cards;
    }

    public static ArrayList<ItemStack> get54Deck() {
        ArrayList<ItemStack> cards = new ArrayList<>();
        for (int j = 0; j<4;j++) {
            for (int i = 0; i < 13; i++) {
                cards.add(getCard(j*20+i));
            }
        }
        cards.add(getCard(75));
        cards.add(getCard(75));
        return cards;
    }


    public static ItemStack getCard(int subType) {
        ItemStack card = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = card.getItemMeta();
        String type = Util.ItemTypes.Card.getValue();
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:NOTHING,18:PLACE_MAIN,146:PLACE_MAIN,66:PLACE_TOP_MAIN,194:NOTHING,194:PLACE_TOP_MAIN,20:PLACE_LEFT,148:PLACE_LEFT,68:PLACE_TOP_LEFT,196:PLACE_TOP_LEFT");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "384:HIDESUB80,520:REVSUB,648:REVSUB,193:REVSUB,193:EFLIP,146:FLIP,66:REVSUB,194:FLIP,20:REVSUB,148:FLIP,68:REVSUB,196:FLIP,18:REVSUB,65:REVSUB,576:REVSUB,704:REVSUB");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
            //set 1, to use tags later.
            card.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(card));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));

            translationManager manager = translationManager.getInstance();
            meta.setLore(manager.getLore(card));
            meta.setDisplayName(manager.getName(card));
        }
        card.setItemMeta(meta);
        return card;
    }
}
