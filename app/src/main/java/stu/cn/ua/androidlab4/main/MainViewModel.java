package stu.cn.ua.androidlab4.main;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import stu.cn.ua.androidlab4.BaseViewModel;
import stu.cn.ua.androidlab4.model.Callback;
import stu.cn.ua.androidlab4.model.Cancellable;
import stu.cn.ua.androidlab4.model.SpacexService;
import stu.cn.ua.androidlab4.model.Ship;
import stu.cn.ua.androidlab4.model.Result;

import static stu.cn.ua.androidlab4.model.Result.Status.EMPTY;
import static stu.cn.ua.androidlab4.model.Result.Status.ERROR;
import static stu.cn.ua.androidlab4.model.Result.Status.LOADING;
import static stu.cn.ua.androidlab4.model.Result.Status.SUCCESS;

public class MainViewModel extends BaseViewModel {

    private SpacexService spacexService;

    private Result<List<Ship>> listResult = Result.empty();
    private MutableLiveData<ViewState> stateLiveData = new MutableLiveData<>();

    {
        updateViewState(Result.empty());
    }

    private Cancellable cancellable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public MainViewModel(SpacexService spacexService) {
        super(spacexService);
    }

    public MutableLiveData<ViewState> getViewState() {
        return stateLiveData;
    }

    public void getShips(String name, boolean hasInternet){
        updateViewState(Result.loading());
        cancellable = getSpacexService().getShips(name, hasInternet, new Callback<List<Ship>>() {
            @Override
            public void onError(Throwable error) {
                if(listResult.getStatus() != SUCCESS) {
                    updateViewState(Result.error(error));
                }
            }

            @Override
            public void onResult(List<Ship> data) {
                updateViewState(Result.success(data));
            }
        });
    }

    private void updateViewState(Result<List<Ship>> shipsResult){
        this.listResult = shipsResult;
        ViewState state = new ViewState();
        state.enableSearchButton = shipsResult.getStatus() != LOADING;
        state.showList = shipsResult.getStatus() == SUCCESS;
        state.showEmptyHint = shipsResult.getStatus() == EMPTY;
        state.showError = shipsResult.getStatus() == ERROR;
        state.showProgress = shipsResult.getStatus() == LOADING;
        state.ships = shipsResult.getData();
        stateLiveData.postValue(state);
    }

    static class ViewState {
        private boolean enableSearchButton;
        private boolean showList;
        private boolean showEmptyHint;
        private boolean showError;
        private boolean showProgress;
        private List<Ship> ships;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public boolean isShowError() {
            return showError;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public List<Ship> getShips() {
            return ships;
        }
    }
}
