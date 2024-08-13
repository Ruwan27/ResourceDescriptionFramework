package com.example.dem0;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class CreateRDF {
    public static void main(String[] args) {
        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Create a resource for a person
        String personURI = "http://example.org/person#RuwanLiyanage";
        String fullName = "Ruwan Liyanage";

        // Add properties to the person
        Resource person = model.createResource(personURI)
                .addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.Given, "Ruwan")
                .addProperty(VCARD.Family, "Liyanage")
                .addProperty(VCARD.Country,"Sri lanka");


        // Write the model in RDF/XML format to standard output
        model.write(System.out, "RDF/XML");
    }
}



