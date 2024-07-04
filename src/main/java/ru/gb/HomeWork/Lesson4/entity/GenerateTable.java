package ru.gb.HomeWork.Lesson4.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateTable {
    private List<Post> posts;
    private List<PostComment> comments;
    String[] postText = {"Новости", "Мир животных", "Как правильно спросить у Яндекса", "Самое важное", "Разбор кода"};
    String[] commentText = {"Все очень сложно", "Как это у них получается?", "Ну с этим понятно", "На оси вертель эти связи"};
//    String[] users = {"Колька", "Тот самый парень", "Умник"};

    public GenerateTable() {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
        generatePosts();
        generatePostComments();
    }

    public void generatePosts() {
        long postID = 1l;
        for (String s : postText) {
            posts.add(new Post(postID, s));
            postID++;
        }

    }

    public void generatePostComments() {

        long comID = 1L;
        for (Post s : posts) {
            comments.add(new PostComment(comID, commentText[random(0, commentText.length)], s));
            comID++;
        }

    }

    private int random(int x, int y) {
        return ThreadLocalRandom.current().nextInt(x, y);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}
