package dei.uc.pt.aor.xml;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class TransformXML {
	
	//XML to String
	public static String convertXMLFileToString(String fileName) 
	{ 
		try { 
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
			InputStream inputStream = new FileInputStream(new File(fileName)); 
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream); 
			StringWriter stw = new StringWriter(); 
			Transformer serializer = TransformerFactory.newInstance().newTransformer(); 
			serializer.transform(new DOMSource(doc), new StreamResult(stw)); 
			return stw.toString(); 
		} 
		catch (Exception e) { 
            System.out.println("TransformXML Error: "+e.getMessage());
		} 
		return null; 
	}
	
	//Entity to XML string
	public static String entityToXML(Class<?> cl, Object obj) {

		try {
			JAXBContext context = JAXBContext.newInstance(cl);
			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter("ficheiro.xml"));
			Marshaller m = context.createMarshaller();
			m.marshal(obj, writer);
			return TransformXML.convertXMLFileToString("ficheiro.xml");
		} catch (JAXBException e) {
			System.out.println("\nError JAXB: "+ e.getMessage());
			return null;
		} catch (IOException ioe) {
			System.out.println("\nError I/O: "+ ioe.getMessage());
			return null;
		}

	}

	//XML String to entity
	public static Object XMLToEntity(Class<?> cl, String stringEntity) {

		try {
			JAXBContext context = JAXBContext.newInstance(cl);
			Unmarshaller um = context.createUnmarshaller();
			StringReader sreader = new StringReader(stringEntity);
			return um.unmarshal(sreader);
		} catch (JAXBException e) {
			System.out.println("\nError JAXB: "+ e.getMessage());
			return false;
		}

	}

}
