package com.Bossslime.WhitelistManager;

import com.Bossslime.WhitelistManager.Commands.WhitelistManagerCommand;
import com.Bossslime.WhitelistManager.GUI.Updaters.NotWhitelistedGUIUpdater;
import com.Bossslime.WhitelistManager.GUI.Updaters.WhitelistedGUIUpdater;
import com.Bossslime.WhitelistManager.GUI.WhitelistedGUI;
import com.Bossslime.WhitelistManager.Listeners.InvCloseEvent;
import com.Bossslime.WhitelistManager.Listeners.NotWhitelistedInventoryClick;
import com.Bossslime.WhitelistManager.Listeners.WhitelistOverride;
import com.Bossslime.WhitelistManager.Listeners.WhitelistedInventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public void onEnable() {
        instance = this;

        //Register Command
        this.getCommand("whitelistmanager").setExecutor(new WhitelistManagerCommand());

        //Register Events
        Bukkit.getPluginManager().registerEvents(new InvCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new WhitelistedInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new NotWhitelistedInventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new WhitelistOverride(), this);


        //Register loops
        new WhitelistedGUIUpdater(true);
        new NotWhitelistedGUIUpdater(true);
    }


    public static Main getInstance() {
        return instance;
    }
}
