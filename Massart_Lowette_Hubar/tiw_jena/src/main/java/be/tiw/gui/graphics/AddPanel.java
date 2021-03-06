package be.tiw.gui.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;

import be.tiw.gui.autocomplete.Autocomplete;

public class AddPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private Button valid;
	private JTextField name, cost, startDate, endDate, location;
	private JPanel centerPanel;
	private JComboBox<String> subclassesCB;
	private JCheckBox needEquipment;
	
	private ArrayList<String> subclassesNames = new ArrayList<String>();	
	private ArrayList<String> locationNames = new ArrayList<String>(); 
	
	private Model ontology;
	
	String IRI = "http://www.semanticweb.org/julienhubar/ontologies/2020/2/TourismeEnWallonie#";
	
	public AddPanel(Model ontology)
	{
		this.ontology=ontology;
		
		StmtIterator iter = this.ontology.listStatements();
		
		while (iter.hasNext())
		{
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();
			
			if (predicate.toString().contains("subClassOf"))
			{
				if (object instanceof Resource && object.toString().contains("Event"))
				{
					this.subclassesNames.add(subject.toString().split("#")[1]);
				}
			}
			if (predicate.toString().contains("type"))
			{
				if (object instanceof Resource && object.toString().contains("Town"))
				{
					locationNames.add(subject.toString().split("#")[1]);
				}
			}
		}
		Collections.sort(this.subclassesNames);
		
		//Init fields
		this.initCenterPanel();
		
		//Sets areas
		this.setLayout(new BorderLayout());
		this.add("Center", centerPanel);
		this.setVisible(true);
	}
	private void initCenterPanel()
	{		
		name = new JTextField();
		name.setPreferredSize(new Dimension(200,30));
		name.setEditable(true);
		name.setBackground(Color.WHITE);
		JLabel nameLabel = new JLabel("Name : ");
		nameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		subclassesCB = new JComboBox<String>(this.subclassesNames.toArray(new String[0]));
		subclassesCB.setPreferredSize(new Dimension(200, 30));
		subclassesCB.addActionListener(this);
		subclassesCB.setBackground(Color.WHITE);
		JLabel scLabel = new JLabel("Class : ");
		scLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		location = new JTextField();
		location.setPreferredSize(new Dimension(200,30));
		location.setEditable(true);
		location.setBackground(Color.WHITE);
		//activate autocomplete
		location.setFocusTraversalKeysEnabled(false);
		Autocomplete auto = new Autocomplete(location, this.locationNames);
		location.getDocument().addDocumentListener(auto);
		location.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "commit");
		location.getActionMap().put("commit", auto.new CommitAction());
		JLabel locLabel = new JLabel("Location : ");
		locLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		needEquipment = new JCheckBox("Need equipment");
		JLabel equipmentLabel = new JLabel("Equipment : ");
		equipmentLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		cost = new JTextField();
		cost.setPreferredSize(new Dimension(200,30));
		cost.setEditable(true);
		cost.setBackground(Color.WHITE);
		JLabel costLabel = new JLabel("Cost : ");
		costLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		startDate = new JTextField();
		startDate.setPreferredSize(new Dimension(200,30));
		startDate.setEditable(true);
		startDate.setBackground(Color.WHITE);
		JLabel sdLabel = new JLabel("Start date : ");
		sdLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		endDate = new JTextField();
		endDate.setPreferredSize(new Dimension(200,30));
		endDate.setEditable(true);
		endDate.setBackground(Color.WHITE);
		JLabel edLabel = new JLabel("End date : ");
		edLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		
		valid = new Button("Submit");
		valid.addActionListener(this);
		
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		centerPanel.add(nameLabel, gbc);
		gbc.gridx=1;
		gbc.gridy=0;
		centerPanel.add(scLabel, gbc);
		gbc.gridx=2;
		gbc.gridy=0;
		centerPanel.add(locLabel, gbc);
		gbc.gridx=3;
		gbc.gridy=0;
		centerPanel.add(costLabel, gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		centerPanel.add(name, gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		centerPanel.add(subclassesCB, gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		centerPanel.add(location, gbc);
		gbc.gridx=3;
		gbc.gridy=1;
		centerPanel.add(cost, gbc);
		
		gbc.gridx=0;
		gbc.gridy=2;
		centerPanel.add(sdLabel, gbc);
		gbc.gridx=1;
		gbc.gridy=2;
		centerPanel.add(edLabel, gbc);
		gbc.gridx=2;
		gbc.gridy=2;
		centerPanel.add(equipmentLabel, gbc);
		
		gbc.gridx=0;
		gbc.gridy=3;
		centerPanel.add(startDate, gbc);
		gbc.gridx=1;
		gbc.gridy=3;
		centerPanel.add(endDate, gbc);
		gbc.gridx=2;
		gbc.gridy=3;
		centerPanel.add(needEquipment, gbc);
		
		gbc.insets=new Insets(50,10,10,10);
		gbc.gridwidth=2;
		gbc.gridx=1;
		gbc.gridy=4;
		centerPanel.add(valid, gbc);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==valid)
		{
			String name = this.name.getText();
			String subclass = this.subclassesNames.get(this.subclassesCB.getSelectedIndex());
			String location = this.location.getText();
			String cost = this.cost.getText();
			String startDate = this.startDate.getText();
			String endDate = this.endDate.getText();
			boolean needEquipment = this.needEquipment.isSelected();
			
			
			System.out.println("Valid");
			System.out.println(name);
			System.out.println(subclass.toString());
			System.out.println(location);
			System.out.println(cost);
			System.out.println(startDate);
			System.out.println(endDate);
			System.out.println(needEquipment);
			
			Resource eventType = this.ontology.getResource(IRI + subclass);
			Resource eventLocation = this.ontology.getResource(IRI + location);
			Literal eventStartDate = this.ontology.createTypedLiteral("2020-11-27T11:00:00+01:00", XSDDatatype.XSDdateTime);
			Literal eventEndDate = this.ontology.createTypedLiteral("2021-01-04T20:00:00+01:00", XSDDatatype.XSDdateTime);
			Literal eventCost = this.ontology.createTypedLiteral(cost, XSDDatatype.XSDdouble);
			Literal eventNE = this.ontology.createTypedLiteral(needEquipment, XSDDatatype.XSDboolean);
			

			Resource newEvent = this.ontology.createResource(IRI + name);

			newEvent.addProperty(RDF.type, eventType);
			newEvent.addProperty(this.ontology.getProperty(IRI + "isLocated"), eventLocation);
			newEvent.addProperty(this.ontology.getProperty(IRI + "startDate"), eventStartDate);
			newEvent.addProperty(this.ontology.getProperty(IRI + "endDate"), eventEndDate);
			newEvent.addProperty(this.ontology.getProperty(IRI + "cost"), eventCost);
			newEvent.addProperty(this.ontology.getProperty(IRI + "needEquipment"), eventNE);
			
			return;
		}
	}

}
