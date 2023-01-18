package com.example.apibasic.post.repository;

import com.example.apibasic.post.entity.HashTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTageRepository extends JpaRepository<HashTagEntity, Long> {
}
