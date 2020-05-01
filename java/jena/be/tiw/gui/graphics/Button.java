package be.tiw.gui.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DEFAULTBACK=Color.decode("#dddddd");
	private static final Color HOVERBACK=Color.decode("#cccccc");
	
	public Button(String s)
	{
		this(s,200,40);
	}
	public Button(String s, int w, int h)
	{
		super(s);
		this.setPreferredSize(new Dimension(w,h));
		this.setBackground(Button.DEFAULTBACK);
		this.addMouseListener(this);
	}
	public Button(String s, int w, int h, boolean toolbar)
	{
		super(s);
		this.setBackground(Button.DEFAULTBACK);
		this.setPreferredSize(new Dimension(w,h));
		this.addMouseListener(this);
		if(toolbar)
		{
			this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		}
	}
	
	//Méthode appelée lors du clic de souris
	public void mouseClicked(MouseEvent event) { }
	
	//Méthode appelée lors du survol de la souris
	public void mouseEntered(MouseEvent event)
	{
		if(this.isEnabled())
			this.setBackground(Button.HOVERBACK);
	}
	
	//Méthode appelée lorsque la souris sort de la zone du bouton
	public void mouseExited(MouseEvent event)
	{
		this.setBackground(Button.DEFAULTBACK);
	}
	
	//Méthode appelée lorsque l'on presse le bouton gauche de la souris
	public void mousePressed(MouseEvent event) { }
	
	//Méthode appelée lorsque l'on relâche le clic de souris
	public void mouseReleased(MouseEvent event) { }
}