package io.swagger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-12-14T16:42:38.623Z")
public class NewChef  {
  
  private String name = null;
  private String password = null;
  private String gender = null;
  private Date birthDate = null;

  
  /**
   **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  
  /**
   **/
  @JsonProperty("gender")
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }

  
  /**
   **/
  @JsonProperty("birthDate")
  public Date getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewChef {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  gender: ").append(gender).append("\n");
    sb.append("  birthDate: ").append(birthDate).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
