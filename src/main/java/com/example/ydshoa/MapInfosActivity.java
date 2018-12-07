package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
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
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.bean.TestQx;

import java.util.List;

public class MapInfosActivity extends Activity implements TextWatcher {
    private SharedPreferences sp;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;
    private String address = "";
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationClient mLocClient;
    private TextView city_tv;
    private EditText  address_tv;
    private Button dw_bt,ok_dt;
    private String sf_and,qx_and,xc_and;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_infos);
        sp = getSharedPreferences("ydbg", 0);
        sf_and =getIntent().getStringExtra("SF");
        qx_and = getIntent().getStringExtra("QX");
        xc_and = getIntent().getStringExtra("XC");

        intView();
    }

    private void intView() {
        city_tv = (TextView) findViewById(R.id.city_tv);
        city_tv.setText(qx_and);
        address_tv = (EditText) findViewById(R.id.address_tv);
        address_tv.setText(xc_and);
        address_tv.addTextChangedListener(this);
//        dw_bt=(Button)findViewById(R.id.dw_bt);
//        ok_dt=(Button)findViewById(R.id.btn_dt_ok);
        searchPois = (ListView) findViewById(R.id.main_search_pois);
        searchPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchPois.setVisibility(View.GONE);
                if (allPoi != null) {
                    if (allPoi.get(position) != null) {
                        LatLng location = allPoi.get(position).location;
                        if (location != null) {
                            address_tv.setText( allPoi.get(position).name);
                            Log.e("LiNing","具体地址======"+allPoi.get(position).address);
                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(location, 18);
                            mBaiduMap.animateMapStatus(msu);
                        }
                    }
                }


            }
        });
        // 获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 设置是否显示比例尺控件
        mMapView.showScaleControl(false);
        // 设置是否显示缩放控件
        mMapView.showZoomControls(true);
        // 删除百度地图LoGo
        mMapView.removeViewAt(1);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        if(city_tv.equals("")){
            // 定位初始化
            mLocClient = new LocationClient(getApplicationContext());
            mLocClient.registerLocationListener(myListener);

                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true); // 打开gps
                option.setCoorType("bd09ll"); // 设置坐标类型
                option.setScanSpan(1000);
                mLocClient.setLocOption(option);
                mLocClient.start();
            }

        // 设置marker图标
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
                Log.e("LiNing","具体地址======"+mCurrentLat+"----"+mCurrentLon);
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
                        address_tv.setText(address);
                        Log.e("LiNing","具体地址======"+address);

                    }
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
            }
        });

//        //点击定位按钮重新定位
//        dw_bt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mBaiduMap.setMyLocationEnabled(true);
//                mLocClient.start();
//            }
//
//        });
//        ok_dt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent localIntent = getIntent();
//                localIntent.putExtra("ANDRESS", address_tv.getText().toString());
//                setResult(1, localIntent);
//                finish();
//            }
//
//        });
        mPoiSearch = PoiSearch.newInstance();
        //创建POI检索监听者；
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city_tv.getText().toString().trim())
                .keyword(address_tv.getText().toString().trim())
                .pageNum(0));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city_tv.getText().toString().trim())
                .keyword(address_tv.getText().toString().trim())
                .pageNum(0));
    }

    //    public class MyLocationListenner implements BDLocationListener {
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
            Log.e("LiNing","具体地址======"+point);
            // 构建MarkerOption，用于在地图上添加Marker
            MarkerOptions options = new MarkerOptions().position(point).icon(
                    bitmap);
            // 在地图上添加Marker，并显示
            mBaiduMap.addOverlay(options);

             ll = new LatLng(location.getLatitude(),
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
                    city_tv.setText(arg0.getAddressDetail().city);
                    address_tv.setText(address);
                    Log.e("LiNing","具体地址======"+address);
                    // 定位成功后就停止

                    mBaiduMap.setMyLocationEnabled(false);
                    if (mLocClient.isStarted()) {
                        mLocClient.stop();
                    };
                }

                @Override
                public void onGetGeoCodeResult(GeoCodeResult arg0) {
                }
            });
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLocClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    //用于条件搜索
    private PoiSearch mPoiSearch;
    List<PoiInfo> allPoi;
    LatLng location_ll,ll;
    private ListView searchPois;
    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        public void onGetPoiResult(PoiResult result) {
            // 关闭定位图层
//            mBaiduMap.clear();
//            mLocClient.stop();
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //详情检索失败
                // result.error请参考SearchResult.ERRORNO
                Toast.makeText(MapInfosActivity.this, "未搜索到POI数据", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                //获取POI检索结果
                Toast.makeText(MapInfosActivity.this, "已搜索到相关数据数据", Toast.LENGTH_SHORT).show();
//                mBaiduMap.clear();

                allPoi = result.getAllPoi();
                for (int i = 0; i < allPoi.size(); i++) {
                    Resources res = getResources();
                    Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.csdw);
                    Bitmap bitmap1 = BitmapFactory.decodeResource(res, R.drawable.cxdw);
//                    Bitmap bitmap1 = ImageUtils.drawTextToCenter(MapInfosActivity.this, bitmap, "" + i, 20, Color.BLACK);

                    OverlayOptions options = new MarkerOptions()
                            .position(result.getAllPoi().get(i).location)
                            .title(result.getAllPoi().get(i).name + ":" + result.getAllPoi().get(i).address)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap1));
                    mBaiduMap.addOverlay(options);
                    //实现位置跟踪

                    location_ll = result.getAllPoi().get(i).location;
//                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(location_ll, 18);
//                    mBaiduMap.animateMapStatus(msu);
                }
                if (allPoi != null) {
                    PoiSearchAdapter poiSearchAdapter = new PoiSearchAdapter(MapInfosActivity.this, allPoi,ll);
                         searchPois.setVisibility(View.VISIBLE);
                    searchPois.setAdapter(poiSearchAdapter);
                    poiSearchAdapter.notifyDataSetChanged();
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
                        Log.e("LiNing","具体地址======"+marker);
                        Intent localIntent = getIntent();
//                        localIntent.putExtra("ANDRESS", marker.getTitle().toString());
                        localIntent.putExtra("ANDRESS",address_tv.getText().toString());
                        setResult(1, localIntent);
                        finish();
                        Toast.makeText(MapInfosActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MapInfosActivity.this, strInfo, Toast.LENGTH_LONG)
                        .show();
            }
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {
            //获取Place详情页检索结果
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(MapInfosActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MapInfosActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
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
    class PoiSearchAdapter extends BaseAdapter {

        private Context context;
        private List<PoiInfo> list;
        private ViewHolder holder;

        public PoiSearchAdapter(MapInfosActivity searchMapActivity, List<PoiInfo> allPoi, LatLng ll) {
            this.context = searchMapActivity;
            this.list = allPoi;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int location) {
            return list.get(location);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new PoiSearchAdapter.ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.map_item_one, null);
                holder.mpoi_name = (TextView) convertView.findViewById(R.id.mpoiNameT);
                holder.mpoi_address = (TextView) convertView.findViewById(R.id.mpoiAddressT);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mpoi_name.setText(list.get(position).name);
            holder.mpoi_address.setText(list.get(position).address);
            // Log.i("yxx", "==1=poi===城市：" + poiInfo.city + "名字：" +
            // poiInfo.name + "地址：" + poiInfo.address);
            return convertView;
        }

        public void addObject(List<PoiInfo> mAppGroup) {
            this.list = mAppGroup;
            notifyDataSetChanged();
        }

        public class ViewHolder {
            public TextView mpoi_name;// 名称
            public TextView mpoi_address;// 地址

        }

    }
}
