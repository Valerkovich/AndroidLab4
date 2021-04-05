package stu.cn.ua.androidlab4;

import androidx.lifecycle.ViewModel;

import stu.cn.ua.androidlab4.model.SpacexService;

public class BaseViewModel extends ViewModel {

    private SpacexService spacexService;

    public BaseViewModel(SpacexService spacexService) {
        this.spacexService = spacexService;
    }

    public SpacexService getSpacexService() {
        return spacexService;
    }
}
