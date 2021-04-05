package stu.cn.ua.androidlab4.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import stu.cn.ua.androidlab4.BaseViewModel;
import stu.cn.ua.androidlab4.model.Callback;
import stu.cn.ua.androidlab4.model.Cancellable;
import stu.cn.ua.androidlab4.model.SpacexService;
import stu.cn.ua.androidlab4.model.Ship;
import stu.cn.ua.androidlab4.model.Result;

public class DetailsViewModel extends BaseViewModel {

    private MutableLiveData<Result<Ship>> shipLiveData = new MutableLiveData<>();

    {
        shipLiveData.setValue(Result.empty());
    }

    private Cancellable cancellable;

    public DetailsViewModel(SpacexService spacexService) {
        super(spacexService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancellable != null) cancellable.cancel();
    }

    public void loadShipById(String id){
        shipLiveData.setValue(Result.loading());
        cancellable = getSpacexService().getShipById(id, new Callback<Ship>() {
            @Override
            public void onError(Throwable error) {
                shipLiveData.postValue(Result.error(error));
            }

            @Override
            public void onResult(Ship data) {
                shipLiveData.postValue(Result.success(data));
            }
        });
    }

    public LiveData<Result<Ship>> getResults(){
        return shipLiveData;
    }
}
