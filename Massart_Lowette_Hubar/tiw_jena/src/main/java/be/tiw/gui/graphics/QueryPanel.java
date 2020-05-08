package be.tiw.gui.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;

public class QueryPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private Button exec, clear;
	private JPanel centerPanel;
	private JTextArea textarea;
	private Model ontology;
	
	public QueryPanel(Model ontology)
	{
		this.ontology=ontology;
		
		//Init panel
		this.initCenterPanel();
		
		//Set areas
		this.setLayout(new BorderLayout());
		this.add("Center", centerPanel);
		this.setVisible(true);
	}
	private void initCenterPanel()
	{
		// Set textarea
		textarea = new JTextArea(30, 100);
		JScrollPane scrollPane = new JScrollPane(textarea);
		textarea.setEditable(true);
		
		// Execute button
		exec = new Button("Execute");
		exec.addActionListener(this);
		
		// Clear button
		clear = new Button("Clear");
		clear.addActionListener(this);
		
		// Layout the textarea and the execute button
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
		gbc.gridx=0;
		gbc.gridy=0;
		centerPanel.add(scrollPane, gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		centerPanel.add(exec, gbc);
	}
	private String queryModel(Model model, String queryString)
	{
		// Creates the query object
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
		
		// Executes the query
		QueryExecution exec = QueryExecutionFactory.create(query, model);
		ResultSet results = exec.execSelect();
		
		// Format the results
		String resultsString = ResultSetFormatter.asText(results, query);
		
		// Closes the QueryExecution
		exec.close();
		
		return resultsString;
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==exec)
		{
			// Gets query
			String queryString = textarea.getText();
			
			// Remove execute button
			centerPanel.remove(exec);
			
			// Add clear button
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx=0;
			gbc.gridy=1;
			centerPanel.add(clear, gbc);
			this.centerPanel.updateUI();
			
			// Queries and displays the results
			textarea.setText(queryModel(ontology, queryString));
			
			return;
		}
		if(e.getSource() == clear)
		{
			// Clears the textarea
			textarea.setText("");
			
			// Removes clear button
			centerPanel.remove(clear);
			
			// Add execute button
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx=0;
			gbc.gridy=1;
			centerPanel.add(exec, gbc);
			this.centerPanel.updateUI();
		}
	}

}
