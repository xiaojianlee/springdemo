package com.example.demo.wo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Post {
    private int postId;
    private String title ;
    private String text;
    private String  category;
    private User user;

    public Post(int postId, String title, String text, String category) {
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.category = category;
    }
}
