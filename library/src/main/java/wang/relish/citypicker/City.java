/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

import java.io.Serializable;
import java.util.List;

/**
 */
public class City implements Serializable, IPickerModel {

    private static final long serialVersionUID = -766853961609144683L;
    private String code;
    private String name;
    private List<Area> area;

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }
}
