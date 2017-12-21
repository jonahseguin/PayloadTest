package com.jonahseguin.payloadtest.database;

import com.jonahseguin.payloadtest.PayloadTest;
import com.jonahseguin.payloadtest.obj.PayloadFaction;
import com.jonahseguin.payloadtest.obj.PayloadPlayer;
import com.jonahseguin.payloadtest.obj.PayloadProfile;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.event.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by jonahseguin on 2017-08-12.
 */
public class MongoManager {

    private final String mongoDatabaseName = "minecraft";
    private final String mongoAuthDatabaseName = "admin";
    private final String mongoHost = "localhost";
    private final int mongoPort = 27017;
    private final String mongoUsername = "username";
    private final String mongoPassword = "password";
    private final Plugin plugin;
    private MongoClient mongoClient;
    private MongoDatabase db;
    private Morphia morphia;
    private Datastore datastore;
    private final boolean useMongoAuth = false;

    private boolean failed = false;

    public MongoManager(PayloadTest plugin) {
        this.plugin = plugin;


        try {
            if (useMongoAuth) {
                MongoCredential credential = MongoCredential.createCredential(mongoUsername, mongoAuthDatabaseName, mongoPassword.toCharArray());
                mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), Arrays.asList(credential));
                db = mongoClient.getDatabase(mongoDatabaseName);
            } else {
                mongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort));
                db = mongoClient.getDatabase(mongoDatabaseName);
            }

            morphia = new Morphia();
            morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
                @Override
                protected ClassLoader getClassLoaderForClass() {
                    return plugin.getPLTClassLoader();
                }
            });
            morphia.map(PayloadFaction.class);
            morphia.map(PayloadProfile.class);
            morphia.mapPackage("com.jonahseguin.payloadtest");
            datastore = morphia.createDatastore(mongoClient, mongoDatabaseName);
            datastore.ensureIndexes();
        } catch (MongoException ex) {
            ex.printStackTrace();
        }
    }

    public MongoDatabase getDb() {
        return db;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public Datastore getDataStore() {
        return datastore;
    }

    public boolean isConnected() {
        try {
            mongoClient.getAddress();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}



