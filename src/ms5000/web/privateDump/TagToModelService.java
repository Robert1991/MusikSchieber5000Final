package ms5000.web.privateDump;

import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.modify.UpdateProcessRemote;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

import ms5000.properties.PropertiesUtils;
import ms5000.properties.web.WebProperties;

public class TagToModelService {

	public static String editEndpoint = PropertiesUtils.getProperty(WebProperties.PRIVATE_DUMP_UPLOAD);
	public static String sparqlEndpoint = PropertiesUtils.getProperty(WebProperties.PRIVATE_DUMP_DATA);
	public static String sparqlUpdateEndpoint = PropertiesUtils.getProperty(WebProperties.PRIVATE_DUMP_UPDATE);
	public static String NumberOfMappingValues = "13";

    /**
     * Launches a http-put for the given endpoint. Replaces the existing model in the 
     * endpoint with the new one
     * 
     * @param model which gets added to the dump
     */
	public static void uploadModel(Model model, String recID) {
		// first clean up the store
		deleteModelFromRecId(recID);
		// then fill in the gap
		DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(editEndpoint);
		accessor.add(model);
	}
    
	/**
     * Launches a http-put for the given endpoint
     * 
     * @param model which gets added to the dump
     */
	public static void uploadModel(Model model) {
		// then fill in the gap
		DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(editEndpoint);
		accessor.add(model);
	}

    /**
     * Deletets all the triples in connection with recId in the sparql endpoint
     * 
     * @param recId Recording id to which all the information gets deleted
     */
	public static void deleteModelFromRecId(String recId) {
		UpdateRequest request = UpdateFactory.create(deleteTagTriplesString(recId));
		UpdateRequest request2 = UpdateFactory.create(deleteTagIdsString(recId));
		UpdateProcessRemote remote = new UpdateProcessRemote(request, sparqlUpdateEndpoint, null);
		UpdateProcessRemote remote2 = new UpdateProcessRemote(request2, sparqlUpdateEndpoint, null);
		remote.execute();
		remote2.execute();
	}

    /**
     * Receives an recording id and transfers it to a
     * corresponding sparql deletion update
     * 
     * @param recID Recording id
     * @return Query string for the deletion update
     */
    private static String deleteTagTriplesString(String recID) {
	String getTagTriples = "prefix p:" + "<http://dbpedia.org/property>" 
		+ " DELETE "
		+ "{ ?s ?p ?o .  } "
		+ "WHERE "
		+ "{ <"+ MappingUrl.OWLNS +recID+"> " + "<" + MappingUrl.PREFIX + MappingUrl.TAG + ">" + "?s . "
		+ "  ?s ?p ?o .}";
	return getTagTriples;
   }
    
    /**
     * Receives an recording id and transfers it to a
     * corresponding sparql deletion update
     * 
     * @param recID Recording id
     * @return Query string for the deletion update
     */
	private static String deleteTagIdsString(String recID) {
		String getTagTriples = "prefix p:" + "<http://dbpedia.org/property>" + " DELETE " + "WHERE " + "{ <"
				+ MappingUrl.PREFIX + recID + "> ?p ?s ." + "}";
		return getTagTriples;
	}
    
    
    /**
     * Gets an recording id and builds a 
     * query which then can be used to receive all the 
     * triples to the rec id from the ms5000 triple store
     * 
     * @param recID recording id
     * @return query string
     */
	private static String constructTagTriplesString(String recID) {
		String getTagTriples = "prefix p:" + "<http://dbpedia.org/property>" + " CONSTRUCT " + "{ ?s ?p ?o . } "
				+ "WHERE " + "{ <" + MappingUrl.PREFIX + recID + "> <" + MappingUrl.PREFIX + MappingUrl.TAG + "> ?s . "
				+ "  ?s ?p ?o .}" + "Limit " + NumberOfMappingValues;
		return getTagTriples;
	}
    
    /**
     * Queries the ms5000 sparql endpoint for the received
     * rec id and delivers all the triples which are connected to it
     * 
     * @param recId Recording id to which all the triples are deliverd
     * @return corresponding model containing all the triples
     */
	public static Model getModelFromRecId(String recId) {
		Query query = QueryFactory.create(constructTagTriplesString(recId));
		QueryExecution qexec = QueryExecutionFactory.sparqlService(sparqlEndpoint, query);
		Model results = qexec.execConstruct();
		return results;
	}

    
}
