package tiw_jena;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

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
		ResultSetFormatter.out(System.out, results, query);
		
		// Closes the QueryExecution
		exec.close();
	}
	
	public static void main(String[] args) {
		String ontologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia.owl";
		String updatedOntologyPath = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\TourismeInWallonia_Updated_J.owl";
		
		String queriesDirectory = "D:\\Documents\\UNIVERSITE\\Bloc5\\Quadri 2\\SemanticData\\Project\\SemanticData\\T-W";
		
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
