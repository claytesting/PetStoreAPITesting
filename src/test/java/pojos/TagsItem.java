package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagsItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private Integer id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}
}