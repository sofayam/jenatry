package jenatry;

import java.io.InputStream;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

// Create new classes and re-reason

public class Test7ClassCreateRereason {

	public static void doit() {
		try {
			InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.rdfxml");

			OWLOntologyManager m = OWLManager.createOWLOntologyManager();

			OWLOntology o = m.loadOntologyFromOntologyDocument(s2);

			Reasoner hermit = new Reasoner(o);

			System.out.println(hermit.isConsistent());

			OWLDataFactory dataFactory = m.getOWLDataFactory();

			OWLClass dueReminder = dataFactory.getOWLClass(makeIRI("DueReminder"));

			NodeSet<OWLClass> subs = hermit.getSubClasses(dueReminder, false);

			for (Node<OWLClass> equivalents : subs.getNodes()) {
				for (OWLClass equivalent : equivalents) {
					System.out.print(equivalent + " ");
				}
				System.out.println();
			}
			
			// Create a NamedPerson called Mark
			
			
			
			OWLClass mark = dataFactory.getOWLClass(makeIRI("Mark"));
			OWLClass namedPerson = dataFactory.getOWLClass(makeIRI("NamedPersons"));
			OWLAxiom axiom1 = dataFactory.getOWLSubClassOfAxiom(mark, namedPerson);
		    AddAxiom addAxiom1 = new AddAxiom(o, axiom1);
	        // We now use the manager to apply the change
	        m.applyChange(addAxiom1);
	        
		
	        // And a reminder called MarksReminder
	        
			OWLClass reminder = dataFactory.getOWLClass(makeIRI("MarkReminder"));
			OWLClass namedReminder = dataFactory.getOWLClass(makeIRI("NamedReminders"));
			OWLClass setReminder = dataFactory.getOWLClass(makeIRI("SetReminder"));
			OWLAxiom axiom2 = dataFactory.getOWLSubClassOfAxiom(reminder, namedReminder);
		    AddAxiom addAxiom2 = new AddAxiom(o, axiom2);
			OWLAxiom axiom3 = dataFactory.getOWLSubClassOfAxiom(reminder, setReminder);
		    AddAxiom addAxiom3 = new AddAxiom(o, axiom3);
	        // We now use the manager to apply the change
	        m.applyChange(addAxiom2);
	        m.applyChange(addAxiom3);


	        
	        // Set all the attributes
	 
	
	        
	        // Now show that the reminder is setBy mark (setBy only Mark)
	        
	        
	        // Now show that Mark has the MarkReminder (hasReminder only MarkReminder)
	        
	        
	        // Now show that Mark has a low heart rate (hasHR only LowHR)	        
	        		
		} catch (Exception e) {
			System.out.println("oops");
		}
	}

	public static IRI makeIRI(String name) {
		String prefix = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#";
		return IRI.create(prefix + name);
	}

	public static void main(String[] args) {

		doit();

	}
}