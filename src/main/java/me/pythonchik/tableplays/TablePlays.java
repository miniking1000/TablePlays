package me.pythonchik.tableplays;

import me.pythonchik.tableplays.managers.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public final class TablePlays extends JavaPlugin implements Listener {

    public static TablePlays instance;
    public static FileConfiguration config = null;
    private static FileConfiguration translations = null;
    public static FileConfiguration data = null;
    public static HashMap<String, ArrayList<ItemStack>> defaults = new HashMap<>();
    public static boolean isDevAndIsMiniking1000TheBestPlayerInHisMind = false; // if true - configs will be just always overwritten by the default ones.
    public static TablePlays getPlugin() {
        return instance;
    }

    public void reload() {
        TablePlays.instance.saveConfig();
        TablePlays.instance.loadConfig();
        new translationManager(translations);
        registerDefaults();
    }

    public void registerDefaults() {
        TablePlays.defaults.put("default.card36", CardCreator.get36Deck());
        TablePlays.defaults.put("default.card52", CardCreator.get52Deck());
        TablePlays.defaults.put("default.card54", CardCreator.get54Deck());
        TablePlays.defaults.put("default.checker", ItemCreator.getCheckerDeck());
        TablePlays.defaults.put("default.chess", ItemCreator.getChessDeck());
        TablePlays.defaults.put("default.domino", ItemCreator.getDominoDeck());
    }

    @Override
    public void onEnable() {
        //Util.init(); // was used for kryo initialization
        instance = this;
        loadConfig();
        new translationManager(translations);
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
        registerDefaults();
        RecipeManager.init(this);
        //always register commands to reload plugin and give all items (to rest those reloads)
        getCommand("gimme").setExecutor(new commands());
        getCommand("gimme").setTabCompleter(new commands());
        getCommand("treload").setExecutor(new commands());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public void saveConfig() {
        try {
            data.save(new File(getDataFolder(), "saved.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig() {
        if (isDevAndIsMiniking1000TheBestPlayerInHisMind) {
            saveResource("config.yml", true);
            File configFile = new File(getDataFolder(), "config.yml");
            config = YamlConfiguration.loadConfiguration(configFile);
            saveResource("ru.yml", true);
            File translFile = new File(getDataFolder(),  "ru.yml");
            translations = YamlConfiguration.loadConfiguration(translFile);
            saveResource("saved.yml", false);
            File dataa = new File(getDataFolder(), "saved.yml");
            data = YamlConfiguration.loadConfiguration(dataa);
            return;
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        if (config.getBoolean("allow_files")) {
            File savingFile = new File(getDataFolder(), "saved.yml");
            if (!savingFile.exists()) {
                saveResource("saved.yml", false);
            }
            data = YamlConfiguration.loadConfiguration(savingFile);
        }

        String lang = config.getString("lang", "en");  // default to "ru" if not set
        File langFile = new File(getDataFolder(), lang + ".yml");
        InputStream resourceStream = getResource(lang + ".yml");
        if (resourceStream != null) {
            // if translation is already done try to save it
            if (!langFile.exists()) {
                saveResource(lang + ".yml", false);
            }
        } else {
            // if not new file for you to create
            if (!langFile.exists()) {
                try {
                    langFile.createNewFile();
                } catch (IOException ignored) {}
            }
        }
        translations = YamlConfiguration.loadConfiguration(langFile);
    }

}
