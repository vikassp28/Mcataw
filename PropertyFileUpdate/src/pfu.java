import java.io.*;
import org.apache.commons.codec.binary.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


import xmlutil.XMLFileReader;


public class pfu {
	private static XMLFileReader xmlFileReader = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		
			System.out.println("Project Start");	
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String hostname = bufferRead.readLine();
			System.out.println("Please enter Protocol -");
			String protocol = bufferRead.readLine();
			System.out.println("Please enter SDM User -");
			String sdm_user = bufferRead.readLine();
			System.out.println("Please enter SDM User Password -");
			String sdm_password = bufferRead.readLine();
			
			System.out.println("user.dir["+System.getProperty("user.dir")+"]");
			
			String filepath = System.getProperty("user.dir")+"\\Configuration.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			XPathFactory xpf = XPathFactory.newInstance();
			XPath xpath = xpf.newXPath();
			    
			/*NodeList  myNodeList = (NodeList) xpath.compile("//Configuration/WebServiceClient/ServiceDeskUserId/text()").evaluate(doc, XPathConstants.NODESET);
			System.out.println(myNodeList.item(0).getNodeValue());
			myNodeList.item(0).setNodeValue("Hi mom!");*/
			
		    Node node = (Node) xpath.evaluate("//Configuration/WebServiceClient/ServiceDeskUserId", doc, XPathConstants.NODE);
		    node.setTextContent(sdm_user);
		    node = (Node) xpath.evaluate("//Configuration/WebServiceClient/ServiceDeskPassword", doc, XPathConstants.NODE);
		    node.setTextContent(new Base64().encodeAsString(sdm_password.getBytes()).toString());
		    node = (Node) xpath.evaluate("//Configuration/WebServiceClient/ServiceEndPoint", doc, XPathConstants.NODE);
		    node.setTextContent(protocol+"://"+hostname+"/axis/services/USD_R11_WebService?wsdl");
		    node = (Node) xpath.evaluate("//Configuration/Options/ServiceDeskBaseUrl", doc, XPathConstants.NODE);
		    node.setTextContent(protocol+"://"+hostname+"/CAisd/pdmweb.exe");
		  
		    Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.transform(new DOMSource(doc), new StreamResult(new File(filepath)));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
}
	


