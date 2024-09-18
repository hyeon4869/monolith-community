package community.community.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "POST")
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String content;

    
}
