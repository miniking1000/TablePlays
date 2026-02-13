package me.pythonchik.tableplays;

import me.pythonchik.tableplays.managers.BundleManager;
import me.pythonchik.tableplays.managers.ModifierManager;
import me.pythonchik.tableplays.managers.Util;
import me.pythonchik.tableplays.managers.ValuesManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;
import me.pythonchik.tableplays.managers.Util.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import me.pythonchik.tableplays.managers.modifiers.ModifierContext;

import java.util.*;

public class Listeners implements Listener {

    private static HashMap<Player, Long> ItemUses = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnItemUse(PlayerStatisticIncrementEvent event) {
        // it's not even the right statistic - get out of here!
        if (!event.getStatistic().equals(Statistic.USE_ITEM) || event.getMaterial() == null || !event.getMaterial().equals(Material.WARPED_FUNGUS_ON_A_STICK)) {
            return;
        }
        Player player = event.getPlayer();
        ActionTagSet currentTag = new ActionTagSet();
        ItemStack mainStack = player.getInventory().getItemInMainHand();
        ItemStack offStack = player.getInventory().getItemInOffHand();

        //prevent double-calling from holding an item in 2 hands
        if (System.currentTimeMillis() - ItemUses.getOrDefault(player, 0L) < 1000/Bukkit.getServer().getServerTickManager().getTickRate()) {
            event.setCancelled(true);
            return;
        }

        //from what hand are we doing this?
        if (mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())
            && !(offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))) {
            currentTag.add(ActionTag.MAIN_HAND);
        } else if (!(mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))
                && offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())) {
            currentTag.add(ActionTag.LEFT_HAND);
        } else if (mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())
                && offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())) {
            currentTag.add(ActionTag.BOTH_HAND);
        }

        // we did not use any item, rather a regular warped fungus on a stick. lol who uses it?
        if (!currentTag.containsAny(ActionTag.MAIN_HAND, ActionTag.LEFT_HAND, ActionTag.BOTH_HAND)) {
            return;
        }

        ItemUses.put(player, System.currentTimeMillis()); // save that we have triggered event

        //to not mess with actual uses of warped fungus
        player.setStatistic(event.getStatistic(), event.getMaterial(), event.getPreviousValue()); // for some reason does not work as intended, just does not.


        // what did he click?
        if (player.getTargetBlockExact((int) player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getBaseValue(), FluidCollisionMode.NEVER) != null) {
            currentTag.add(ActionTag.ON_BLOCK);
        } else {
            currentTag.add(ActionTag.ON_AIR);
        }
        Vector clicked_position = Util.getClickedPosition(player);
        //is he holding shift?
        if (player.isSneaking()) {
            currentTag.add(ActionTag.WITH_SHIFT);
        }

        //now that we have all active tags I really need to handle only edge cases, like 2 items that _should_ work together, and leave the rest to the function I'm yet to create

        //now that we have handled the edge cases - throw this shit into this function ->
        handle_action(player, currentTag, currentTag.containsAny(ActionTag.MAIN_HAND, ActionTag.BOTH_HAND) ? mainStack : null, currentTag.containsAny(ActionTag.LEFT_HAND, ActionTag.BOTH_HAND) ? offStack : null, null, clicked_position);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractAtEntityEvent event) {
        //We are not interested in non-my-interactions
        if (!event.getRightClicked().getType().equals(EntityType.INTERACTION) || !event.getRightClicked().getPersistentDataContainer().has(ItemTags.Entity.getValue()) || event.getRightClicked().getVehicle() == null || !(event.getRightClicked().getVehicle().getType() == EntityType.ITEM_DISPLAY) || !event.getRightClicked().getVehicle().getPersistentDataContainer().has(ItemTags.Entity.getValue())) {
            return;
        }

        Player player = event.getPlayer();
        ActionTagSet currentTag = new ActionTagSet();
        ItemStack mainStack = player.getInventory().getItemInMainHand();
        ItemStack offStack = player.getInventory().getItemInOffHand();
        if (mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())
                && !(offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))) {
            currentTag.add(ActionTag.MAIN_HAND);
        } else if (!(mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))
                && offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())) {
            currentTag.add(ActionTag.LEFT_HAND);
        } else if (!(mainStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))
                && !(offStack.getType().equals(Material.WARPED_FUNGUS_ON_A_STICK) && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()))) {
            currentTag.add(ActionTag.NONE_HAND);
        } else {
            currentTag.add(ActionTag.BOTH_HAND);
        }
        currentTag.add(ActionTag.ON_ITEM);
        if (player.isSneaking()) {
            currentTag.add(ActionTag.WITH_SHIFT);
        }

        //prevent double-calling from holding an item in 2 hands
        if (System.currentTimeMillis() - ItemUses.getOrDefault(player, 0L) < 1000/Bukkit.getServer().getServerTickManager().getTickRate()) {
            event.setCancelled(true);
            return;
        }
        ItemUses.put(player, System.currentTimeMillis()); // save that we have triggered event

        //now that we have all active tags I really need to handle only edge cases, like 2 items that _should_ work together, and leave the rest to the function I'm yet to create

        //now that we have handled the edge cases - throw this shit into this function ->
        handle_action(player, currentTag, currentTag.contains(ActionTag.MAIN_HAND) ? mainStack : null, currentTag.contains(ActionTag.LEFT_HAND) ? offStack : null, (Interaction) event.getRightClicked(), event.getClickedPosition());
    }

    public static void handle_action(Player player, ActionTagSet currentTag, ItemStack mainStack, ItemStack offStack, Interaction interaction, Vector clicked_position) {
        //this should be impossible, but we can never know
        if (mainStack == null && offStack == null && interaction == null) {
            return;
        }
        ItemStack groundStack = null;
        if (interaction != null && interaction.getVehicle() != null && interaction.getPersistentDataContainer().has(ItemTags.Entity.getValue()) && interaction.getVehicle().getType().equals(EntityType.ITEM_DISPLAY) && ((ItemDisplay) interaction.getVehicle()).getItemStack() != null) {
            groundStack = ((ItemDisplay) interaction.getVehicle()).getItemStack();
        }
        // priority - groud - main - off
        // data format = "int:action,int:action,int:action" executed from left to right by priority.
        // for example for bundle that might be = "134:PICK_UP,292:PUT_DOWN"
        // that means that if we have action 134(on item + left hand) then we do action PICK_UP, then, if fails, we check for 292(with shift, on block, main hand) and if yes - place down bundle

        //special case: we have bundle in left hand
        if (BundleManager.isValidBundle(offStack)) {
            // then handle it first, for example to grab items from the ground:
            if (offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Actions.getValue(), PersistentDataType.STRING)) {
                String[] data = (offStack.getItemMeta().getPersistentDataContainer().get(ItemTags.Actions.getValue(), PersistentDataType.STRING)).split(","); // ignore null execution on split, can not be(well, it might if Actions tag will be not a string, but who cares
                for (String currentCheck : data) {
                    String[] split = currentCheck.split(":");
                    if (split.length > 1 && split[0].equals(currentTag.toString())) { // split is in the correct format, and we are doing the correct action
                        ArrayList<String> modifiers = Util.getModifiers(offStack, currentTag.toString());
                        // if we return false, e.g. do not continue -> then do not continue
                        if (!executeAction(player, currentTag, split[1], Callers.Left.getValue(), modifiers, mainStack, offStack, interaction, clicked_position)) return;
                    }
                }
            }
        }

        if (groundStack != null && groundStack.getItemMeta() != null && groundStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Actions.getValue())) {
            String[] data = (groundStack.getItemMeta().getPersistentDataContainer().get(ItemTags.Actions.getValue(), PersistentDataType.STRING)).split(","); // ignore null execution on split, can not be(well, it might if Actions tag will be not a string, but who cares
            for (String currentCheck : data) {
                String[] split = currentCheck.split(":");
                if (split.length > 1 && split[0].equals(currentTag.toString())) { // split is in the correct format, and we are doing the correct action
                    ArrayList<String> modifiers = Util.getModifiers(groundStack, currentTag.toString());
                    // if we return false, e.g. do not continue -> then do not continue
                    if (!executeAction(player, currentTag, split[1], Callers.Ground.getValue(), modifiers, mainStack, offStack, interaction, clicked_position)) return;
                }
            }
        }

        if (mainStack != null && mainStack.getItemMeta() != null && mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Actions.getValue())) {
            String[] data = (mainStack.getItemMeta().getPersistentDataContainer().get(ItemTags.Actions.getValue(), PersistentDataType.STRING)).split(","); // ignore null execution on split, can not be(well, it might if Actions tag will be not a string, but who cares
            for (String currentCheck : data) {
                String[] split = currentCheck.split(":");
                if (split.length > 1 && split[0].equals(currentTag.toString())) { // split is in the correct format, and we are doing the correct action
                    ArrayList<String> modifiers = Util.getModifiers(mainStack, currentTag.toString());
                    // if we return false, e.g. do not continue -> then do not continue
                    if (!executeAction(player, currentTag, split[1], Callers.Main.getValue(),  modifiers, mainStack, offStack, interaction, clicked_position)) return;
                }
            }
        }
        if (offStack != null && offStack.getItemMeta() != null && offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Actions.getValue())) {
            String[] data = (offStack.getItemMeta().getPersistentDataContainer().get(ItemTags.Actions.getValue(), PersistentDataType.STRING)).split(","); // ignore null execution on split, can not be(well, it might if Actions tag will be not a string, but who cares
            for (String currentCheck : data) {
                String[] split = currentCheck.split(":");
                if (split.length > 1 && split[0].equals(currentTag.toString())) { // split is in the correct format, and we are doing the correct action
                    ArrayList<String> modifiers = Util.getModifiers(offStack, currentTag.toString());
                    // if we return false, e.g. do not continue -> then do not continue
                    if (!executeAction(player, currentTag, split[1], Callers.Left.getValue(), modifiers, mainStack, offStack, interaction, clicked_position)) return;
                }
            }
        }
    }
    //make a lot of functions to handle actions with card, maybe transport here also some params, maybe in hashmap format, but how to get the obj?

    public static boolean executeAction(Player player, ActionTagSet all, String action, String caller, ArrayList<String> modifiers, ItemStack mainStack, ItemStack offStack, Interaction interaction, Vector clicked_position) {
        //returns True if you need to continue the execution, and false it the action if final.
        switch (action.toUpperCase()) {
            //does nothing by itself, but applies modifiers to everything it can
            case "NOTHING" -> {
                if (mainStack != null) {
                    ModifierContext context = new ModifierContext(action, player, mainStack, null, null, clicked_position, Callers.Main.getValue(), interaction);
                    ModifierManager.applyModifiers(context, modifiers);
                } else if (offStack != null) {
                    ModifierContext context = new ModifierContext(action, player, offStack, null, null, clicked_position, Callers.Left.getValue(), interaction);
                    ModifierManager.applyModifiers(context, modifiers);
                } else {
                    ModifierContext context = new ModifierContext(action, player, ((ItemDisplay) interaction.getVehicle()).getItemStack(), null, null, clicked_position, Callers.Ground.getValue(), interaction);
                    ModifierManager.applyModifiers(context, modifiers);
                }
                return true;
            }
            //place X down, main hand
            case "PLACE_MAIN" -> {
                if (!caller.equals(Callers.Main.getValue())) return true;
                Location toPlace = Util.getBlockEyeLoc(player);

                ModifierContext context = new ModifierContext(action, player, null, null, toPlace, clicked_position, Callers.Main.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                Interaction spawned_interaction = player.getWorld().spawn(toPlace, Interaction.class);
                ItemDisplay display = player.getWorld().spawn(toPlace, ItemDisplay.class);
                spawned_interaction.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                display.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                List<Float> hitbox = ValuesManager.getItemHitbox(mainStack); // should return 2 values, width and height, in that order
                if (hitbox != null) {
                    spawned_interaction.setInteractionWidth(hitbox.getFirst());
                    spawned_interaction.setInteractionHeight(hitbox.getLast());
                }
                //item handling
                ItemStack single_item = mainStack.clone();

                context = new ModifierContext(action, player, single_item, spawned_interaction, toPlace, clicked_position, Callers.Main.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                single_item.setAmount(1);
                display.setItemStack(single_item);

                if (player.getInventory().getItemInMainHand().getAmount() == 1) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                //correct size and shit for the model, data driven
                display.setTransformation(ValuesManager.getTransformation(single_item));

                //make them aligned to the ground
                display.setRotation(display.getLocation().getYaw(),ValuesManager.getPitch(single_item));

                display.addPassenger(spawned_interaction);

                context = new ModifierContext(action, player, single_item, spawned_interaction, toPlace, clicked_position, Callers.Main.getValue());
                ModifierManager.applyModifiers(context, modifiers);
                return false;
            }
            //place X down, left hand
            case "PLACE_LEFT" -> {
                if (!caller.equals(Callers.Left.getValue())) return true;
                Location toPlace = Util.getBlockEyeLoc(player);
                //modify the location of spawn if needed
                ModifierContext context = new ModifierContext(action, player, null, null, toPlace, clicked_position, Callers.Left.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                Interaction spawned_interaction = player.getWorld().spawn(toPlace, Interaction.class);
                ItemDisplay display = player.getWorld().spawn(toPlace, ItemDisplay.class);
                spawned_interaction.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                display.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                List<Float> hitbox = ValuesManager.getItemHitbox(offStack); // should return 2 values, width and height, in that order
                if (hitbox != null) {
                    spawned_interaction.setInteractionWidth(hitbox.getFirst());
                    spawned_interaction.setInteractionHeight(hitbox.getLast());
                }
                //item handling
                ItemStack single_item = offStack.clone();

                //modify item if needed
                context = new ModifierContext(action, player, single_item, spawned_interaction, toPlace, clicked_position, Callers.Left.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                single_item.setAmount(1);
                display.setItemStack(single_item);

                if (player.getInventory().getItemInOffHand().getAmount() == 1) {
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                } else {
                    player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount()-1);
                }

                //correct size and shit for the model, data driven
                display.setTransformation(ValuesManager.getTransformation(single_item));

                //make item aligned to the ground
                display.setRotation(display.getLocation().getYaw(),ValuesManager.getPitch(single_item));

                display.addPassenger(spawned_interaction);
                //final apply if not already
                context = new ModifierContext(action, player, single_item, spawned_interaction, toPlace, clicked_position, Callers.Left.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                return false;
            }
            // place X on top of an item, main hand
            case "PLACE_TOP_MAIN" -> {
                if (!caller.equals(Callers.Main.getValue())) return true;
                if (clicked_position == null || interaction == null) {
                    return true;
                }
                clicked_position.setY(interaction.getInteractionHeight()+0.001); // offset for not clipping
                Location spawn_loc = interaction.getLocation();
                spawn_loc.setYaw(player.getLocation().getYaw());
                spawn_loc.add(clicked_position);

                ModifierContext context = new ModifierContext(action, player, null, null, spawn_loc, clicked_position, Callers.Main.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                Interaction spawned_interaction = player.getWorld().spawn(spawn_loc, Interaction.class);
                ItemDisplay display = player.getWorld().spawn(spawn_loc, ItemDisplay.class);
                spawned_interaction.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                display.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                List<Float> hitbox = ValuesManager.getItemHitbox(mainStack); // should return 2 values, width and height, in that order
                if (hitbox != null) {
                    spawned_interaction.setInteractionWidth(hitbox.getFirst());
                    spawned_interaction.setInteractionHeight(hitbox.getLast());
                }
                //item handling
                ItemStack single_item = mainStack.clone();

                context = new ModifierContext(action, player, single_item, spawned_interaction, spawn_loc, clicked_position, Callers.Main.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                single_item.setAmount(1);
                display.setItemStack(single_item);

                if (player.getInventory().getItemInMainHand().getAmount() == 1) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                }

                //correct size and shit for the model, data driven
                display.setTransformation(ValuesManager.getTransformation(single_item));

                //make them aligned to the ground
                display.setRotation(display.getLocation().getYaw(),ValuesManager.getPitch(single_item));

                display.addPassenger(spawned_interaction);
                //final-check to apply everything if needed
                context = new ModifierContext(action, player, single_item, spawned_interaction, spawn_loc, clicked_position, Callers.Main.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                return false;
            }
            // place X on top of an item, left hand
            case "PLACE_TOP_LEFT" -> {
                if (!caller.equals(Callers.Left.getValue())) return true;
                if (clicked_position == null || interaction == null) {
                    return true;
                }
                clicked_position.setY(interaction.getInteractionHeight()+0.001); // offset for not clipping
                Location spawn_loc = interaction.getLocation();
                spawn_loc.setYaw(player.getLocation().getYaw());
                spawn_loc.add(clicked_position);

                ModifierContext context = new ModifierContext(action, player, null, null, spawn_loc, clicked_position, Callers.Left.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                Interaction spawned_interaction = player.getWorld().spawn(spawn_loc, Interaction.class);
                ItemDisplay display = player.getWorld().spawn(spawn_loc, ItemDisplay.class);
                spawned_interaction.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                display.getPersistentDataContainer().set(ItemTags.Entity.getValue(), PersistentDataType.BOOLEAN, true);
                List<Float> hitbox = ValuesManager.getItemHitbox(offStack); // should return 2 values, width and height, in that order
                if (hitbox != null) {
                    spawned_interaction.setInteractionWidth(hitbox.getFirst());
                    spawned_interaction.setInteractionHeight(hitbox.getLast());
                }
                //item handling
                ItemStack single_item = offStack.clone();

                context = new ModifierContext(action, player, single_item, spawned_interaction, spawn_loc, clicked_position, Callers.Left.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                single_item.setAmount(1);
                display.setItemStack(single_item);

                if (player.getInventory().getItemInOffHand().getAmount() == 1) {
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                } else {
                    player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount()-1);
                }

                //correct size and shit for the model, data driven
                display.setTransformation(ValuesManager.getTransformation(single_item));

                //make them aligned to the ground
                display.setRotation(display.getLocation().getYaw(),ValuesManager.getPitch(single_item));

                display.addPassenger(spawned_interaction);

                context = new ModifierContext(action, player, single_item, spawned_interaction, spawn_loc, clicked_position, Callers.Left.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                return false;
            }
            // suck to bundle in off hand from the main hand
            case "PUT_FROM_MAIN" -> {
                if (!caller.equals(Callers.Left.getValue())) return true;
                //assumption is - used in bundle to requested to put the item in bundle, the bundle is always in off hand, and the item is in main hand
                if (offStack == null || offStack.getItemMeta() == null || !offStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue()) || !offStack.getItemMeta().getPersistentDataContainer().get(ItemTags.Item.getValue(), PersistentDataType.STRING).equals(ItemTypes.Bundle.getValue()))  {
                    return true;
                }
                if (mainStack == null || mainStack.getItemMeta() == null || !mainStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())) {
                    return true;
                }
                ItemStack single_item = mainStack.clone();
                single_item.setAmount(1);

                ModifierContext context = new ModifierContext(action, player, single_item, null, null, clicked_position, Callers.Left.getValue());
                ModifierManager.applyModifiers(context, modifiers);

                ModifierContext context2 = new ModifierContext(action, player, single_item, null, null, clicked_position, Callers.Left.getValue());
                ActionTagSet tagSet = new ActionTagSet(all.contains(ActionTag.WITH_SHIFT) ? ActionTag.WITH_SHIFT.getValue() : 0);
                tagSet.add(ActionTag.TO_BUNDLE);
                tagSet.add(ActionTag.BOTH_HAND);
                ModifierManager.applyModifiers(context2, Util.getModifiers(single_item, tagSet.toString()));

                boolean result = BundleManager.addToBundle(offStack, single_item);
                if (result) {
                    mainStack.setAmount(mainStack.getAmount()-1);
                    return false;
                }

                return true;
            }
            // suck to bundle in the off hand from the ground
            case "PUT_FROM_GROUND" -> {
                if (!caller.equals(Callers.Left.getValue())) return true;
                //assumption is - used in bundle to requested to put the item in bundle, the bundle is always in off hand, and the item is in the ground
                if (offStack == null || offStack.getItemMeta() == null || !offStack.getItemMeta().getPersistentDataContainer().getOrDefault(ItemTags.Item.getValue(), PersistentDataType.STRING, "not a bundle tag. If you make this a bundle tag, you are a fucking piece of shit.").equals(ItemTypes.Bundle.getValue()))  {
                    return true;
                }
                if (interaction == null) {
                    return true;
                }
                ItemStack groundStack =  ((ItemDisplay) interaction.getVehicle()).getItemStack();
                if (groundStack == null || groundStack.getItemMeta() == null || !groundStack.getItemMeta().getPersistentDataContainer().has(ItemTags.Item.getValue())) {
                    return true;
                }
                ItemStack single_item = groundStack.clone();
                single_item.setAmount(1);

                ModifierContext context = new ModifierContext(action, player, single_item, null, null, clicked_position, Callers.Left.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                ModifierContext context2 = new ModifierContext(action, player, single_item, null, null, clicked_position, Callers.Left.getValue(), interaction);
                ActionTagSet tagSet = new ActionTagSet(all.contains(ActionTag.WITH_SHIFT) ? ActionTag.WITH_SHIFT.getValue() : 0);
                tagSet.add(ActionTag.TO_BUNDLE);
                tagSet.add(ActionTag.ON_ITEM);
                ModifierManager.applyModifiers(context2, Util.getModifiers(single_item, tagSet.toString()));

                boolean result = BundleManager.addToBundle(offStack, single_item);
                if (result) {
                    if (groundStack.getAmount() == 1) {
                        // no items left, just kill this thing
                        interaction.getVehicle().remove();
                        interaction.remove();
                    } else {
                        groundStack.setAmount(groundStack.getAmount()-1);
                        ((ItemDisplay)interaction.getVehicle()).setItemStack(groundStack);
                    }
                    return false;
                }

                return true;
            }
            // get from the bundle in main
            case "GET_FROM_BUNDLE_MAIN" -> {
                if (!caller.equals(Callers.Main.getValue())) return true;
                if (!BundleManager.isValidBundle(mainStack)) {
                    return true;
                }
                ArrayList<ItemStack> items = BundleManager.getBundleItems(mainStack);
                if (items.isEmpty()) return true;
                String order = mainStack.getItemMeta().getPersistentDataContainer().get(ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).split(";")[2]; // literal 'random', 'stack', or 'queue'
                ItemStack toAdd;
                int index;
                if (order.equals("random") && items.size() != 1) {
                    index = new Random().nextInt(0, items.size()-1);
                    toAdd = items.get(index);
                } else if (order.equals("stack")) {
                    index = items.size()-1;
                    toAdd = items.getLast();
                } else {
                    index = 0;
                    toAdd = items.getFirst();
                }
                ModifierContext context = new ModifierContext(action, player, toAdd, null, null, clicked_position, Callers.Main.getValue());
                ActionTagSet tagSet = new ActionTagSet(all.contains(ActionTag.WITH_SHIFT) ? ActionTag.WITH_SHIFT.getValue() : 0);
                tagSet.add(ActionTag.FROM_BUNDLE);
                ModifierManager.applyModifiers(context, Util.getModifiers(toAdd, tagSet.toString()));
                items.remove(index);
                BundleManager.saveItemsToBundle(mainStack, items);
                HashMap<Integer, ItemStack> left = player.getInventory().addItem(toAdd);
                if (!left.isEmpty()) {
                    for (ItemStack leftover : left.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), leftover);
                    }
                }
                return false;
            }
            // get from the bundle in main
            case "GET_FROM_BUNDLE_LEFT" -> {
                if (!caller.equals(Callers.Left.getValue())) return true;
                if (!BundleManager.isValidBundle(offStack)) {
                    return true;
                }
                ArrayList<ItemStack> items = BundleManager.getBundleItems(offStack);
                if (items.isEmpty()) return true;
                String order = offStack.getItemMeta().getPersistentDataContainer().get(ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).split(";")[2]; // literal 'random', 'stack', or 'queue'
                ItemStack toAdd;
                int index;
                if (order.equals("random") && items.size() != 1) {
                    index = new Random().nextInt(0, items.size()-1);
                    toAdd = items.get(index);
                } else if (order.equals("stack")) {
                    index = items.size()-1;
                    toAdd = items.getLast();
                } else {
                    index = 0;
                    toAdd = items.getFirst();
                }
                ModifierContext context = new ModifierContext(action, player, toAdd, null, null, clicked_position, Callers.Left.getValue());
                ActionTagSet tagSet = new ActionTagSet(all.contains(ActionTag.WITH_SHIFT) ? ActionTag.WITH_SHIFT.getValue() : 0);
                tagSet.add(ActionTag.FROM_BUNDLE);
                ModifierManager.applyModifiers(context, Util.getModifiers(toAdd, tagSet.toString()));
                items.remove(index);
                BundleManager.saveItemsToBundle(offStack, items);
                HashMap<Integer, ItemStack> left = player.getInventory().addItem(toAdd);
                if (!left.isEmpty()) {
                    for (ItemStack leftover : left.values()) {
                        player.getWorld().dropItemNaturally(player.getLocation(), leftover);
                    }
                }
                return false;
            }
            // pick up from the ground
            case "PICK_UP" -> {
                if (!caller.equals(Callers.Ground.getValue())) return true;
                if (interaction == null || interaction.getVehicle() == null) return true;
                ItemDisplay display = (ItemDisplay) interaction.getVehicle();
                ItemStack groundStack = display.getItemStack();
                // if protect in modifiers -> do not remove unless clicked in bottom fifth of the hitbox
                if (modifiers.contains("PROTECT") && clicked_position.getY()*5 > interaction.getInteractionHeight()) {
                    return true;
                }
                ModifierContext context = new ModifierContext(action, player, groundStack, null, null, clicked_position, Callers.Ground.getValue(), interaction);
                ModifierManager.applyModifiers(context, modifiers);

                HashMap<Integer, ItemStack> left = player.getInventory().addItem(groundStack);
                if (!left.isEmpty()) {
                    for (ItemStack leftover : left.values()) {
                        player.getWorld().dropItemNaturally(interaction.getLocation(), leftover);
                    }
                }
                interaction.remove();
                display.remove();
                return false;
            }
            //TODO make more modes

            default -> {
                return true;
            }
        }
    }


    @EventHandler
    public static void CraftListener(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack[] matrix = inventory.getMatrix();

        int itemCount = 0;
        int subType = -1;
        int amount = 0;
        int itemSlot = -1;

        for (int i = 0; i < matrix.length; i++) {
            ItemStack item = matrix[i];
            if (item == null || item.getType().isAir()) continue; // Skip empty slots
            // If there's more than one item, stop early
            if (++itemCount > 1) return;

            // Check if it's a Warped Fungus on a Stick
            if (item.getType() != Material.WARPED_FUNGUS_ON_A_STICK) return;
            // Check for PersistentDataContainer (PDC) data
            ItemMeta meta = item.getItemMeta();
            if (meta == null) return;

            PersistentDataContainer data = meta.getPersistentDataContainer();

            // Ensure the first tag (String) exists and matches the required value
            if (!data.has(Util.ItemTags.Item.getValue(), PersistentDataType.STRING) ||
                    !Util.ItemTypes.Chip.getValue().equals(data.get(Util.ItemTags.Item.getValue(), PersistentDataType.STRING))) {
                return;
            }

            // Ensure the second tag (Int) exists and store it in a variable
            if (!data.has(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER)) {
                return;
            }
            subType = data.get(Util.ItemTags.SubType.getValue(), PersistentDataType.INTEGER);
            amount = item.getAmount();
            itemSlot = i+1; // +1 because i++, and not ++i;
        }

        if (itemCount == 1) {
            Material resultMaterial = null;
            HashMap<Material, Integer> map = ValuesManager.getVariants(ItemTypes.Chip.getValue());
            for (Material material : map.keySet()) {
                if (map.get(material) == subType) {
                    resultMaterial = material;
                    break;
                }
            }
            if (resultMaterial != null) {
                inventory.setItem(itemSlot, new ItemStack(resultMaterial, amount));
            }
        }
    }

    @EventHandler
    public static void ItemDespawnListener(ItemDespawnEvent event) {
        if (!BundleManager.isValidBundle(event.getEntity().getItemStack())) {
            //its not the bundle - ignore
            return;
        }
        if (!event.getEntity().getItemStack().getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).startsWith("uuid")) {
            // its data bundle - ignore
            return;
        }
        if (!TablePlays.data.contains(event.getEntity().getItemStack().getItemMeta().getPersistentDataContainer().get(ItemTags.BundleData.getValue(), PersistentDataType.STRING))) {
            // no data on the server anyway, dont bother
            return;
        }
        // remove the value from the server if item is deleted
        TablePlays.data.set(event.getEntity().getItemStack().getItemMeta().getPersistentDataContainer().get(ItemTags.BundleData.getValue(), PersistentDataType.STRING), null);
    }

    @EventHandler
    public static void ItemKillLitener(EntityDeathEvent event) {
        if (event.getEntity().getType() != EntityType.ITEM) {
            //its not item how is dead
            return;
        }
        ItemStack stack = ((Item) event.getEntity()).getItemStack();
        if (!BundleManager.isValidBundle(stack)) {
            //its not the bundle - ignore
            return;
        }
        if (!stack.getItemMeta().getPersistentDataContainer().get(Util.ItemTags.BundleMeta.getValue(), PersistentDataType.STRING).startsWith("uuid")) {
            // its data bundle - ignore
            return;
        }
        if (!TablePlays.data.contains(stack.getItemMeta().getPersistentDataContainer().get(ItemTags.BundleData.getValue(), PersistentDataType.STRING))) {
            // no data on the server anyway, dont bother
            return;
        }
        // remove the value from the server if item is deleted
        TablePlays.data.set(stack.getItemMeta().getPersistentDataContainer().get(ItemTags.BundleData.getValue(), PersistentDataType.STRING), null);

    }
}
