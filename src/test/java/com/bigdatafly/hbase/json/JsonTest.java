/**
 * 
 */
package com.bigdatafly.hbase.json;

import java.util.List;

import org.junit.Test;
import org.mortbay.util.ajax.JSON;

import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.utils.JsonUtils;

/**
 * @author summer
 * The mapping from JSON to java is:<pre>
 *   object ==> Map
 *   array  ==> Object[]
 *   number ==> Double or Long
 *   string ==> String
 *   null   ==> null
 *   bool   ==> Boolean
 * </pre>
 * </p><p>
 * The java to JSON mapping is:<pre>
 *   String --> string
 *   Number --> number
 *   Map    --> object
 *   List   --> array
 *   Array  --> array
 *   null   --> null
 *   Boolean--> boolean
 *   Object --> string (dubious!)
 */
public class JsonTest {

	static String strJson = "{\"beans\" : [ {\"name\" : \"Hadoop:service=HBase,name=Master,sub=Server\","
			+ "\"modelerType\" : \"Master,sub=Server\","
			+ "\"tag.liveRegionServers\" : \"slave05,16020,1464069701740;slave06,16020,1464069701296;slave08,16020,1464069700273;slave03,16020,1464069701504;slave04,16020,1464069701719;slave02,16020,1464069701865;slave10,16020,1464069701423;slave01,16020,1464069701636\","
			+ "\"tag.deadRegionServers\" : \"\","
			+ "\"tag.zookeeperQuorum\" : \"slave01:2181,master:2181,slave02:2181\","
			+ "\"tag.serverName\" : \"master,16020,1464069684113\","
			+ "\"tag.clusterId\" : \"75102093-713d-408e-a8ab-20e68eab3409\","
			+ "\"tag.isActiveMaster\" : \"true\","
			+ "\"tag.Context\" : \"master\","
			+ "\"tag.Hostname\" : \"master\","
			+ "\"masterActiveTime\" : 1464069699302,"
			+ "\"masterStartTime\" : 1464069684113,"
			+ "\"averageLoad\" : 4.125,\"numRegionServers\" : 8,"
			+ "\"numDeadRegionServers\" : 0,\"clusterRequests\" : 2554} ]}";
	
	@Test
	public void testJson(){
		Object obj = new JSON().fromJSON(strJson);
		System.out.println(obj.toString());
		System.out.println("**************************************");
		Beans aa = JsonUtils.fromJson(strJson, Beans.class);
		System.out.println(aa.getBeans());
		System.out.println("**************************************");
		System.out.println(JsonUtils.toJson(aa));
	}
	
	@Test
	public void messageParserTest(){
		
		System.out.println("*****************regionservers hostname*********************");
		Beans beans = JsonUtils.fromJson(strJson, Beans.class);
		List<String> regionServerHosts = MessageParser.getLiveRegionServerFromJmxMessage(beans);
		System.out.println(regionServerHosts);
		
		List<String> deadRegionServerHosts = MessageParser.getDeadRegionServerFromJmxMessage(beans);
		System.out.println(deadRegionServerHosts);
	}

}
