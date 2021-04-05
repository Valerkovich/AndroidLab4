package stu.cn.ua.androidlab4.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import stu.cn.ua.androidlab4.model.network.ShipNetworkEntity;

@Entity(tableName = "ships")
public class ShipDbEntity {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "homePort")
    private String homePort;

    @ColumnInfo(name = "shipType")
    private String shipType;

    public ShipDbEntity() {
    }

    public ShipDbEntity(ShipNetworkEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.homePort = entity.getHomePort();
        this.shipType = entity.getShipType();
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomePort(String homePort) {
        this.homePort = homePort;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHomePort() {
        return homePort;
    }

    public String getShipType() {
        return shipType;
    }
}
