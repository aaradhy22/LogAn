// src/main/java/com/logancmd/ConfigParser.java
package com.logancmd;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class ConfigParser {
    public static String logFilePath;
    public static String dbUrl;
    public static String dbUser;
    public static String dbPassword;

    public static void loadConfig(String configFileName) {
        try (InputStream inputStream = ConfigParser.class.getClassLoader().getResourceAsStream(configFileName)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            logFilePath = doc.getElementsByTagName("path").item(0).getTextContent();
            dbUrl = doc.getElementsByTagName("url").item(0).getTextContent();
            dbUser = doc.getElementsByTagName("user").item(0).getTextContent();
            dbPassword = doc.getElementsByTagName("password").item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or set default values
            System.exit(1); // Exit if config fails to load
        }
    }
}