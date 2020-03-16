/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

/**
 */
public class PickerModel implements IPickerModel {

    private String code;
    private String name;

    public PickerModel() {

    }

    public PickerModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PickerModel)) return false;
        PickerModel that = (PickerModel) o;
        return getCode() != null ? getCode().equals(that.getCode()) : that.getCode() == null;
    }

    @Override
    public int hashCode() {
        return getCode() != null ? getCode().hashCode() : 0;
    }
}