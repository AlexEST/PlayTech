package com.playtech.util.xml.helpers;

import com.playtech.report.column.Column;
import com.playtech.report.transformer.impl.AggregatorTransformer;
import com.playtech.report.transformer.impl.MathOperationTransformer;
import com.playtech.report.transformer.impl.OrderingTransformer;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.playtech.util.xml.adapters.ColumnAdapter;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Parameters {
    @XmlElementWrapper(name = "inputs")
    @XmlElement(name = "input")
    @XmlJavaTypeAdapter(ColumnAdapter.class)
    private List<Column> inputs;

    @XmlIDREF
    @XmlElement(name = "input")
    private Column input;

    @XmlIDREF
    private Column output;

    private String format;
    private OrderingTransformer.Order order;
    private MathOperationTransformer.MathOperation operation;

    @XmlIDREF
    private Column groupBy;

    @XmlElementWrapper(name = "aggregateBys")
    @XmlElement(name = "aggregateBy")
    private List<AggregatorTransformer.AggregateBy> aggregateBys;

    public List<Column> getInputs() {
        return inputs;
    }

    public Column getInput() {
        return input;
    }

    public Column getOutput() {
        return output;
    }

    public String getFormat() {
        return format;
    }

    public OrderingTransformer.Order getOrder() {
        return order;
    }

    public MathOperationTransformer.MathOperation getOperation() {
        return operation;
    }

    public Column getGroupBy() {
        return groupBy;
    }

    public List<AggregatorTransformer.AggregateBy> getAggregateBys() {
        return aggregateBys;
    }
}


