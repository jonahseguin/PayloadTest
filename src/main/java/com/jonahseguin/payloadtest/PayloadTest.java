package com.jonahseguin.payloadtest;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PayloadTest extends JavaPlugin {

    private static PayloadTest instance = null;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
