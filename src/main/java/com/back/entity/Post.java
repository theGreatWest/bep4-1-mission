package com.back.entity;


import com.back.jpa.entity.BaseIdAndTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
public class Post extends BaseIdAndTime {
    // Post : Member = N : 1
    // LAZY : 지연로딩, 필요할 때 DB에서 조회
    // EAGER : 즉시로딩, Post 조회 시 Member도 무조건 같이 Join해서 조회
    @ManyToOne(fetch = LAZY)
    private Member author;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToMany(mappedBy = "post", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(Member author, String content) {
        PostComment postComment = new PostComment(this, author, content);

        comments.add(postComment);

        return postComment;
    }

    public boolean hasComments() {
        return !comments.isEmpty();
    }
}