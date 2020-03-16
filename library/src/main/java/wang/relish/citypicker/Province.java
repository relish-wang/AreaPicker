/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;


import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Province implements Serializable, IPickerModel {

    private static final long serialVersionUID = -7369995457956799280L;
    private String code;
    private String name;
    private List<City> city;

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

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
