package com.github.jackieonway.sms.entity;

/**
 * @author Jackie
 * @version \$Id: BaseRequest.java, v 0.1 2019-07-25 9:37 Jackie Exp $$
 */
public class BaseRequest {
    private String templateId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "templateId='" + templateId + '\'' +
                '}';
    }
}
