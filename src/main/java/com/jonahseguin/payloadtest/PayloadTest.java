package com.jonahseguin.payloadtest;

import com.jonahseguin.payload.common.cache.CacheDatabase;
import com.jonahseguin.payload.object.cache.PayloadObjectCache;
import com.jonahseguin.payload.profile.cache.PayloadProfileCache;
import com.jonahseguin.payload.simple.cache.PayloadSimpleCache;
import com.jonahseguin.payloadtest.database.MongoManager;
import com.jonahseguin.payloadtest.database.RedisManager;
import com.jonahseguin.payloadtest.hook.FactionCacheCreator;
import com.jonahseguin.payloadtest.hook.PlayerCacheCreator;
import com.jonahseguin.payloadtest.hook.ProfileCacheCreator;
import com.jonahseguin.payloadtest.listener.PlayerListener;
import com.jonahseguin.payloadtest.obj.PayloadFaction;
import com.jonahseguin.payloadtest.obj.PayloadPlayer;
import com.jonahseguin.payloadtest.obj.PayloadProfile;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PayloadTest extends JavaPlugin {

    private static PayloadTest instance = null;

    private MongoManager mongoManager;
    private RedisManager redisManager;

    private PayloadSimpleCache<PayloadPlayer> playerCache = null;
    private PayloadProfileCache<PayloadProfile> profileCache = null;
    private PayloadObjectCache<PayloadFaction> factionCache = null;

    @Override
    public void onEnable() {
        instance = this;

        mongoManager = new MongoManager(this);
        redisManager = new RedisManager();

        CacheDatabase database = new CacheDatabase(mongoManager.getMongoClient(), mongoManager.getDb(), redisManager.getJedisResource(), mongoManager.getMorphia(), mongoManager.getDataStore());

        playerCache = PlayerCacheCreator.create(this);
        profileCache = ProfileCacheCreator.create(this, database);
        factionCache = FactionCacheCreator.create(this, database);

        playerCache.init();
        profileCache.init();
        factionCache.init();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        playerCache.shutdown();
        profileCache.shutdown();
        factionCache.shutdown();

        instance = null;
    }

    public ClassLoader getPLTClassLoader() {
        return getClassLoader();
    }

}
