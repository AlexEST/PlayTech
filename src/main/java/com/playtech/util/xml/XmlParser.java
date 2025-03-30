package com.playtech.util.xml;

import com.playtech.report.Report;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XmlParser {
    public static Report parseReport(String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Report) unmarshaller.unmarshal(new File(filePath));
    }
}
