package com.Bossslime.WhitelistManager.GUI.Updaters;

import com.Bossslime.WhitelistManager.GUI.WhitelistedGUI;
import com.Bossslime.WhitelistManager.Listeners.WhitelistedInventoryClick;
import com.Bossslime.WhitelistManager.Main;
import com.Bossslime.WhitelistManager.PluginUtils.Chat;
import com.Bossslime.WhitelistManager.PluginUtils.XSeries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class WhitelistedGUIUpdater implements Runnable {

    public WhitelistedGUIUpdater(boolean start) {
        if(start) Main.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this, 20, 5);
    }

    @Override
    public void run() {
        if (!WhitelistedInventoryClick.getArray().isEmpty()) {
            ArrayList<Player> array = WhitelistedInventoryClick.getArray();
            for (Player p : array) {
                ArrayList<ItemStack> array1 = new ArrayList<>();
                for (OfflinePlayer pl : Bukkit.getWhitelistedPlayers()) {
                    ItemStack item = XMaterial.PLAYER_HEAD.parseItem();
                    SkullMeta meta = (SkullMeta) item.getItemMeta();
                    meta.setOwner(pl.getName());
                    meta.setDisplayName(Chat.color("&a" + pl.getName()));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(Chat.color("&e" + pl.getName() + " &7is currently whitelisted."));
                    lore.add(Chat.color("&7 "));
                    lore.add(Chat.color("&cClick to remove from whitelist."));

                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    array1.add(item);
                }

                new WhitelistedGUI().updateInv(array1, "WhitelistManager ⪼ {CurrentPageNumber}", p);
                Inventory inv = WhitelistedGUI.users.get(p.getUniqueId()).pages.get(WhitelistedGUI.users.get(p.getUniqueId()).currpage);

                Main.getInstance().getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    for (int i = 0; i < inv.getSize(); i++) {
                        if (WhitelistedInventoryClick.getArray().contains(p)) {
                            p.getOpenInventory().getTopInventory().setItem(i, inv.getItem(i));
                        }
                    }

                });
            }
        }
    }
}
