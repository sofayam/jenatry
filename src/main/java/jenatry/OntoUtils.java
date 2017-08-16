package jenatry;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class OntoUtils {

	public String iriPrefix;
	public OWLOntology o;
	public Reasoner hermit;
	public OWLOntologyManager m;
	public OWLDataFactory dataFactory;

	public OntoUtils(String the_iriPrefix, String fileName) {

		try {
			iriPrefix = the_iriPrefix;
			m = OWLManager.createOWLOntologyManager();
			File f = new File(fileName);
			OWLOntology o = m.loadOntologyFromOntologyDocument(f);
			hermit = new Reasoner(o);
			dataFactory = m.getOWLDataFactory();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {

	}

	public OWLLiteral getDataProperty(String individual, String property) {
		OWLNamedIndividual ind = dataFactory.getOWLNamedIndividual(makeIRI(individual));
		OWLDataProperty prop = dataFactory.getOWLDataProperty(makeIRI(property));
		Set<OWLLiteral> results = ind.getDataPropertyValues(prop.asOWLDataProperty(), o);
		Iterator<OWLLiteral> iter = results.iterator();
		return iter.next();
	}

	public NodeSet<OWLNamedIndividual> getNamedInstances(String name) {
		OWLClass cls = dataFactory.getOWLClass(makeIRI(name));
		NodeSet<OWLNamedIndividual> nodes = hermit.getInstances(cls, false);
		return nodes;
	}

	public IRI makeIRI(String name) {
		return IRI.create(iriPrefix + name);
	}

}
