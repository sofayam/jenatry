package jenatry;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class ReminderBetter {

	
	public static void doit() {
		OntoUtils onto = new OntoUtils(
				"http://www.semanticweb.org/anm2fr/ontologies/2017/7/reminder#", 
				"C:\\work\\repos\\ontologies\\Reminder\\ReminderOntoMark.owl");
		
		System.out.println("Getting named instances of Person");
		
		NodeSet<OWLNamedIndividual> peeps = onto.getNamedInstances("Person");
		for (OWLNamedIndividual i : peeps.getFlattened()) {
			System.out.println(i.getIRI().toString());
		}
		
		System.out.println("Getting a data property");
		
		OWLLiteral ol = onto.getDataProperty("mark", "hasGoodMood");
		
		System.out.println(ol.toString());
		
		
		
	}
	
	public static void main(String[] args) {
		doit();
	}

}
