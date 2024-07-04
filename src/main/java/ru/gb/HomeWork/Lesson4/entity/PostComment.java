package ru.gb.HomeWork.Lesson4.entity;

import javax.persistence.*;

@Entity
@Table(name = "post_Comment")
public class PostComment {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    @JoinColumn
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users userId;



    public PostComment(Long id, String text, Post post) {
        this.id = id;
        this.text = text;
        this.post = post;
//        this.userId = userId;
    }

    public PostComment(String text, Post post) {
        this.text = text;
        this.post = post;
    }

    public PostComment() {
    }
    //    public PostComment(String text, Post post_id, Users userId) {
//        this.text = text;
//        this.post_id = post_id;
//        this.userId = userId;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Users getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Users userId) {
//        this.userId = userId;
//    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "post_id=" + post +
                ", id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
