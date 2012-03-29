package nl.hr.energize.entity;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Premises implements Iterable<Entity>, Iterator<Entity> {

	private final static String KIND = "Pand";
	
	private PreparedQuery preparedQuery;
	
	private Iterator<Entity> entities;

	public Premises() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(KIND);
		preparedQuery = datastore.prepare(query);
		fetchData();
	}
	
	@Override
	public Iterator<Entity> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return entities.hasNext();
	}

	@Override
	public Entity next() {
		return entities.next();
	}

	@Override
	public void remove() {
		entities.remove();
	}
	
	public Premises reset() {
		fetchData();
		return this;
	}
	
	private void fetchData() {
		entities = preparedQuery.asIterable().iterator();
	}

}
