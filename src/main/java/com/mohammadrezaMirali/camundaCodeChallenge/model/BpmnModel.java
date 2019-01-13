package com.mohammadrezaMirali.camundaCodeChallenge.model;

import java.io.Serializable;

/**
 * Created by mmirali on 13/01/2019.
 */
public class BpmnModel implements Serializable{
    private String id;
    private String bpmn20Xml;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBpmn20Xml() {
        return bpmn20Xml;
    }

    public void setBpmn20Xml(String bpmn20Xml) {
        this.bpmn20Xml = bpmn20Xml;
    }
}
