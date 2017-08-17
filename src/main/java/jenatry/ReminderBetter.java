package jenatry;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class ReminderBetter {

	
	public static void doit() {
		OntoUtils onto = new OntoUtils(
				"http://www.semanticweb.org/anm2fr/ontologies/2017/7/reminder#", 
				"C:\\work\\repos\\ontologies\\Reminder\\ReminderOntoMarkComplex.owl");
		
		System.out.println("Getting named instances of DueReminder");
		
		NodeSet<OWLNamedIndividual> rems = onto.getNamedInstances("DueReminder");
		for (OWLNamedIndividual i : rems.getFlattened()) {
			System.out.println(i.getIRI().toString());
		}
		
		/*
		System.out.println("Getting a data property");
		
		OWLLiteral ol = onto.getDataProperty("mark", "hasGoodMood");
		
		System.out.println(ol.toString());
		
		*/
		
	}
	
	public static void main(String[] args) {
		doit();
	}

}
