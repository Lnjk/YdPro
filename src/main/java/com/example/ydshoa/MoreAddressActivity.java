package com.example.ydshoa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
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
import com.baidu.mapapi.search.sug.SuggestionSearch;

import java.util.List;

public class MoreAddressActivity extends Activity implements TextWatcher, BaiduMap.OnMapStatusChangeListener, View.OnClickListener, OnGetGeoCoderResultListener {
    private ImageView iv_left;
    protected static final String TAG = "MoreAddressActivity";
    protected static final int REQUEST_OK = 0;
    private ListView lv_near_address;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    private double latitude;
    private double longitude;
//     * 搜索关键字输入窗口
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;
    private PoiSearchAdapter adapter;
    private MapView map;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private LocationClient mLocClient;
    private GeoCoder geoCoder;
    private String city;
    private boolean isFirstLoc = true;
    private LatLng locationLatLng;
    private EditText searchAddress;
    private List<PoiInfo> poiInfos;
    private ListView searchPois;
    private ImageView mImg_load_animation;
    private long startTime;
    private long stopTome;
    private TextView mTv_net;
    private RelativeLayout mRl_gps;
    private String sf_and,qx_and;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_more_address);
        sf_and =getIntent().getStringExtra("SF");
        qx_and = getIntent().getStringExtra("QX");
        initData();
        startTime = System.currentTimeMillis();
    }

    private void initData() {
        searchAddress = (EditText) findViewById(R.id.main_search_address);
//        searchAddress.setText(sf_and+qx_and);
        searchPois = (ListView) findViewById(R.id.main_search_pois);
//        mImg_load_animation = (ImageView) findViewById(R.id.img_load_animation);
//        mTv_net = (TextView) findViewById(R.id.tv_net);
//        mRl_gps = (RelativeLayout) findViewById(R.id.rl_gps);
//        mRl_gps.setClickable(true);
        searchAddress.addTextChangedListener(this);
        searchPois.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchPois.setVisibility(View.GONE);
                if (poiInfos != null) {
                    if (poiInfos.get(position) != null) {
                        LatLng location = poiInfos.get(position).location;
                        if (location != null) {
                            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(location, 18);
                            mBaiduMap.animateMapStatus(msu);
                        }
                    }
                }


            }
        });
        map = (MapView) findViewById(R.id.map);
        mBaiduMap = map.getMap();
        MapStatus mapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        // 地图状态改变相关监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位图层显示方式
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode,true,null));
        mLocClient = new LocationClient(this);
//        // 注册定位监听
        mLocClient.registerLocationListener(myListener);
        // 定位选项
        LocationClientOption option = new LocationClientOption();
//         coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系
//         bd09ll
        option.setCoorType("bd09ll");
        // 设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        // 设置是否需要返回位置语义化信息，可以在BDLocation.getLocationDescribe()中得到数据，ex:"在天安门附近"，
        // 可以用作地址信息的补充
        option.setIsNeedLocationDescribe(true);
        // 设置是否需要返回位置POI信息，可以在BDLocation.getPoiList()中得到数据
        option.setIsNeedLocationPoiList(true);
//         * 设置定位模式 Battery_Saving 低功耗模式 Device_Sensors 仅设备(Gps)模式 Hight_Accuracy
//         * 高精度模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 设置是否打开gps进行定位
        option.setOpenGps(true);
        // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        option.setScanSpan(1000);
        // 设置 LocationClientOption
        mLocClient.setLocOption(option);
        // 开始定位
        mLocClient.start();
        lv_near_address = (ListView) findViewById(R.id.lv_near_address);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 || "".equals(s.toString())) {
            searchPois.setVisibility(View.GONE);
        } else {
            searchPois.setVisibility(View.VISIBLE);

            // 创建PoiSearch实例
            PoiSearch poiSearch;
            poiSearch = PoiSearch.newInstance();
            // 城市内检索
            PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
            // 关键字
            poiCitySearchOption.keyword(s.toString());
            // 城市
            poiCitySearchOption.city(city);
            // 设置每页容量，默认为每页10条
            poiCitySearchOption.pageCapacity(5);
            // 分页编号
            poiCitySearchOption.pageNum(3);
            poiSearch.searchInCity(poiCitySearchOption);
            // 设置poi检索监听者
            poiSearch .setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                // poi 查询结果回调
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    poiInfos = poiResult.getAllPoi();
                    Log.e("LiNing","查询数据========"+poiInfos);
                    if (poiInfos != null) {
                        PoiSearchAdapter poiSearchAdapter = new PoiSearchAdapter(
                                MoreAddressActivity.this, poiInfos,
                                locationLatLng);
//                         searchPois.setVisibility(View.VISIBLE);
                        searchPois.setAdapter(poiSearchAdapter);
                    }
                }

                // poi 详情查询结果回调
                @Override
                public void onGetPoiDetailResult(
                        PoiDetailResult poiDetailResult) {
                }

                @Override
                public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

                }

                @Override
                public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                }
            });
        }
    }
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
            if (bdLocation == null || mBaiduMap == null) {
                return;
            }

            // 定位数据
            MyLocationData data = new MyLocationData.Builder()
                    // 定位精度bdLocation.getRadius()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(bdLocation.getDirection())
                    // 经度
                    .latitude(bdLocation.getLatitude())
                    // 纬度
                    .longitude(bdLocation.getLongitude())
                    // 构建
                    .build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(data);

            // 是否是第一次定位
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(ll, 18);
                mBaiduMap.animateMapStatus(msu);
            }

            locationLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            // 获取城市，待会用于POISearch
            city = bdLocation.getCity();
            Log.e("LiNing","-----"+city);
            // 创建GeoCoder实例对象
            geoCoder = GeoCoder.newInstance();
            // 发起反地理编码请求(经纬度->地址信息)
            ReverseGeoCodeOption reverseGeoCodeOption = new ReverseGeoCodeOption();
            // 设置反地理编码位置坐标
            reverseGeoCodeOption.location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
            geoCoder.reverseGeoCode(reverseGeoCodeOption);

            // 设置查询结果监听者
            geoCoder.setOnGetGeoCodeResultListener((OnGetGeoCoderResultListener) this);
        }
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        // 地图操作的中心点
        LatLng cenpt = mapStatus.target;

//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;

            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        map.onDestroy();
        if (geoCoder != null) {
            geoCoder.destroy();
        }
        map = null;
        if (mSuggestionSearch != null) {
            mSuggestionSearch.destroy();
            mSuggestionSearch = null;
        }

    }
    public void onResume() {
        super.onResume();
        map.onResume();
//     if (getIntent().getStringExtra("latitude") != null &&
//     (!"".equals(getIntent().getStringExtra("latitude")))) {
//     latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
//     longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
//     TtelifeLog.i(TAG, "latitude" + latitude);
//     }
//     MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        stopTome = System.currentTimeMillis();
        //   if (stopTome - startTime > 5000) {

        //       mImg_load_animation.setVisibility(View.GONE);
        //       mTv_net.setVisibility(View.VISIBLE);
        //    } else {
        //        mRl_gps.setVisibility(View.GONE);
        //     mRl_gps.setClickable(false);
        // mImg_load_animation.setVisibility(View.GONE);
        //}
        mRl_gps.setVisibility(View.GONE);
        mLocClient.stop();
        final List<PoiInfo> poiInfos = reverseGeoCodeResult.getPoiList();
        Log.i(TAG, "这里的值:" + poiInfos);
        if (poiInfos != null && !"".equals(poiInfos)) {
            PoiAdapter poiAdapter = new PoiAdapter(MoreAddressActivity.this, poiInfos);
            lv_near_address.setAdapter(poiAdapter);
            lv_near_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = poiInfos.get(position).name.toString();
                    Intent intent = new Intent();
                    intent.putExtra("selectAddress", name);
                    setResult(1, intent);
                    Toast.makeText(MoreAddressActivity.this, name, Toast.LENGTH_LONG).show();
                      finish();
                }
            });
        }
    }

    class PoiSearchAdapter extends BaseAdapter {

        private Context context;
        private List<PoiInfo> list;
        private ViewHolder holder;


        public PoiSearchAdapter(MoreAddressActivity context, List<PoiInfo> poiInfos, LatLng locationLatLng) {
            this.context = context;
            this.list = poiInfos;
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
                holder = new ViewHolder();
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
    class PoiAdapter extends BaseAdapter {
        private Context context;
        private List<PoiInfo> pois;
        private LinearLayout linearLayout;


        public PoiAdapter(MoreAddressActivity context, List<PoiInfo> poiInfos) {
            this.context = context;
            this.pois = poiInfos;
        }

        @Override
        public int getCount() {
            return pois.size();
        }

        @Override
        public Object getItem(int position) {
            return pois.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.map_item_two, null);
                linearLayout = (LinearLayout) convertView.findViewById(R.id.locationpois_linearlayout);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                holder.iv_gps.setImageDrawable(getResources().getDrawable(R.drawable.xialabuttons));
                holder.locationpoi_name.setTextColor(Color.parseColor("#000000"));
                holder.locationpoi_address.setTextColor(Color.parseColor("#000000"));
            }
            if (position == 0 && linearLayout.getChildCount() < 2) {
                ImageView imageView = new ImageView(context);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(32, 32);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.TRANSPARENT);
                imageView.setImageResource(R.drawable.xialabutton);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                linearLayout.addView(imageView, 0, params);
                holder.locationpoi_name.setTextColor(Color.parseColor("#000000"));
            }
            PoiInfo poiInfo = pois.get(position);
            holder.locationpoi_name.setText(poiInfo.name);
            holder.locationpoi_address.setText(poiInfo.address);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_gps;
            TextView locationpoi_name;
            TextView locationpoi_address;

            ViewHolder(View view) {
                locationpoi_name = (TextView) view.findViewById(R.id.locationpois_name);
                locationpoi_address = (TextView) view.findViewById(R.id.locationpois_address);
                iv_gps = (ImageView) view.findViewById(R.id.iv_gps);
            }
        }
    }
}
