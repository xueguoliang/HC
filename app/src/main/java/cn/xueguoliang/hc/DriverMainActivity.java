package cn.xueguoliang.hc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;

public class DriverMainActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;
    private Button button;

    private String[] texts = {"开始接单", "停止接单", "接单确认", "乘客已经上车", "目的地已经到达"};
    private final int startWork = 0;
    private final int stopWork = 1;
    private final int recvOrderAck = 2;
    private final int startOrder = 3;
    private final int completeOrder = 4;

    private boolean startWorkFlag = false;

    private LocationSource.OnLocationChangedListener source;
    private AMapLocationClient locationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        mapView = (MapView)findViewById(R.id.map);
        button = (Button)findViewById(R.id.button);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonClicked();
            }
        });

        setMyLoation();
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
                            if(startWorkFlag)
                            {
                                // 发送位置给服务器
                                Jni.instance().LocationChange(aMapLocation.getLongitude(),
                                        aMapLocation.getLatitude());
                            }
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

    private void ButtonClicked() {
        String text = button.getText().toString();
        if (text.equals(texts[startWork])) {
            startWorkFlag = true;
            button.setText(texts[stopWork]);

            // 当开始接单时，只要往服务器发送司机位置即可
            // 服务器会给有位置信息的司机派单

            Log.e("aaa", "bbb");

        } else if (text.equals(texts[stopWork])) {
            startWorkFlag = false;
            button.setText(texts[startWork]);
            // 发送停止接单命令给服务器，让服务器清理我的位置

        } else if (text.equals(texts[recvOrderAck]))
        {
            button.setText(texts[startOrder]);
            // 向服务器发送订单确认
        }
        else if(text.equals(texts[startOrder]))
        {
            button.setText(texts[completeOrder]);
            // 想服务器发送乘客已经上车
        }
        else if(text.equals(texts[completeOrder]))
        {
            button.setText(texts[stopWork]);
            // 向服务器发送乘客已经下车
        }
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
