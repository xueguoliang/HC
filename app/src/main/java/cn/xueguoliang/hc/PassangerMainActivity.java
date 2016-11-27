package cn.xueguoliang.hc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;

public class PassangerMainActivity extends AppCompatActivity {

    private AutoCompleteTextView target;
    private Button startButton;
    private MapView mapView;
    private AMap aMap;

    private LocationSource.OnLocationChangedListener source;
    private AMapLocationClient locationClient = null;

    private LatLonPoint startPos = new LatLonPoint(0, 0);
    private LatLonPoint endPos;
    private String city = "北京";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passanger_main);

        target = (AutoCompleteTextView)findViewById(R.id.target);
        startButton = (Button)findViewById(R.id.start);
        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        setupAutoComplete();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(target.getText().toString().length() == 0)
                    return;

                startOrder();
            }
        });
    }

    private void getEndPos(){
        PoiSearch.Query query = new PoiSearch.Query(city, "", target.getText().toString().trim());
        PoiSearch search = new PoiSearch(getApplication(), query);
        PoiResult poiResult = null;
        try {
            poiResult = search.searchPOI();
        }
        catch (AMapException e) {
            return;
        }

        if(poiResult == null || poiResult.getPois().size() == 0)
            return;

        PoiItem targetPoi = poiResult.getPois().get(0);
        endPos = targetPoi.getLatLonPoint();
    }

    private void startOrder(){
        // 把开始的位置和目标位置，发送给服务器，估计价格
        getEndPos();


    }

    private void setupAutoComplete(){
        target.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 得到用户输入的内容
                // 搜索POI
                // 把搜索的POI列表放入AutuComplteTextView的备选列表中
                String keyword = target.getText().toString().trim();

                // 搜索条件
                PoiSearch.Query query = new PoiSearch.Query(city, "", keyword);

                // 进行搜索
                PoiSearch search = new PoiSearch(getApplication(), query);
                search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                    @Override
                    public void onPoiSearched(PoiResult poiResult, int result) {
                        // 将PoiResult中的结果，放入AutoCompleteTextView
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(),
                                android.R.layout.simple_spinner_item);

                        ArrayList<PoiItem> items = poiResult.getPois();
                        for (int i=0;i <items.size(); ++i)
                        {
                            adapter.add(items.get(i).getTitle());
                        }

                        target.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    // 不好用
                    @Override
                    public void onPoiItemSearched(PoiItem poiItem, int i) {

                    }
                });
                search.searchPOIAsyn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setMyLoation(){
        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                source = onLocationChangedListener;

                if(locationClient == null)
                {
                    locationClient = new AMapLocationClient(getApplication());
                    locationClient.setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {
                            source.onLocationChanged(aMapLocation);
                            startPos.setLatitude(aMapLocation.getLatitude());
                            startPos.setLongitude(aMapLocation.getLongitude());
                        }
                    });
                }
                locationClient.startLocation();
            }

            @Override
            public void deactivate() {
                source = null;
                if(locationClient != null)
                {
                    locationClient.stopLocation();
                    locationClient.onDestroy();
                    locationClient = null;
                }
            }
        });

        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
