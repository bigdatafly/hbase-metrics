/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.util.Map;
import java.util.Set;

import org.apache.storm.shade.com.google.common.collect.Sets;
import org.apache.storm.shade.org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public class JmxQueryConstants {

	public static final String MASTER_MONITOR_TYPE="1";
	public static final String REGION_SERVER_MONITOR_TYPE="2";
	public static final String REGION_MONITOR_TYPE="3";
	public static final String JVM_MONITOR_TYPE="4";
	
	//---------------------------MonitorType 1--master performances index begin-------------------------------------//
	public static final String URL_MASTER_BALANCER = "Hadoop:service=HBase,name=Master,sub=Balancer";
	
	/*
	 * {
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
	
	public static final String URL_REGIONSERVER = "Hadoop:service=HBase,name=Master,sub=Server";
	
	public static final String LIVE_REGION_SERVERS_TAG = "tag.liveRegionServers";
	public static final String clusterId = "tag.clusterId";
	public static final String liveRegionServers="tag.liveRegionServers";
	public static final String deadRegionServers="tag.deadRegionServers";
	public static final String zookeeperQuorum="tag.zookeeperQuorum";
	public static final String serverName="tag.serverName";		
	public static final String isActiveMaster="tag.isActiveMaster";	
	public static final String masterActiveTime = "masterActiveTime";
	public static final String masterStartTime = "masterStartTime";
	public static final String averageLoad = "averageLoad";
	public static final String numRegionServers="numRegionServers";
	public static final String numDeadRegionServers="numDeadRegionServers";

	public static final Set<String> MASTER_PERFORMANCES_INDEX = Sets.newHashSet
			(new String[]{
					LIVE_REGION_SERVERS_TAG,
					clusterId,
					liveRegionServers,
					deadRegionServers,
					zookeeperQuorum,
					serverName,
					isActiveMaster,
					masterActiveTime,
					masterStartTime,
					averageLoad,
					numRegionServers,
					numDeadRegionServers
					});
	public static final Map<String,String> PERFORMANCES_MAP = Maps.newHashMap();
	
	static{
	
		for(String PERFORMANCES_INDEX:MASTER_PERFORMANCES_INDEX){
			PERFORMANCES_MAP.put(PERFORMANCES_INDEX, MASTER_MONITOR_TYPE+StringUtils.leftPad(String.valueOf(PERFORMANCES_MAP.size()+1), 3,"0") );
		}
	}
	
	public static String getMonitorTypeByItem(String item){
		return PERFORMANCES_MAP.get(item);
	}
	
	//-----------------------------master performances index end-------------------------------------//

	//---------------------------MonitorType 2--regionserver performances index begin-------------------------------------//
	
	public static final String totalRequestCount = "totalRequestCount";
	public static final String readRequestCount = "readRequestCount";
	public static final String writeRequestCount="writeRequestCount";
	public static final String blockCacheSize="blockCacheSize";
	public static final String blockCacheHitCount="blockCacheHitCount";
	public static final String regionCount="regionCount";		
	public static final String blockCacheCountHitPercent = "blockCacheCountHitPercent";
	public static final String blockCacheEvictionCount="blockCacheEvictionCount";
	public static final String blockCacheMissCount="blockCacheMissCount";

	public static final Set<String> REGION_SERVER_PERFORMANCES_INDEX = Sets.newHashSet
			(new String[]{
					totalRequestCount,
					readRequestCount,
					writeRequestCount,
					blockCacheSize,
					blockCacheHitCount,
					blockCacheMissCount,
					blockCacheEvictionCount,
					blockCacheCountHitPercent,
					regionCount
					});
	//public static final Map<String,String> REGION_SERVER_PERFORMANCES_MAP = Maps.newHashMap();
	
	static{
	
		for(String PERFORMANCES_INDEX:REGION_SERVER_PERFORMANCES_INDEX){
			PERFORMANCES_MAP.put(PERFORMANCES_INDEX, REGION_SERVER_MONITOR_TYPE+StringUtils.leftPad(String.valueOf(PERFORMANCES_MAP.size()+1), 3,"0") );
		}
	}
	/*
	public static String getMonitorTypeByItem(String item){
		return PERFORMANCES_MAP.get(item);
	}*/
	//---------------------------MonitorType 2--regionserver performances index end-------------------------------------//
}
