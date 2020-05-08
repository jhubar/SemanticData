package tiw_jena;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class JenaAPI {
	
	public static void queryModel(Model model, String queryPath) throws IOException {
		// Reader to the path
		FileReader reader = new FileReader(queryPath);
		
		// Buffered reader
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		// Reads the query file
		String queryString = "";
		String line;
		
		while ((line = bufferedReader.readLine()) != null) {
			queryString += line;
		}
		bufferedReader.close();
		
		// Creates the query object
		Query query = QueryFactory.create(queryString, Syntax.syntaxARQ);
		
		// Executes the query
		QueryExecution exec = QueryExecutionFactory.create(query, model);
		ResultSet results = exec.execSelect();
		
		// Outputs the results
		String[] pathElements = queryPath.split("\\\\");
		String queryName = pathElements[pathElements.length - 1];
		System.out.println(queryName);
		ResultSetFormatter.out(System.out, results, query);
		System.out.println("\n");
		
		// Closes the QueryExecution
		exec.close();
	}
	
	public static void main(String[] args) {
		String ontologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia.owl";
		String updatedOntologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia_Updated_J.owl";
		
		String queriesDirectory = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\T-W\\";
		
		String IRI = "http://www.semanticweb.org/julienhubar/ontologies/2020/2/TourismeEnWallonie#";
		
		try {
			// Creates input stream
			InputStream ontologyFile = new FileInputStream(new File(ontologyPath));
			
			// Creates model
			Model model = ModelFactory.createMemModelMaker().createModel("Model");
			
			// Reads file into model
			model.read(ontologyFile, null);
			
			// Closes input stream
			ontologyFile.close();
			
			// Loops over queries
			File dir = new File(queriesDirectory);
			File[] directoryContent = dir.listFiles();
			if (directoryContent != null) {
				for (File query : directoryContent) {
					queryModel(model, query.getAbsolutePath());
				}
			} else {
				System.err.println("ERROR: Cannot find queries.");
			}
			
			queryModel(model, queriesDirectory + "Event_And_TypeEvent.rq");
			
			// Adds event
			String eventName = IRI + "Marche_de_Noel_Liege";
			String eventType = IRI + "ChristmasMarket";
			String eventLocation = IRI + "Liege";
			Literal startDate = model.createTypedLiteral("2020-11-27T11:00:00+01:00", XSDDatatype.XSDdateTime);
			Literal endDate = model.createTypedLiteral("2021-01-04T20:00:00+01:00", XSDDatatype.XSDdateTime);
			
			Resource christmasMarketLiege = model.createResource();
			
			christmasMarketLiege.addProperty(RDF.type, model.getResource(eventType));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
