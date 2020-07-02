package com.github.jackieonway.sms.entity;


/**
 * 云之讯短信平台接口参数
 *
 * @author bigbearLoveTingting
 * @version \$id: UcpassSmsRequest.java v 0.1 2019-7-15 14:27 bigbearLoveTingting Exp $$
 * @see <a href="http://docs.ucpaas.com/doku.php?id=%E7%9F%AD%E4%BF%A1:sendsms"></a>
 */
public class UcpassSmsRequest extends BaseRequest {

    /**
     * 模板中的替换参数，如该模板不存在参数则无需传该参数或者参数为空，
     * 如果有多个参数则需要写在同一个字符串中，以英文逗号分隔 （如：“a,b,c”），
     * 参数中不能含有特殊符号“【】”和“,”
     */
    private String param;

    /**
     * 接收的手机号，多个手机号码以英文逗号分隔 （如：“18011984299,18011801180”），
     * 最多单次支持100个号码，
     * 如果号码重复，则只发送一条，暂仅支持国内号码
     */
    private String mobile;

    /**
     * 用户透传ID，随状态报告返回
     */
    private String uid;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
