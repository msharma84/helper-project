package com;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

	public static void main(String[] args) {
		
		Condition c2 = new Condition("Status","Fixed");
		Condition c3 = new Condition("Status","Pending");
		Condition c1 = new Condition("Priority","P1");
		
		List<Condition> conditionList = new ArrayList<Condition>();
		conditionList.add(c1);
		conditionList.add(c2);
		conditionList.add(c3);
		
		String query = "",initialQuery ="";
		int conditionListSize = conditionList.size();
		if(!conditionList.isEmpty()) {
			int i = 0 ;
			for(Condition condition : conditionList) {
				if(i==0) {
					initialQuery = "select definition_id from definition_condition_rules where"
							+" criteria_attribute='"+condition.getAttribute()+"' and criteria_value_display='"+condition.getValue()+"'";
				}else {
					query = "select definition_id from definition_condition_rules where definition_id in (" +initialQuery
							+") and criteria_attribute='"+condition.getAttribute()+"' and criteria_value_display='"+condition.getValue()+"'";
					initialQuery = query;
				}
				i++;
				if(conditionListSize == i) {
					query = initialQuery;
				}
			}
			String definitionRecord = "select * from definition_tbl where definition_id in ("+query+")";
			System.out.println("Query --->"+definitionRecord);
		}
	}
}

class Condition {
	
	public Condition(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	private String attribute;
	
	private String value;

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
