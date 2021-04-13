package org.appsugar.integration.data.jpa.generator;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * 每个实体类对应一个ID生成器实例对象
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.archetypes.hibernate
 * @className SnowflakeIdGenerator
 * @date 2021-03-27  09:56
 */
@Slf4j
public class SnowflakeIdGenerator implements IdentifierGenerator, Configurable {
    private static final String ENV_PREFIX = "snowflake";
    public static final String ENV_WORK_ID = ENV_PREFIX + "WorkId";
    public static final String ENV_DATACENTER_ID = ENV_PREFIX + "DataCenterId";
    protected SnowflakeIdWorker worker;


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return worker.nextId();
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        ConfigurationService configurationService = serviceRegistry.getService(ConfigurationService.class);
        Properties p = new Properties();
        p.putAll((Map<Object, Object>) configurationService.getSettings());
        p.putAll(System.getenv());
        long workerId = Long.parseLong(p.getProperty(ENV_WORK_ID, "0"));
        long datacenterId = Long.parseLong(p.getProperty(ENV_DATACENTER_ID, "0"));
        log.debug("load snowFlakeId from system property and env  workId is {} dataCenterId is {}", workerId, datacenterId);
        this.worker = new SnowflakeIdWorker(workerId, datacenterId);
        p.clear();
    }

}
