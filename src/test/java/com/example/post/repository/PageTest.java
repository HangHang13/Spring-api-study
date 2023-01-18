import com.example.apibasic.ApibasicApplication;
import com.example.apibasic.post.dto.PageRequestDTO;
import com.example.apibasic.post.dto.PageResponseDTO;
import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



//@Transactional
//@SpringBootTest(classes = ApibasicApplication.class)
class PageTest {
    @Autowired
    PostRepository postRepository;

//    @BeforeEach
//    void bulkInsert() {
//        for (int i = 1; i <= 500; i++) {
//            PostEntity post = PostEntity.builder()
//                    .title("안녕~~" + i)
//                    .writer("김말종" + i)
//                    .content("아무말아무말아무말~~~" + i)
//                    .build();
//            postRepository.save(post);
//        }
//    }


    @Test
    void pagingTest() {

        //클라이언트가 전달한 페이지 정보
        PageRequestDTO dto = PageRequestDTO.builder().page(4).sizePerPage(19).build();

        //페이지 정보 생성
        PageRequest pageInfo = PageRequest.of(dto.getPage()-1, dto.getSizePerPage(), Sort.Direction.DESC
                , "createDate" //정렬 기준 필드
        );


        Page<PostEntity> postEntities = postRepository.findAll(pageInfo);

        // 실제 조회된 데이터
        List<PostEntity> content = postEntities.getContent();
        int totalPage = postEntities.getTotalPages();
        long totalElements = postEntities.getTotalElements();
        postEntities.getPageable().next();

        System.out.println("totalPage"+totalPage);
        System.out.println("totalElements"+totalElements);

        System.out.println(content.size());
        content.forEach(System.out::println);
    }

    @Test
    @DisplayName("제목에 3이 포함된 결과를 검색 후 1페이지 정보를 조회해야 한다.")
    void pageTest2(){
        //given
        String title = "3";
        PageRequest pageRequest = PageRequest.of(
                14,
                10,
                Sort.Direction.DESC,
                "createDate");


        Page<PostEntity> postEntityPage
                = postRepository.findByTitleContaining(title, pageRequest);
        List<PostEntity> contents = postEntityPage.getContent();
        contents.forEach(System.out::println);



        PageResponseDTO<PostEntity> DTO = new PageResponseDTO<PostEntity>(postEntityPage);

        System.out.println("DTO"
                + DTO);
    }
}