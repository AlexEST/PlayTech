package com.playtech.report.transformer.impl;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import java.util.List;
import java.util.Map;

public class MathOperationTransformer implements Transformer {
    public final static String NAME = "MathOperation";

    private List<Column> inputs;
    private MathOperation operation;
    private Column output;

    public MathOperationTransformer(List<Column> inputs, MathOperation operation, Column output) {
        this.inputs = inputs;
        this.operation = operation;
        this.output = output;
    }

    @Override
    public void transform(Report report, List<Map<String, Object>> rows) {
        String outputName = output.getName();
        for (Map<String, Object> row : rows) {
            double result = 0;
            if (!inputs.isEmpty()) {
                Object firstVal = row.get(inputs.get(0).getName());
                result = (firstVal instanceof Number) ? ((Number) firstVal).doubleValue() : 0;
                for (int i = 1; i < inputs.size(); i++) {
                    Object val = row.get(inputs.get(i).getName());
                    double d = (val instanceof Number) ? ((Number) val).doubleValue() : 0;
                    if (operation == MathOperation.ADD) {
                        result += d;
                    } else if (operation == MathOperation.SUBTRACT) {
                        result -= d;
                    }
                }
            }
            row.put(outputName, result);
        }
    }

    public enum MathOperation {
        ADD,
        SUBTRACT,
    }
}
