package jenatry;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class Test8IndividualCreate {
	public static void doit() throws OWLOntologyCreationException, OWLOntologyStorageException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		IRI ontologyIRI = IRI.create("http://example.com/owlapi/families");
		OWLOntology ont = manager.createOntology(ontologyIRI);
		OWLDataFactory factory = manager.getOWLDataFactory();

		OWLIndividual john = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "#John"));
		OWLIndividual mary = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "#Mary"));


		OWLObjectProperty hasWife = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "#hasWife"));

		OWLObjectPropertyAssertionAxiom axiom1 = factory.getOWLObjectPropertyAssertionAxiom(hasWife, john, mary);

		AddAxiom addAxiom1 = new AddAxiom(ont, axiom1);
		// Now we apply the change using the manager.
		manager.applyChange(addAxiom1);

		System.out.println("RDF/XML: ");
		manager.saveOntology(ont, new StreamDocumentTarget(System.out));

	}

	public static void main(String[] args) {
		try {
			doit();
		} catch (Exception e) {
		}
	}

}
