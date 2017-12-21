package com.jonahseguin.payloadtest.hook;

import com.jonahseguin.payload.common.cache.CacheDatabase;
import com.jonahseguin.payload.object.cache.PayloadObjectCache;
import com.jonahseguin.payload.object.obj.ObjectInstantiator;
import com.jonahseguin.payload.object.util.ObjectCacheBuilder;
import com.jonahseguin.payloadtest.obj.PayloadFaction;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jonah on 12/20/2017.
 * Project: PayloadTest
 *
 * @ 7:48 PM
 */
public class FactionCacheCreator {

    public static PayloadObjectCache<PayloadFaction> create(JavaPlugin plugin, CacheDatabase database) {
        return new ObjectCacheBuilder<>(plugin, PayloadFaction.class)
                .withDatabase(database)
                .withMongo(true)
                .withMongoIdentifier("name")
                .withSaveAfterLoad(true)
                .withCreateOnNull(false)
                .withRedisKey("payload.test.faction.")
                .withDebugger(new MyCacheDebugger(plugin))
                .withRedis(true)
                .withObjectInstantiator(new ObjectInstantiator<PayloadFaction>() {
                    @Override
                    public PayloadFaction instantiate(String s) {
                        return new PayloadFaction(s);
                    }

                    @Override
                    public PayloadFaction instantiate() {
                        return new PayloadFaction();
                    }
                })
                .build();
    }

}
