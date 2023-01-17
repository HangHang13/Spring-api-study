package com.example.apibasic.post.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 게시물의 데이터 자바빈즈
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="postId")
@Entity
@Table(name="post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //기본키 생성 전략
    @Column(name="post_id")
    private Long postId;


    @Column(name="writer", unique = true, length = 30)
    private String writer; //작성자
    @Column(nullable = false, name="title")
    private String title; //제목

    @Column(nullable = false, name="content")
    private String content; //내용
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="hashTags")
    private List<String> hashTags = new ArrayList<String>(); // 해시태그 목록


    @CreationTimestamp
    @Column(name="createDate")
    private LocalDateTime createDate; //작성시간


    @UpdateTimestamp
    @Column(name="modifyDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDate; //수정시간

    public void update(String title, String content, List<String> hashTags, String writer){
        this.title = title;
        this.content = content;
        this.hashTags = hashTags;
        this.writer = writer;
    }



}
