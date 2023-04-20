package com.vada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vada.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
