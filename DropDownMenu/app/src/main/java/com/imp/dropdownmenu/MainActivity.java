package com.imp.dropdownmenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imp.dropdownmenu.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private View dissmiss;
    private TextView search;
    private EditText edit_query;
    private PopupWindow popupWindow;
    private View zhezhao;   //底下半透明背景，实现矩形进入效果
    private LinearLayout sex;
    private LinearLayout nation;
    private LinearLayout country;
    private LinearLayout culture;
    private TextView sex_text;
    private TextView nation_text;
    private TextView country_text;
    private TextView culture_text;
    List<Map<String, String>> sexResult;
    List<Map<String, String>> nationResult;
    List<Map<String, String>> countryResult;
    List<Map<String, String>> cultureResult;
    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private  SearchAdapter sexAdapter;
    private SearchAdapter countryAdapter;
    private SearchAdapter nationAdapter;
    private SearchAdapter cultureAdapter;
    private DropDownMenu dropDownMenu;
    private LinearLayout layout;
    private View listItem;
    private View listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        edit_query = findViewById(R.id.edit_query);

        sex = findViewById(R.id.sex);
        nation = findViewById(R.id.nation);
        country = findViewById(R.id.country);
        culture = findViewById(R.id.culture);
        sex_text = findViewById(R.id.sex_text);
        nation_text = findViewById(R.id.nation_text);
        country_text = findViewById(R.id.country_text);
        culture_text = findViewById(R.id.culture_text);
        layout = (LinearLayout) getLayoutInflater().inflate(R.layout.pup_selectlist, null, false);


        sex.setOnClickListener(this);
        nation.setOnClickListener(this);
        country.setOnClickListener(this);
        culture.setOnClickListener(this);

        dropDownMenu = DropDownMenu.getInstance(this, new DropDownMenu.OnListCkickListence() {
            @Override
            public void search(String code, String type) {
                System.out.println("======"+code+"========="+type);
            }

            @Override
            public void changeSelectPanel(Madapter madapter, View view) {

            }
        });
        dropDownMenu.setIndexColor(R.color.colorAccent);
        dropDownMenu.setShowShadow(true);
        dropDownMenu.setShowName("name");
        dropDownMenu.setSelectName("code");

        initData();

    }

    protected void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
//                    finish();
                }
            });
        }

    }

    private void initData(){
        sexAdapter = new SearchAdapter(this);  //真实项目里，适配器初始化一定要写在这儿 不然如果new出来的设配器里面没有值，会报空指针

        List<Dic> sexResult = new ArrayList<>();
        sexResult.add(new Dic("000","全部"));
        sexResult.add(new Dic("001","男"));
        sexResult.add(new Dic("002","女"));

        sexAdapter.setItems(sexResult);



        nationAdapter = new SearchAdapter(this);
        List<Dic> nationResult = new ArrayList<>();
        nationResult.add(new Dic("000","全部"));
        nationResult.add(new Dic("001","汉族"));
        nationResult.add(new Dic("002","回族"));
        nationResult.add(new Dic("003","满族"));
        nationResult.add(new Dic("004","布依族"));
        nationResult.add(new Dic("005","保安族"));
        nationResult.add(new Dic("006","保安族"));
        nationResult.add(new Dic("007","保安族"));
        nationResult.add(new Dic("008","保安族"));

        nationAdapter.setItems(nationResult);

        countryAdapter = new SearchAdapter(this);
        List<Dic> countryResult = new ArrayList<>();
        countryResult.add(new Dic("000","全部"));
        countryResult.add(new Dic("001","中国"));
        countryResult.add(new Dic("002","法国"));
        countryResult.add(new Dic("003","俄罗斯"));
        countryResult.add(new Dic("004","越南"));
        countryResult.add(new Dic("005","老挝"));
        countryResult.add(new Dic("006","缅甸"));

        countryAdapter.setItems(countryResult);

        cultureAdapter = new SearchAdapter(this);
        List<Dic> cultureResult = new ArrayList<>();
        cultureResult.add(new Dic("000","全部"));
        cultureResult.add(new Dic("001","小学"));
        cultureResult.add(new Dic("002","初中"));
        cultureResult.add(new Dic("003","高中"));
        cultureResult.add(new Dic("004","中专"));
        cultureResult.add(new Dic("005","大专"));
        cultureResult.add(new Dic("006","本科"));

        cultureAdapter.setItems(cultureResult);

        listItem = getLayoutInflater().inflate(R.layout.item_listview, null, false);
        listView = getLayoutInflater().inflate(R.layout.pup_selectlist, null, false);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sex:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(this),
                        ScreenUtils.getScreenHeight(this), sexAdapter,
                        listView, listItem,sex, sex_text, "cyry.xbdm", false);
                break;
            case R.id.nation:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(this),
                        ScreenUtils.getScreenHeight(this), nationAdapter,
                        listView, listItem,nation,nation_text,"cyry.mzdm",true);
                break;
            case R.id.country:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(this),
                        ScreenUtils.getScreenHeight(this), countryAdapter,
                        listView, listItem,country,country_text,"cyry.gjdm",true);

                break;
            case R.id.culture:
                dropDownMenu.showSelectList(ScreenUtils.getScreenWidth(this),
                        ScreenUtils.getScreenHeight(this), cultureAdapter,
                        listView, listItem,culture,culture_text,"cyry.whcd",true);
                break;
            default:
                break;
        }
    }

}
