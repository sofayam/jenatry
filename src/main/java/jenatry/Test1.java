package jenatry;

/* 
 *  Printing stuff by hand and with the built in formatters
 * */
 

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;

public class Test1 {
	
	static void doit() {
		Model m = RDFDataMgr.loadModel("c:\\work\\webtools\\data\\university.owl");
		StmtIterator iter = m.listStatements();
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
		    Resource  subject   = stmt.getSubject();     // get the subject
		    Property  predicate = stmt.getPredicate();   // get the predicate
		    RDFNode   object    = stmt.getObject();      // get the object

		    System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // object is a literal
		        System.out.print(" \"" + object.toString() + "\"");
		    }

		    System.out.println(" .");
		    
		    System.out.println(" -------------------------------------------------------------------- ");
		    m.write(System.out, "RDF/XML-ABBREV");
		    
		    System.out.println(" -------------------------------------------------------------------- ");
		    m.write(System.out, "TURTLE");
		    
		    
		    System.out.println(" -------------------------------------------------------------------- ");
		    m.write(System.out, "N-TRIPLE");
		}
	}
	
	public static void main(String[] args) {
		doit();
	}


}
