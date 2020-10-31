package com.oracle.mybatis.domain;

//import java.util.Date;
import java.util.List;

public class UserDO {
   private Integer id;
   private String userName;
   private String password;
   private Integer sex;
   private List<String> hobbies;  

   public Integer getId() {
       return id;
   }

   public void setId(Integer id) {
       this.id = id;
   }

   public String getUserName() {
       return userName;
   }

   public void setUserName(String userName) {
       this.userName = userName;
   }

   public String getPassword() {
       return password;
   }

   public void setPassword(String password) {
       this.password = password;
   }

   public Integer getSex() {
       return sex;
   }

   public void setSex(Integer sex) {
       this.sex = sex;
   }

      
   public List<String> getHobbies() {
	return hobbies;
}

public void setHobbies(List<String> hobbies) {
	this.hobbies = hobbies;
}


}