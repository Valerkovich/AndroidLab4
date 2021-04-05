package stu.cn.ua.androidlab4.model;

public interface Callback<T> {

    void onError(Throwable error);

    void onResult(T data);

}
