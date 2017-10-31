package ${basePackage}.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages =
        {"${basePackage}.dao", "${basePackage}.service"})
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setBlockWhenExhausted(true);
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        config.setMaxTotal(32);
        config.setMaxIdle(32);
        config.setMaxWaitMillis(300 * 1000);
        config.setMinEvictableIdleTimeMillis(15 * 60 * 1000);
        return new JedisPool(config, "127.0.0.1", 6379, 20 * 1000);
    }

    @Bean
    public DataSource mysqlDataSource() {
        DataSource dataSource;
        JndiTemplate jndi = new JndiTemplate();
        try {
            dataSource = jndi.lookup("Type your jndi here", DataSource.class);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean
    LocalSessionFactoryBean factoryBean(DataSource dataSource, HibernatePropertiesConfig propertiesConfig) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setAnnotatedPackages("${basePackage}.domain");
        sfb.setPackagesToScan("${basePackage}.domain");
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        sfb.setHibernateProperties(properties);
        return sfb;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }
}
