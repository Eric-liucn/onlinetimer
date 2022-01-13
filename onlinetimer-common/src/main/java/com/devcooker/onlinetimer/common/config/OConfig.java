package com.devcooker.onlinetimer.common.config;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@ConfigSerializable
public class OConfig {

    @Setting("host")
    @Comment("Database host")
    public String host = "localhost";

    @Setting("port")
    @Comment("Database port")
    public String port = "3306";

    @Setting("database")
    @Comment("Database used")
    public String database = "test";

    @Setting("table_prefix")
    @Comment("Table prefix")
    public String tablePrefix = "prefix";

    @Setting("user")
    @Comment("Database username")
    public String username = "root";

    @Setting("port")
    @Comment("Database password")
    public String password = "932065";

}
