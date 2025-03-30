package com.playtech.report;

import com.playtech.report.column.Column;
import com.playtech.report.transformer.Transformer;
import com.playtech.util.xml.adapters.TransformerAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {
    private String reportName;
    private FileFormat outputFormat;

    @XmlElementWrapper(name = "inputs")
    @XmlElement(name = "input")
    private List<Column> inputs;

    @XmlElementWrapper(name = "outputs")
    @XmlElement(name = "output")
    private List<Column> outputs;

    @XmlElementWrapper(name = "transformers")
    @XmlElement(name = "transformer")
    @XmlJavaTypeAdapter(TransformerAdapter.class)
    private List<Transformer> transformers;

    public enum FileFormat {
        CSV,
        JSONL
    }

    public String getReportName() {
        return reportName;
    }

    public FileFormat getOutputFormat() {
        return outputFormat;
    }

    public List<Column> getInputs() {
        return inputs;
    }

    public List<Column> getOutputs() {
        return outputs;
    }

    public List<Transformer> getTransformers() {
        return transformers;
    }
}

