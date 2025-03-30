package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import java.util.List;
import java.util.Map;

public class StringFormatterTransformer implements Transformer {
    public final static String NAME = "StringFormatter";

    private List<Column> inputs;
    private String format;
    private Column output;

    public StringFormatterTransformer(List<Column> inputs, String format, Column output) {
        this.inputs = inputs;
        this.format = format;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        String outputName = output.getName();
        for (Map<String, Object> row : rows) {
            Object[] values = inputs.stream().map(col -> row.get(col.getName())).toArray();
            row.put(outputName, String.format(format, values));
        }
    }
}

