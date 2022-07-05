package com.Bossslime.WhitelistManager.GUI.Updaters;

import com.Bossslime.WhitelistManager.GUI.NotWhitelistedGUI;
import com.Bossslime.WhitelistManager.Listeners.NotWhitelistedInventoryClick;
import com.Bossslime.WhitelistManager.Main;
import com.Bossslime.WhitelistManager.PluginUtils.Chat;
import com.Bossslime.WhitelistManager.PluginUtils.XSeries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class NotWhitelistedGUIUpdater implements Runnable {

    public NotWhitelistedGUIUpdater(boolean start) {
        if(start) Main.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this, 20, 5);
    }

    @Override
    public void run() {
        if (!NotWhitelistedInventoryClick.getArray().isEmpty()) {
            ArrayList<Player> array = NotWhitelistedInventoryClick.getArray();
            for (Player p : array) {
                ArrayList<ItemStack> array1 = new ArrayList<>();
                for (OfflinePlayer pl : Bukkit.getOfflinePlayers()) {
                    if (!pl.isWhitelisted()) {
                        ItemStack item = XMaterial.PLAYER_HEAD.parseItem();
                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                        meta.setOwner(pl.getName());
                        meta.setDisplayName(Chat.color("&a" + pl.getName()));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Chat.color("&e" + pl.getName() + " &7is not whitelisted."));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&aClick to add to whitelist."));

                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        if (!array1.contains(item)) {
                            array1.add(item);
                        }
                    }
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (!pl.isWhitelisted()) {
                        ItemStack item = XMaterial.PLAYER_HEAD.parseItem();
                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                        meta.setOwner(pl.getName());
                        meta.setDisplayName(Chat.color("&a" + pl.getName()));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Chat.color("&e" + pl.getName() + " &7is not whitelisted."));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&aClick to add to whitelist."));

                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        if (!array1.contains(item)) {
                            array1.add(item);
                        }
                    }
                }
                new NotWhitelistedGUI().updateInv(array1, "WhitelistManager ⪼ {CurrentPageNumber}", p);
                Inventory inv = NotWhitelistedGUI.users.get(p.getUniqueId()).pages.get(NotWhitelistedGUI.users.get(p.getUniqueId()).currpage);

                Main.getInstance().getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    for (int i = 0; i < inv.getSize(); i++) {
                        if (NotWhitelistedInventoryClick.getArray().contains(p)) {
                            p.getOpenInventory().getTopInventory().setItem(i, inv.getItem(i));
                        }
                    }

                });
            }
        }
    }
}
