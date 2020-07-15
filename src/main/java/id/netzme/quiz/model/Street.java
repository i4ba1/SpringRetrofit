package id.netzme.quiz.model;

import com.google.gson.annotations.SerializedName;

public class Street{

	@SerializedName("number")
	private int number;

	@SerializedName("name")
	private String name;

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"Street{" + 
			"number = '" + number + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}