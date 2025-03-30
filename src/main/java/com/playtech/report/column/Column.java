package com.playtech.report.column;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Column {
    @XmlID
    private String name;
    private DataType type;

    public enum DataType {
        STRING,
        INTEGER,
        DOUBLE,
        DATE,
        DATETIME,
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }
}
