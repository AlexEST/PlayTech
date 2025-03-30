package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DateTimeFormatterTransformer implements Transformer {
    public static final String NAME = "DateTimeFormatter";

    private Column input;
    private String format;
    private Column output;

    public DateTimeFormatterTransformer(Column input, String format, Column output) {
        this.input = input;
        this.format = format;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String inputName = input.getName();
        String outputName = output.getName();
        for (Map<String, Object> row : rows) {
            Object dateObj = row.get(inputName);
            if (dateObj instanceof OffsetDateTime) {
                row.put(outputName, ((OffsetDateTime) dateObj).format(formatter));
            } else if (dateObj instanceof LocalDate) {
                row.put(outputName, ((LocalDate) dateObj).format(formatter));
            }
        }
    }
}
