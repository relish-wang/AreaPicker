/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

/**
 * 调试模式方法接口
 */
public interface IDebug {
    /**
     * 设置调试模式
     * 开启调试模式有可能在一定程度上降低代码执行效率，请务必在正式发布时关闭调试模式
     *
     * @param isDebug 是否为调试模式
     */
    void setDebug(boolean isDebug);
}