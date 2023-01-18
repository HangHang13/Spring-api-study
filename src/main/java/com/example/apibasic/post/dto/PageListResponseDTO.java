package com.example.apibasic.post.dto;

import com.example.apibasic.post.entity.PostEntity;
import lombok.*;

import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class PageListResponseDTO {

    private int count;

    private PageResponseDTO<PostEntity> pageInfo;

    private List<PageListResponseDTO> list;

}
