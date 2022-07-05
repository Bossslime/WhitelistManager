package com.Bossslime.WhitelistManager.Listeners.Utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClickCooldown {
    private static ArrayList<Player> cooldown = new ArrayList<>();

    public static ArrayList getArray() {
        return cooldown;
    }
}
