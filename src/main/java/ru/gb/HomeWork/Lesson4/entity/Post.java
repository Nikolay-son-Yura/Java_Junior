package ru.gb.HomeWork.Lesson4.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @JoinColumn
    private String name;

    @OneToMany
    private List<PostComment> comments;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users userId;

    public Post(Long id, String name) {
        this.id = id;
        this.name = name;
//        this.userId = userId;
    }

    public Post(String name, List<PostComment> comments) {
        this.name = name;
        this.comments = comments;
    }

    public Post() {
    }
    //    public Post(String name, Users userId) {
//        this.name = name;
//        this.userId = userId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

//    public Users getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Users userId) {
//        this.userId = userId;
//    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
