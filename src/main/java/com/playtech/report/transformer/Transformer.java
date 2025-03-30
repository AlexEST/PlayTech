package com.playtech.report.transformer;

import com.playtech.report.Report;

import java.util.List;
import java.util.Map;

public interface Transformer {
    void transform(Report report, List<Map<String, Object>> rows);
}
