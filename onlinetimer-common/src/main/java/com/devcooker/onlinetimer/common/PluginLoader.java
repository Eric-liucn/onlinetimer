package com.devcooker.onlinetimer.common;

import com.devcooker.onlinetimer.common.config.ConfigLoader;
import com.devcooker.onlinetimer.common.handler.DatabaseHandler;
import com.devcooker.onlinetimer.common.handler.OnlineTimerHandler;

import java.io.IOException;
import java.nio.file.Path;

public abstract class PluginLoader {

    protected ConfigLoader configLoader;
    protected DatabaseHandler databaseHandler;
    protected OnlineTimerHandler onlineTimerHandler;

    public void init(Path configDir) throws IOException {
        configLoader = new ConfigLoader(configDir);
        databaseHandler = new DatabaseHandler(
                configLoader.getConfig().host,
                configLoader.getConfig().port,
                configLoader.getConfig().database,
                configLoader.getConfig().tablePrefix,
                configLoader.getConfig().username,
                configLoader.getConfig().password
        );
        setOnlineTimerHandler();
    }

    protected void setOnlineTimerHandler(){

    };

}
