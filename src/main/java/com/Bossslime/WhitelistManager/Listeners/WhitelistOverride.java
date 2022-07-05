package com.Bossslime.WhitelistManager.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WhitelistOverride implements Listener {

    private static Boolean whitelistOverride = false;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (whitelistOverride == true) {
            if (!e.getPlayer().hasPermission("minecraft.login.bypass-whitelist")) {
                e.getPlayer().kickPlayer("You are not whitelisted on this server!");
            }
        }

    }


    public static void setWhitelistOverride(Boolean status) {
        whitelistOverride = status;
    }

    public static Boolean inEnabled() {
        return whitelistOverride;
    }
}
