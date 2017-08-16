package jenatry;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class Reminder {

	public static void main(String[] args) {
		doit();

	}

	public static void doit() {
		try {
			OWLOntologyManager m = OWLManager.createOWLOntologyManager();
			File f = new File("C:\\work\\repos\\ontologies\\Reminder\\ReminderOntoMark.owl");
			OWLOntology o = m.loadOntologyFromOntologyDocument(f);

			Reasoner hermit = new Reasoner(o);
			OWLDataFactory dataFactory = m.getOWLDataFactory();

			// Show all Ball subclasses

			OWLClass person = dataFactory.getOWLClass(makeIRI("Person"));

			OWLClass reminder = dataFactory.getOWLClass(makeIRI("Reminder"));

			OWLClass dueReminder = dataFactory.getOWLClass(makeIRI("DueReminder"));
			
			
			System.out.println(" Reminder Subclasses ");

			NodeSet<OWLClass> subs = hermit.getSubClasses(reminder, false);

			for (Node<OWLClass> equivalents : subs.getNodes()) {
				for (OWLClass equivalent : equivalents) {
					System.out.print(equivalent + " ");
				}
				System.out.println();
			}

			// Show all Person instances

			System.out.println(" Person Instances");

			NodeSet<OWLNamedIndividual> instances = hermit.getInstances(person, false);
			for (OWLNamedIndividual i : instances.getFlattened()) {
				System.out.println(i.getIRI().toString());
			}

			// Show all Person instances

			System.out.println(" DueReminder Instances");

			NodeSet<OWLNamedIndividual> instances1 = hermit.getInstances(dueReminder, false);
			for (OWLNamedIndividual i : instances1.getFlattened()) {
				System.out.println(i.getIRI().toString());
			}

			// Now put bob in a bad mood
			
			OWLNamedIndividual mark = dataFactory.getOWLNamedIndividual(makeIRI("mark"));
			
			OWLDataProperty odp  = dataFactory.getOWLDataProperty(makeIRI("hasGoodMood"));
			
			Set<OWLLiteral> results = mark.getDataPropertyValues(odp.asOWLDataProperty(), o);
			
			Iterator<OWLLiteral> iter = results.iterator();
			while (iter.hasNext()) {
			    System.out.println("Here is a property: "+ iter.next());
			}
	
		
			
			
			
			
			
			System.out.println(odp.toString());
			
			
			
			//OWLDataProperty odp =  mark.getDataPropertyValues(hasMood, o);
			
			
			// Stolen from
			// https://stackoverflow.com/questions/8138084/owl-individual-in-java

			// Add a new Ball instance

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static IRI makeIRI(String name) {
		String prefix = "http://www.semanticweb.org/anm2fr/ontologies/2017/7/reminder#";
		return IRI.create(prefix + name);
	}

}
