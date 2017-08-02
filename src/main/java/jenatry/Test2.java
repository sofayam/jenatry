package jenatry;


import java.io.InputStream;
import java.net.URL;

/*
 *  Simple Sparql query
 */

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


public class Test2 {
	
	static void doit() {
		
	    Model model = ModelFactory.createDefaultModel();
		
		InputStream s = Test2.class.getResourceAsStream("/uni.ttl");
		
		model.read(s,null,"TTL");

	
		String querystr = "PREFIX  uni:   <http://www.semanticweb.org/anm2fr/ontologies/2017/6/uni#>  " +
				          "PREFIX  :   <.>  " +
						  "SELECT ?lecturer ?module ?name WHERE {{?lecturer uni:teaches ?module} {?lecturer uni:lastName ?name}} ";
		Query query = QueryFactory.create(querystr);
		QueryExecution qe = QueryExecutionFactory.create(query,model);
		ResultSet results = qe.execSelect();

		ResultSetFormatter.out(System.out, results);
		qe.close();

		
	}
	
	public static void main(String[] args) {
		doit();
	}


}

