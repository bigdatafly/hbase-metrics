/**
 * 
 */
package com.bigdatafly.spring.configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * @author summer
 *
 */
@Configuration
@PropertySource("classpath:hbase.properties")
public class HbaseConfiguration {

	@Value("${hbase.zk.host}")
    String host;
    @Value("${hbase.zk.port}")
    String port;
    //@Resource(name = "hbaseConf")
    //private org.apache.hadoop.conf.Configuration config;

    @Bean
    public HbaseTemplate hbase() {
        HbaseTemplate hbaseTemplate = new HbaseTemplate(hbaseConf());
        return hbaseTemplate;
    }
    
    @Bean
    public org.apache.hadoop.conf.Configuration hbaseConf() {
    	org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
    	config.set("hbase.zookeeper.quorum", host);
        config.set("hbase.zookeeper.property.clientPort", port);
        return config;
    }
}
