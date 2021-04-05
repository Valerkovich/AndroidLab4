package stu.cn.ua.androidlab4.model.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpacexApi {

    @GET("ships")
    Call<List<ShipNetworkEntity>> getShips();
}
