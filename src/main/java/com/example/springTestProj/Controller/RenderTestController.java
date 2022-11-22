package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.QuestionRenderHelper;
import com.example.springTestProj.Service.QuestionService;
import org.springframework.stereotype.Controller;

@Controller
public class RenderTestController {

    private final QuestionRenderHelper questionRenderHelper;
    private final QuestionService questionService;

    public RenderTestController(QuestionRenderHelper questionRenderHelper) {
        this.questionRenderHelper = questionRenderHelper;
    }


}
