package com.example.dem0;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class QueryRDF {
    public static void main(String[] args) {
        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Read RDF data into the model
        String rdfData = "test.rdf";
        model.read(rdfData);

        // Define a SPARQL query
        String queryString =
                "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
                        "SELECT ?fullName WHERE { " +
                        "  ?person vcard:FN ?fullName . " +
                        "}";

        // Execute the query and obtain results
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            // Output query results
            ResultSetFormatter.out(System.out, results, query);
        }
    }
}
