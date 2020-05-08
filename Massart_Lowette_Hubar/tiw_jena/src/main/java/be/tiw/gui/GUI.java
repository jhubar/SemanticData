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
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import be.tiw.gui.graphics.AddPanel;
import be.tiw.gui.graphics.Button;
import be.tiw.gui.graphics.QueryPanel;
import be.tiw.gui.file.FileOpener;

public class GUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem exit, open, save;
	private JPanel centerPanel, topPanel;
	private Button add, request, back, openFile;
	
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
		
		back=new Button("Back");
		back.addActionListener(this);
		
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new GridBagLayout());
		
		topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.setLayout(new BorderLayout());
		
		this.initTopPanel();
		
		this.initCenterPanel();
		
		//Set areas
		this.getContentPane().add(topPanel, BorderLayout.SOUTH);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		this.setVisible(true);    
		this.setResizable(false);
	}
	private void initMenu()
	{
		menuBar = new JMenuBar();
		file = new JMenu("File");
		exit = new JMenuItem("Exit");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		
		save.addActionListener(this);
		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		
		exit.addActionListener(this);
		exit.setMnemonic('E');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		
		file.add(open);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		file.setMnemonic('F');
		
		menuBar.add(file);
		this.setJMenuBar(menuBar);
	}
	private void initTopPanel()
	{
		JLabel logo = new JLabel(new ImageIcon("src\\main\\java\\be\\tiw\\gui\\assets\\logo_wallonie.png"));
		
		this.topPanel.add("East", logo);
	}
	private void initCenterPanel()
	{		
		add = new Button("Add");
		add.addActionListener(this);
		JLabel addLabel = new JLabel("Add an instance");
		request = new Button("Request");
		request.addActionListener(this);
		JLabel requestLabel = new JLabel("Make request on the ontology");
		
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
	    centerPanel.add(addLabel, gbc);
	    gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridx+=1;
	    centerPanel.add(add, gbc);
	    gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbc.gridx-=1;
		gbc.gridy+=1;
	    centerPanel.add(requestLabel, gbc);
	    gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.gridx+=1;
	    centerPanel.add(request, gbc);
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
		if(e.getSource()==save)
		{
			try
			{
				OutputStream outFile = new FileOutputStream("src\\main\\java\\be\\tiw\\gui\\assets\\updated_ontology.owl");
				this.ontologyModel.write(outFile, "RDF/XML");
			}
			catch (Exception exception)
			{
				JOptionPane.showMessageDialog(null, "Error while saving", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		if(e.getSource()==exit)
		{
			this.close();
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
			this.centerPanel.add("Center",new AddPanel(ontologyModel));
			this.centerPanel.updateUI();
			return;
		}
		if(e.getSource()==request)
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
			this.centerPanel.add("Center",new QueryPanel(ontologyModel));
			this.centerPanel.updateUI();
			return;
		}
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
