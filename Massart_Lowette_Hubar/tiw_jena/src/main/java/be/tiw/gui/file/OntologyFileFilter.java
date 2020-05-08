package be.tiw.gui.file;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class OntologyFileFilter extends FileFilter
{
	//Accept all directories and owl files.
	
    public String getDescription()
    {
        return "OWL file in RDF/XML format (*.owl)";
    }
    
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }
        else
        {
            return f.getName().toLowerCase().endsWith(".owl");
        }
    }
}