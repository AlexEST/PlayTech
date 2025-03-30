package com.playtech.util.xml.helpers;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "transformer")
public class TransformerWrapper {
    private String name;
    private Parameters parameters;

    public String getName() {
        return name;
    }

    public Parameters getParameters() {
        return parameters;
    }
}

