package environment_variables;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.InputStream;

public class EnvVar {
    private String outputFileName;

    public EnvVar(){
//        try {
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            InputStream inputFile = classLoader.getResourceAsStream("environment_variables/config.xml");
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(inputFile);
//            doc.getDocumentElement().normalize();
//
//            NodeList nList = doc.getElementsByTagName("property");
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//                Node nNode = nList.item(temp);
//                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) nNode;
//                    String name = eElement.getAttribute("name");
//                    String value = eElement.getAttribute("value");
//                    System.setProperty(name, value);
//                }
//            }
//        }
//        catch (Exception e){
//            System.out.println("Config.xml file not found, please try again!");
//        }

        this.outputFileName = "GW_Basic_To_PCL_Output.c";
    }

    public String getOutputFileName() {
        // return System.getProperty("output_file_name");
        return this.outputFileName;
    }
}
