package com.example.apibasic.post.dto;


import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class PostCreateDTO {
    /*
    * Notnull : null인 경우 에러발생
    * NotEmpty : 빈문자열일 경우 에러발생
    * NotBlank : null이거나 빈 문자열일 경우 에러발생
    *
    * */
    @NotBlank
//    @Size(min = 2, max = 5) //글자수는 2~5사이
    private String writer;

    @NotBlank
//    @Min(1) @Max(20)
    private String title;

    @NotBlank
    private String content;


    private List<String> hashTags;


    //PostEntity로 변환하는 메서드

    public PostEntity toEntity(){
        return PostEntity.builder()
                .title(this.title)
                .writer(this.writer)
                .content(this.content)
//                .hashTags(this.hashTags)
                .createDate(LocalDateTime.now())
                .build();
    }

}
