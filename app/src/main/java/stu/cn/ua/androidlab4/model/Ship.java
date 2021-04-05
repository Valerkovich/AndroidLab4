package stu.cn.ua.androidlab4.model;

import stu.cn.ua.androidlab4.model.db.ShipDbEntity;
import stu.cn.ua.androidlab4.model.network.ShipNetworkEntity;

public class Ship {

    private final String id;
    private final String name;
    private final String homePort;
    private final String shipType;

    public Ship(String id, String name, String homePort, String shipType){
        this.id = id;
        this.name = name;
        this.homePort = homePort;
        this.shipType = shipType;
    }

    public Ship(ShipDbEntity entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getHomePort(),
                entity.getShipType()
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShipType() {
        return shipType;
    }

    @Override
    public String toString() {
        return "id: " + this.id + "\n"
                + "home port: " + this.homePort + "\n"
                + "ship type: " + this.shipType;
    }
}
