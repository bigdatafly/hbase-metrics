/**
 * 
 */
package com.bigdatafly.hbase.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mortbay.util.ajax.JSON;

import com.google.gson.Gson;

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

	static class Beans{
		List<AS> as;
	}
	static class AS{
		
		A a;
		B b;
		List<C> c;
		D d;
		public A getA() {
			return a;
		}
		public void setA(A a) {
			this.a = a;
		}
		public B getB() {
			return b;
		}
		public void setB(B b) {
			this.b = b;
		}
		public List<C> getC() {
			return c;
		}
		public void setC(List<C> c) {
			this.c = c;
		}
		public D getD() {
			return d;
		}
		public void setD(D d) {
			this.d = d;
		}
		
		@Override
		public String toString(){
			return this.a + "|" + this.b + "|" + this.c + "|" +this.d;
		}
	}
	
	static class A{
		String a1 = "111";
		String a2 = "222";
		public String getA1() {
			return a1;
		}
		public void setA1(String a1) {
			this.a1 = a1;
		}
		public String getA2() {
			return a2;
		}
		public void setA2(String a2) {
			this.a2 = a2;
		}
		
		public String toString(){
			return a1 + "|" +a2;
		}
	}
	
	static class B{
		String b1;
		String b2;
		public String getB1() {
			return b1;
		}
		public void setB1(String b1) {
			this.b1 = b1;
		}
		public String getB2() {
			return b2;
		}
		public void setB2(String b2) {
			this.b2 = b2;
		}
		
	}
	
	static class C{
		String c1;
		String c2;
		public String getC1() {
			return c1;
		}
		public void setC1(String c1) {
			this.c1 = c1;
		}
		public String getC2() {
			return c2;
		}
		public void setC2(String c2) {
			this.c2 = c2;
		}
		
	}
	
	static class D{
		String d1;
		String d2;
		public String getD1() {
			return d1;
		}
		public void setD1(String d1) {
			this.d1 = d1;
		}
		public String getD2() {
			return d2;
		}
		public void setD2(String d2) {
			this.d2 = d2;
		}
		
		
	}
	/**
	 * {"ch":[{
	 * "names":"怡美家园",
	 * "data":[2,2,1,1,1,1],
	 * "times":[10,11,13,13,21,23]}]}  
	 */
	static class AA{
		List<A> as = new ArrayList<A>();
		
		public AA(){
			as.add(new A());
		}
		
		public List<A> getAs(){
			return as;
		}
		
		@Override
		public String toString(){
			return as.toString();
		}
	}
	@Test
	public void testJson(){
		
		String strJson = "{\"beans\":["
				+"{\"a1\":a1,\"a2\":a2},"
				+"{\"b1\":b1,\"b2\":b2},"
				+"[{\"c1\":c1,\"c2\":c2}],"
				+"{\"d1\":d1,\"d2\":d2}"
				+ "]}";
		
		//strJson = "{\"beans\":111}";
		JSON json = new JSON();
		//Object obj = json.fromJSON(strJson);
		//System.out.println(json.fromJSON(strJson));
		//System.out.println(new Gson().fromJson(strJson, Beans.class));
		
		List<A> as = new ArrayList<A>();
		as.add(new A());
		System.out.println(new Gson().toJson(new AA()));
		
		String json1 ="{as:[{a1:111,a2:222}]}";
		AA aa = new Gson().fromJson(json1, AA.class);
		System.out.println(aa.getAs().toString());
	}

}
