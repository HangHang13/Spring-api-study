package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostDetailResponseDTO extends PostResponseDTO {

    @JsonFormat(pattern = "yyyy/MM/DD")
    private LocalDateTime modDate;

    public PostDetailResponseDTO(PostEntity entity) {
        super(entity);
        this.modDate = entity.getModifyDate();
    }


    public PostDetailResponseDTO(Optional<PostEntity> entity) {
        super(entity);

    }
}