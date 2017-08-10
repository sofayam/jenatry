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


/**
 * This example demonstrates how HermiT can be used to check the consistency of the Pizza ontology
 */
public class Test6Hermit {

	public static void doit() throws Exception {
		
		InputStream s2 = Test2.class.getResourceAsStream("/PulseMoodInferOnto.rdfxml");
    	// First, we create an OWLOntologyManager object. The manager will load and save ontologies.
        OWLOntologyManager m=OWLManager.createOWLOntologyManager();
        // We use the OWL API to load the Pizza ontology.
        
        OWLOntology o=m.loadOntologyFromOntologyDocument(s2);
        // Now, we instantiate HermiT by creating an instance of the Reasoner class in the package org.semanticweb.HermiT.
        Reasoner hermit=new Reasoner(o);
        // Finally, we output whether the ontology is consistent.
        System.out.println(hermit.isConsistent());
        
        OWLDataFactory dataFactory=m.getOWLDataFactory();
        
        OWLClass person = dataFactory.getOWLClass(makeIRI("DueReminder"));
        
        NodeSet<OWLClass> subs = hermit.getSubClasses(person, false);
        
        for (Node<OWLClass> equivalents : subs.getNodes()) {
        	  for (OWLClass equivalent : equivalents) {
                  System.out.print(equivalent+" ");
              }
              System.out.println();
        }
	}
	
	public static IRI makeIRI(String name) {
		String prefix = "http://www.semanticweb.org/antonioskaratzoglou/ontologies/2017/7/mood_person_onto#";
		return IRI.create(prefix + name);
	}
	
	public static void main(String[] args) throws Exception {
		doit();
	}
}