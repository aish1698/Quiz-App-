package com.aishwarya.demo.service;



import com.aishwarya.demo.dao.QuestionDao;
import com.aishwarya.demo.dao.QuizDao;
import com.aishwarya.demo.model.Question;
import com.aishwarya.demo.model.QuestionWrapper;
import com.aishwarya.demo.model.Quiz;
import com.aishwarya.demo.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class QuizService {
    @Autowired

    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);

       Quiz quiz=new Quiz();

       quiz.setTitle(title);
       quiz.setQuestions(questions);
       quizDao.save(quiz);

       return new ResponseEntity<>("Success", HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
       Optional<Quiz>quiz =quizDao.findById(id);

       List<Question> questionsFromDB=quiz.get().getQuestions();

       List<QuestionWrapper> questionsForUser=new ArrayList<>();

       for(Question q:questionsFromDB){
           QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
           questionsForUser.add(qw);

       }
       return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Optional<Quiz> quiz=quizDao.findById(id);
        List<Question> questions=quiz.get().getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

                i++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
