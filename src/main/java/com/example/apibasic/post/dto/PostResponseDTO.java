package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostResponseDTO {

    private String author;

    private String title;
    private String content;

    private List<String> hashTags;

    @JsonFormat(pattern = "yyyy/MM/DD")
    private LocalDateTime regDate;


    // 엔터티를 DTO로 변환하는 생성자
    public PostResponseDTO(Optional<PostEntity> entity) {
        this.author = entity.get().getWriter();
        this.content = entity.get().getContent();
        this.title = entity.get().getTitle();
        this.regDate = entity.get().getCreateDate();
        this.hashTags = entity.get().getHashTags();
    }

    public PostResponseDTO(PostEntity entity) {
        this.author = entity.getWriter();
        this.content = entity.getContent();
        this.title = entity.getTitle();
        this.regDate = entity.getCreateDate();
        this.hashTags = entity.getHashTags();
    }
}
