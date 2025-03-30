package com.playtech.util.xml.adapters;

import com.playtech.report.transformer.Transformer;
import com.playtech.report.transformer.impl.AggregatorTransformer;
import com.playtech.report.transformer.impl.DateTimeFormatterTransformer;
import com.playtech.report.transformer.impl.MathOperationTransformer;
import com.playtech.report.transformer.impl.OrderingTransformer;
import com.playtech.report.transformer.impl.StringFormatterTransformer;
import com.playtech.util.xml.helpers.TransformerWrapper;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class TransformerAdapter extends XmlAdapter<TransformerWrapper, Transformer> {
    @Override
    public Transformer unmarshal(TransformerWrapper wrapper) {
        return switch (wrapper.getName()) {
            case StringFormatterTransformer.NAME ->
                    new StringFormatterTransformer(wrapper.getParameters().getInputs(),
                            wrapper.getParameters().getFormat(),
                            wrapper.getParameters().getOutput());
            case OrderingTransformer.NAME ->
                    new OrderingTransformer(wrapper.getParameters().getInput(),
                            wrapper.getParameters().getOrder());
            case MathOperationTransformer.NAME ->
                    new MathOperationTransformer(wrapper.getParameters().getInputs(),
                            wrapper.getParameters().getOperation(),
                            wrapper.getParameters().getOutput());
            case DateTimeFormatterTransformer.NAME ->
                    new DateTimeFormatterTransformer(wrapper.getParameters().getInput(),
                            wrapper.getParameters().getFormat(),
                            wrapper.getParameters().getOutput());
            case AggregatorTransformer.NAME ->
                    new AggregatorTransformer(wrapper.getParameters().getGroupBy(),
                            wrapper.getParameters().getAggregateBys());
            default -> throw new IllegalArgumentException("Unknown transformer type: " + wrapper.getName());
        };
    }

    @Override
    public TransformerWrapper marshal(Transformer transformer) {
        // Ignoring marshalling
        return null;
    }
}


