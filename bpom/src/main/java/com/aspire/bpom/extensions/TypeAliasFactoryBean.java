package com.aspire.bpom.extensions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;


public class TypeAliasFactoryBean implements FactoryBean<Class<?>[]>, InitializingBean {

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    private Class<?>[] typeAliases;

    private Resource[] aliasClassFiles;

    public Resource[] getAliasClassFiles() {
        return aliasClassFiles;
    }

    public void setAliasClassFiles(Resource[] aliasClassFiles) {
        this.aliasClassFiles = aliasClassFiles;
    }

    /**
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Class<?>> aliasList = new ArrayList<>();
        for (Resource resource : aliasClassFiles) {
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    Class<?> clazz = ClassUtils.getClass(metadataReader.getClassMetadata().getClassName());
                    aliasList.add(clazz);
                } catch (Throwable ex) {
                }
            } else {
            }
        }
        typeAliases = new Class<?>[aliasList.size()];
        for (int i = 0; i < aliasList.size(); i++) {
            typeAliases[i] = aliasList.get(i);
        }
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public Class<?>[] getObject() throws Exception {
        return typeAliases;
    }

    /**
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return Class[].class;
    }

    /**
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

}
