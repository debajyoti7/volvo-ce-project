package xml;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlData {
	
	enum Datalog {
		Product_ID,
		Serial_ID,
		Time;
	}	
	enum Program {
		Program_name,
		Version;
	}		
	enum Run {
		Operator,
		Batch_ID,
		Device_ID,
		Serial_ID,
		Time,
		DUT_Number,
		TestStationID,
		Error,
		Pass,
		ExecTime;
	}
	enum Step {
		TestID,
		SubID,
		Pass,
		Test_Error,
		Test_Type;
	}
	
	public static class Sample {		
		
		private final Map<Step, String> stepMap;
		private final int[] xValues;
		private final double[] yValues;
		
		Sample(Node node) {
			stepMap = populate(node, Step.class);
			
			NodeList children = node.getChildNodes();			
			//System.out.println("children: " + children.getLength());
			xValues = new int[children.getLength() / 2];
			yValues = new double[children.getLength() / 2];
			int index = 0;
			for (int i = 0; i < children.getLength(); i++) {
				NamedNodeMap attributes = children.item(i).getAttributes();
				if (attributes != null) {					
//					System.out.print(" " + attributes.item(0).getNodeName() + "=");
//					System.out.print(new java.math.BigDecimal(attributes.item(0).getNodeValue()).intValue());
					
//					System.out.print(", " + attributes.item(1).getNodeName() + "=");
					
					xValues[index] = new java.math.BigDecimal(attributes.item(0).getNodeValue()).intValue();
					yValues[index] = Double.parseDouble(attributes.item(1).getNodeValue());
					index++;
				}
			}
//			System.out.println("\n" + data.size() + "     " + values.length);
		}
		
		public Map<Step, String> getAttributes() { return stepMap; }
		public int[] getXValues() { return xValues; }
		public double[] getYValues() { return yValues; }

		@Override
		public String toString() {
			return stepMap.toString() + "\n\t" + Arrays.toString(yValues);
		}			
	}
	
	private Map<Datalog, String> datalog;
	private Map<Program, String> program;
	private Map<Run, String> run;	
	private List<Sample> samples = new ArrayList<>();	
	
	public XmlData(File file) throws IOException, ParserConfigurationException, SAXException {
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(file);

        // normalize text representation
        doc.getDocumentElement ().normalize ();          
        
        populate(doc.getChildNodes());
        
//        System.out.println(datalog);
//        System.out.println(program);
//        System.out.println(run);
//        for (Sample sample : samples)
//        	System.out.println(sample.toString());
	}
	
	public Map<Datalog, String> getDatalogAttributes() { return datalog; }
	public Map<Program, String> getProgramAttributes() { return program; }
	public Map<Run, String> getRunAttributes() { return run; }
	public List<Sample> getSamples() { return samples; } 
	
	private void populate(NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			
			if (!node.getNodeName().equals("#text")) {
				
				if (node.getNodeName().equals("Datalog"))
					datalog = populate(node, Datalog.class);
				else if (node.getNodeName().equals("Program"))
					program = populate(node, Program.class);				
				else if (node.getNodeName().equals("Run"))
					run = populate(node, Run.class);
				else if (node.getNodeName().equals("Step")) {
					samples.add(new Sample(node));
				}
			}			
		}
		for (int k = 0; k < nodes.getLength(); k++)
			if (!nodes.item(k).getNodeName().equals("Step"))
				populate(nodes.item(k).getChildNodes());		
	}
	
	private static <T extends Enum<T>> Map<T, String> populate(Node node, Class<T> clazz) {
		EnumMap<T, String> result = new EnumMap<>(clazz);		
		NamedNodeMap nnm = node.getAttributes();
		for (T value : clazz.getEnumConstants()) {
			result.put(value, nnm.getNamedItem(value.name()).getNodeValue()) ; 			
		}
		return Collections.unmodifiableMap(result);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		XmlData x = new XmlData(new File("test.xml"));
		XmlData x = new XmlData(new File("full2.xml"));
		
		for (Sample sample : x.samples) {
			System.out.println(sample.xValues.length);			
		}
	}
}
