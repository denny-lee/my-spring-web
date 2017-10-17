package dev.local.mongo;

import com.mongodb.*;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.ArrayList;
import java.util.List;

public class MongoFactoryBean extends AbstractFactoryBean<MongoClient> {
    private String[] servers;
    private List<MongoCredential> mongoCredentials;
    private MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder().build();

    @Override
    public Class<?> getObjectType() {
        return MongoClient.class;
    }

    @Override
    protected MongoClient createInstance() throws Exception {
        MongoClient mongoClient = null;
        if (null != mongoClientOptions) {
            mongoClient = new MongoClient(getServerList(), mongoCredentials, mongoClientOptions);
        } else {
            mongoClient = new MongoClient(getServerList(), mongoCredentials);
        }
        return mongoClient;
    }

    private List<ServerAddress> getServerList() throws Exception {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        try {
            for (String s : servers) {
                String[] temp = s.split(":");
                String host = temp[0];
                if (temp.length > 2) {
                    throw new IllegalArgumentException("");
                } else if (temp.length == 2) {
                    serverAddresses.add(new ServerAddress(host, Integer.parseInt(temp[1])));
                } else {
                    serverAddresses.add(new ServerAddress(host));
                }
            }
            return serverAddresses;
        } catch (IllegalArgumentException e) {
            throw new Exception("");
        }
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public MongoClientOptions getMongoClientOptions() {
        return mongoClientOptions;
    }

    public void setMongoClientOptions(MongoClientOptions mongoClientOptions) {
        this.mongoClientOptions = mongoClientOptions;
    }

    public List<MongoCredential> getMongoCredentials() {
        return mongoCredentials;
    }

    public void setMongoCredentials(List<MongoCredential> mongoCredentials) {
        this.mongoCredentials = mongoCredentials;
    }
}
