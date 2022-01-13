package com.devcooker.onlinetimer.common.handler;

import com.devcooker.onlinetimer.common.data.ClosedRecord;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseHandler {

    public static DatabaseHandler instance;

    private final String host;
    private final String port;
    private final String database;
    private final String tablePrefix;
    private final String userName;
    private final String password;

    private DataSource dataSource;

    public DatabaseHandler(String host, String port, String database, String tablePrefix, String userName, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.tablePrefix = tablePrefix;
        this.userName = userName;
        this.password = password;
        this.init();
        this.createTable();
    }

    private void init(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setUsername(userName);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
    }

    private String tableName(){
        return " `" + tablePrefix + "_online_timer" + "` ";
    }

    public void createTable(){
        String SQL = "CREATE TABLE IF NOT EXISTS" +
                tableName() +
                "(`index` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR (50)," +
                "`uuid` VARCHAR (44)," +
                "`start_time` TIMESTAMP NOT NULL," +
                "`end_time` TIMESTAMP NOT NULL," +
                "INDEX(`uuid`)," +
                "INDEX(`name`)," +
                "PRIMARY KEY (`index`)" +
                ")";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL)
                ){
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void save(ClosedRecord closedRecord){
        String SQL = "INSERT INTO " + tableName() + " (`name`, `uuid`, `start_time`, `end_time`) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL)
                ){
            statement.setString(1, closedRecord.getPlayerName());
            statement.setString(2, closedRecord.getPlayerUUI().toString());
            statement.setTimestamp(3, closedRecord.getStartTime());
            statement.setTimestamp(4, closedRecord.getEndTime());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<ClosedRecord> allRecords(UUID playerUUID){
        String SQL = "SELECT * FROM " + tableName() + " WHERE `uuid`=?";
        List<ClosedRecord> closedRecords = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL)
        ){
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                ClosedRecord closedRecord = new ClosedRecord(
                        resultSet.getTimestamp("start_time"),
                        resultSet.getTimestamp("end_time"),
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name")
                );
                closedRecords.add(closedRecord);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return closedRecords;
    }

}
