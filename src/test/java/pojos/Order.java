package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

	@JsonProperty("petId")
	private Integer petId;

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("shipDate")
	private String shipDate;

	@JsonProperty("complete")
	private Boolean complete;

	@JsonProperty("status")
	private String status;

	public void setPetId(Integer petId){
		this.petId = petId;
	}

	public Integer getPetId(){
		return petId;
	}

	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}

	public Integer getQuantity(){
		return quantity;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setShipDate(String shipDate){
		this.shipDate = shipDate;
	}

	public String getShipDate(){
		return shipDate;
	}

	public void setComplete(Boolean complete){
		this.complete = complete;
	}

	public Boolean isComplete(){
		return complete;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}