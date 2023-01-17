package com.example.apibasic.post.dto;


import com.example.apibasic.post.entity.PostEntity;
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
public class PostModifyDTO {

    private String writer;
    private String title;
    private String content;
    private List<String> hashTags;
//    private String

    public PostEntity toEntity(){
        return PostEntity.builder()
                .title(this.title)
                .writer(this.writer)
                .content(this.content)
                .hashTags(this.hashTags)
                .modifyDate(LocalDateTime.now())
                .build();
    }

}
