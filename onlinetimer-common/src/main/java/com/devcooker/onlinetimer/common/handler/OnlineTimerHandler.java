package com.devcooker.onlinetimer.common.handler;

import com.devcooker.onlinetimer.common.data.ClosedRecord;
import com.devcooker.onlinetimer.common.data.UnClosedRecord;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class OnlineTimerHandler {

    private final List<UnClosedRecord> unClosedRecordList = new ArrayList<>();

    public Duration playedTime(UUID uuid){
        long time = 0L;
        for (ClosedRecord closedRecord : DatabaseHandler.instance.allRecords(uuid)) {
            time += (closedRecord.getEndTime().getTime() - closedRecord.getStartTime().getTime());
        }
        for (UnClosedRecord unClosedRecord : unClosedRecordList) {
            if (unClosedRecord.getPlayerUUID().equals(uuid)){
                time += (Instant.now().getEpochSecond() - unClosedRecord.getStartTime().getTime());
            }
        }
        return Duration.ofMillis(time);
    }

    protected void onLoginIn(String name, UUID uuid){
        UnClosedRecord unClosedRecord = new UnClosedRecord(uuid, name);
        unClosedRecordList.add(unClosedRecord);
    }

    protected void onDisconnect(UUID uuid){
        for (UnClosedRecord unClosedRecord : unClosedRecordList) {
            if (unClosedRecord.getPlayerUUID().equals(uuid)){
                DatabaseHandler.instance.save(unClosedRecord.close());
                this.unClosedRecordList.remove(unClosedRecord);
            }
        }
    }

    protected void onServerClose(){
        for (UnClosedRecord unClosedRecord : unClosedRecordList) {
            DatabaseHandler.instance.save(unClosedRecord.close());
        }
    }

}
