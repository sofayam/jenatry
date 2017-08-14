package jenatry;


import java.io.InputStream;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

// Inspired by example at https://github.com/phillord/hermit-reasoner/blob/master/examples/org/semanticweb/HermiT/examples/Explanations.java


public class Test6Hermit {

	public static void doit()  {
		try {
		InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.rdfxml");

        OWLOntologyManager m=OWLManager.createOWLOntologyManager();
      
        OWLOntology o=m.loadOntologyFromOntologyDocument(s2);

        Reasoner hermit=new Reasoner(o);
  
        System.out.println(hermit.isConsistent());
        
        OWLDataFactory dataFactory=m.getOWLDataFactory();
        
        OWLClass dueReminder = dataFactory.getOWLClass(makeIRI("DueReminder"));
        
        NodeSet<OWLClass> subs = hermit.getSubClasses(dueReminder, false);
        
        for (Node<OWLClass> equivalents : subs.getNodes()) {
        	  for (OWLClass equivalent : equivalents) {
                  System.out.print(equivalent+" ");
              }
              System.out.println();
        }
		} catch (Exception e) { System.out.println("oops"); }
	}
	
	public static IRI makeIRI(String name) {
		String prefix = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#";
		return IRI.create(prefix + name);
	}
	
	public static void main(String[] args) {
	
		doit();
		
	}
}