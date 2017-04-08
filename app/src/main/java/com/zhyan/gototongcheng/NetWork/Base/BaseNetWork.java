package com.zhyan.gototongcheng.NetWork.Base;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by admin on 2017/2/21.
 */

public class BaseNetWork extends RetrofitUtils{

    /**
     * 插入观察者
     * @param observable
     * @param observer
     * @param <T>
     */
    public  <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }
}
