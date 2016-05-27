/**
 * 
 */
package com.bigdatafly.storm.nlp;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * @author summer
 *
 */
public class WordCountStorm extends BaseRichBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	OutputCollector _collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		
		this._collector = collector;
		
	}

	@Override
	public void execute(Tuple input) {
		
		this._collector.emit(input,  new Values(input.getString(0)));
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("word","count"));
		
	}

}
