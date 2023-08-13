package com.aishwarya.demo.controller;

import com.aishwarya.demo.model.Question;
import com.aishwarya.demo.model.QuestionWrapper;
import com.aishwarya.demo.model.Quiz;
import com.aishwarya.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import  java.util.*;

@RestController
@RequestMapping("quiz")

public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title){

        return quizService.createQuiz(category,numQ,title);

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){

        return quizService.getQuizQuestions(id);





    }


}
