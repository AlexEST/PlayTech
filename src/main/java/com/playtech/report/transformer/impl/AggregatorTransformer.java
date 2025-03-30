package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlIDREF;
import java.util.*;
import java.util.stream.Collectors;

public class AggregatorTransformer implements Transformer {
    public static final String NAME = "Aggregator";

    private Column groupByColumn;
    private List<AggregateBy> aggregateColumns;

    public AggregatorTransformer(Column groupByColumn, List<AggregateBy> aggregateColumns) {
        this.groupByColumn = groupByColumn;
        this.aggregateColumns = aggregateColumns;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        String groupByKey = groupByColumn.getName();
        Map<Object, List<Map<String, Object>>> grouped = rows.stream()
                .collect(Collectors.groupingBy(row -> row.get(groupByKey)));

        List<Map<String, Object>> aggregatedRows = new ArrayList<>();
        for (Object key : grouped.keySet()) {
            List<Map<String, Object>> groupRows = grouped.get(key);
            Map<String, Object> aggregated = new HashMap<>();
            aggregated.put(groupByKey, key);
            for (AggregateBy agg : aggregateColumns) {
                String inputName = agg.getInput().getName();
                String outputName = agg.getOutput().getName();
                double sum = groupRows.stream()
                        .mapToDouble(row -> {
                            Object val = row.get(inputName);
                            return (val instanceof Number) ? ((Number) val).doubleValue() : 0;
                        }).sum();
                if (agg.getMethod() == Method.AVG && !groupRows.isEmpty()) {
                    sum = sum / groupRows.size();
                }
                aggregated.put(outputName, sum);
            }
            aggregatedRows.add(aggregated);
        }
        rows.clear();
        rows.addAll(aggregatedRows);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AggregateBy {
        @XmlIDREF
        private Column input;
        private Method method;
        @XmlIDREF
        private Column output;

        public AggregateBy() { }

        public AggregateBy(Column input, Method method, Column output) {
            this.input = input;
            this.method = method;
            this.output = output;
        }

        public Column getInput() {
            return input;
        }

        public Column getOutput() {
            return output;
        }

        public Method getMethod() {
            return method;
        }
    }

    public enum Method {
        SUM,
        AVG
    }
}
