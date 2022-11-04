package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.Feedback;
import com.example.springTestProj.Entities.User;
import com.example.springTestProj.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    public void saveFeedbackToRepo(Feedback feedback){
        feedbackRepository.save(feedback);
        System.out.println("feedback saved?");

    }

    public Feedback createFeedback(){
        String feedBack = String.valueOf(UUID.randomUUID());  // probably dont do this maybe
        Feedback feedback = new Feedback(feedBack, 20.3, "yah", 40.2);

        return feedback;
    }
}
