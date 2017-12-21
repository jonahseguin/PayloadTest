package com.jonahseguin.payloadtest.hook;

import com.jonahseguin.payload.common.cache.CacheDatabase;
import com.jonahseguin.payload.profile.cache.PayloadProfileCache;
import com.jonahseguin.payload.profile.util.ProfileCacheBuilder;
import com.jonahseguin.payloadtest.obj.PayloadProfile;

import org.bukkit.plugin.java.JavaPlugin;

public class ProfileCacheCreator {

    public static PayloadProfileCache<PayloadProfile> create(JavaPlugin plugin, CacheDatabase database) {
        return new ProfileCacheBuilder<PayloadProfile>(plugin)
                .withDatabase(database)
                .withEnableAsyncCaching(true)
                .withCacheFailRetryIntervalSeconds(60)
                .withHaltListenerEnabled(true)
                .withCacheLogoutSaveDatabase(true)
                .withCacheRemoveOnLogout(false)
                .withProfileClass(PayloadProfile.class)
                .withDebugger(new MyCacheDebugger(plugin))
                .withInstantiator(PayloadProfile::new)
                .build();
    }

}
