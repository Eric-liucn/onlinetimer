package com.devcooker.onlinetimer.common.data;

import java.sql.Timestamp;
import java.util.UUID;

public class ClosedRecord {

    private final Timestamp startTime;
    private final Timestamp endTime;
    private final UUID playerUUI;
    private final String playerName;


    public ClosedRecord(Timestamp startTime, Timestamp endTime, UUID playerUUI, String playerName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.playerUUI = playerUUI;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public UUID getPlayerUUI() {
        return playerUUI;
    }

}
