package tiw;

import java.awt.List;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class OWLAPITuto {
	
	private static void printInstances(OWLReasoner reasoner, OWLDataFactory dataFactory, IRI classIRI) {
		NodeSet<OWLNamedIndividual> events = reasoner.getInstances(dataFactory.getOWLClass(classIRI), false);
		long length = events.entities().count();
		
		System.out.println("The ontology contains the following " + length + " events:");
		events.forEach(e -> System.out.println(e.getRepresentativeElement().getIRI().getShortForm()));
		System.out.println();
	}
	
	public static void main(String[] args) {
		String ontologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia.owl";
		String updatedOntologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia_Updated.owl";
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		IRI ontologyIRI;
		OWLDataFactory dataFactory;
		
		OWLReasoner reasoner;
		
		try {
			File inputFile = new File(ontologyPath);
			
			// Ontology loading
			System.out.println("#################### LOADING ONTOLOGY ####################\n");
			
			ontology = manager.loadOntologyFromOntologyDocument(inputFile);
			
			System.out.println("Loaded ontology : " + ontology.getOntologyID() + "\n");
			
			ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();
			
			dataFactory = manager.getOWLDataFactory();
			
			reasoner = new ReasonerFactory().createReasoner(ontology);
			reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			
			// Add an event
			System.out.println("##################### EVENT ADDITION #####################\n");
			
			System.out.println("BEFORE INSERTION");			
			printInstances(reasoner, dataFactory, IRI.create(ontologyIRI + "#Event"));
			
			System.out.println("Insertion of a new event\n");
			
			OWLNamedIndividual newEventName = dataFactory.getOWLNamedIndividual(IRI.create(ontologyIRI + "#Marche_de_Noel_Liege"));
			OWLNamedIndividual newEventLocation = dataFactory.getOWLNamedIndividual(IRI.create(ontologyIRI + "#Liege"));
			OWLLiteral startDateLiteral = dataFactory.getOWLLiteral("2020-11-27T11:00:00+01:00", OWL2Datatype.XSD_DATE_TIME);
			OWLLiteral endDateLiteral = dataFactory.getOWLLiteral("2021-01-04T20:00:00+01:00", OWL2Datatype.XSD_DATE_TIME);
			
			OWLClass newEventType = dataFactory.getOWLClass(IRI.create(ontologyIRI + "#ChristmasMarket"));
			
			OWLObjectProperty isLocated = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRI + "#isLocated"));
			
			OWLDataProperty cost = dataFactory.getOWLDataProperty(IRI.create(ontologyIRI + "#cost"));
			OWLDataProperty startDate = dataFactory.getOWLDataProperty(IRI.create(ontologyIRI + "#startDate"));
			OWLDataProperty endDate = dataFactory.getOWLDataProperty(IRI.create(ontologyIRI + "#endDate"));
			
			Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
			
			// Specify class of new event
			axioms.add(dataFactory.getOWLClassAssertionAxiom(newEventType, newEventName));
			
			// Specify location of new event
			axioms.add(dataFactory.getOWLObjectPropertyAssertionAxiom(isLocated, newEventName, newEventLocation));
			
			// Specify data properties of new event
			axioms.add(dataFactory.getOWLDataPropertyAssertionAxiom(cost, newEventName, 0.0));
			axioms.add(dataFactory.getOWLDataPropertyAssertionAxiom(startDate, newEventName, startDateLiteral));
			axioms.add(dataFactory.getOWLDataPropertyAssertionAxiom(endDate, newEventName, endDateLiteral));
			
			// Make new event different from others
			Set<OWLNamedIndividual> individuals = ontology.individualsInSignature().collect(Collectors.toSet());
			individuals.add(newEventName);
			axioms.add(dataFactory.getOWLDifferentIndividualsAxiom(individuals));
			
			manager.addAxioms(ontology, axioms.stream());
			
			// Restart reasoner
			reasoner = new ReasonerFactory().createReasoner(ontology);
			reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
			
			System.out.println("AFTER INSERTION");
			printInstances(reasoner, dataFactory, IRI.create(ontologyIRI + "#Event"));
			
			// Reasoning
			System.out.println("####################### REASONING ########################\n");
			System.out.println("Subclasses of People :");
			reasoner.getSubClasses(dataFactory.getOWLClass(IRI.create(ontologyIRI + "#People")), false).forEach(sc -> System.out.println(sc.getRepresentativeElement().getIRI().getShortForm()));
			
			System.out.println("\nInstances of People :");
			reasoner.getInstances(dataFactory.getOWLClass(IRI.create(ontologyIRI + "#People")), false).forEach(sc -> System.out.println(sc.getRepresentativeElement().getIRI().getShortForm()));
			
			
			// Saving
			System.out.println("#################### SAVING ONTOLOGY #####################\n");
			File outputFile = new File(updatedOntologyPath);
			OWLDocumentFormat format = manager.getOntologyFormat(ontology);
			manager.saveOntology(ontology, format, IRI.create(outputFile.toURI()));
			
			System.out.println("########################## END ###########################\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
