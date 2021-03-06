package org.appsugar.integration.data.jpa.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.appsugar.integration.data.jpa.entity.Person;
import org.dbunit.ant.Operation;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DefaultMetadataHandler;
import org.dbunit.database.ForwardOnlyResultSetTableFactory;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;

@EntityScan(basePackageClasses = Person.class)
@SpringBootApplication
@TestConfiguration
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@Import(PersonStatRepository.class)
public class RepositoryTestConfiguration {

    private static final String BASE_PAT = "src/test/resources/data/";
    private static final String[] SAMPLE_FILES = new String[]{"sample-data.xml", "other-module-data.xml"};

    /**
     * 保证每个test方法执行前,数据都是最新的
     */
    @Autowired
    public void dbunitCleanInstall(DataSource dataSource) throws Exception {
        Connection con = dataSource.getConnection();
        DatabaseConnection connection = new DatabaseConnection(con);
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, new ForwardOnlyResultSetTableFactory());
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new DefaultMetadataHandler());
        for (String fileName : SAMPLE_FILES) {
            Operation operation = new Operation();
            operation.setTransaction(true);
            operation.setType("INSERT");
            operation.setFormat("flat");
            operation.setSrc(new File(BASE_PAT + fileName));
            operation.execute(connection);
        }
        connection.close();
    }
}
