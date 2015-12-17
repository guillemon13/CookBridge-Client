package io.swagger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-12-14T16:42:38.623Z")
public class Work  {
  
	@Id
  private Long id = null;
  private Long restaurantId = null;
  private Long chefId = null;
  private String position = null;
  private Date beginDate = null;
  private Date endDate = null;

  
  /**
   **/
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  
  /**
   **/
  @JsonProperty("restaurantId")
  public Long getRestaurantId() {
    return restaurantId;
  }
  public void setRestaurantId(Long restaurantId) {
    this.restaurantId = restaurantId;
  }

  
  /**
   **/
  @JsonProperty("chefId")
  public Long getChefId() {
    return chefId;
  }
  public void setChefId(Long chefId) {
    this.chefId = chefId;
  }

  
  /**
   **/
  @JsonProperty("position")
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }

  
  /**
   **/
  @JsonProperty("beginDate")
  public Date getBeginDate() {
    return beginDate;
  }
  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  
  /**
   **/
  @JsonProperty("endDate")
  public Date getEndDate() {
    return endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Work {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  restaurantId: ").append(restaurantId).append("\n");
    sb.append("  chefId: ").append(chefId).append("\n");
    sb.append("  position: ").append(position).append("\n");
    sb.append("  beginDate: ").append(beginDate).append("\n");
    sb.append("  endDate: ").append(endDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
