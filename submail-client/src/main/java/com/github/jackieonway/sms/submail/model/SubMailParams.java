package com.github.jackieonway.sms.submail.model;

import com.github.jackieonway.sms.submail.utils.SignTypeEnum;

/**
 * request params
 *
 * @author bing_huang
 * @see <a href="https://www.mysubmail.com/chs/documents/developer/YPWD84">请求参数</a>
 * @since V1.0 2020/07/08 9:02
 */
public class SubMailParams {
    /**
     * 此参数用于标记一次 API 请求
     */
    private String tag;
    /**
     * API 授权模式
     */
    private String signType = SignTypeEnum.NORMAL.getValue();

    public SubMailParams() {
    }

    public SubMailParams(String tag, String signType) {
        this.tag = tag;
        this.signType = signType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
