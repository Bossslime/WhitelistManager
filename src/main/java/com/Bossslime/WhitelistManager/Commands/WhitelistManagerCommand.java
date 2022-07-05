package com.Bossslime.WhitelistManager.Commands;

import com.Bossslime.WhitelistManager.GUI.WhitelistedGUI;
import com.Bossslime.WhitelistManager.PluginUtils.Chat;
import com.Bossslime.WhitelistManager.PluginUtils.XSeries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class WhitelistManagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("minecraft.command.whitelist")) {
                ArrayList<ItemStack> array = new ArrayList<>();
                for (OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
                    ItemStack item = XMaterial.PLAYER_HEAD.parseItem();
                    SkullMeta meta = (SkullMeta) item.getItemMeta();
                    meta.setOwner(p.getName());
                    meta.setDisplayName(Chat.color("&a" + p.getName()));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(Chat.color("&e" + p.getName() + " &7is currently whitelisted."));
                    lore.add(Chat.color("&7 "));
                    lore.add(Chat.color("&cClick to remove from whitelist."));

                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    array.add(item);
                }

                new WhitelistedGUI().setInv(array, "WhitelistManager ⪼ {CurrentPageNumber}", (Player) sender);
            }else {
                sender.sendMessage(Chat.color("&cI'm sorry but you don't have permission to execute this command."));
            }



        }else {
            Chat.tellConsole("&cOnly players can run this command.");
        }

        return false;
    }
}
