package com.example.apibasic;

import com.example.apibasic.post.entity.PostEntity;
import com.example.apibasic.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApibasicApplication {

	@Autowired
	final PostRepository postRepository;

	public ApibasicApplication(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApibasicApplication.class, args);
	}

//	@PostConstruct
//	void test(){
//		for (int i = 1; i <= 500; i++) {
//			PostEntity post = PostEntity.builder()
//					.title("안녕~~" + i)
//					.writer("김말종" + i)
//					.content("아무말아무말아무말~~~" + i)
//					.build();
//			postRepository.save(post);
//	}
//	}
}

