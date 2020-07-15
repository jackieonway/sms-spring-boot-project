package com.github.jackieonway.sms.submail.model;

/**
 * subMail properties
 *
 * @author bing_huang
 * @date 2020/07/08 8:53
 * @see <a href="https://www.mysubmail.com/chs/documents/developer/YPWD84"></a>
 * @since V1.0
 */
public class SubMailProperties {
    /**
     * appid
     */
    private String appid;
    /**
     * appkey
     */
    private String appKey;

    public SubMailProperties() {
    }

    public SubMailProperties(String appid, String appKey) {
        this.appid = appid;
        this.appKey = appKey;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
