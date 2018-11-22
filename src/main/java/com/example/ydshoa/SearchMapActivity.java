package com.example.ydshoa;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import org.kymjs.kjframe.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchMapActivity extends Activity implements TextWatcher {
    private EditText mEtCity;
    private AutoCompleteTextView mActvSearchkey, mActvSearchkey_city;
    private PoiSearch mPoiSearch;
    private BaiduMap mBaiduMap;
    private List<LatLng> latLngs = new ArrayList<>();
    private MapView mMapView = null;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private String address = "";
    private BitmapDescriptor bitmap;
    private String city;
    private Button search_map, chage_map;
    private String sf_and, qx_and;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_search_map);
        sf_and = getIntent().getStringExtra("SF");
        qx_and = getIntent().getStringExtra("QX");
        initDate();
        mMapView = (MapView) findViewById(R.id.texture_map);
        mBaiduMap = mMapView.getMap();
//        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
//        // 设置marker图标
        bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.dtdw);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            // 此方法就是点击地图监听
            @Override
            public void onMapClick(LatLng latLng) {
                // 获取经纬度
                mCurrentLat = latLng.latitude;
                mCurrentLon = latLng.longitude;
                Log.e("LiNing", "具体地址======" + mCurrentLat + "----" + mCurrentLon);
                // 先清除图层
                mBaiduMap.clear();
                // 定义Maker坐标点
                LatLng point = new LatLng(mCurrentLat, mCurrentLon);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(options);
                // 实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                // 设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                // 发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetReverseGeoCodeResult(
                            ReverseGeoCodeResult arg0) {
                        // 获取点击的坐标地址
                        address = arg0.getAddress();
//                        city_tv.setText(arg0.getAddressDetail().city);
                        mActvSearchkey.setText(address);
                        Log.e("LiNing", "具体地址======" + address);

                    }

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
            }
        });
//
//        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        //创建POI检索监听者；
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
    }

    private void initDate() {
        mActvSearchkey_city = (AutoCompleteTextView) findViewById(R.id.searchkey_city);
        mActvSearchkey = (AutoCompleteTextView) findViewById(R.id.searchkey);
        mActvSearchkey.addTextChangedListener(this);
//        search_map= (Button) findViewById(R.id.search_info_map);
//        chage_map= (Button) findViewById(R.id.chage_info_map);
//        search_map.addTextChangedListener(this);
//        chage_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent localIntent = getIntent();
//                localIntent.putExtra("ANDRESS", mActvSearchkey.getText().toString());
//                setResult(1, localIntent);
//                finish();
//            }
//        });
    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        public void onGetPoiResult(PoiResult result) {
            // 关闭定位图层
            mBaiduMap.clear();
//            mLocClient.stop();
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //详情检索失败
                // result.error请参考SearchResult.ERRORNO
                Toast.makeText(SearchMapActivity.this, "未搜索到POI数据", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取POI检索结果
                Toast.makeText(SearchMapActivity.this, "已搜索到相关数据数据", Toast.LENGTH_SHORT).show();
//                mBaiduMap.clear();
                List<PoiInfo> allPoi = result.getAllPoi();
                for (int i = 0; i < allPoi.size(); i++) {
                    Resources res = getResources();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.csdw);
                    Bitmap bitmap1 = BitmapFactory.decodeResource(res, R.drawable.cxdw);
//                    Bitmap bitmap1 = ImageUtils.drawTextToCenter(SearchMapActivity.this, bitmap, "" + i, 20, Color.BLACK);

                    OverlayOptions options = new MarkerOptions()
                            .position(result.getAllPoi().get(i).location)
                            .title(result.getAllPoi().get(i).name + ":" + result.getAllPoi().get(i).address)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
                    mBaiduMap.addOverlay(options);
                    //实现位置跟踪
                    LatLng location = result.getAllPoi().get(i).location;
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(location,18);
                    mBaiduMap.animateMapStatus(msu);
                }
//                if (allPoi != null) {
//                    if (allPoi.get(position) != null) {
//                        LatLng location = allPoi.get(position).location;
//                        if (location != null) {
//                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(location, 18);
//                            mBaiduMap.animateMapStatus(msu);
//                        }
//                    }
//                }
                mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent localIntent = getIntent();
                        localIntent.putExtra("ANDRESS", marker.getTitle().toString());
                        setResult(1, localIntent);
                        finish();
                        Toast.makeText(SearchMapActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

                // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                String strInfo = "在";
                for (CityInfo cityInfo : result.getSuggestCityList()) {
                    strInfo += cityInfo.city;
                    strInfo += ",";
                }
                strInfo += "找到结果";
                Toast.makeText(SearchMapActivity.this, strInfo, Toast.LENGTH_LONG)
                        .show();
            }
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {
            //获取Place详情页检索结果
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(SearchMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(SearchMapActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    @Override
    protected void onDestroy() {
        //释放POI检索实例；
        mPoiSearch.destroy();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mActvSearchkey_city.getText().toString().equals("")) {
            mActvSearchkey_city.setText("北京市");
        }
        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city("北京")
                .city(mActvSearchkey_city.getText().toString().trim())
                .keyword(mActvSearchkey.getText().toString().trim())
                .pageNum(0));
//        mPoiSearch.searchNearby(new PoiNearbySearchOption()
//                .keyword(mActvSearchkey.getText().toString().trim())
//                .sortType(PoiSortType.distance_from_near_to_far)
//                .location(center)
//                .radius(2000)
//                .pageNum(10);
    }

    private boolean isFirstLoc = true;

    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            // 获取经纬度
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            // 先清除图层
            mBaiduMap.clear();
            // 定义Maker坐标点
            LatLng point = new LatLng(mCurrentLat, mCurrentLon);
            Log.e("LiNing", "具体地址======" + point);
            // 构建MarkerOption，用于在地图上添加Marker
            MarkerOptions options = new MarkerOptions().position(point).icon(
                    bitmap);
            // 在地图上添加Marker，并显示
            mBaiduMap.addOverlay(options);
//            // 是否是第一次定位
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
//                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
//                mBaiduMap.animateMapStatus(msu);
//            }

            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                    .newMapStatus(builder.build()));

            // 实例化一个地理编码查询对象
            GeoCoder geoCoder = GeoCoder.newInstance();
            // 设置反地理编码位置坐标
            ReverseGeoCodeOption op = new ReverseGeoCodeOption();
            op.location(point);
            // 发起反地理编码请求(经纬度->地址信息)
            geoCoder.reverseGeoCode(op);
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                    // 获取点击的坐标地址
                    address = arg0.getAddress();
//                    获取对应城市
                    city = arg0.getAddressDetail().city;
                    Log.e("LiNing", "具体城市地址======" + city);
//                    city_tv.setText(arg0.getAddressDetail().city);
                    mActvSearchkey.setText(address);
                    Log.e("LiNing", "具体地址======" + address);
                    // 定位成功后就停止

                    mBaiduMap.setMyLocationEnabled(false);
                    if (mLocClient.isStarted()) {
                        mLocClient.stop();
                    }
                    ;
                }

                @Override
                public void onGetGeoCodeResult(GeoCodeResult arg0) {
                }
            });
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        }

    }
}
