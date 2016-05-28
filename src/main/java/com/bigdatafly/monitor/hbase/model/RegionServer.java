/**
 * 
 */
package com.bigdatafly.monitor.hbase.model;

/**
 * @author summer
 *
 */
@Deprecated
public class RegionServer implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2010866062483074517L;

	private String key;
	private RegionServerValue value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public RegionServerValue getValue() {
		return value;
	}
	public void setValue(RegionServerValue value) {
		this.value = value;
	}
	
	

}
