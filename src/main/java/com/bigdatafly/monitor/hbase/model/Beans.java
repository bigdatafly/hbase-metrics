/**
 * 
 */
package com.bigdatafly.monitor.hbase.model;

import java.util.List;
import java.util.Map;

/**
 * @author summer
 *** {
  "beans" : [ {
    "name" : "Hadoop:service=HBase,name=Master,sub=Server",
    "modelerType" : "Master,sub=Server",
    "tag.liveRegionServers" : "slave05,16020,1464069701740;slave06,16020,1464069701296;slave08,16020,1464069700273;slave03,16020,1464069701504;slave04,16020,1464069701719;slave02,16020,1464069701865;slave10,16020,1464069701423;slave01,16020,1464069701636",
    "tag.deadRegionServers" : "",
    "tag.zookeeperQuorum" : "slave01:2181,master:2181,slave02:2181",
    "tag.serverName" : "master,16020,1464069684113",
    "tag.clusterId" : "75102093-713d-408e-a8ab-20e68eab3409",
    "tag.isActiveMaster" : "true",
    "tag.Context" : "master",
    "tag.Hostname" : "master",
    "masterActiveTime" : 1464069699302,
    "masterStartTime" : 1464069684113,
    "averageLoad" : 4.125,
    "numRegionServers" : 8,
    "numDeadRegionServers" : 0,
    "clusterRequests" : 2554
  } ]
}
	 
 */
public class Beans implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8155825278650454988L;
	
	List<Map<String,String>> beans;

	public List<Map<String, String>> getBeans() {
		return beans;
	}

	public void setBeans(List<Map<String, String>> beans) {
		this.beans = beans;
	}
}
