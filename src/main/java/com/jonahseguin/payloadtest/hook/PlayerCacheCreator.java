package com.jonahseguin.payloadtest.hook;

import com.jonahseguin.payload.simple.cache.PayloadSimpleCache;
import com.jonahseguin.payload.simple.util.SimpleCacheBuilder;
import com.jonahseguin.payloadtest.obj.PayloadPlayer;

import org.bukkit.plugin.Plugin;

/**
 * Created by Jonah on 12/20/2017.
 * Project: PayloadTest
 *
 * @ 5:40 PM
 */
public class PlayerCacheCreator {

    public static PayloadSimpleCache<PayloadPlayer> create(Plugin plugin) {
        return new SimpleCacheBuilder<>(plugin, PayloadPlayer::new)
                .withRemoveOnLogout(true)
                .withCleanupCheckIntervalMinutes(2)
                .withExpiryMinutesAfterLogout(15)
                .build();
    }

}
