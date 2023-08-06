package com.javatechie.report.service;

import com.javatechie.report.entity.Employee;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {



    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "/Users/tutv/Downloads/spring-jasper-report-master/";
        List<Employee> employees = new ArrayList<>();
        /*
            private int id;
    private String name;
    private String designation;
    private double salary;
    private String doj;
         */
        employees.add(new Employee(1,"A", "YES", 1000, "2023-08-01"));
        employees.add(new Employee(2,"B", "YES", 2000, "2023-08-01"));
        employees.add(new Employee(3,"C", "YES", 3000, "2023-08-01"));

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "employees.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "employees.pdf");
        }
        if (reportFormat.equalsIgnoreCase("csv")) {

            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("result.xlsx"));

            SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
            reportConfig.setSheetNames(new String[] { "Employee" });

            exporter.setConfiguration(reportConfig);

            try {
                exporter.exportReport();
            } catch (JRException ex) {
              //  Logger.getLogger(SimpleReportFiller.class.getName()).log(Level.SEVERE, null, ex);
            }

//            JRXlsxExporter exporter = new JRXlsxExporter();
//            SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
//            reportConfigXLS.setSheetNames(new String[] { "sheet1" });
//            exporter.setConfiguration(reportConfigXLS);
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//
//            try (ByteArrayOutputStream excelStream = new ByteArrayOutputStream();
//                 FileOutputStream fos = new FileOutputStream("result.xlsx")) {
//                OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(excelStream);
//                exporter.setExporterOutput(exporterOutput);
//                exporter.exportReport();
//
//                excelStream.writeTo(fos);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }

        return "report generated in path : " + path;
    }
}
