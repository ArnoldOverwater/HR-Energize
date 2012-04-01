package nl.hr.energize.entity;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Entities implements Iterable<Entity> {

	private DatastoreService datastore;
	
	private EntityKind kind;
	
	private PreparedQuery preparedQuery;
	
	public Entities() {
		this(DatastoreServiceFactory.getDatastoreService(), null);
	}
	
	public Entities(DatastoreService datastore) {
		this(datastore, null);
	}
	
	public Entities(EntityKind kind) {
		this(DatastoreServiceFactory.getDatastoreService(), kind);
	}
	
	public Entities(DatastoreService datastore, EntityKind kind) {
		this.datastore = datastore;
		if (kind == null)
			setKind(EntityKind.Premises);
		else
			setKind(kind);
	}
	
	public Entities setDatastore(DatastoreService datastore) {
		if (datastore != null)
			this.datastore = datastore;
		return this;
	}
	
	public Entities setKind(EntityKind kind) {
		if (kind != null) {
			this.kind = kind;
			Query query = new Query(kind.kindName);
			preparedQuery = datastore.prepare(query);
		}
		return this;
	}
	
	public DatastoreService getDatastore() {
		return datastore;
	}
	
	public EntityKind getKind() {
		return kind;
	}
	
	public Iterable<Entity> iterable() {
		return preparedQuery.asIterable();
	}

	@Override
	public Iterator<Entity> iterator() {
		return iterable().iterator();
	}
	
	public enum EntityKind {
		
		Premises("Pand"), WeekMeasures("Weekmeting"), Temperature("Temperatuur");
		
		private String kindName;

		private EntityKind(String kindName) {
			this.kindName = kindName;
		}
		
		public String getKindName() {
			return kindName;
		}

	}

}
