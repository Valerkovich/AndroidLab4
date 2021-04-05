package stu.cn.ua.androidlab4;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;

import stu.cn.ua.androidlab4.model.SpacexService;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ViewModelFactory implements ViewModelProvider.Factory {

    public static final String TAG = ViewModelFactory.class.getSimpleName();

    private SpacexService spacexService;

    public ViewModelFactory(SpacexService spacexService) {
        this.spacexService = spacexService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(SpacexService.class);
            return constructor.newInstance(spacexService);
        } catch (ReflectiveOperationException e){
            Log.e(TAG, "Error in ViewModelFactory", e);
            RuntimeException wrapper = new RuntimeException();
            wrapper.initCause(e);
            throw wrapper;
        }
    }
}
