package xmlutil;

/**
 * @author Integral
 *
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLStringUtils {
		
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	private Document doc = null;
	private XPathFactory xpathFactory = null;
	private XPath xpath = null;

	public XMLStringUtils(String xmlString) throws Exception {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
		
			this.builder = this.factory.newDocumentBuilder();
			
			this.doc = this.builder.parse(new ByteArrayInputStream (xmlString.getBytes()));
		
			this.xpathFactory = XPathFactory.newInstance();
		
			this.xpath = this.xpathFactory.newXPath();
			
		} catch (ParserConfigurationException e) {
			throw new Exception ("Failed at creating document Builder", e);
		} catch (SAXException e) {
			throw new Exception ("Failed at Parsing XML String", e);
		} catch (IOException e) {
			throw new Exception ("Failed at Parsing XML String", e);
		}
	}
	
	public XMLStringUtils(Element element) throws Exception {
		try {
			this.factory = DocumentBuilderFactory.newInstance();
		
			this.builder = this.factory.newDocumentBuilder();
			
			this.doc = element.getOwnerDocument();
		
			this.xpathFactory = XPathFactory.newInstance();
		
			this.xpath = this.xpathFactory.newXPath();
			
		} catch (ParserConfigurationException e) {
			throw new Exception ("Failed at creating document Builder", e);
		}
	}

	public Object executeQuery (String expr, QName qName) throws Exception {
		Object result = null;
		XPathExpression xpathExpr = null;
		
		try {
			xpathExpr = xpath.compile(expr);
		} catch (XPathExpressionException e) {
			//AppLogger.error(this.className, "Exception", e);
			throw new Exception ("Failed at Compiling XPath Expression ["+expr+"]", e);
		}
		try {
			result = xpathExpr.evaluate(doc, qName);
		} catch (XPathExpressionException e) {
			//AppLogger.error(this.className, "Exception", e);
			throw new Exception ("Failed at Evaluating XPath Expression", e);
		}
		
		return result;
	}
	
	public Object executeQuery (String expr, Object obj, QName returnType) throws Exception {
		Object result = null;
		
		try {
			result = xpath.evaluate(expr, obj, returnType);
		} catch (XPathExpressionException e) {
			//AppLogger.error(this.className, "Exception", e);
			throw new Exception ("Failed at Evaluating XPath Expression", e);
		}
		return result;
	}
	
}
