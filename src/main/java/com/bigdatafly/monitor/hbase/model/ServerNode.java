/**
 * 
 */
package com.bigdatafly.monitor.hbase.model;

/**
 * @author summer
 *
 */
public class ServerNode implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4701904926135126602L;
	
	public static final String MASTER_MONITOR_TYPE =  "1";
	public static final String SLAVE_MONITOR_TYPE = "2";
	
	String serverName;
	String monitorType;
	String online;
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getMonitorType() {
		return monitorType;
	}
	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}
	
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	@Override
	public String toString() {
		
		return "{\"serverName\":"
				+serverName
				+",\"monitorType\":"+monitorType
				+",\"online\":"
				+online+"}";
	}
	
	
}
