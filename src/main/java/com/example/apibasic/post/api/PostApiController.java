package com.example.apibasic.post.api;

import com.example.apibasic.jpabasic.repository.MemberRepository;
import com.example.apibasic.post.dto.*;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import com.example.apibasic.post.service.PostService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//리소스 : 게시물 post
/*
*
*   게시물 목록 조회 : /posts      -GET
*   게시물 개별 조회 : /posts/{id} -GET
*   게시물 등록:      /posts      -POST
*   게시물 수정:      /posts/{id} -PATCH
*   게시물 삭제:      /posts/{id} -DELETE
* */

/*
*
* PostRepositroy에 종속
*
* */
@RestController
@Slf4j
@Service
@RequiredArgsConstructor // final을 초기화해주는 생성자를 자동으로 만들어줌 의존 객체가 자동주입되는 효과
@RequestMapping("/posts")
public class PostApiController {
    private final MemberRepository memberRepository;


    public final PostRepository postRepository ; // = new PostRepository()는 spring이 해줌

    //spirng 컨테이너에 등록해야댐 -> bean등록
    private final PostService postService;
//    @Autowired //스프링 컨네이너에 의존객체를 자동주입해달라는 얘기 @Service
//    public PostApiController(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }

    //세터주입법은 쓰지마라
    //언제든지 생성가능하기 때문에 프로그램 실행중에는 해당 의존객체가 불변이여야함
    //그래서 객체를 하나만 만드는 싱글턴만듬
    //fianl을 붙여서 불변성 만든다.
//    public void setPostRepository(PostRepository postRepository){
//        this.postRepository = postRepository;
//    }

    //제어의 역전
    //의존하는 객체를 누군가 넣어줬음 좋겠다 => 의존성 주입
    //1.필드주입
    //2.생성자주입

    //oop 객체마다 책임과 역할이 있다.
    //게시물 목록조회
    @GetMapping()
    public ResponseEntity<?> list(PageRequestDTO pageRequestDTO){
        log.info("/posts -GET request");
        log.info("request page info {}", pageRequestDTO);
        //service에 위임해서 가져옴. 다른 객체가 무엇을 하는지 궁금해하지 않는 것이 객체지향적 개발, 책임과 권한
        try {
            PostListResponseDTO postListResponseDTO = postService.getList(pageRequestDTO);
            return ResponseEntity
                    .ok()
                    .body(postListResponseDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound().build();
        }
    }


    //게시물 개별 조회
    @GetMapping("/{postNo}")
    public ResponseEntity<?> detail(@PathVariable("postNo") Long postNo){
        log.info("/posts/{id} -GET request", postNo);
        try {
            PostDetailResponseDTO postFindOneResDTO = postService.getDetail(postNo);
            return ResponseEntity
                    .ok()
                    .body(postFindOneResDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .notFound().build();
        }
    }
    public PostDetailResponseDTO getDetail(Long postNo){

        PostEntity post= postRepository
                .findById(postNo)
                .orElseThrow(()->new RuntimeException(
                        postNo+"번 게시물이 존재하지 않음"
                ));

        return new PostDetailResponseDTO(post);
    }

    //게시물 생성
//    @Parameters({
//            @Parameter(name = "작성자", description = "게시물 작성자를 입력", example = "김철수")
//    })
//    @PostMapping
//    public ResponseEntity<?> create(@Validated @RequestBody PostCreateDTO postCreateDTO , BindingResult result //검증에러를 가지고 있는 객체
//    ) {
//        if (postCreateDTO ==null){
//            return ResponseEntity
//                    .badRequest()
//                    .body("게시물 정보를 전달해주세요");
//        }
//
//        log.warn("잘못 요청함");
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            return ResponseEntity
//                    .badRequest()
//                    .body(fieldErrors);
//        }
//        log.info("/posts   -POST request");
//        log.info("게시물 정보 : {} " , postCreateDTO);
//        try {
//            PostDetailResponseDTO responseDTO = postService.insert(postCreateDTO);
//            return ResponseEntity
//                    .ok()
//                    .body(responseDTO);
//        }catch ( RuntimeException e){
//        return Po
//
//        }
//    }

    // 게시물 등록
    @Parameters({
            @Parameter(name = "작성자", description = "게시물 작성자를 입력", example = "김철수")
            , @Parameter(name = "내용", description = "글 내용을 입력", example = "하하호호호~~~")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Validated @RequestBody PostCreateDTO createDTO
            , BindingResult result // 검증 에러 정보를 갖고 있는 객체
    ) {
        if (createDTO == null) {
            return ResponseEntity
                    .badRequest()
                    .body("게시물 정보를 전달해주세요.");
        }

        if (result.hasErrors()) { // 검증에러가 발생할 시 true 리턴
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(err -> {
                log.warn("invalidated client data - {}", err.toString());
            });
            return ResponseEntity
                    .badRequest()
                    .body(fieldErrors);
        }

        log.info("/posts POST request");
        log.info("게시물 정보: {}", createDTO);

        try {
            PostDetailResponseDTO responseDTO = postService.insert(createDTO);
            return ResponseEntity
                    .ok()
                    .body(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }

    }

//        PostEntity flag = postService.insert(postCreateDTO);
//        if (flag==null) {return ResponseEntity.ok("생성실패");}
//        else {return ResponseEntity.badRequest().body("생성완료");}}

    //게시물 수정
    @PatchMapping("/{postNo}")
    public ResponseEntity<?> modify(@PathVariable("postNo") Long postNo, @RequestBody PostModifyDTO postModifyDTO)
        throws RuntimeException {

        final PostEntity entity = postRepository
                .findById(postNo)
                        .orElseThrow(()->new RuntimeException());

        log.info("/posts   -POST request " + postNo);

        try {
        PostDetailResponseDTO responseDTO =postService.update(postNo, postModifyDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
        }catch (RuntimeException e){
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{postNo}")
    public ResponseEntity<?> remove(@PathVariable Long postNo) {
        log.info("/posts/{} DELETE request", postNo);

        try {
            postService.delete(postNo);
            return ResponseEntity.ok().body("Delete Success!");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }

    }



}
