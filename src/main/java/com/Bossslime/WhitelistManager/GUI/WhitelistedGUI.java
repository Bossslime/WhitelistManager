package com.Bossslime.WhitelistManager.GUI;

import com.Bossslime.WhitelistManager.Listeners.WhitelistOverride;
import com.Bossslime.WhitelistManager.Listeners.WhitelistedInventoryClick;
import com.Bossslime.WhitelistManager.PluginUtils.Chat;
import com.Bossslime.WhitelistManager.PluginUtils.XSeries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WhitelistedGUI {
    public ArrayList<Inventory> pages = new ArrayList<Inventory>();
    public UUID id;
    public int currpage = 0;
    public int maxpage;

    public static HashMap<UUID, WhitelistedGUI> users = new HashMap<UUID, WhitelistedGUI>();
    //Running this will open a paged inventory for the specified player, with the items in the arraylist specified.
    public WhitelistedGUI(){
        this.id = UUID.randomUUID();
    }

    public void updateInv(ArrayList<ItemStack> items, String name, Player p) {
        //create new blank page
        Inventory page = getBlankPage(name, p);
        currpage = users.get(p.getUniqueId()).currpage;

        //Get Max Page
        maxpage = getMaxPage(items);


        //According to the items in the arraylist, add items to the ScrollerInventory
        for(int i = 0;i < items.size(); i++){
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            if(page.firstEmpty() == -1){
                int slot = 0;
                for (ItemStack item : page.getContents()) {

                    page.remove(XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
                    slot = slot + 1;

                }
                pages.add(page);
                page = getBlankPage(name, p);
                page.addItem(items.get(i));
            }else{
                //Add the item to the current page as per normal
                page.addItem(items.get(i));
            }
        }
        int slot = 0;
        for (ItemStack item : page.getContents()) {

            page.remove(XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
            slot = slot + 1;

        }
        pages.add(page);
        users.put(p.getUniqueId(), this);
    }

    public void setInv(ArrayList<ItemStack> items, String name, Player p) {
        //create new blank page
        Inventory page = getBlankPage(name, p);

        //Get Max Page
        maxpage = getMaxPage(items);


        //According to the items in the arraylist, add items to the ScrollerInventory
        for(int i = 0;i < items.size(); i++){
            //If the current page is full, add the page to the inventory's pages arraylist, and create a new page to add the items.
            if(page.firstEmpty() == -1){
                int slot = 0;
                for (ItemStack item : page.getContents()) {

                    page.remove(XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
                    slot = slot + 1;

                }
                pages.add(page);
                page = getBlankPage(name, p);
                page.addItem(items.get(i));
            }else{
                //Add the item to the current page as per normal
                page.addItem(items.get(i));
            }
        }
        int slot = 0;
        for (ItemStack item : page.getContents()) {

            page.remove(XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
            slot = slot + 1;

        }
        pages.add(page);
        //open page 0 for the specified player
        p.openInventory(pages.get(currpage));
        WhitelistedInventoryClick.addPlayerToMainArray(p);
        users.put(p.getUniqueId(), this);
    }



    //This creates a blank page with the next and prev buttons
    private Inventory getBlankPage(String name, Player p){
        if (name.contains("{CurrentPageNumber}")) {
            name = name.replace("{CurrentPageNumber}", String.valueOf(currpage + 1));
        }
        if (name.contains("{PlayerName}")) {
            name = name.replace("{PlayerName}", p.getName());
        }
        if (name.contains("{MaxPageNumber}")) {
            name = name.replace("{MaxPageNumber}", String.valueOf(maxpage));
        }

        Inventory page = Bukkit.createInventory(p, 36, Chat.color(name));



        ItemStack nextpage =  XMaterial.ARROW.parseItem();
        ItemMeta meta = nextpage.getItemMeta();
        meta.setDisplayName(Chat.color("&cNext Page"));
        ArrayList<String> array = new ArrayList<>();
        array.add(Chat.color("&7Goto the next page"));
        meta.setLore(array);
        nextpage.setItemMeta(meta);

        ItemStack prevpage =  XMaterial.ARROW.parseItem();
        meta = nextpage.getItemMeta();
        meta.setDisplayName(Chat.color("&cPrevious Page"));
        array = new ArrayList<>();
        array.add(Chat.color("&7Goto the previous page"));
        meta.setLore(array);
        prevpage.setItemMeta(meta);

        for(int i=0; i < 36; i++) {
            switch(i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:


                case 27:
                    if (currpage > 0) {
                        page.setItem(27, prevpage);
                    }else {
                        page.setItem(27, XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
                    }
                case 35:
                    if (currpage < pages.size() - 1) {
                        page.setItem(35, nextpage);
                    }else {
                        page.setItem(35, XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
                    }

                case 28: // Enable/Disable whitelist button
                    if (Bukkit.hasWhitelist()) {
                        ItemStack item = XMaterial.GREEN_TERRACOTTA.parseItem();
                        meta = item.getItemMeta();
                        meta.setDisplayName(Chat.color("&aEnable/Disable Whitelist"));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Chat.color("&7Enable or Disable the whitelist"));
                        lore.add(Chat.color("&e&lStatus: &a&lEnabled"));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&eClick to Disable the whitelist"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        page.setItem(28, item);

                    }else {
                        ItemStack item = XMaterial.RED_TERRACOTTA.parseItem();
                        meta = item.getItemMeta();
                        meta.setDisplayName(Chat.color("&cEnable/Disable Whitelist"));
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Chat.color("&7Enable or Disable the whitelist"));
                        lore.add(Chat.color("&e&lStatus: &c&lDisabled"));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&eClick to Enable the whitelist"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        page.setItem(28, item);

                    }


                case 29: //Multipage Item
                    ItemStack item = XMaterial.BOOK.parseItem();
                    meta = item.getItemMeta();
                    meta.setDisplayName(Chat.color("&aPage Type"));
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add(Chat.color("&7Change the page type"));
                    lore.add(Chat.color("&7 "));
                    lore.add(Chat.color("&a>> &eWhitelisted &a<<"));
                    lore.add(Chat.color("&eNot Whitelisted"));
                    lore.add(Chat.color("&7 "));
                    lore.add(Chat.color("&aClick to change page type "));

                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    page.setItem(29, item);

                case 33: //Search Item
                    item = XMaterial.COMPASS.parseItem();
                    meta = item.getItemMeta();
                    meta.setDisplayName(Chat.color("&cSearch"));
                    lore = new ArrayList<>();
                    lore.add(Chat.color("&7Search for a player"));
                    lore.add(Chat.color("&7 "));
                    lore.add(Chat.color("&cComing soon..."));

                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    page.setItem(33, item);

                case 34: // Whitelist override button
                    if (WhitelistOverride.inEnabled()) {
                        item = XMaterial.GREEN_TERRACOTTA.parseItem();
                        meta = item.getItemMeta();
                        meta.setDisplayName(Chat.color("&aWhitelist Override"));
                        lore = new ArrayList<>();
                        lore.add(Chat.color("&7Enable or Disable the whitelist override"));
                        lore.add(Chat.color("&e&lStatus: &a&lEnabled"));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&eClick to Disable the whitelist override"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        page.setItem(34, item);

                    }else {
                        item = XMaterial.RED_TERRACOTTA.parseItem();
                        meta = item.getItemMeta();
                        meta.setDisplayName(Chat.color("&cWhitelist Override"));
                        lore = new ArrayList<>();
                        lore.add(Chat.color("&7Enable or Disable the whitelist override"));
                        lore.add(Chat.color("&e&lStatus: &c&lDisabled"));
                        lore.add(Chat.color("&7 "));
                        lore.add(Chat.color("&eClick to Enable the whitelist override"));
                        meta.setLore(lore);
                        item.setItemMeta(meta);

                        page.setItem(34, item);

                    }

                    continue;
            }

            if(i == 31) {
                ItemStack item = XMaterial.BARRIER.parseItem();
                meta = item.getItemMeta();
                meta.setDisplayName(Chat.color("&cClose"));
                array = new ArrayList<>();
                array.add(Chat.color("&7Close the GUI"));
                meta.setLore(array);
                item.setItemMeta(meta);


                page.setItem(i, item);
                continue;
            }
            page.setItem(i, XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem());
        }
        return page;
    }


    private static int getMaxPage(List<ItemStack> list) {
        int maxPage = 0;
        int amount = list.size();
        for (; amount > 27; amount -= 27, maxPage++) {}
        return maxPage;
    }
}
