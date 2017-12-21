package com.jonahseguin.payloadtest.listener;

import com.jonahseguin.payload.profile.event.PayloadProfileInitializedEvent;
import com.jonahseguin.payload.profile.event.PayloadProfilePreSaveEvent;
import com.jonahseguin.payload.simple.event.PlayerCachedEvent;
import com.jonahseguin.payloadtest.PayloadTest;
import com.jonahseguin.payloadtest.obj.PayloadFaction;
import com.jonahseguin.payloadtest.obj.PayloadPlayer;
import com.jonahseguin.payloadtest.obj.PayloadProfile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Jonah on 12/20/2017.
 * Project: PayloadTest
 *
 * @ 7:54 PM
 */
public class PlayerListener implements Listener {

    private final PayloadTest instance;

    public PlayerListener(PayloadTest instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerLoaded(PayloadProfileInitializedEvent<PayloadProfile> event) {
        if (event.getCache().getCacheId().equals(instance.getProfileCache().getCacheId())) {
            PayloadProfile profile = event.getProfile();
            profile.setLastLogin(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPreSave(PayloadProfilePreSaveEvent<PayloadProfile> event) {
        if (event.getProfileCache().getCacheId().equals(instance.getProfileCache().getCacheId())) {
            if (event.getProfile().getPlayer() != null && event.getProfile().getPlayer().isOnline()) {
                long playTime = System.currentTimeMillis() - event.getProfile().getLastLogin();
                event.getProfile().setPlayTime(event.getProfile().getPlayTime() + playTime);
            }
        }
    }

    @EventHandler
    public void onCached(PlayerCachedEvent<PayloadPlayer> event) {
        PayloadPlayer player = event.getPlayer();
        player.getPlayer().sendMessage("Hey"); // The player *won't* see this as this event is called from PlayerLoginEvent
        Bukkit.getLogger().info("Called PlayerCachedEvent for " + event.getPlayer().getPlayer().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PayloadPlayer player = instance.getPlayerCache().get(event.getPlayer());
        player.getPlayer().sendMessage(ChatColor.GREEN + "Hey"); // The player *will* see this
        instance.getServer().getScheduler().runTaskAsynchronously(instance, () -> {
            PayloadFaction faction = instance.getFactionCache().getOrDefault("MyFaction", new PayloadFaction("MyFaction"));
            player.getPlayer().sendMessage(ChatColor.GREEN + "Your faction " + faction.getName() + "'s balance is: " + faction.getBalance());
            instance.getFactionCache().saveEverywhere(faction);
        });
    }



}
