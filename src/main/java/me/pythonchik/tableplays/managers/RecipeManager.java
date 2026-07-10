package me.pythonchik.tableplays.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class RecipeManager {
    public static void init(Plugin plugin) {
        dice(plugin);
        board(plugin);
        nardyboard(plugin);
        nardy(plugin);

        // these 6 need to fill after crafting
        cards36(plugin);
        cards52(plugin);
        cards54(plugin);

        checkers(plugin);
        chess(plugin);
        domino(plugin);
        // --- end of those 6 ---

        chips(plugin);
        chip_bundles(plugin);
    }

    private static void dice(Plugin plugin) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "dice"), ItemCreator.getDice());
        recipe.shape(
                " W ",
                "ABA",
                " b "
        );
        recipe.setIngredient('W', Material.WHITE_DYE);
        recipe.setIngredient('A', Material.AMETHYST_SHARD);
        recipe.setIngredient('B', Material.BONE_BLOCK);
        recipe.setIngredient('b', Material.BLACK_DYE);
        Bukkit.addRecipe(recipe);
    }

    private static void board(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"board"), ItemCreator.getBoard());
        recipe.shape(
                "BS",
                "SB"
        );
        recipe.setIngredient('B', Material.BIRCH_SLAB);
        recipe.setIngredient('S',Material.DARK_OAK_SLAB);
        Bukkit.addRecipe(recipe);
    }

    private static void nardyboard(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"nardyboard"), ItemCreator.getNardyBoard());
        recipe.shape(
                "BS",
                "SB"
        );
        recipe.setIngredient('B', Material.BIRCH_SLAB);
        recipe.setIngredient('S', Material.SPRUCE_SLAB);
        Bukkit.addRecipe(recipe);
    }

    private static void nardy(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"nardy"), ItemCreator.getNardy());
        recipe.shape(
                "BBB",
                "WSD",
                " L "
        );
        recipe.setIngredient('B', Material.BONE);
        recipe.setIngredient('D',Material.BLACK_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('W',Material.WHITE_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void cards36(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"cards36bundle"), ItemCreator.get36bundle());
        recipe.shape(
                "PPP",
                "BSR",
                " L "
        );
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R',Material.RED_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void cards52(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"cards52bundle"), ItemCreator.get52bundle());
        recipe.shape(
                "PPP",
                " S ",
                "BLR"
        );
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R',Material.RED_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void cards54(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"cards54bundle"), ItemCreator.get54bundle());
        recipe.shape(
                "PPP",
                "RSB",
                "RLB"
        );
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R',Material.RED_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void checkers(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"checkersbundle"), ItemCreator.getCheckers());
        recipe.shape(
                " Q ",
                "WSB",
                " L "
        );
        recipe.setIngredient('Q', Material.QUARTZ_BLOCK);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('W',Material.WHITE_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void chess(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"chessbundle"), ItemCreator.getChess());
        recipe.shape(
                "WQB",
                "WSB",
                " L "
        );
        recipe.setIngredient('Q', Material.BONE_BLOCK);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('W',Material.WHITE_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void domino(Plugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"dominobundle"), ItemCreator.getDomino());
        recipe.shape(
                " M ",
                "WSB",
                " L "
        );
        recipe.setIngredient('M', Material.BONE_BLOCK);
        recipe.setIngredient('B',Material.BLACK_DYE);
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('W',Material.WHITE_DYE);
        recipe.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(recipe);
    }

    private static void chips(Plugin plugin) {
        HashMap<Material, Integer> variants = ValuesManager.getVariants(Util.ItemTypes.Chip.getValue());
        for (Material mat : variants.keySet()) {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "chip_" + variants.get(mat)), ItemCreator.getChip(variants.get(mat)));
            recipe.shape(
                    " # ",
                    "#M#",
                    " # "
            );
            recipe.setIngredient('M', mat);
            recipe.setIngredient('#', Material.GOLD_NUGGET);
            Bukkit.addRecipe(recipe);
        }
    }

    private static void chip_bundles(Plugin plugin) {
        HashMap<Material, Integer> variants = ValuesManager.getVariants(Util.ItemTypes.Bundle.getValue());
        for (Material mat : variants.keySet()) {
            ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "cbundle_" + variants.get(mat)), ItemCreator.getChipBundle(variants.get(mat)));
            recipe.shape(
                    "#D#",
                    "#S#",
                    " L "
            );
            recipe.setIngredient('D', mat);
            recipe.setIngredient('S', Material.STRING);
            recipe.setIngredient('L', Material.LEATHER);
            recipe.setIngredient('#', Material.GOLD_NUGGET);
            Bukkit.addRecipe(recipe);
        }
    }

}
