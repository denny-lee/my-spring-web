package dev.local.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class DataStoreFactoryBean extends AbstractFactoryBean<Datastore> {
    private Morphia morphia;
    private MongoClient mongoClient;
    private String dbName;
    private boolean toEnsureIndex = false;
    private boolean toEnsureCaps = false;

    @Override
    public Class<?> getObjectType() {
        return Datastore.class;
    }

    @Override
    protected Datastore createInstance() throws Exception {
        Datastore ds = morphia.createDatastore(mongoClient, dbName);
        if (toEnsureCaps) {
            ds.ensureCaps();
        }
        if (toEnsureIndex) {
            ds.ensureIndexes();
        }
        return ds;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if (null == morphia) {
            throw new IllegalStateException("no morphia");
        }
        if (null == mongoClient) {
            throw new IllegalStateException("no mongoClient!");
        }
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isToEnsureIndex() {
        return toEnsureIndex;
    }

    public void setToEnsureIndex(boolean toEnsureIndex) {
        this.toEnsureIndex = toEnsureIndex;
    }

    public boolean isToEnsureCaps() {
        return toEnsureCaps;
    }

    public void setToEnsureCaps(boolean toEnsureCaps) {
        this.toEnsureCaps = toEnsureCaps;
    }
}
