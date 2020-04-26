package msc;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAPITuto {
	
	public static void main(String[] args) {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			OWLOntology ontology;
			
			//ontology = manager.createOntology();
			
			File file = new File("D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia.owl");
			ontology = manager.loadOntologyFromOntologyDocument(file);
			
			System.out.println(ontology);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
