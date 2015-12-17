package io.swagger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.*;

@Entity
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-12-14T16:42:38.623Z")
public class Application  {
  
	@Id
  private Long applicationId = null;
  private Long jobOfferId = null;
  private Long chefId = null;
  private Date applicationDate = null;

  
  /**
   **/
  @JsonProperty("applicationId")
  public Long getApplicationId() {
    return applicationId;
  }
  public void setApplicationId(Long applicationId) {
    this.applicationId = applicationId;
  }

  
  /**
   **/
  @JsonProperty("jobOfferId")
  public Long getJobOfferId() {
    return jobOfferId;
  }
  public void setJobOfferId(Long jobOfferId) {
    this.jobOfferId = jobOfferId;
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
  @JsonProperty("applicationDate")
  public Date getApplicationDate() {
    return applicationDate;
  }
  public void setApplicationDate(Date applicationDate) {
    this.applicationDate = applicationDate;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Application {\n");
    
    sb.append("  applicationId: ").append(applicationId).append("\n");
    sb.append("  jobOfferId: ").append(jobOfferId).append("\n");
    sb.append("  chefId: ").append(chefId).append("\n");
    sb.append("  applicationDate: ").append(applicationDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
