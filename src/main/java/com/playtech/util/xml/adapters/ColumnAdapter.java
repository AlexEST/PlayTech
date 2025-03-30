
package com.playtech.util.xml.adapters;

import com.playtech.report.column.Column;
import com.playtech.util.xml.refs.InputRef;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class ColumnAdapter extends XmlAdapter<InputRef, Column> {
    @Override
    public Column unmarshal(InputRef ref) throws Exception {
        return ref.getColumn();
    }

    @Override
    public InputRef marshal(Column column) throws Exception {
        // Ignoring marshalling
        return null;
    }
}
