package dev.local.mongo;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {
    private String[] mapPackages;
    private String[] mapClasses;
    private boolean ignore;
    @Override
    protected Morphia createInstance() throws Exception {
        Morphia morphia = new Morphia();
        if (null != mapPackages) {
            for (String pa : mapPackages) {
                morphia.mapPackage(pa, ignore);
            }
        }
        if (null != mapClasses) {
            for (String cl : mapClasses) {
                morphia.map(Class.forName(cl));
            }
        }
        return morphia;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    public String[] getMapPackages() {
        return mapPackages;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    public String[] getMapClasses() {
        return mapClasses;
    }

    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
}
