package myaddressselector.myaddressselector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import myaddressselector.myaddressselector.view.AddressDialog;
import myaddressselector.myaddressselector.view.AddressSelectorDialog;
import myaddressselector.myaddressselector.view.SettingItemView;
import myaddressselector.myaddressselector.callback.SelectAddressInterface;


public class AddAddressActivity extends AppCompatActivity implements SelectAddressInterface,View.OnClickListener{

    TextView mcompany;
    EditText mConsignee;
    SettingItemView mSivAddress;
    EditText mDetailsAddress;
    EditText mPostcode;
    EditText mPhoneNum;
    TextView mAddressSave;
    Button mAck;

    private AddressSelectorDialog addressSelectorDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendpackage);
        initView();
    }

    private void initView() {
        mcompany = (TextView) findViewById(R.id.choose_company_edit);
        mConsignee = (EditText) findViewById(R.id.consignee);
        mSivAddress = (SettingItemView) findViewById(R.id.siv_address);
        mDetailsAddress = (EditText) findViewById(R.id.details_address);
        mPostcode = (EditText) findViewById(R.id.postcode);
        mPhoneNum = (EditText) findViewById(R.id.phoneNum);
        mAddressSave = (TextView) findViewById(R.id.address_save);
//        mAck = (Button) findViewById(R.id.ack_information);
        addAddressAreaData();
//        mAddressSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddAddressActivity.this, "已经保存了地址", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    private void addAddressAreaData() {
        mSivAddress.setItemTip("请选择  ");
        mSivAddress.setItemText("所在地区");
        mSivAddress.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCitySelectorDialog();  //三级联动
            }
        });
    }

    private void setCitySelectorDialog() {
        if (addressSelectorDialog == null) {
            addressSelectorDialog = new AddressSelectorDialog(this);
        }
        addressSelectorDialog.show();
        addressSelectorDialog.setOnAddressChangedListener(new AddressDialog.OnAddressChangedListener() {
            @Override
            public void onCanceled() {
                addressSelectorDialog.dismiss();
            }

            @Override
            public void onConfirmed(String currentProvinceName, String currentCityName, String currentDistrictName, String currentZipCode) {
                mSivAddress.setItemTip(currentProvinceName + currentCityName + currentDistrictName);
                mPostcode.setText(currentZipCode);
                addressSelectorDialog.dismiss();
            }
        });
    }

    public static void startAction(Context context) {
        Intent i = new Intent(context, AddAddressActivity.class);
        context.startActivity(i);
    }

    @Override
    public void setAreaString(String area) {

    }

    @Override
    public void setAreaString(String currentProvinceName, String currentCityName, String currentDistrictName, String currentZipCode) {
        mSivAddress.setItemTip(currentProvinceName + currentCityName + currentDistrictName);
        mPostcode.setText(currentZipCode);
    }

    @Override
    public void setAreaString(String currentProvinceName, String currentCityName) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.ack_information:
//                startActivity(new InstantiationError);
        }
    }
}
