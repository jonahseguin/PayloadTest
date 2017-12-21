package com.jonahseguin.payloadtest.obj;

import com.jonahseguin.payload.simple.simple.PlayerCacheable;
import org.bukkit.entity.Player;

public class PayloadPlayer implements PlayerCacheable {

    private final Player player;

    /*
    Transient / non-persistent per-player data should be stored in this object; for fast, easy retrieval of this data
     */

    public PayloadPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
