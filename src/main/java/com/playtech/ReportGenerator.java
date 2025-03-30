package com.playtech;

import com.playtech.report.Report;
import com.playtech.report.column.Column;
import com.playtech.report.column.Column.DataType;
import com.playtech.report.transformer.Transformer;
import com.playtech.util.xml.XmlParser;
import jakarta.xml.bind.JAXBException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

public class ReportGenerator {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Application should have 3 paths as arguments: csv file path, xml file path and output directory");
            System.exit(1);
        }
        String csvDataFilePath = args[0];
        String reportXmlFilePath = args[1];
        String outputDirectoryPath = args[2];

        Report report;
        try {
            report = XmlParser.parseReport(reportXmlFilePath);
        } catch (JAXBException e) {
            System.err.println("Parsing of the xml file failed:");
            throw new RuntimeException(e);
        }

        try {
            List<Map<String, Object>> rows = parseCsvFile(csvDataFilePath, report.getInputs());

            List<Transformer> transformers = report.getTransformers();
            for (Transformer transformer : transformers) {
                transformer.transform(report, rows);
            }

            String outputFileName = Paths.get(outputDirectoryPath, report.getReportName() + ".jsonl").toString();
            writeJsonlOutput(rows, report.getOutputs(), outputFileName);
            System.out.println("Report generated: " + outputFileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static List<Map<String, Object>> parseCsvFile(String csvFilePath, List<Column> inputs) throws Exception {
        List<Map<String, Object>> records = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
        if (lines.isEmpty()) {
            return records;
        }
        String headerLine = lines.get(0);
        String[] headers = headerLine.split(",");
        Map<String, Column> inputMap = new HashMap<>();
        for (Column col : inputs) {
            inputMap.put(col.getName(), col);
        }
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] cols = line.split(",");
            if (cols.length != headers.length) {
                continue;
            }
            Map<String, Object> record = new HashMap<>();
            boolean valid = true;
            for (int j = 0; j < headers.length; j++) {
                String header = headers[j];
                String value = cols[j];
                Column col = inputMap.get(header);
                if (col == null) {
                    continue;
                }
                try {
                    record.put(header, convertValue(value, col.getType()));
                } catch (Exception ex) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                records.add(record);
            }
        }
        return records;
    }

    private static Object convertValue(String value, DataType type) {
        switch (type) {
            case STRING:
                return value;
            case INTEGER:
                return Integer.parseInt(value);
            case DOUBLE:
                return Double.parseDouble(value);
            case DATE:
                return LocalDate.parse(value);
            case DATETIME:
                return OffsetDateTime.parse(value);
            default:
                return value;
        }
    }

    private static void writeJsonlOutput(List<Map<String, Object>> rows, List<Column> outputs, String outputFilePath) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            for (Map<String, Object> row : rows) {
                Map<String, Object> outRow = new LinkedHashMap<>();
                for (Column col : outputs) {
                    outRow.put(col.getName(), row.get(col.getName()));
                }
                writer.println(toJson(outRow));
            }
        }
    }

    private static String toJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder("{");
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof String) {
                sb.append("\"").append(value).append("\"");
            } else {
                sb.append(value);
            }
            if (it.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
