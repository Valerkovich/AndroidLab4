package stu.cn.ua.androidlab4.model;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Response;
import stu.cn.ua.androidlab4.logger.Logger;
import stu.cn.ua.androidlab4.model.db.ShipDbEntity;
import stu.cn.ua.androidlab4.model.db.ShipDAO;
import stu.cn.ua.androidlab4.model.network.ShipNetworkEntity;
import stu.cn.ua.androidlab4.model.network.SpacexApi;

public class SpacexService {
    private ExecutorService executorService;
    private ShipDAO shipDAO;
    private SpacexApi spacexApi;
    private Logger logger;

    private List<Ship> ships;
    private List<ShipDbEntity> entities;

    public SpacexService(SpacexApi spacexApi, ShipDAO shipDAO,
                         ExecutorService executorService, Logger logger) {
        this.spacexApi = spacexApi;
        this.shipDAO = shipDAO;
        this.executorService = executorService;
        this.logger = logger;
    }

    public Cancellable getShips(String name, boolean hasInternet, Callback<List<Ship>> callback){
        Future<?> future = executorService.submit(() -> {
            try {

                if(hasInternet){
                    Response<List<ShipNetworkEntity>> response = spacexApi.getShips().execute();

                    if(response.isSuccessful()) {
                        List<ShipDbEntity> newShips = networkToDbEntity(response.body());
                        shipDAO.updateShips(newShips);
                        callback.onResult(convertToShip(newShips));
                    }
                }

                if(name.equals("") || name.equals("%%")){
                    entities = shipDAO.getShips();
                } else {
                    entities = shipDAO.getByName(name);
                }
                ships = convertToShip(entities);
                callback.onResult(ships);

                if(!ships.isEmpty()){
                    RuntimeException runtimeException = new RuntimeException("Something happened");
                    logger.e(runtimeException);
                    callback.onError(runtimeException);
                }
            } catch (Exception e) {
                logger.e(e);
                callback.onError(e);
            }
        });

        return new FutureCancellable(future);
    }

    public Cancellable getShipById(String id, Callback<Ship> callback){
        Future<?> future = executorService.submit(() -> {
            try {
                ShipDbEntity dbEntity = shipDAO.getById(id);
                Ship ship = new Ship(dbEntity);
                callback.onResult(ship);
            } catch (Exception e){
                logger.e(e);
                callback.onError(e);
            }
        });

        return new FutureCancellable(future);
    }

    private List<Ship> convertToShip(List<ShipDbEntity> entities){
        return Stream.of(entities).map(Ship::new).toList();
    }

    private List<ShipDbEntity> networkToDbEntity(List<ShipNetworkEntity> entities){
        return Stream.of(entities)
                .map(ShipDbEntity::new)
                .toList();
    }

    static class FutureCancellable implements Cancellable {

        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }
}
