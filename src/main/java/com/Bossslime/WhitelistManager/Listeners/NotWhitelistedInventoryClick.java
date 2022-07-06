package com.Bossslime.WhitelistManager.Listeners;

import com.Bossslime.WhitelistManager.GUI.NotWhitelistedGUI;
import com.Bossslime.WhitelistManager.GUI.WhitelistedGUI;
import com.Bossslime.WhitelistManager.Listeners.Utils.ClickCooldown;
import com.Bossslime.WhitelistManager.Main;
import com.Bossslime.WhitelistManager.PluginUtils.Chat;
import com.Bossslime.WhitelistManager.PluginUtils.XSeries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class NotWhitelistedInventoryClick implements Listener {
    private static ArrayList<Player> notWhitelistedMain = new ArrayList<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getInventory() == null) return;
        if (notWhitelistedMain.contains(e.getWhoClicked())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            NotWhitelistedGUI inv = NotWhitelistedGUI.users.get(e.getWhoClicked().getUniqueId());
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            if (e.getCurrentItem().equals((Object) XMaterial.AIR.parseItem()) ) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Chat.color("&cNext Page"))){
                //If there is no next page, don't do anything
                if(inv.currpage >= inv.pages.size()-1){
                    return;
                }else{
                    //Next page exists, flip the page
                    inv.currpage += 1;
                    updateGUI(p);
                    p.openInventory(NotWhitelistedGUI.users.get(p.getUniqueId()).pages.get(inv.currpage));
                    NotWhitelistedInventoryClick.addPlayerToMainArray(p);
                }
                //if the pressed item was a previous page button
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(Chat.color("&cPrevious Page"))) {
                //If the page number is more than 0 (So a previous page exists)
                if (inv.currpage > 0) {
                    //Flip to previous page
                    inv.currpage -= 1;
                    updateGUI(p);
                    p.openInventory(NotWhitelistedGUI.users.get(p.getUniqueId()).pages.get(inv.currpage));
                    NotWhitelistedInventoryClick.addPlayerToMainArray(p);

                }


            }else if (e.getCurrentItem().getType().equals((Object) XMaterial.PLAYER_HEAD.parseMaterial())) {
                String playerName = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
                if (Bukkit.getPlayerExact(playerName) != null) {
                    Player player = Bukkit.getPlayer(playerName);
                    if (!player.isWhitelisted()) {
                        player.setWhitelisted(true);
                        p.sendMessage(Chat.color("&e" + player.getName() + " &ahas been added to the whitelist!"));
                    }else {
                        p.sendMessage(Chat.color("&e" + player.getName() + " &cis already whitelisted!"));
                    }
                }else {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
                    if (!player.isWhitelisted()) {
                        player.setWhitelisted(true);
                        p.sendMessage(Chat.color("&e" + player.getName() + " &ahas been added to the whitelist!"));
                    }else {
                        p.sendMessage(Chat.color("&e" + player.getName() + " &cis already whitelisted!"));
                    }
                }


            }else if (e.getSlot() == 31) {
                p.closeInventory();
            }else if (e.getSlot() == 29) {
                if (ClickCooldown.getArray().contains(e.getWhoClicked())) return;
                ArrayList<ItemStack> array = new ArrayList<>();
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

                    array.add(item);
                }
                ClickCooldown.getArray().add(e.getWhoClicked());

                if (notWhitelistedMain.contains(e.getWhoClicked())) {
                    notWhitelistedMain.remove(e.getWhoClicked());
                }

                new WhitelistedGUI().setInv(array, "WhitelistManager ⪼ {CurrentPageNumber}", ((Player) e.getWhoClicked()).getPlayer());
                Main.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                    ClickCooldown.getArray().remove(e.getWhoClicked());
                }, 1 *20L);
            }else if (e.getSlot() == 33) {
                p.sendMessage(Chat.color("&cThis feature is coming soon..."));
            }else if (e.getSlot() == 28) {
                if (Bukkit.hasWhitelist()) {
                    Bukkit.setWhitelist(false);
                    p.sendMessage(Chat.color("&eThe whitelist has been &cDisabled&e."));
                }else {
                    Bukkit.setWhitelist(true);
                    p.sendMessage(Chat.color("&eThe whitelist has been &aEnabled&e."));
                }

            }else if (e.getSlot() == 34) {
                if (WhitelistOverride.inEnabled()) {
                    WhitelistOverride.setWhitelistOverride(false);
                    p.sendMessage(Chat.color("&eThe whitelist override has been &cDisabled&e."));
                }else {
                    WhitelistOverride.setWhitelistOverride(true);
                    p.sendMessage(Chat.color("&eThe whitelist override has been &aEnabled&e."));
                }

            }
        }

    }

    public static void addPlayerToMainArray(Player p) {
        if (!notWhitelistedMain.contains(p)) {
            notWhitelistedMain.add(p);
        }
    }

    public static ArrayList getArray() {
        return notWhitelistedMain;
    }



    private static void updateGUI(Player p) {
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
    }
}
