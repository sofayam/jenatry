package jenatry;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Doit {
	
	public static final String CHEESE_SCHEMA = "http://data.kasabi.com/dataset/cheese/schema/";
    public static final String CHEESE_DATA = "http://data.kasabi.com/dataset/cheese/";

private static final String CHEESE_DATA_FILE = "C:\\work\\webtools\\apache-jena-3.4.0\\src-examples\\jena-examples\\src\\main\\resources\\data\\cheeses-0.1.ttl";

public static void main (String[] args) {

	
	Model m = ModelFactory.createDefaultModel();
	FileManager.get().readModel(m, CHEESE_DATA_FILE);
	showModelSize(m);
	listCheeses(m);
    System.out.println("Hello from maven");
}

 static void showModelSize( Model m ) {
    System.out.println( String.format( "The model contains %d triples", m.size() ) );
}

 static void listCheeses( Model m ) {
     Resource cheeseClass = m.getResource( CHEESE_SCHEMA + "Cheese" );

     StmtIterator i = m.listStatements( null, RDF.type, cheeseClass );

     while (i.hasNext()) {
         Resource cheese = i.next().getSubject();
         String label = getEnglishLabel( cheese );
         System.out.println( String.format( "Cheese %s has name: %s", cheese.getURI(), label ) );
     }
 }
 static String getEnglishLabel( Resource cheese ) {
     StmtIterator i = cheese.listProperties( RDFS.label );
     while (i.hasNext()) {
         Literal l = i.next().getLiteral();

         if (l.getLanguage() != null && l.getLanguage().equals( "en")) {
             // found the English language label
             return l.getLexicalForm();
         }
     }

     return "A Cheese with No Name!";
 }
}