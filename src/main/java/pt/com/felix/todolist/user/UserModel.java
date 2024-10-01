package pt.com.felix.todolist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

@Data //este @Data dá os gets e sets automaticamente
@Entity (name = "tb_user")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID userID;

    @Column(unique = true)
    private String username;

    private String name;
    private int age;
    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    //#region GETS&SETS
    //GETS E SETS, Lombox é uma libary que ajuda a fazer metodos como os gets e set mais facilmente
    
    /* 

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    
    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    */

    //#endregion
   
}
