package myaddressselector.myaddressselector.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import myaddressselector.myaddressselector.R;


/**
 * Created by Army on 2017/2/24
 */
public class AddressSelectorDialog extends AddressDialog implements View.OnClickListener {
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Context mContext;
    private TextView tvCancel;
    private TextView tvConfirm;
    protected boolean isDataLoaded = false;

    public AddressSelectorDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public AddressSelectorDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address);
        setUpViews();
        setUpListener();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        tvCancel = (TextView) findViewById(R.id.btn_cancel);
        tvConfirm = (TextView) findViewById(R.id.btn_confirm);
        initProvinceSelectView();
    }

    private void setUpListener() {
        // 取消和确定
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                if (null != listener) {
                    listener.onCanceled();
                }
                break;
            case R.id.btn_confirm:
                if (null != listener) {
                    listener.onConfirmed(mCurrentProvinceName, mCurrentCityName, mCurrentDistrictName, mCurrentZipCode);
                }
                break;
            default:
                break;
        }
    }

    private void initProvinceSelectView() {
        mViewProvince.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String provinceText = mProvinceDatas.get(id);
                if (!mCurrentProvinceName.equals(provinceText)) {
                    mCurrentProvinceName = provinceText;
                    ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProvinceName);
                    mViewCity.resetData(mCityData);
                    mViewCity.setDefault(0);
                    mCurrentCityName = mCityData.get(0);

                    ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                    mViewDistrict.resetData(mDistrictData);
                    mViewDistrict.setDefault(0);
                    mCurrentDistrictName = mDistrictData.get(0);
                }
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mViewCity.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProvinceName);
                String cityText = mCityData.get(id);
                if (!mCurrentCityName.equals(cityText)) {
                    mCurrentCityName = cityText;
                    ArrayList<String> mCountyData = mDistrictDatasMap.get(mCurrentCityName);
                    mViewDistrict.resetData(mCountyData);
                    mViewDistrict.setDefault(0);
                    mCurrentDistrictName = mCountyData.get(0);
                }
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mViewDistrict.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                String districtText = mDistrictData.get(id);
                if (!mCurrentDistrictName.equals(districtText)) {
                    mCurrentDistrictName = districtText;
                }
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        getData();


    }

    private void getData() {
        // 启动线程读取数据
        new Thread() {
            @Override
            public void run() {
                // 读取数据
                isDataLoaded = initProvinceDatas();

                // 通知界面线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        setDefaultData();
                    }
                });
            }
        }.start();
    }

    private void setDefaultData() {
        mViewProvince.setData(mProvinceDatas);
        mViewProvince.setDefault(0);
        mCurrentProvinceName = mProvinceDatas.get(0);

        ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProvinceName);
        mViewCity.setData(mCityData);
        mViewCity.setDefault(0);
        mCurrentCityName = mCityData.get(0);

        ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
        mViewDistrict.setData(mDistrictData);
        mViewDistrict.setDefault(0);
        mCurrentDistrictName = mDistrictData.get(0);
        mCurrentZipCode = mZipcodeDatasMap.get(0);
    }
}
