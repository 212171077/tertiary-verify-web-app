package com.pc.service;

import com.pc.mail.MailSender;
import com.pc.utils.AppUtility;
import net.sf.jasperreports.engine.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperService {

    /**
     * The Constant _pdfContent.
     */
    private static final String _pdfContent = "application/pdf";
    /**
     * The Constant _excelContent.
     */
    private static final String _excelContent = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static JasperService jasperService;
    @Autowired
    MailSender mailSender;

    public static synchronized JasperService instance() {
        if (jasperService == null) {
            jasperService = new JasperService();
        }
        return jasperService;
    }

    private static void convertByteArrayToServletOutputStream(byte[] bytes, String filename) throws Exception {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object resp = context.getExternalContext().getResponse();
        if (resp instanceof HttpServletResponse) {
            HttpServletResponse response = createRespObj((HttpServletResponse) resp, _pdfContent, filename);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
            context.responseComplete();
        }
    }

    public static void convertByteArrayToServletOutputStreamExcel(byte[] bytes, String filename) throws Exception {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object resp = context.getExternalContext().getResponse();
        if (resp instanceof HttpServletResponse) {
            HttpServletResponse response = createRespObj((HttpServletResponse) resp, _excelContent, filename);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
            context.responseComplete();
        }
    }

    public static void convertByteArrayToServletOutputStream(byte[] bytes, String filename, String contentType) throws Exception {
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object resp = context.getExternalContext().getResponse();
        if (resp instanceof HttpServletResponse) {
            HttpServletResponse response = createRespObj((HttpServletResponse) resp, contentType, filename);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
            out.flush();
            out.close();
            context.responseComplete();
        }
    }

    /**
     * Creates the resp obj.
     *
     * @param response    the response
     * @param contentType the content type
     * @param fileName    the file name
     * @return the http servlet response
     */
    private static HttpServletResponse createRespObj(HttpServletResponse response, String contentType, String fileName) {
        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        return response;
    }

    /**
     * Convert a file to bite array
     *
     * @param file
     * @return byte[]
     * @throws Exception the exception
     */
    public static byte[] readBytesFromFile(File file) throws Exception {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        bytesArray = new byte[(int) file.length()];

        // read file into bytes[]
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bytesArray);
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        return bytesArray;
    }

    public void addLogo(Map<String, Object> params) {
        try {
            byte[] buff = FileUtils.readFileToByteArray(new File(geWebappPath() + "/resources/img/logo2.png"));
            params.put("logo", new ImageIcon(buff).getImage());
            byte[] buff2 = FileUtils.readFileToByteArray(new File(geWebappPath() + "/resources/img/mer.jpeg"));
            params.put("backround_image", new ImageIcon(buff2).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addImage(Map<String, Object> params, String image, String paramName) {
        try {
            InputStream in = getClass().getResourceAsStream("/resources/img/" + image);
            byte[] buff = IOUtils.toByteArray(in);
            //byte[] buff = FileUtils.readFileToByteArray(new File(geWebappPath() + "/resources/img/" + image));
            params.put(paramName, new ImageIcon(buff).getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printQRCode(String title, String qrCode) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        addImage(params, "het.png", "logo");
        params.put("qrCode", new ImageIcon(AppUtility.getQRCodeImage(qrCode, 350, 350)).getImage());
        params.put("title", title);

        InputStream in = getClass().getResourceAsStream("/reports/QR_Code.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(in);
        //JasperReport jasperReport = JasperCompileManager.compileReport(getReportPath() + "/" + "QR_Code.jrxml");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
        // PrintingThe Report
        byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
        convertByteArrayToServletOutputStream(output, "QR_Code.pdf");

    }

    /**
     * Format array.
     *
     * @param arr the arr
     * @return the string
     */
    private String formatArray(Address[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].toString());
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public String getReportPath() {
        /* /C:/Users/Christoph%20Sibiya/Documents/mopanefsProject/MopaneFS/src/main/webapp */
        String path = getPath().replace("target/classes/", "src/main/webapp");
        if (path.contains("%20")) {
            path = path.replaceAll("%20", " ");
        }
        if (String.valueOf(path.charAt(0)).equalsIgnoreCase("/") || String.valueOf(path.charAt(0)).equalsIgnoreCase("\\")) {
            path = path.replaceFirst(String.valueOf(path.charAt(0)), "");
        }

        path = path + "/reports";

        return path;

    }

    public String geWebappPath() {
        /* /C:/Users/Christoph%20Sibiya/Documents/mopanefsProject/MopaneFS/src/main/webapp */
        String path = getPath().replace("target/classes/", "src/main/webapp");
        if (path.contains("%20")) {
            path = path.replaceAll("%20", " ");
        }
        if (String.valueOf(path.charAt(0)).equalsIgnoreCase("/") || String.valueOf(path.charAt(0)).equalsIgnoreCase("\\")) {
            path = path.replaceFirst(String.valueOf(path.charAt(0)), "");
        }


        return path;

    }

    public String getPath() {
        URL resource = getClass().getResource("/");
        String path = resource.getPath();
        return path;
    }

    /**
     * The Class RunReportToPdfStreamHelper.
     */
    class RunReportToPdfStreamHelper implements Work {

        /**
         * The input stream.
         */
        private InputStream inputStream;

        /**
         * The output stream.
         */
        private OutputStream outputStream;

        /**
         * The parameters.
         */
        private Map<?, ?> parameters;

        /**
         * Instantiates a new run report to pdf stream helper.
         *
         * @param inputStream  the input stream
         * @param outputStream the output stream
         * @param parameters   the parameters
         */
        public RunReportToPdfStreamHelper(InputStream inputStream, OutputStream outputStream, Map<?, ?> parameters) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.parameters = parameters;
        }

        /*
         * (non-Javadoc)
         *
         * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
         */
        @Override
        public void execute(Connection conn) throws SQLException {
            try {
                JasperRunManager.runReportToPdfStream(inputStream, outputStream, (Map<String, Object>) parameters, conn);
            } catch (JRException e) {
                throw new SQLException(e);
            }
        }

    }


}
