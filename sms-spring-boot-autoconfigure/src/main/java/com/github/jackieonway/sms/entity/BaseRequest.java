package com.github.jackieonway.sms.entity;

import java.io.Serializable;

/**
 * @author Jackie
 * @version \$Id: BaseRequest.java, v 0.1 2019-07-25 9:37 Jackie Exp $$
 */
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = -3202828912350900963L;
    private String templateId;

    private Limit limit = new Limit();

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "templateId='" + templateId + '\'' +
                ", limit=" + limit +
                '}';
    }
}
