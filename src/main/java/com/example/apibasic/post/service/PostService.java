package com.example.apibasic.post.service;

import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.HashTagEntity;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.HashTageRepository;
import com.example.apibasic.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class PostService {


    //IOC 제어의 역전
    //객체의 라이프사이클을 역전시킴
    private final PostRepository postRepository;

    private final HashTageRepository hashTageRepository;

    // 목록 조회 중간처리
    public PostListResponseDTO getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSizePerPage(),
                Sort.Direction.DESC,
                "createDate"
        );

        final Page<PostEntity> pageData = postRepository.findAll(pageable);
        List<PostEntity> list = pageData.getContent();

        if (list.isEmpty()) {
            throw new RuntimeException("조회 결과가 없어용~");
        }

        // 엔터티 리스트를 DTO리스트로 변환해서 클라이언트에 응답
        List<PostResponseDTO> responseDTOList = list.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        PostListResponseDTO listResponseDTO = PostListResponseDTO.builder()
                .count(responseDTOList.size())
                .pageInfo(new PageResponseDTO<PostEntity>(pageData))
                .posts(responseDTOList)
                .build();

        return listResponseDTO;

    }
    //개별 조회 중간처리
    public PostDetailResponseDTO getDetail(long postNo){
        Optional<PostEntity> post = postRepository.findById(postNo);

        if (post == null) throw new RuntimeException(postNo + "번 게시물이 존재하지 않음!!");

        // 엔터티를 DTO로 변환
        return new PostDetailResponseDTO(post);
    }

    //등록 중간처리
    @Transactional //DML 쿼리가 여러개 동시에 나가는 상황에서 트랜잭션을 처리
    public PostDetailResponseDTO insert(final PostCreateDTO postCreateDTO){

        //dto를 entity 변환 작업
        final PostEntity entity = postCreateDTO.toEntity();
        PostEntity savedPost = postRepository.save(entity);
        //hashtag를 db에 저장
//        List<HashTagEntity> hashtags = postCreateDTO.getHashTags();
        List<String> hashTags = postCreateDTO.getHashTags();
        //해시태그 문자열 리스트에서 문자열들을 하나하나 추출한 뒤
        //해시태그 엔티티로 만들고 그 엔티티를 데이터베이스에 저장한다.
        List<HashTagEntity> hashTagEntities = new ArrayList<>();
        for (String ht : hashTags) {
            HashTagEntity tagEntity = HashTagEntity.builder()
                    .post(savedPost)
                    .tagName(ht)
                    .build();
            HashTagEntity savedTag = hashTageRepository.save(tagEntity);
            hashTagEntities.add(savedTag);
        }
        savedPost.setHashTags(hashTagEntities);


//        PostEntity entity1 = PostEntity.builder()
//                .hashTags(hashtags)
//                .content(postCreateDTO.getContent())
//                .writer(postCreateDTO.getWriter())
//                .title(postCreateDTO.getTitle())
//                .build();
//        postRepository.save(entity1);
        return new PostDetailResponseDTO(savedPost);
    }

    //수정 중간 처리
    public PostDetailResponseDTO update(Long postNo, PostModifyDTO postModifyDTO){

         Optional<PostEntity> postEntity1 = postRepository.findById(postNo);

         String content = postModifyDTO.getContent();
         if (postModifyDTO.getContent() == null) { content = postEntity1.get().getContent();}
         String title = postModifyDTO.getTitle();
         if (postModifyDTO.getTitle() == null) { title = postEntity1.get().getTitle();}
         String writer = postEntity1.get().getWriter();
         List<String>hashTages = postModifyDTO.getHashTags();
//         if (postModifyDTO.getHashTags() == null) { hashTages = postEntity1.get().getHashTags();}
        List<String> hashTags = postModifyDTO.getHashTags();

        List<HashTagEntity> preHash = postEntity1.get().getHashTags();

        List<String> preList = new ArrayList<>();
        for (HashTagEntity ht : preHash){
            preList.add(ht.getTagName());
        }
        //hash태그 수정
        List<HashTagEntity> hashTagEntities = new ArrayList<>();
        for (String ht : hashTags) {
            if (preList.contains(ht)==true) {
                continue;
            } else {
                HashTagEntity tagEntity = HashTagEntity.builder()
                        .post(postEntity1.get())
                        .tagName(ht)
                        .build();
                HashTagEntity savedTag = hashTageRepository.save(tagEntity);
                hashTagEntities.add(savedTag);
            }
        }
        postEntity1.get().update(title, content, writer, hashTagEntities);
//        postRepository.save(postEntity1);
        PostDetailResponseDTO responseDTO = new PostDetailResponseDTO(postEntity1);
         return responseDTO;
     }


    // 삭제 중간처리
    public boolean delete(final Long postNo) {
        Optional<PostEntity> postEntity1 = postRepository.findById(postNo);
        boolean set = false;
        if (postEntity1.isPresent()){
            postRepository.deleteById(postNo);
            set = true;
        }
        return set;
    }
}
