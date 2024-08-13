package com.example.dem0;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.VCARD;
import java.nio.file.Paths;

public class TDBPersistentStorageExample {
    public static void main(String[] args) {
        // Directory for TDB storage (relative path)
        String directory = Paths.get("tdb-storage").toAbsolutePath().toString();

        // Create a TDB-backed dataset
        Dataset dataset = TDBFactory.createDataset(directory);

        // Adding data to the dataset
         addData(dataset);

        // Querying the data
        queryData(dataset);

        // Close the dataset
        dataset.close();
    }

    private static void addData(Dataset dataset) {
        // Start a write transaction
        dataset.begin(ReadWrite.WRITE);
        try {
            // Get the default model within the transaction
            Model model = dataset.getDefaultModel();

            // Add RDF data to the model
            String personURI = "http://example.org/person#KasunRajapaksh";
            String fullName = "Kasun Rajapaksh";

            model.createResource(personURI)
                    .addProperty(VCARD.FN, fullName)
                    .addProperty(VCARD.Given, "Kasun")
                    .addProperty(VCARD.Family, "Rajapaksha")
                    .addProperty(VCARD.Country, "United States") ;

            // Commit the transaction
            dataset.commit();
        } catch (Exception e) {
            // If there is an exception, abort the transaction
            dataset.abort();
            e.printStackTrace();
        } finally {
            // End the transaction
            dataset.end();
        }
    }

    private static void queryData(Dataset dataset) {
        // Start a read transaction
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();
        try {
            // Define a SPARQL query
            String queryString =
//                    "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
//                            "SELECT ?fullname WHERE { " +
//                            "  ?person vcard:Given?fullname. " +
//                            "}";
//                    "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
//                            "SELECT ?Given WHERE { " +
//                            "  ?person vcard:Given?Given. " +
//                            "}";
                    "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#> " +
                            "SELECT * WHERE { " +
                            "  ?s ?p ?o. " +
                            "}";

            // Execute the query and obtain results
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
                ResultSet results = qexec.execSelect();
                // Output query results
                ResultSetFormatter.out(System.out, results, query);
                //model.write(System.out, "RDF/XML");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // End the read transaction
            dataset.end();
        }
    }
}

