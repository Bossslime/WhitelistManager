package com.Bossslime.WhitelistManager.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvCloseEvent implements Listener {

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (WhitelistedInventoryClick.getArray().contains(e.getPlayer())) {
            WhitelistedInventoryClick.getArray().remove(e.getPlayer());
        }

        if (NotWhitelistedInventoryClick.getArray().contains(e.getPlayer())) {
            NotWhitelistedInventoryClick.getArray().remove(e.getPlayer());
        }
    }
}
