//package be.tiw.gui.graphics;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.StringTokenizer;
//
//import javax.swing.BorderFactory;
//import javax.swing.ButtonGroup;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JTextField;
//
//import org.apache.jena.rdf.model.Model;
//
//public class AddPanel extends JPanel implements ActionListener
//{
//	private static final long serialVersionUID = 1L;
//
//	private Button valid;
//	private JTextField code, name, coreq, prereq;
//	private JPanel centerPanel;
//	private JComboBox<String> nameCB;
//	private JRadioButton bac, master;
//	
//	private Model ontology;
//	
//	public AddPanel(CourseDatabase cdb)
//	{
//		this.cdb=cdb;
//		
//		//Initialisation champs
//		this.initCenterPanel();
//		
//		//On positionne nos zones
//		this.setLayout(new BorderLayout());
//		this.add("Center", centerPanel);
//		this.setVisible(true);
//	}
//	private void initCenterPanel()
//	{
//		code = new JTextField();
//		code.setPreferredSize(new Dimension(200,30));
//		code.setEditable(true);
//		code.setBackground(Color.WHITE);
//		JLabel codeLabel = new JLabel("Code : ");
//		codeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
//		
//		name = new JTextField();
//		name.setPreferredSize(new Dimension(200,30));
//		name.setEditable(true);
//		name.setBackground(Color.WHITE);
//		JLabel nameLabel = new JLabel("Intitulé : ");
//		nameLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
//		
//		nameCB = new JComboBox<String>(cdb.getCoursesName());
//		nameCB.setPreferredSize(new Dimension(200, 30));
//		nameCB.addActionListener(this);
//		nameCB.setBackground(Color.WHITE);
//		
//		bac = new JRadioButton("Bac");
//		bac.setBackground(Color.WHITE);
//		master = new JRadioButton("Master");
//		master.setBackground(Color.WHITE);
//		ButtonGroup bg = new ButtonGroup();
//		bg.add(bac);
//		bg.add(master);
//		JLabel blocLabel = new JLabel("Bac/Master : ");
//		blocLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
//		
//		coreq = new JTextField();
//		coreq.setPreferredSize(new Dimension(200,30));
//		coreq.setEditable(true);
//		coreq.setBackground(Color.WHITE);
//		JLabel coreqLabel = new JLabel("Corequis : ");
//		coreqLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
//		
//		prereq = new JTextField();
//		prereq.setPreferredSize(new Dimension(200,30));
//		prereq.setEditable(true);
//		prereq.setBackground(Color.WHITE);
//		JLabel prereqLabel = new JLabel("Prérequis : ");
//		prereqLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
//		
//		valid = new MyButton("Valider");
//		valid.addActionListener(this);
//		
//		centerPanel = new JPanel();
//		centerPanel.setBackground(Color.WHITE);
//		centerPanel.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.insets=new Insets(10,10,10,10);
//		gbc.fill=GridBagConstraints.HORIZONTAL;
//		gbc.gridx=0;
//		gbc.gridy=0;
//		centerPanel.add(codeLabel, gbc);
//		gbc.gridx=1;
//		gbc.gridy=0;
//		centerPanel.add(nameLabel, gbc);
//		gbc.gridx=2;
//		gbc.gridy=0;
//		centerPanel.add(blocLabel, gbc);
//		gbc.fill=GridBagConstraints.HORIZONTAL;
//		gbc.gridx=0;
//		gbc.gridy=3;
//		centerPanel.add(coreqLabel, gbc);
//		gbc.gridx=1;
//		gbc.gridy=3;
//		centerPanel.add(prereqLabel, gbc);
//		
//		gbc.insets=new Insets(5,10,0,10);
//		gbc.gridx=1;
//		gbc.gridy=1;
//		centerPanel.add(name, gbc);
//		gbc.gridx=1;
//		gbc.gridy=2;
//		centerPanel.add(nameCB, gbc);
//		gbc.gridx=2;
//		gbc.gridy=1;
//		centerPanel.add(bac, gbc);
//		gbc.gridx=2;
//		gbc.gridy=2;
//		centerPanel.add(master, gbc);
//		
//		gbc.gridheight=2;
//		gbc.gridx=0;
//		gbc.gridy=1;
//		centerPanel.add(code, gbc);
//		gbc.gridheight=1;
//		gbc.gridx=0;
//		gbc.gridy=4;
//		centerPanel.add(coreq, gbc);
//		gbc.gridx=1;
//		gbc.gridy=4;
//		centerPanel.add(prereq, gbc);
//		gbc.gridx=2;
//		gbc.gridy=4;
//		centerPanel.add(valid, gbc);
//	}
//	public void modifyDB()
//	{
//		Course c=cdb.getCourseByName((String)nameCB.getSelectedItem());
//		String codeCourse=code.getText();
//		c.setCode(codeCourse);
//		c.setMaster(master.isSelected());
//		StringTokenizer stc=new StringTokenizer(coreq.getText(), ",");
//		while(stc.hasMoreTokens())
//		{
//			String s = stc.nextToken().trim();
//			if(cdb.hasCourse(codeCourse))
//			{
//				c.addCoreq(cdb.getCourse(codeCourse));
//			}
//			else
//			{
//				Course co = new Course(s, codeCourse, false);
//				cdb.getContent().add(co);
//				cdb.add(co);
//				c.addCoreq(co);
//			}
//		}
//		StringTokenizer stp=new StringTokenizer(prereq.getText(), ",");
//		while(stp.hasMoreTokens())
//		{
//			String s = stp.nextToken().trim();
//			if(cdb.hasCourse(codeCourse))
//			{
//				c.addPrereq(cdb.getCourse(codeCourse));
//			}
//			else
//			{
//				Course co = new Course(s, codeCourse, false);
//				cdb.getContent().add(co);
//				cdb.add(co);
//				c.addPrereq(co);
//			}
//		}
//		JOptionPane.showMessageDialog(this, "Modification réussie", "Modification", JOptionPane.PLAIN_MESSAGE);
//	}
//	public void addDB()
//	{
//		if(cdb.hasCourse(code.getText()))
//		{
//			JOptionPane.showMessageDialog(this, "Cours déjà présent", "Erreur", JOptionPane.ERROR_MESSAGE); 
//			return;
//		}
//		String codeC = code.getText();
//		Course c=new Course(name.getText().trim(), codeC, master.isSelected());
//		StringTokenizer stc=new StringTokenizer(coreq.getText(), ",");
//		while(stc.hasMoreTokens())
//		{
//			String s = stc.nextToken().trim();
//			if(cdb.hasCourse(codeC))
//			{
//				c.addCoreq(cdb.getCourse(codeC));
//			}
//			else
//			{
//				Course co = new Course(s, codeC, false);
//				cdb.getContent().add(co);
//				cdb.add(co);
//				c.addCoreq(co);
//			}
//		}
//		StringTokenizer stp=new StringTokenizer(prereq.getText(), ",");
//		while(stp.hasMoreTokens())
//		{
//			String s = stp.nextToken().trim();
//			if(cdb.hasCourse(codeC))
//			{
//				c.addPrereq(cdb.getCourse(codeC));
//			}
//			else
//			{
//				Course co = new Course(s, codeC, false);
//				cdb.getContent().add(co);
//				cdb.add(co);
//				c.addPrereq(co);
//			}
//		}
//		cdb.add(c);//Peut ameliorer les message reussi en verifiant effectivement
//		JOptionPane.showMessageDialog(this, "Ajout réussi", "Ajout", JOptionPane.PLAIN_MESSAGE);
//	}
//	public void actionPerformed(ActionEvent e)
//	{
//		if(e.getSource()==valid)
//		{
//			if(!bac.isSelected() && !master.isSelected())
//			{
//				JOptionPane.showMessageDialog(this, "Bac ou Master ?", "Erreur", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(name.getText().equals(""))
//			{
//				this.modifyDB();
//			}
//			else
//			{
//				this.addDB();
//			}
//			return;
//		}
//		if(e.getSource()==nameCB)
//		{
//			Course crt = cdb.getCourseByName((String)nameCB.getSelectedItem());
//			if(crt.isMaster())
//			{
//				master.setSelected(true);
//			}
//			else
//			{
//				bac.setSelected(true);
//			}
//			coreq.setText(crt.getCoreqName());
//			prereq.setText(crt.getPrereqName());
//			code.setText(crt.getCode());
//		}
//	}
//
//}
