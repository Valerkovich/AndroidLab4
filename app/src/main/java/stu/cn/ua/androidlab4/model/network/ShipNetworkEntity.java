package stu.cn.ua.androidlab4.model.network;

import com.google.gson.annotations.SerializedName;

public class ShipNetworkEntity {
    private String ship_id;
    private String ship_name;
    private String ship_type;
    private String home_port;

    public String getId() {
        return ship_id;
    }

    public String getName() {
        return ship_name;
    }

    public String getShipType() {
        return ship_type;
    }

    public String getHomePort() {
        return home_port;
    }
}
