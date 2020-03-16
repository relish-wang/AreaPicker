/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SCWheelAreaPicker extends LinearLayout implements IWheelAreaPicker {
    private static final float ITEM_TEXT_SIZE = 18;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    private static final int PROVINCE_INITIAL_INDEX = 0;

    private Context mContext;

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<IPickerModel> mProvinceName, mCityName;

    private AssetManager mAssetManager;

    private LayoutParams mLayoutParams;

    private SCWheelPicker mWPProvince, mWPCity, mWPArea;

    public SCWheelAreaPicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        initLayoutParams();

        initView(context);

        mProvinceList = getJsonDataFromAssets(mAssetManager);

        obtainProvinceData();

        addListenerToWheelPicker();
    }

    @SuppressWarnings("unchecked")
    private List<Province> getJsonDataFromAssets(AssetManager assetManager) {
        List<Province> provinceList = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = assetManager.open("area_de.json");
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String str = "";
            do {
                try {
                    str = reader.readLine();
                } catch (IllegalStateException e) {
                    break;// 读到文件末尾了
                }
                if (str == null) break;
                builder.append(str);
            } while (true);
            final String json = builder.toString();
            provinceList = (List<Province>) new Gson().fromJson(json, new TypeToken<List<Province>>() {
            }.getType());
            return provinceList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return provinceList;
    }

    private void initLayoutParams() {
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mContext = context;

        mAssetManager = mContext.getAssets();

        mProvinceName = new ArrayList<>();
        mCityName = new ArrayList<>();

        mWPProvince = new SCWheelPicker(context);
        mWPCity = new SCWheelPicker(context);
        mWPArea = new SCWheelPicker(context);

        initWheelPicker(mWPProvince, 1);
        initWheelPicker(mWPCity, 1.5f);
        initWheelPicker(mWPArea, 1.5f);
    }

    private void initWheelPicker(SCWheelPicker SCWheelPicker, float weight) {
        mLayoutParams.weight = weight;
        SCWheelPicker.setItemTextSize(dip2px(mContext, ITEM_TEXT_SIZE));
        SCWheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        SCWheelPicker.setCurved(true);
        SCWheelPicker.setLayoutParams(mLayoutParams);
        addView(SCWheelPicker);
    }

    private void obtainProvinceData() {
        for (Province province : mProvinceList) {
            IPickerModel pickerModel = new PickerModel();
            pickerModel.setCode(province.getCode());
            pickerModel.setName(province.getName());
            mProvinceName.add(pickerModel);
        }
        mWPProvince.setData(mProvinceName);
        setCityAndAreaData(PROVINCE_INITIAL_INDEX);
    }

    private void addListenerToWheelPicker() {
        //监听省份的滑轮,根据省份的滑轮滑动的数据来设置市跟地区的滑轮数据
        mWPProvince.setOnItemSelectedListener(new SCWheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(SCWheelPicker picker, IPickerModel data, int position) {
                //获得该省所有城市的集合
                mCityList = mProvinceList.get(position).getCity();
                setCityAndAreaData(position);
            }
        });

        mWPCity.setOnItemSelectedListener(new SCWheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(SCWheelPicker picker, IPickerModel data, int position) {
                //获取城市对应的城区的名字
                mWPArea.setData(mCityList.get(position).getArea());
            }
        });
    }

    private void setCityAndAreaData(int position) {
        //获得该省所有城市的集合
        mCityList = mProvinceList.get(position).getCity();
        //获取所有city的名字
        //重置先前的城市集合数据
        mCityName.clear();
        for (City city : mCityList) {
            IPickerModel pickerModel = new PickerModel();
            pickerModel.setCode(city.getCode());
            pickerModel.setName(city.getName());
            mCityName.add(pickerModel);
        }
        mWPCity.setData(mCityName);
        mWPCity.setSelectedItemPosition(0);
        //获取第一个城市对应的城区的名字
        //重置先前的城区集合的数据
        mWPArea.setData(mCityList.get(0).getArea());
        mWPArea.setSelectedItemPosition(0);
    }

    @Override
    public String getProvince() {
        return mProvinceList.get(mWPProvince.getCurrentItemPosition()).getName();
    }

    @Override
    public String getCity() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getName();
    }

    @Override
    public String getArea() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getArea().get(mWPArea.getCurrentItemPosition()).getName();
    }

    public String getCode() {
        return mCityList.get(mWPCity.getCurrentItemPosition()).getArea().get(mWPArea.getCurrentItemPosition()).getCode();
    }

    @Override
    public void hideArea() {
        this.removeViewAt(2);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setPickedArea(String areaCode) {
        try {
            int code = Integer.parseInt(areaCode);
            final int aPos = code % 100 - 1;
            final int cPos = (code / 100) % 100 - 1;
            final int pPos = (code / 10000) % 100 - 1;
            if (pPos >= 0 && mWPProvince != null) {
                mWPProvince.setSelectedItemPosition(pPos);
            }
            if (cPos >= 0 && mWPCity != null) {
                if (mWPProvince != null) {
                    mWPProvince.post(new Runnable() {
                        @Override
                        public void run() {
                            mWPCity.setSelectedItemPosition(cPos);
                        }
                    });
                }
            }
            if (aPos >= 0 && mWPArea != null) {
                if (mWPCity != null) {
                    mWPCity.post(new Runnable() {
                        @Override
                        public void run() {
                            mWPArea.setSelectedItemPosition(aPos);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
