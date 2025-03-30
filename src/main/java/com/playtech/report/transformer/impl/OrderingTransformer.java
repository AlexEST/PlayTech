package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import java.util.*;
import java.util.stream.Collectors;

public class OrderingTransformer implements Transformer {
    public final static String NAME = "Ordering";

    private Column input;
    private Order order;

    public OrderingTransformer(Column input, Order order) {
        this.input = input;
        this.order = order;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        String inputName = input.getName();
        Comparator<Map<String, Object>> comparator = Comparator.comparing(
                row -> (Comparable) row.get(inputName)
        );
        if (order == Order.DESC) {
            comparator = comparator.reversed();
        }
        List<Map<String, Object>> sorted = rows.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        rows.clear();
        rows.addAll(sorted);
    }

    public enum Order {
        ASC,
        DESC
    }
}

