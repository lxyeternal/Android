package myaddressselector.myaddressselector.view;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import myaddressselector.myaddressselector.bean.CityModel;
import myaddressselector.myaddressselector.bean.DistrictModel;
import myaddressselector.myaddressselector.bean.ProvinceModel;
import myaddressselector.myaddressselector.service.XmlParserHandler;


/**
 * Created by Army on 2017/2/21
 */

public class AddressDialog extends BaseDialog {
    /**
     * 省
     */
    protected ArrayList<String> mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, ArrayList<String>> mCitisDatasMap = new HashMap<String, ArrayList<String>>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, ArrayList<String>> mDistrictDatasMap = new HashMap<String, ArrayList<String>>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省份
     */
    protected String mCurrentProvinceName;
    /**
     * 当前城市
     */
    protected String mCurrentCityName;
    /**
     * 当前区
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前邮编
     */
    protected String mCurrentZipCode = "";
    public OnAddressChangedListener listener;

    public AddressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddressDialog(Context context) {
        super(context);
    }

    /**
     * 初始化数据
     */

    protected boolean initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");

            SAXParserFactory spf = SAXParserFactory.newInstance();

            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();

            provinceList = handler.getDataList();

            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProvinceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            //            mProvinceDatas = new String[provinceList.size()];
            mProvinceDatas = new ArrayList<String>();
            for (int i = 0; i < provinceList.size(); i++) {

                mProvinceDatas.add(provinceList.get(i).getName());
                List<CityModel> cityList = provinceList.get(i).getCityList();
                ArrayList<String> cityNames = new ArrayList<String>();

                for (int j = 0; j < cityList.size(); j++) {

                    cityNames.add(cityList.get(j).getName());
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    ArrayList<String> distrinctNameArray = new ArrayList<String>();
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];

                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray.add(districtModel.getName());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                    }
                    mDistrictDatasMap.put(cityNames.get(j), distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }

    public interface OnAddressChangedListener {
        void onCanceled();

        void onConfirmed(String currentProvinceName, String currentCityName, String currentDistrictName, String currentZipCode);
    }

    public void setOnAddressChangedListener(OnAddressChangedListener listener) {
        this.listener = listener;
    }
}
