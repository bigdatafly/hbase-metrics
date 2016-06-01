/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.google.common.collect.Maps;


/**
 * @author summer
 *
 */
public abstract class HbaseOperator {

	Configuration hbaseConfig;
	RowkeyGenerator rowkeyGenerator;
	HbaseMonitorConfiguration conf;

	
	public HbaseOperator(HbaseMonitorConfiguration conf){
		this.conf = conf;
		rowkeyGenerator = RowkeyGenerator.builder();
		this.hbaseConfig = createHBaseConfiguration(conf.getHbaseZookeeperHost(), conf.getHbaseZookeeperPort());
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
	
	protected void put(String tableName,String family,Map<String,Object> values) throws IOException{
		
		Table table = null;
		try(Connection connection = createConnection()){
			table = connection.getTable(TableName.valueOf(tableName));
			List<Put> puts = new ArrayList<Put>();
			for(Map.Entry<String,Object> e : values.entrySet()){
				String key = e.getKey();
				key = JmxQueryConstants.getMonitorTypeByItem(key);
				Object value = e.getValue();
				String rowkey = rowkeyGenerator(getServername(),key);
				Put put = serializer(rowkey,family,key,value);
				puts.add(put);
			}
			
			table.put(puts);
		}catch(IOException ex){
			throw ex;
		}finally{
			if(table != null)
				table.close();
		}
		
		
	}
	

	protected String getServername() {
		
		return null;
	}

	
	protected Put serializer(String rowkey,String family,String qualifier,Object value){
		Put put = new Put(Bytes.toBytes(rowkey));
		put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value.toString()));
		return put;
	}
	/*
	protected Put serializer(String rowkey,String family,Map<String,Object> cf){
		Put put = new Put(Bytes.toBytes(rowkey));
		for(Map.Entry<String, Object> e : cf.entrySet()){
			String qualifier = e.getKey();
			Object value = e.getValue();
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value.toString()));
		}
		return put;
	}
	*/
	protected abstract String rowkeyGenerator(String serverName,String key);
	
	protected void createTable(String tableName,String family){
		
		Admin admin = null;
		try(Connection connection = createConnection()){
			admin = connection.getAdmin();
			if(!admin.tableExists(TableName.valueOf(tableName))){
				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(
						family);
				tableDescriptor.addFamily(columnDescriptor);
				admin.createTable(tableDescriptor);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			if(admin!=null)
				try {
					admin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	protected void dropTable(String tableName){
		Admin admin = null;
		try(Connection connection = createConnection()){
			admin = connection.getAdmin();
			if(admin.tableExists(TableName.valueOf(tableName)) && admin.isTableEnabled(TableName.valueOf(tableName))){
				admin.disableTable(TableName.valueOf(tableName));
				admin.deleteTable(TableName.valueOf(tableName));
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			if(admin!=null)
				try {
					admin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public Map<String,String> findAll(final String tableName, final String family) throws IOException{
		
		Map<String,String> rs = Maps.newHashMap();
		Table table = null;
		try(Connection connection = createConnection()){
			table = connection.getTable(TableName.valueOf(tableName));
			ResultScanner scanner = table.getScanner(Bytes.toBytes(family));
			String key;
			String value;
			for(Result result:scanner){
				List<Cell> cells = result.listCells();
				for(Cell cell:cells){
					key = Bytes.toString(CellUtil.cloneQualifier(cell));
					value  = Bytes.toString(CellUtil.cloneValue(cell));
					rs.put(key, value);
				}
			}
			
		}catch(IOException ex){
			throw ex;
		}finally{
			if(table != null)
				table.close();
		}
		
		return rs;
		
	}

	public boolean exists(String tableName, String keyrow) throws IOException{
		
		Table table = null;
		try(Connection connection = createConnection()){
			table = connection.getTable(TableName.valueOf(tableName));
			Get get = new Get(Bytes.toBytes(keyrow));
			return table.exists(get);
			
		}catch(IOException ex){
			throw ex;
		}finally{
			if(table != null)
				table.close();
		}
	}
	
}
