package jenatry;



import java.io.InputStream;

import java.util.Iterator;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;

import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

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
		    System.out.println("OK model is valid");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator<Report> i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    } 
		}
		
		InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.owl");
		
	    OntModel model = ModelFactory.createOntologyModel();

	    model.read(s2,null,"TTL");
		
	    ExtendedIterator<OntClass> classes = model.listClasses();

	    while (classes.hasNext())

	    {

	      OntClass thisClass = (OntClass) classes.next();

	      System.out.println("Found class: " + thisClass.toString());

	      ExtendedIterator<? extends OntResource> instances = thisClass.listInstances();

	      while (instances.hasNext())

	      {

	        Individual thisInstance = (Individual) instances.next();

	        System.out.println("  Found instance: " + thisInstance.toString());

	      }

	    }

	    printSubclasses(model, "Reminder");
		
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

