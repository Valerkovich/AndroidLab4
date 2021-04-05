package stu.cn.ua.androidlab4.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ShipDAO {

    @Query("SELECT * FROM ships ORDER BY id COLLATE NOCASE")
    List<ShipDbEntity> getShips() ;

    @Query("SELECT * FROM ships WHERE name COLLATE UTF8_GENERAL_CI LIKE :name")
    List<ShipDbEntity> getByName(String name);

    @Query("SELECT * FROM ships WHERE id = :id")
    ShipDbEntity getById(String id);

    @Insert
    void insertShips(List<ShipDbEntity> repositories);

    @Query("DELETE FROM ships")
    void deleteShips();

    @Transaction
    default void updateShips(List<ShipDbEntity> entities){
        deleteShips();
        insertShips(entities);
    }
}
