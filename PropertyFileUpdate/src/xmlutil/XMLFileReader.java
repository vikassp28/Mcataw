package xmlutil;


/**
 * @author Integral
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;




public class XMLFileReader {
	
	private Class<XMLFileReader> className = XMLFileReader.class;
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	private Document doc = null;
	private XPathFactory xpathFactory = null;
	private XPath xpath = null;
	
	public XMLFileReader(String fileName) throws Exception {
		this.factory = DocumentBuilderFactory.newInstance();
		
		this.builder = this.factory.newDocumentBuilder();
		System.out.println("Project Start");
		System.out.println("user.dir["+System.getProperty("user.dir")+"]");
		try {
		this.doc = this.builder.parse(new FileInputStream (fileName));
		} catch (Exception e) {
			if (e instanceof FileNotFoundException || e instanceof IOException) {
				this.doc = this.builder.parse(ClassLoader.getSystemResourceAsStream(fileName));
			} else {
				throw e;
			}
		}
		
		xpathFactory = XPathFactory.newInstance();
		
		xpath = xpathFactory.newXPath();
		
	}

	

	public Document getDocument() {
		return this.doc;
	}


	public void setDocument(Document doc) {
		this.doc = doc;

	}

	public Object executeQuery (String expr, QName qName) throws Exception {
		Object result = null;
		XPathExpression xpathExpr = null;
		
		try {
			xpathExpr = xpath.compile(expr);
		} catch (XPathExpressionException e) {
			System.out.println( "Exception"+e);
			throw e;
		}
		try {
			result = xpathExpr.evaluate(doc, qName);
		} catch (XPathExpressionException e) {
			System.out.println("Exception"+ e);
			throw e;
		}
		
		return result;
	}
	
	public Object executeQuery (String expr, Object obj, QName returnType) throws Exception {
		Object result = null;
		
		try {
			result = xpath.evaluate(expr, obj, returnType);
		} catch (XPathExpressionException e) {
			System.out.println( "Exception"+ e);
			throw e;
		}
		return result;
	}
}
