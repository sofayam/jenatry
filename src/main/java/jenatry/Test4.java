package jenatry;



import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Iterator;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.ValidityReport.Report;
import org.apache.jena.util.iterator.ExtendedIterator;



public class Test4 {

	static void doit() {
		
	    Model m = ModelFactory.createDefaultModel();
		
		InputStream s1 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.owl");
		
		m.read(s1,null,"TTL");
		
		InfModel inf = ModelFactory.createRDFSModel(m);  

		/* First check validity */
		
		ValidityReport validity = inf.validate();
		
		
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator<Report> i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    } 
		}
		
		/* Now find newly generated facts (entailed statements) */ 
		
		/* Where are the Due Reminders? */
		
		OntModel ontModel = ModelFactory.createOntologyModel();
		
		/* partially stolen from https://stackoverflow.com/questions/3024273/inferring-using-jena */
		
		InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.rdfxml");
		ontModel.read(s2,"RDF/XML");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(ontModel);
		OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
		ontModelSpec.setReasoner(reasoner);
		OntModel model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
		
		ExtendedIterator<OntClass> it = model.listClasses();
		
		 while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
		
		//String dueReminder = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#DueReminder";
		
		 System.out.println("Listed all classes");
		printSubclasses(model, "DueReminder");
		
		//OntClass dueReminderClass = model.getOntClass(dueReminder);
/*		
		ExtendedIterator<OntClass> sci = dueReminderClass.listSubClasses();
		
		System.out.println("************ subclasses of " + dueReminder);
		
		while (sci.hasNext()) {
			OntClass found = sci.next();
			System.out.println(found.toString());
		}*/
	}

	public static void printSubclasses(OntModel model, String className) {
		String fullName = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#" + className;
		OntClass ontClass = model.getOntClass(fullName);
		
		ExtendedIterator<OntClass> sci = ontClass.listSubClasses();
		
		System.out.println("************ subclasses of " + className);
		
		while (sci.hasNext()) {
			OntClass found = sci.next();
			System.out.println(found.toString());
		}
	}
	
	public static void main(String[] args) {
		doit();
	}


}

