package com.devcooker.onlinetimer.handler;

import com.devcooker.onlinetimer.common.handler.OnlineTimerHandler;
import org.spongepowered.api.Engine;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public class OnlineTimerSpongeHandler extends OnlineTimerHandler {

    @Listener
    public void onPlayerJoin(ServerSideConnectionEvent.Join event, @Getter("player") ServerPlayer player){
        this.onLoginIn(player.name(), player.uniqueId());
    }

    @Listener
    public void onDisconnect(ServerSideConnectionEvent.Disconnect event, @Getter("player") ServerPlayer player){
        this.onDisconnect(player.uniqueId());
    }

    @Listener
    public void onServerCLose(StoppingEngineEvent<Server> event){
        this.onServerClose();
    }

}
