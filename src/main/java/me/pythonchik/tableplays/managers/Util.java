package me.pythonchik.tableplays.managers;

import me.pythonchik.tableplays.TablePlays;
import org.bukkit.util.RayTraceResult;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;

public class Util {

    public enum ActionTag {
        NONE_HAND(1),
        MAIN_HAND(2),
        LEFT_HAND(4),
        BOTH_HAND(8),
        ON_BLOCK(16),
        ON_AIR(32),
        ON_ITEM(64),
        WITH_SHIFT(128),
        FROM_BUNDLE(256),
        TO_BUNDLE(512);

        private final int value;

        ActionTag(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }
    /*
    public static void init() {
        kryo = new Kryo();
        kryo.register(ItemStack.class);
        kryo.register(Integer.class);
    }
     */

    public static class ActionTagSet {
        public static ActionTagSet fromEnum(EnumSet<ActionTag> enumSet) {
            ActionTagSet toReturn = new ActionTagSet();
            for (ActionTag tag : enumSet) {
                toReturn.add(tag);
            }
            return toReturn;
        }

        private int value;

        public ActionTagSet() {
            this.value = 0;
        }

        public ActionTagSet(int initialValue) {
            this.value = initialValue;
        }

        public void add(ActionTag tag) {
            this.value |= tag.getValue();
        }

        public void remove(ActionTag tag) {
            this.value &= ~tag.getValue();
        }

        public boolean contains(ActionTag tag) {
            return (this.value & tag.getValue()) != 0;
        }

        public boolean containsAny(ActionTag... tags) {
            //TOD0 implement BTrees to improve 0.0001 MS spent on this function
            //(I'm not doing this shit, it's a joke)
            for (ActionTag tag : tags) {
                if (contains(tag)) return true;
            }
            return false;
        }

        public int getInt() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }
    }

    public enum ItemTags {
        Item(new NamespacedKey(TablePlays.getPlugin(), "item")), // string, type of the item/entity
        Entity(new NamespacedKey(TablePlays.getPlugin(), "entity")), // boolean, used only in .has
        SubType(new NamespacedKey(TablePlays.getPlugin(), "subtype")), // integer, add this number to the config base to get a variant of current type, for example cards or dominos
        Actions(new NamespacedKey(TablePlays.getPlugin(), "actions")), //string, actions described in another file
        Modifiers(new NamespacedKey(TablePlays.getPlugin(), "modifiers")), //string, all modifiers applied to the actions
        ExtraData(new NamespacedKey(TablePlays.getPlugin(), "extra")), // planned to use for things like hcard, or simular


        Bundle(new NamespacedKey(TablePlays.getPlugin(), "bundle")), // string, bundle accepted types, e.g. what types of item may be placed inside.
        BundleMeta(new NamespacedKey(TablePlays.getPlugin(), "bundlemeta")), //NOT FACTS check BundleManager.isValidBundle for more accurate meta format. string of literal of "data", "uuid", then ";" with number, maximum items in the bundle. regex: (data|uuid);[123456789]\d*
        BundleData(new NamespacedKey(TablePlays.getPlugin(), "bundledata")); // string, the uuid if save is server side, or base64 string with arraylist of item if saved localy;

        private final NamespacedKey value;

        ItemTags(NamespacedKey value) {
            this.value = value;
        }

        public NamespacedKey getValue() {
            return value;
        }

    }

    public enum ItemTypes {
        Dice("dice"),
        Card("card"),
        Chip("chip"),
        Bundle("bundle"),
        Board("board"),
        Checker("checker"),
        Chess("chess"),
        Domino("domino");
        private String value;
        ItemTypes(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    public enum Callers {
        Ground("ground"),
        Main("main"),
        Left("left");
        private String value;
        Callers(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    public static Integer getItemStackCountFromString(String baseString) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(baseString));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            int size = dataInput.readInt();
            dataInput.close();
            return size;
        } catch (Exception ignored) {}
        return 0;
    }

    public static ArrayList<ItemStack> getItemsFromBase64(String baseString) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(baseString));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            int size = dataInput.readInt();
            ArrayList<ItemStack> returning_stacks = new ArrayList<>();
            for (int i = 0; i < size;i++){
                returning_stacks.add((ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return returning_stacks;
        } catch (Exception ignored) {}
        return new ArrayList<>();
    }

    public static String convertItemsToBase64(ArrayList<ItemStack> itemStacks) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(itemStacks.size());
            for (ItemStack stack : itemStacks) {
                dataOutput.writeObject(stack);
            }
            dataOutput.close();
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception ignored) {}
        return "Errore";
    }


    /*
    public static Integer getItemStackCountFromString(String baseString) {
        try {
            byte[] data = Base64.getDecoder().decode(baseString);
            try (Input input = new Input(data)) {
                return kryo.readObject(input, Integer.class);
            }
        } catch (Exception e) {
            System.out.println(baseString);
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<ItemStack> getItemsFromBase64(String baseString) {
        try {
            byte[] data = Base64.getDecoder().decode(baseString);
            try (Input input = new Input(data)) {
                int size = kryo.readObject(input, Integer.class);
                ArrayList<ItemStack> returningStacks = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    returningStacks.add(kryo.readObject(input, ItemStack.class));
                }
                return returningStacks;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String convertItemsToBase64(ArrayList<ItemStack> itemStacks) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            kryo.writeObject(output, itemStacks.size());
            for (ItemStack itemStack : itemStacks) {
                kryo.writeObject(output, itemStack);
            }
            // Ensure that all data is flushed to the underlying stream
            output.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            System.out.println(Base64.getEncoder().encodeToString(bytes));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Errore";
    }
     */

    public static Location getBlockEyeLoc(Player player) {
        Vector direction = player.getEyeLocation().getDirection();
        Location eyeloc = player.getEyeLocation();
        for (double i = 0; i < player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getBaseValue(); i += 0.002) {
            eyeloc.add(direction.clone().multiply(0.002));
            if (eyeloc.getBlock().getType() != Material.AIR) {break;}
        }
        eyeloc.setY(eyeloc.getY() + 0.005); //moving up a bit, to not clip into anything
        return eyeloc;
    }

    public static ArrayList<String> getModifiers(ItemStack stack, String action) { // action here is number from ActionTagSet.toString()
        String[] modifs = (stack.getItemMeta().getPersistentDataContainer().getOrDefault(ItemTags.Modifiers.getValue(), PersistentDataType.STRING, "")).split(",");
        ArrayList<String> modifiers = new ArrayList<>();
        for (String modif : modifs) {
            String[] split_mod = modif.split(":");
            if (split_mod.length >= 2 && (split_mod[0].equals(action) || split_mod[0].equals("*"))){
                modifiers.add(split_mod[1]);
            }
        }
        return modifiers;
    }

    public static Vector getClickedPosition(Player player) {
        RayTraceResult result = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), (int) player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getBaseValue());

        if (result == null || result.getHitBlock() == null) {
            return null; // No block hit
        }

        // Get the exact hit position
        Location hitPos = result.getHitPosition().toLocation(player.getWorld());

        // Get the block's world position (integer values)
        Location blockPos = result.getHitBlock().getLocation();

        // Convert to local block coordinates (values between 0 and 1)
        double localX = hitPos.getX() - blockPos.getBlockX();
        double localY = hitPos.getY() - blockPos.getBlockY();
        double localZ = hitPos.getZ() - blockPos.getBlockZ();

        return new Vector(localX, localY, localZ);
    }
}
