package com.jonahseguin.payloadtest.hook;

import com.jonahseguin.payload.common.cache.CacheDebugger;

import org.bukkit.plugin.Plugin;

/**
 * Created by Jonah on 12/20/2017.
 * Project: PayloadTest
 *
 * @ 7:34 PM
 */
public class MyCacheDebugger implements CacheDebugger {

    private final Plugin plugin;

    public MyCacheDebugger(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void debug(String s) {
        plugin.getLogger().info("[Payload][Debug] " + s);
    }

    @Override
    public void error(Exception e) {
        plugin.getLogger().severe("[Payload][Error] " + e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void error(Exception e, String s) {
        plugin.getLogger().severe("[Payload][Error] " + e.getMessage() + " - " + s);
        e.printStackTrace();
    }

    @Override
    public boolean onStartupFailure() {
        plugin.getLogger().severe("[Payload][FATAL] Failed to startup.  Payload cache shutting down.");
        return true;
    }
}
