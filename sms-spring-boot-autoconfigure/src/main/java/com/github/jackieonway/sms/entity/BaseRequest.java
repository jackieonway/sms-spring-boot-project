package com.github.jackieonway.sms.entity;

/**
 * @author Jackie
 * @version \$Id: BaseRequest.java, v 0.1 2019-07-25 9:37 Jackie Exp $$
 */
public class BaseRequest {
    private String templateCode;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "templateCode='" + templateCode + '\'' +
                '}';
    }
}
