package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostFindOneResDTO {

    private String author;

    private String title;
    private String content;

    private List<HashTagEntity> hashTags;

    @JsonFormat(pattern = "yyyy/MM/DD")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy/MM/DD")
    private LocalDateTime modifiedDate;

    // 엔터티를 DTO로 변환하는 생성자
    public PostFindOneResDTO(PostEntity entity) {
        this.author = entity.getWriter();
        this.content = entity.getContent();
        this.title = entity.getTitle();
        this.createDate = entity.getCreateDate();
        this.modifiedDate = entity.getModifyDate();
        this.hashTags = entity.getHashTags();
    }
}
