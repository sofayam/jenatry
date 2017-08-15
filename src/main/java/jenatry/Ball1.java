package jenatry;

import java.io.File;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class Ball1 {

	public static void doit() {
		try {
			OWLOntologyManager m=OWLManager.createOWLOntologyManager();
			File f = new File("C:\\work\\repos\\ontologies\\Examples\\Balls.rdfxml");
			OWLOntology o=m.loadOntologyFromOntologyDocument(f);
			
			
			
		
        Reasoner hermit=new Reasoner(o);
		OWLDataFactory dataFactory=m.getOWLDataFactory();
        
		// Show all Ball subclasses
		
		OWLClass ball = dataFactory.getOWLClass(makeIRI("Ball"));
        

		OWLClass redBall = dataFactory.getOWLClass(makeIRI("RedBall"));
		
        NodeSet<OWLClass> subs = hermit.getSubClasses(ball, false);
        
        for (Node<OWLClass> equivalents : subs.getNodes()) {
        	  for (OWLClass equivalent : equivalents) {
                  System.out.print(equivalent+" ");
              }
              System.out.println();
		}
        
        // Show all Ball instances
        

        System.out.println("Original Instances");
        
        NodeSet<OWLNamedIndividual> instances = hermit.getInstances(redBall, false);
        for (OWLNamedIndividual i : instances.getFlattened()) {
            System.out.println(i.getIRI().toString());
        }
        
        // Stolen from https://stackoverflow.com/questions/8138084/owl-individual-in-java
        
        // Add a new Ball instance
        
        OWLNamedIndividual newBall = dataFactory.getOWLNamedIndividual(makeIRI("newBall"));
        OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(ball, newBall);
        m.addAxiom(o, classAssertion);
        
        // Add property
        
        OWLNamedIndividual red = dataFactory.getOWLNamedIndividual(makeIRI("Red"));
        OWLObjectProperty hasColour = dataFactory.getOWLObjectProperty(makeIRI("hasColour"));
        OWLObjectPropertyAssertionAxiom propertyAssertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasColour, newBall, red);
        
        m.addAxiom(o, propertyAssertion);
        
        // Rereason
        
        Reasoner newHermit = new Reasoner(o);
        
        // Show all new Ball instances
        
        System.out.println("New Instances");
        
        NodeSet<OWLNamedIndividual> instances1 = newHermit.getInstances(redBall, false);
        for (OWLNamedIndividual i : instances1.getFlattened()) {
            System.out.println(i.getIRI().toString());
        }
        
        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static IRI makeIRI(String name) {
		String prefix = "http://www.semanticweb.org/anm2fr/ontologies/2017/7/untitled-ontology-34#";
		return IRI.create(prefix + name);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doit();
	}

}
