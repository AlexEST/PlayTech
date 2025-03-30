package com.playtech.util.xml.refs;

import com.playtech.report.column.Column;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.XmlValue;

public class InputRef {
    @XmlIDREF
    @XmlValue
    private Column column;

    public Column getColumn() {
        return column;
    }
}
