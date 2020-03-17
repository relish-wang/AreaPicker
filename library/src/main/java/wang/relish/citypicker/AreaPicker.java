/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class AreaPicker extends BaseDialog implements WheelPicker.OnItemSelectedListener {


    private TextView mTvCancel;
    private TextView mTvConfirm;

    private String rightText = "确定";
    private int rightColor = Color.parseColor("#4CA6FF");
    private int rightSize = 16;
    private String leftText = "取消";
    private int leftColor = Color.parseColor("#1A1A1A");
    private int leftSize = 16;


    private RelativeLayout mRlRoot;
    private WheelAreaPicker mPicker;

    private OnAreaPickedListener mOnAreaPickedListener;

    public AreaPicker(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.widget_dialog_area_picker, null);
        mRlRoot = (RelativeLayout) view.findViewById(R.id.widget_rl_root);
        mTvCancel = (TextView) view.findViewById(R.id.fcprompt_date_picker_tv_cancel);
        mTvConfirm = (TextView) view.findViewById(R.id.fcprompt_date_picker_tv_confirm);
        mPicker = (WheelAreaPicker) view.findViewById(R.id.wheel_area_picker);
        return view;
    }

    @Override
    public void setupView() {
        if (getWindow() != null) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        mRlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        mTvCancel.setText(leftText);
        mTvCancel.setTextColor(leftColor);
        mTvCancel.setTextSize(leftSize);

        mTvConfirm.setText(rightText);
        mTvConfirm.setTextColor(rightColor);
        mTvConfirm.setTextSize(rightSize);

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnAreaPickedListener != null) {
                    mOnAreaPickedListener.onAreaPicked(
                            mPicker.getProvince(),
                            mLevel == Level.PROVINCE ? null : mPicker.getCity(),
                            mLevel == Level.PROVINCE || mLevel == Level.CITY ? null : mPicker.getArea(),
                            mPicker.getCode()
                    );
                }
            }
        });

        if (!TextUtils.isEmpty(mPickedAreaCode)) {
            mPicker.setPickedArea(mPickedAreaCode);
        }
        mPicker.setLevel(mLevel);
    }

    @Override
    public void onItemSelected(WheelPicker picker, IPickerModel data, int position) {
        // do nothing.
    }

    @Override
    public BaseAnimatorSet createShowAnimation() {
        return new SlideBottomEnter();
    }

    @Override
    public BaseAnimatorSet createDismissAnimation() {
        return new SlideBottomExit();
    }

    public AreaPicker withLeftText(String text) {
        if (TextUtils.isEmpty(text)) {
            return this;
        }
        leftText = text;
        return this;
    }

    public AreaPicker withLeftTextColor(String textColor) {
        if (TextUtils.isEmpty(textColor)) {
            return this;
        }
        leftColor = Color.parseColor(textColor);
        return this;
    }

    public AreaPicker withLeftTextSize(String textSize) {
        if (TextUtils.isEmpty(textSize)) {
            return this;
        }
        leftSize = Integer.valueOf(textSize);
        return this;
    }

    public AreaPicker withRightText(String text) {
        if (TextUtils.isEmpty(text)) {
            return this;
        }
        rightText = text;
        return this;
    }

    public AreaPicker withRightTextColor(String textColor) {
        if (TextUtils.isEmpty(textColor)) {
            return this;
        }
        rightColor = Color.parseColor(textColor);
        return this;
    }

    public AreaPicker withRightTextSize(String textSize) {
        if (TextUtils.isEmpty(textSize)) {
            return this;
        }
        rightSize = Integer.valueOf(textSize);
        return this;
    }

    private String mPickedAreaCode = "";

    public AreaPicker withPickedArea(String areaCode) {
        //  已选择的城市
        mPickedAreaCode = areaCode;
        if (mPicker != null) {
            mPicker.setPickedArea(areaCode);
        }
        return this;
    }


    public AreaPicker withAreaPickedListener(OnAreaPickedListener listener) {
        mOnAreaPickedListener = listener;
        return this;
    }

    @Level
    private int mLevel = Level.AREA;

    public AreaPicker withLevel(@Level int level) {
        mLevel = level;
        return this;
    }

    public interface OnAreaPickedListener {

        void onAreaPicked(@NonNull String province, @Nullable String city, @Nullable String area, @NonNull String code);
    }


    @IntDef({Level.PROVINCE, Level.CITY, Level.AREA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Level {
        int PROVINCE = 1;
        int CITY = 2;
        int AREA = 3;
    }
}