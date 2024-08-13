package com.example.dem0;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.vocabulary.VCARD;

import java.nio.file.Paths;

public class TDBExample {
    public static void main(String[] args) {
        // Directory for TDB storage
        String directory = "/Users/ruwan/Documents/Java/TBDc";
        //String directory= Paths.get("/Users/ruwan/Documents/Java/TBD").toAbsolutePath().toString();
        // Create a TDB-backed dataset
        Dataset dataset = TDBFactory.createDataset(directory);

        // Get the default model
        Model model = dataset.getDefaultModel();

        // Add RDF data to the model
        String personURI = "http://example.org/person#JaneDoe";
        String fullName = "Jane Doe";

        model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.Given, "Jane")
                .addProperty(VCARD.Family, "Doe");

        // Commit the transaction and close the dataset

        dataset.commit();
        dataset.close();
    }
}


