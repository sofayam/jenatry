package jenatry;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;


public class Test3 {

	static void doit() {

		Model model = ModelFactory.createDefaultModel();

		InputStream s = Test3.class.getResourceAsStream("/dataset.n3");

		model.read(s,null,"TTL");

		InputStream jr = Test3.class.getResourceAsStream("/rules.txt");

		BufferedReader br = new BufferedReader( new InputStreamReader(jr));

		Rule.Parser p = Rule.rulesParserFromReader(br);
		
		// List<Rule> ruleList = p.getRulesPreload();
		List<Rule> ruleList = new ArrayList<Rule>();
		boolean finished = false;
		while (!finished) {
			try {
				Rule r = p.parseRule();
				ruleList.add(r);
			} catch (Exception e) {
				finished = true;
			}
		}

		
		
		Reasoner reasoner = new GenericRuleReasoner(ruleList);

		InfModel infModel = ModelFactory.createInfModel( reasoner, model );

		StmtIterator it = infModel.listStatements();

		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();

			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();

			System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );

		}
	}

	public static void main(String[] args) {
		doit();
	}


}

