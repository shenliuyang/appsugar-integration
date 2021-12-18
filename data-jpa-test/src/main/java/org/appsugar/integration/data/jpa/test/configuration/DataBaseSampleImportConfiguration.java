package org.appsugar.integration.data.jpa.test.configuration;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.SneakyThrows;
import org.dbunit.DatabaseUnitException;
import org.dbunit.ant.Operation;
import org.dbunit.database.*;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>数据库取样配置</p>
 * <p>如需关闭 dbunit.enabled=false</p>
 * <p>操作类型 dbunit.operationType=CLEAN_INSERT</p>
 * <p>取样文件根路径dbunit.sampleDir="src/test/resources/data/" </p>
 * <p>取样文件集dbunit.sampleFiles[0]="sample-data.xml" </p>
 * <p>h2以外的数据库需要而外配置</p>
 * <h1>mysql<h1/> <p style="color:red">dbunit.dataTypeFactoryClass=org.dbunit.ext.mysql.MySqlDataTypeFactory<p/>
 * <p style="color:red">dbunit.metadataHandlerClass=org.dbunit.ext.mysql.MySqlMetadataHandler<p/>
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.test.configuration
 * @className DataBaseSampleImportConfiguration
 * @date 2021-12-18  15:21
 */
@Configuration
@EnableConfigurationProperties({DataBaseSampleImportConfiguration.DbunitConfig.class})
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@ConditionalOnProperty(name = "dbunit.enabled", matchIfMissing = true, havingValue = "true")
public class DataBaseSampleImportConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseSampleImportConfiguration.class);

    @Lazy(false)
    @Autowired
    @SneakyThrows
    public void configure(DataSource dataSource, DbunitConfig dbunitConfig) {
        logger.info("start to import test db config is {}", dbunitConfig);
        Connection con = dataSource.getConnection();
        DatabaseConnection connection = new DatabaseConnection(con);
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, new ForwardOnlyResultSetTableFactory());
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, dbunitConfig.dataTypeFactoryClass.getConstructor().newInstance());
        config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, dbunitConfig.metadataHandlerClass.getConstructor().newInstance());
        List<IDataSet> iDataSets = new ArrayList<>();
        for (String fileName : dbunitConfig.sampleFiles) {
            iDataSets.add(new FlatXmlDataSetBuilder().build(new File(dbunitConfig.sampleDir + fileName)));
        }
        Operation operation = new Operation() {
            @Override
            protected IDataSet getSrcDataSet(File src, String format, boolean forwardOnly) throws DatabaseUnitException {
                IDataSet[] sets = iDataSets.stream().toArray(it -> new IDataSet[it]);
                return new CompositeDataSet(sets);
            }
        };
        operation.setTransaction(true);
        operation.setType("INSERT");
        operation.setFormat("flat");
        operation.execute(connection);
        connection.close();
        logger.info("success to import test db");
    }

    @ConfigurationProperties("dbunit")
    @Data
    public static class DbunitConfig {
        private String sampleDir = "src/test/resources/data/";
        private List<String> sampleFiles = Lists.newArrayList("sample-data.xml");
        private String operationType = "CLEAN_INSERT";
        private Class<? extends IDataTypeFactory> dataTypeFactoryClass = H2DataTypeFactory.class;
        private Class<? extends IMetadataHandler> metadataHandlerClass = DefaultMetadataHandler.class;
    }
}
