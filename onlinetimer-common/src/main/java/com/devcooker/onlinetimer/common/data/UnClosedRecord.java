package com.devcooker.onlinetimer.common.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class UnClosedRecord {

    private final Timestamp startTime;
    private final UUID playerUUID;
    private final String playerName;

    public UnClosedRecord(UUID playerUUID, String playerName) {
        this.startTime = Timestamp.valueOf(LocalDateTime.now());
        this.playerUUID = playerUUID;
        this.playerName = playerName;
    }

    public ClosedRecord close(){
        return new ClosedRecord(startTime, Timestamp.valueOf(LocalDateTime.now()),
                playerUUID, playerName);
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public String getPlayerName() {
        return playerName;
    }
}
