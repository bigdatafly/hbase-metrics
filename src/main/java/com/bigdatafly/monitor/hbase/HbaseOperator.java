/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;


/**
 * @author summer
 *
 */
public class HbaseOperator {

	Configuration hbaseConfig;
	RowkeyGenerator rowkeyGenerator;
	
	static{
		System.setProperty("HADOOP_USER_NAME", "hadoop");
	}
	
	public HbaseOperator(){
		
		rowkeyGenerator = RowkeyGenerator.builder();
	}
	
	Configuration createHBaseConfiguration(String zk,int port){
		
		 Configuration hbaseConfig = HBaseConfiguration.create();
		 hbaseConfig.set("hbase.zookeeper.quorum", zk);
		 hbaseConfig.set("hbase.zookeeper.property.clientPort", String.valueOf(port));
		 return hbaseConfig;
	}
	
	/*
	 * 
	 *  Connection connection = ConnectionFactory.createConnection(conf);
   * Table table = connection.getTable(TableName.valueOf("mytable"));
   * try {
   *   table.get(...);
   *   ...
   * } finally {
   *   table.close();
   *   connection.close();
   * }
	 */
	
	protected Connection createConnection() throws IOException{
		User user = User.createUserForTesting(hbaseConfig, "hadoop",new String[]{"hadoop"});
		Connection connection = ConnectionFactory.createConnection(hbaseConfig,user);
		return connection;
	}
	
	protected void put(String tableName,String rowkey,String family,Map<String,Object> values) throws IOException{
		
		Table table = null;
		try(Connection connection = createConnection()){
			 table = connection.getTable(TableName.valueOf(tableName));
			Put put = serializer(rowkey,family,values);
			table.put(put);
		}catch(IOException ex){
			throw ex;
		}finally{
			if(table != null)
				table.close();
		}
		
		
	}
	
	protected Put serializer(String rowkey,String family,Map<String,Object> cf){
		Put put = new Put(Bytes.toBytes(rowkey));
		for(Map.Entry<String, Object> e : cf.entrySet()){
			String qualifier = e.getKey();
			Object value = e.getValue();
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value.toString()));
		}
		return put;
	}
	
	protected String RowkeyGenerator(){
		return "";
	}
	
	protected void createTable(String tableName,String family){
		
		try(Connection connection = createConnection()){
			Admin admin = connection.getAdmin();
			if(!admin.tableExists(TableName.valueOf(tableName))){
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(
						family);
				tableDescriptor.addFamily(columnDescriptor);
				admin.createTable(tableDescriptor);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
