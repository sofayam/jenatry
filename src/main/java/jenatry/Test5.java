package jenatry;




import java.io.InputStream;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.iterator.ExtendedIterator;



public class Test5 {

	static void doit() {
				
		/* Now find newly generated facts (entailed statements) */ 
		
		/* Where are the Due Reminders? */
		
		OntModel ontModel = ModelFactory.createOntologyModel();
		
		/* partially stolen from https://stackoverflow.com/questions/3024273/inferring-using-jena */
		
		InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.rdfxml");
		ontModel.read(s2,"RDF/XML");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(ontModel);
		OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM_RULE_INF;
		ontModelSpec.setReasoner(reasoner);
		OntModel model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
		
		ExtendedIterator<OntClass> it = model.listClasses();
		
		// while (it.hasNext()) {
		//	System.out.println(it.next().toString());
		//}
		
		//String dueReminder = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#DueReminder";
		
		//System.out.println("Listed all classes");
		
		
		//printSubclasses(model, "DueReminder");
		printSubclasses(model, "Reminder");
		
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

