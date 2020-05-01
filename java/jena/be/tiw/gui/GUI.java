package be.tiw.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

//import be.me.pae.database.CourseDatabase;
//import be.me.pae.graphics.AddPanel;
//import be.me.pae.graphics.GraphPanel;
//import be.me.pae.graphics.Legend;
import be.tiw.gui.graphics.Button;
//import be.me.pae.graphics.SearchDialog;
import be.tiw.gui.file.FileOpener;
//import be.me.pae.graph.Graph;
//import be.me.pae.graph.Node;

public class GUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar;
	private JMenu file, about;
	private JMenuItem exit, ab, open, help;
	private JPanel centerPanel;
	private Button add, request, back, openFile;
//	private GraphPanel gp;
//	private JScrollPane scroll;
	
	private Model ontologyModel;

	public GUI(String name)
	{
		super(name);
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				close();
			}
		});
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}

		this.initMenu();
		
		ontologyModel = ModelFactory.createMemModelMaker().createModel("Model");
		back=new Button("Retour");
		back.addActionListener(this);
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new GridBagLayout());
		
		this.initCenterPanel();
		
		//Set areas
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.setVisible(true);    
		this.setResizable(true);
	}
	private void initMenu()
	{
		menuBar = new JMenuBar();
		file = new JMenu("File");
		about = new JMenu("?");
		exit = new JMenuItem("Exit");
		ab = new JMenuItem("About");
		open = new JMenuItem("Open");
		help = new JMenuItem("Help");
		
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		
		exit.addActionListener(this);
		exit.setMnemonic('Q');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		
		ab.addActionListener(this);
		
		help.addActionListener(this);
		help.setAccelerator(KeyStroke.getKeyStroke("F1"));
		
		file.add(open);
		file.add(exit);
		file.setMnemonic('F');
		
		about.add(help);
		about.add(ab);
		about.setMnemonic('?');
		
		menuBar.add(file);
		menuBar.add(about);	
		this.setJMenuBar(menuBar);
	}
//	private void setGraphMenu()
//	{	
//		menuBar.add(display, 1);
//	}
	private void initCenterPanel()
	{		
		add = new Button("Add");
		add.addActionListener(this);
		JLabel modeSLabel = new JLabel("Add an instance");
		request = new Button("Request");
		request.addActionListener(this);
		JLabel modePLabel = new JLabel("Make request on the ontology");
		
		openFile = new Button("Select an ontology");
		openFile.addActionListener(this);
		
		this.centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets=new Insets(10,10,10,10);
	    gbc.gridwidth=GridBagConstraints.REMAINDER;
	    gbc.gridx=0;
		gbc.gridy=0;
	    centerPanel.add(openFile, gbc);
	    gbc.fill=GridBagConstraints.HORIZONTAL;
	    gbc.gridwidth=GridBagConstraints.RELATIVE;
	    gbc.insets=new Insets(5,10,0,10);
		gbc.gridy+=1;
	    centerPanel.add(modeSLabel, gbc);
	    gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridx+=1;
	    centerPanel.add(add, gbc);
	    gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbc.gridx-=1;
		gbc.gridy+=1;
	    centerPanel.add(modePLabel, gbc);
	    gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridx+=1;
	    centerPanel.add(request, gbc);
	}

	private String aboutText()
	{
		String mess = "Tourism In Wallonia\n\n";
		mess+= "Author : Quentin Lowette\n";
		mess+="Version : 1.0\n";
		return mess;
	}
	private String helpText()
	{
		String mess = "Tourism In Wallonia\n\n";
		mess+= "Lorem Ipsum\n\n";
		return mess;
	}
	private void close()
	{
		System.exit(0);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==open)
		{
			if(FileOpener.openFile(ontologyModel))
			{
				this.setTitle("Tourism In Wallonia - "+ontologyModel.getNsPrefixMap().get(""));
			}
			else
			{
				this.setTitle("Tourism In Wallonia");
			}
			return;
		}
		if(e.getSource()==exit)
		{
			this.close();
			return;
		}
		if(e.getSource()==ab)
		{
			JOptionPane.showMessageDialog(null, this.aboutText(), "About", JOptionPane.PLAIN_MESSAGE);  
			return;
		}
		if(e.getSource()==help)
		{
			JOptionPane.showMessageDialog(this, this.helpText(), "Help", JOptionPane.PLAIN_MESSAGE);  
			return;
		}
		if(e.getSource()==openFile)
		{
			if(FileOpener.openFile(ontologyModel))
			{
				this.setTitle("Tourism In Wallonia - "+ontologyModel.getNsPrefixMap().get(""));
			}
			return;
		}
		if(e.getSource()==add)
		{
			if(this.getTitle().length()<22)
			{
				JOptionPane.showMessageDialog(this, "No ontology selected", "Error", JOptionPane.ERROR_MESSAGE);  
				return;
			}
			this.centerPanel.removeAll();
			this.centerPanel.setLayout(new BorderLayout());
			JPanel pan=new JPanel();
			pan.setBackground(Color.WHITE);
			pan.add(back);
			this.centerPanel.add("South", pan);
//			this.centerPanel.add("Center",new AddPanel(CDB));
			this.centerPanel.updateUI();
			return;
		}
//		if(e.getSource()==view)
//		{
//			if(this.getTitle().length()<6)
//			{
//				JOptionPane.showMessageDialog(this, "Aucune base de donnée sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);  
//				return;
//			}
//			this.setGraphMenu();
//			this.setLegend();
//			this.centerPanel.removeAll();
//			this.centerPanel.setLayout(new BorderLayout());
//			JPanel pan=new JPanel();
//			pan.setBackground(Color.WHITE);
//			pan.add(back);
//			this.centerPanel.add("South", pan);
//			gp=new GraphPanel(CDB);
//			gp.setBackground(Color.WHITE);
//			scroll = new JScrollPane(gp);
//			scroll.setBackground(Color.WHITE);
//
//			
//			this.centerPanel.add("Center",scroll);
//			this.centerPanel.updateUI();
//			return;
//		}
		if(e.getSource()==back)
		{
			this.centerPanel.removeAll();
			this.initCenterPanel();
			this.centerPanel.updateUI();
			this.menuBar.removeAll();
			this.initMenu();
			return;
		}
	}
	
	public static void main(String[] args)
	{
		new GUI("Tourism In Wallonia");
	}
}
