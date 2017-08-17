package jenatry;

import java.io.InputStream;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Doit {
	
	public static final String CHEESE_SCHEMA = "http://data.kasabi.com/dataset/cheese/schema/";
    public static final String CHEESE_DATA = "http://data.kasabi.com/dataset/cheese/";

public static void main (String[] args) {

	boolean cheesey = true;
	
	if (cheesey) {
	   ReminderBetter.doit();

	} else {
		doit();
		Test1.doit();
		Test2.doit();
		Test3.doit();
		Test4.doit();
		Test5.doit();
		Test6Hermit.doit();
	}

}

static void doit() {
    Model model = ModelFactory.createDefaultModel();
	
	InputStream s = new Test2().getClass().getResourceAsStream("/cheeses-0.1.ttl");
	
	model.read(s,null,"TTL");
	
	showModelSize(model);
	listCheeses(model);
}

static void loadUni(String path) {
	Model m = RDFDataMgr.loadModel(path);
	m.addLiteral(null, null, false);
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