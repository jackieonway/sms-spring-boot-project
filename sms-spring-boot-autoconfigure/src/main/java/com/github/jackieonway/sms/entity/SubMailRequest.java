package com.github.jackieonway.sms.entity;

import com.github.jackieonway.sms.submail.utils.SignTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * 赛邮云通信 sms
 *
 * @author bing_huang
 * @date 2020/07/09 9:14
 * @since V1.0
 */
public class SubMailRequest extends BaseRequest {
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 短信正文
     */
    private String content;

    /**
     * API 授权模式
     */
    private SignTypeEnum signType = SignTypeEnum.NORMAL;
    /**
     * 此参数用于标记一次 API 请求
     */
    private String tag;
    /**
     * 批量
     */
    private List<Multi> multi;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SignTypeEnum getSignType() {
        return signType;
    }

    public void setSignType(SignTypeEnum signType) {
        this.signType = signType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Multi> getMulti() {
        return multi;
    }

    public void setMulti(List<Multi> multi) {
        this.multi = multi;
    }

    /**
     * 用于批量
     */
    public static class Multi {
        /**
         * 手机号
         */
        private String to;
        /**
         * 文本变量 json格式
         */
        private Map<String, Object> vars;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Map<String, Object> getVars() {
            return vars;
        }

        public void setVars(Map<String, Object> vars) {
            this.vars = vars;
        }
    }
}
