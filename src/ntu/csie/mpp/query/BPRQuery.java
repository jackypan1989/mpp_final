package ntu.csie.mpp.query;

import java.util.*;

public class BPRQuery extends BPR{
	
	public BPRQuery(BPR bpr){
		super(bpr);
	}

	//random ranking
	public BPRQuery(Map<String,Integer> entity_types){
		
		this.entity_types = entity_types;
		for( String s: entity_types.keySet() )
			features.put(s,Vector.rand(K));
	}
	
	//ranking with recommendation
	public BPRQuery(Map<String,Vector> fea, Map<String,Integer> entity_types){
		
		features = fea;
		this.entity_types = entity_types;
	}
	
	public List<String> query(String query,Integer type){
		
		Vector qfea = features.get(query);
		
		TreeMap<Double,String> score_id = new TreeMap(new Comparator<Double>(){
			public int compare(Double a,Double b){
				return -a.compareTo(b);
			}
		});
		
		Double score;
		for(String eid: type_entities.get(type)){
			score = qfea.dot(features.get(eid));
			score_id.put(score,eid);
		}
		
		List<String> rank = new ArrayList();
		for(String id: score_id.values()){
			if(!query.equals(id))
				rank.add(id);
		}

		return rank;
	}
	
	//input: query=[id1,id2,...]
	//output: ranking list=[id1,id2...]
	public List<String> query(Collection<String> query,Integer type){
		
		Vector qfea ;
		qfea = cooccurFea(query);
		/*
		qfea = Vector.zeros(K);
		for(String id: query){
			qfea = qfea.add(features.get(id));
		}
		*/
		TreeMap<Double,String> score_id = new TreeMap(new Comparator<Double>(){
			public int compare(Double a,Double b){
				return -a.compareTo(b);
			}
		});
		
		Double score;
		for(String eid: type_entities.get(type)){
			
			score = qfea.dot(features.get(eid));
			score_id.put(score,eid);
		}
		
		List<String> rank = new ArrayList();
		for(String id: score_id.values()){
			if(!query.contains(id))
				rank.add(id);
		}

		return rank;
	}
}
