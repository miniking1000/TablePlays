package me.pythonchik.tableplays.managers;

import me.pythonchik.tableplays.TablePlays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

public class ItemCreator {
    // new iteration of big data, better and not so big :(

    public static ItemStack getNull() {
        return new ItemStack(Material.AIR);
    }

    public static ItemStack getDice() {
        ItemStack dice = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );
        ItemMeta meta = dice.getItemMeta();
        String type = Util.ItemTypes.Dice.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING,
                    "18:PLACE_MAIN,20:PLACE_LEFT,146:PLACE_MAIN,148:PLACE_LEFT,194:PLACE_TOP_MAIN,196:PLACE_TOP_LEFT,65:PICK_UP,66:PICK_UP,68:PICK_UP");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "18:RCMDP6,20:RCMDP6,146:RCMDP6,148:RCMDP6,194:RCMDP6,196:RCMDP6,65:RESETCMD,66:RESETCMD,68:RESETCMD,196:ALIGN"); // ,66:ALIGN

            dice.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(dice));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(dice));
            meta.setDisplayName(manager.getName(dice));

        }

        dice.setItemMeta(meta);
        return dice;
    }

    public static ItemStack getBoard() {
        ItemStack board = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );
        ItemMeta meta = board.getItemMeta();
        String type = Util.ItemTypes.Board.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "18:PLACE_MAIN,20:PLACE_LEFT,146:PLACE_MAIN,148:PLACE_LEFT,65:PICK_UP");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "18:BLGRID1,20:BLGRID1,146:BLGRID1,148:BLGRID1,18:ROT90,20:ROT90,146:ROT90,148:ROT90,65:PROTECT");

            board.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(board));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(board));
            meta.setDisplayName(manager.getName(board));

        }

        board.setItemMeta(meta);
        return board;
    }

    public static ItemStack getNardyBoard() {
        ItemStack board = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );
        ItemMeta meta = board.getItemMeta();
        String type = Util.ItemTypes.NardyBoard.getValue();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "18:PLACE_MAIN,20:PLACE_LEFT,146:PLACE_MAIN,148:PLACE_LEFT,65:PICK_UP");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "18:BLGRID1,20:BLGRID1,146:BLGRID1,148:BLGRID1,18:ROT90,20:ROT90,146:ROT90,148:ROT90,65:PROTECT");

            board.setItemMeta(meta);

            meta.setCustomModelData(ValuesManager.getBaseCMD(board));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(board));
            meta.setDisplayName(manager.getName(board));

        }

        board.setItemMeta(meta);
        return board;
    }


    public static ItemStack get36bundle() {
        return CardCreator.getEmpty36bundle();
    }

    public static ItemStack get52bundle() {
        return CardCreator.getEmpty52bundle();
    }

    public static ItemStack get54bundle() {
        return CardCreator.getEmpty54bundle();
    }


    public static ItemStack getChip(int subType) {
        //ItemStack chip = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemStack chip = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        ); // items can not be both stackable and damagable, so I have to do this to remove this tag.

        ItemMeta meta = chip.getItemMeta();
        String type = Util.ItemTypes.Chip.getValue();
        if (meta == null) {
            TablePlays.instance.getLogger().warning("Meta is null during chip creation, why? Report this to the authors please");
            return chip;
        }

        meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
        meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:PLACE_MAIN,146:PLACE_MAIN,66:PLACE_TOP_MAIN,194:PLACE_TOP_MAIN,20:PLACE_LEFT,148:PLACE_LEFT,68:PLACE_TOP_LEFT,196:PLACE_TOP_LEFT");
        meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "18:RANDYAW,18:CGRID4,146:CGRID4,66:ALIGN,66:RANDYAW,66:PUSH,194:ALIGN,194:PUSH,20:RANDYAW,20:CGRID4,148:CGRID4,68:ALIGN,68:RANDYAW,68:PUSH,196:ALIGN,196:PUSH");
        meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
        chip.setItemMeta(meta);
        meta.setCustomModelData(ValuesManager.getBaseCMD(chip));
        meta.setMaxStackSize(ValuesManager.getMaxStack(type));
        //more values form config?

        translationManager manager = translationManager.getInstance();

        meta.setLore(manager.getLore(chip));
        meta.setDisplayName(manager.getName(chip));


        chip.setItemMeta(meta);
        return chip;
    }

    public static ItemStack getChipBundle(int subtype) {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );
        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        int size = ValuesManager.getChipsSize();
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subtype);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Chip.getValue());
            String saveType = ValuesManager.getSaveType(size);
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";" + size + ";random");
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "");
            } else {
                String uuid = UUID.randomUUID().toString();
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, uuid);
                TablePlays.data.set(uuid, "");
            }
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


    public static ItemStack getDomino() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );
        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        int size = 28; // standard domino size
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 1);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Domino.getValue());
            String saveType = ValuesManager.getSaveType(size);
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";" + size + ";random");
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.domino"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, UUID.randomUUID().toString()); // generate new bundle
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

    public static ArrayList<ItemStack> getDominoDeck() {
        ArrayList<ItemStack> result = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            result.add(getDomino(i));
        }
        return result;
    }

    public static ItemStack getDomino(int subType) {
        ItemStack domino = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = domino.getItemMeta();
        String type = Util.ItemTypes.Domino.getValue();
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:NOTHING,18:PLACE_MAIN,146:PLACE_MAIN,20:PLACE_LEFT,148:PLACE_LEFT");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "193:ERT90,18:ROT90,18:CGRID6,146:ROT90,146:CGRID6,20:ROT90,20:CGRID6,148:ROT90,148:CGRID6");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
            domino.setItemMeta(meta);
            meta.setCustomModelData(ValuesManager.getBaseCMD(domino));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(domino));
            meta.setDisplayName(manager.getName(domino));

        }

        domino.setItemMeta(meta);
        return domino;
    }


    public static ItemStack getCheckers() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        int size = 24; // standard checker size
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 3);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Checker.getValue());
            String saveType = ValuesManager.getSaveType(size);
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";" + size + ";stack");
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.checker"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, UUID.randomUUID().toString()); // generate new bundle
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
        // now we have bundle, fill it
        //fillCheckersBundle(bundle);
        return bundle;
    }

    public static ArrayList<ItemStack> getCheckerDeck() {
        ArrayList<ItemStack> result = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            result.add(getChecker(1));
        }
        for (int i = 0; i < 12; i++) {
            result.add(getChecker(2));
        }
        return result;
    }

    public static ItemStack getChecker(int subType) {
        ItemStack checker = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = checker.getItemMeta();
        String type = Util.ItemTypes.Checker.getValue();
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,66:PLACE_TOP_MAIN,194:PLACE_TOP_MAIN,148:PLACE_LEFT,68:PLACE_TOP_LEFT,196:PLACE_TOP_LEFT");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "576:RESETCMD,704:RESETCMD,520:RESETCMD,648:RESETCMD,66:ECGRID10,66:ROT90,194:ECGRID10,194:ROT90,194:SHIFT2,148:SHIFT2,148:ECGRID10,148:RESETCMD,68:ECGRID10,68:ROT90,196:ECGRID10,196:ROT90,196:SHIFT2");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
            checker.setItemMeta(meta);
            meta.setCustomModelData(ValuesManager.getBaseCMD(checker));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(checker));
            meta.setDisplayName(manager.getName(checker));
        }

        checker.setItemMeta(meta);
        return checker;
    }


    public static ItemStack getNardy() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        int size = 30; // standard nardy size
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 4);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Checker.getValue());
            String saveType = ValuesManager.getSaveType(size);
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";" + size + ";stack");
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.nardy"); // generate new bundle

            bundle.setItemMeta(meta); // set partial meta early so that other code can see what item this is (Values and Translation managers)

            meta.setCustomModelData(ValuesManager.getBaseCMD(bundle));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));

            translationManager manager = translationManager.getInstance();
            meta.setLore(manager.getLore(bundle));
            meta.setDisplayName(manager.getName(bundle));
        }
        bundle.setItemMeta(meta);
        return bundle;
    }

    public static ArrayList<ItemStack> getNardyDeck() {
        ArrayList<ItemStack> result = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            result.add(getNardyPiece(1));
        }
        for (int i = 0; i < 15; i++) {
            result.add(getNardyPiece(2));
        }
        return result;
    }

    public static ItemStack getNardyPiece(int subType) {
        ItemStack nardy = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = nardy.getItemMeta();
        String type = Util.ItemTypes.Nardy.getValue();
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING,
                    "65:PICK_UP,193:PICK_UP," + // picking up
                            "66:PLACE_TOP_MAIN," +
                            "194:PLACE_TOP_MAIN," +
                            "148:PLACE_LEFT," + // shift left block to just place
                            "68:PLACE_TOP_LEFT," +
                            "196:PLACE_TOP_LEFT");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING,
                    "66:PUSH,66:NARDYGRID,66:ROT90," +
                            "194:PUSH,194:NARDYGRID,194:ROT90," +
                            "68:PUSH,68:NARDYGRID,68:ROT90,68:ECGRID1," +
                            "196:PUSH,196:NARDYGRID,196:ROT90,196:ECGRID1");

            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
            nardy.setItemMeta(meta);
            meta.setCustomModelData(ValuesManager.getBaseCMD(nardy));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(nardy));
            meta.setDisplayName(manager.getName(nardy));
        }

        nardy.setItemMeta(meta);
        return nardy;
    }


    public static ItemStack getChess() {
        ItemStack bundle = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = bundle.getItemMeta();
        String type = Util.ItemTypes.Bundle.getValue();
        int size = 32; // standard chess set size
        if (meta != null){
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,18:GET_FROM_BUNDLE_MAIN,146:PLACE_MAIN,34:GET_FROM_BUNDLE_MAIN,162:GET_FROM_BUNDLE_MAIN,66:GET_FROM_BUNDLE_MAIN,194:GET_FROM_BUNDLE_MAIN,20:GET_FROM_BUNDLE_LEFT,148:PLACE_LEFT,36:GET_FROM_BUNDLE_LEFT,164:GET_FROM_BUNDLE_LEFT,68:PUT_FROM_GROUND,196:PUT_FROM_GROUND,24:PUT_FROM_MAIN,24:GET_FROM_BUNDLE_MAIN,152:PUT_FROM_MAIN,152:GET_FROM_BUNDLE_MAIN,40:PUT_FROM_MAIN,168:PUT_FROM_MAIN,72:PUT_FROM_MAIN,72:PUT_FROM_GROUND,200:PUT_FROM_MAIN,200:PUT_FROM_GROUND");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, 2);
            meta.getPersistentDataContainer().set(Util.ItemTags.Bundle.getValue(), PersistentDataType.STRING, Util.ItemTypes.Chess.getValue());
            String saveType = ValuesManager.getSaveType(size);
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING, saveType + ";" + size + ";queue");
            meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, "default.chess"); // generate new bundle
            /*
            if (saveType.equals("data")) {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, ""); // generate new bundle
            } else {
                meta.getPersistentDataContainer().set(Util.ItemTags.BundleData.getValue(), PersistentDataType.STRING, UUID.randomUUID().toString()); // generate new bundle
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

    public static ArrayList<ItemStack> getChessDeck() {
        ArrayList<ItemStack> result = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 8; i++) {
                result.add(getChessPiece(1+j)); // pawn
            }
            result.add(getChessPiece(3+j)); // de rook
            result.add(getChessPiece(5+j)); // knight
            result.add(getChessPiece(7+j)); // bishop
            result.add(getChessPiece(11+j)); // king
            result.add(getChessPiece(9+j)); // queen
            result.add(getChessPiece(7+j)); // bishop
            result.add(getChessPiece(5+j)); // knight
            result.add(getChessPiece(3+j)); // de rook
        }
        return result;
    }

    public static ItemStack getChessPiece(int subType) {
        ItemStack chess = Bukkit.getItemFactory().createItemStack(
                "minecraft:warped_fungus_on_a_stick[!minecraft:max_damage]"
        );

        ItemMeta meta = chess.getItemMeta();
        String type = Util.ItemTypes.Chess.getValue();
        if (meta != null) {
            meta.getPersistentDataContainer().set(Util.ItemTags.Item.getValue(), PersistentDataType.STRING, type);
            meta.getPersistentDataContainer().set(Util.ItemTags.Actions.getValue(), PersistentDataType.STRING, "65:PICK_UP,193:PICK_UP,66:PLACE_TOP_MAIN,194:PLACE_TOP_MAIN,148:PLACE_LEFT,68:PLACE_TOP_LEFT,196:PLACE_TOP_LEFT");
            meta.getPersistentDataContainer().set(Util.ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "66:ECGRID10,66:ROT90,194:ECGRID10,194:ROT90,148:ECGRID10,68:ECGRID10,68:ROT90,196:ECGRID10,196:ROT90");
            meta.getPersistentDataContainer().set(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER, subType);
            chess.setItemMeta(meta);
            meta.setCustomModelData(ValuesManager.getBaseCMD(chess));
            meta.setMaxStackSize(ValuesManager.getMaxStack(type));
            //more values form config?

            translationManager manager = translationManager.getInstance();

            meta.setLore(manager.getLore(chess));
            meta.setDisplayName(manager.getName(chess));

        }

        chess.setItemMeta(meta);
        return chess;
    }
}
