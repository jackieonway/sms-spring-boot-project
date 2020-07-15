package com.github.jackieonway.sms.submail.model;

import java.util.Map;

/**
 * 批量发送multi参数
 *
 * @author bing_huang
 * @see <a href="https://www.mysubmail.com/chs/documents/developer/fsSgK1"></a>
 * @see <a href="https://www.mysubmail.com/chs/documents/developer/vNvBE"></a>
 * @since V1.0 2020/07/08 9:11
 */
public class MultiParams {
    private String to;
    private Map<String, Object> vars;

    public MultiParams() {
    }

    public MultiParams(String to, Map<String, Object> vars) {
        this.to = to;
        this.vars = vars;
    }

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
