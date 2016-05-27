/**
 * 
 */
package com.bigdatafly.spring.configuration;

import java.net.MalformedURLException;

import javax.management.JMException;
import javax.management.MalformedObjectNameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.hadoop.config.common.annotation.EnableAnnotationConfiguration;
import org.springframework.jmx.access.MBeanProxyFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;

import com.sun.corba.se.spi.activation.ServerManager;

/**
 * @author summer
 *
 */
@Configuration
@EnableAnnotationConfiguration
public class JMXConfiguration {

	//"service:jmx:jmxmp://localhost:9875"
	public final static String SERVICE_URL_KEY = "";
	
	@Autowired
	Environment environment;
	/**
	 *  <bean id="serverManagerProxy" class="org.springframework.jmx.access.MBeanProxyFactoryBean"
	          p:objectName="org.springbyexample.jmx:name=ServerManager"
	          p:proxyInterface="org.springbyexample.jmx.ServerManager"
	          p:server-ref="clientConnector" />
	 * @author summer
	 * @throws JMException 
	 *
	 */
	@Bean
	public MBeanProxyFactoryBean getMBeanProxyFactoryBean() throws MalformedObjectNameException{
		
		MBeanProxyFactoryBean bean = new MBeanProxyFactoryBean();
		bean.setProxyInterface(ServerManager.class);
		bean.setObjectName("org.springbyexample.jmx:name=ServerManager");
		
		return bean;
	}
	@Bean
	public MBeanServerConnectionFactoryBean getMBeanServerConnectionFactoryBean() throws MalformedURLException{
		
		MBeanServerConnectionFactoryBean bean = new MBeanServerConnectionFactoryBean();
		bean.setServiceUrl(getServiceUrl());
		return bean;
	}
	
	protected String getServiceUrl(){
		return environment.getProperty(SERVICE_URL_KEY);
	}
}
