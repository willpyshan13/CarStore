package com.yanxin.home.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.car.baselib.BaseLibCore;
import com.car.baselib.bean.CommonBean;
import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.net.http.ResponseThrowable;
import com.car.baselib.utils.LogUtils;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.PositionReq;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhouz
 * @date 2021/3/7
 */
public class LocationUtils {
    private volatile static LocationUtils instance;
    private LocationManager locationManager;
    private Context mContext;
    private final String[] permissions = {Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};

    private LocationUtils() {
    }

    public static LocationUtils getInstance(){
        if (instance == null) {
            synchronized (LocationUtils.class){
                if (instance == null) {
                    instance = new LocationUtils();
                }
            }
        }
        return instance;
    }

    public void upLoadLocation(Context context){
        mContext = context.getApplicationContext();
        Observable.interval(0,60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<CommonBean<String>>>() {
                    @Override
                    public ObservableSource<CommonBean<String>> apply(Long aLong) throws Exception {
                        Location location = getLocation();
                        if (location != null) {
                            //获取纬度
                            double lat = location.getLatitude();
                            //获取经度
                            double lng = location.getLongitude();
                            PositionReq positionReq = new PositionReq();
                            positionReq.setLatitude(lat);
                            positionReq.setLongitude(lng);
                            return RemoteDataService.getInstance().getApiService(NetService.class)
                                    .positionReport(HttpParams.getToken(),positionReq);
                        }
                        return Observable.error(new ResponseThrowable(9999,"获取经纬度失败"));
                    }
                })
                .subscribe(new Observer<CommonBean<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonBean<String> stringCommonBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Location getLocation(){
        if (locationManager == null) {
            // 获取的是位置服务
            String serviceString = Context.LOCATION_SERVICE;
            // 调用getSystemService()方法来获取LocationManager对象
            locationManager = (LocationManager) mContext.getSystemService(serviceString);
        }
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            // 转到手机设置界面，用户设置GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
        // 指定LocationManager的定位方法
        String provider = LocationManager.GPS_PROVIDER;
        // 调用getLastKnownLocation()方法获取当前的位置信息
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
//        locationManager.addGpsStatusListener(new GpsStatus.Listener() {
//            @Override
//            public void onGpsStatusChanged(int event) {
//                LogUtils.e("event: "+event);
//            }
//        });
        return locationManager.getLastKnownLocation(provider);

//        locationManager.requestLocationUpdates(provider, 1000 * 60, 100, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                if (location != null){
//                    LogUtils.d(location.getLatitude() + "---" + location.getLongitude());
//                }
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                LogUtils.d(provider + "---" );
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                LogUtils.d(provider + "--777-" );
//            }
//        });

    }
}
