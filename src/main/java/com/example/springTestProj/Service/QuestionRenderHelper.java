package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.QuestionType;
import org.springframework.stereotype.Component;

@Component
public class QuestionRenderHelper {

    public static String getHtml(QuestionType questionType) {
        var out = "";
        switch (questionType) {
            case ESSAY:
                out = getEssayHtml();
                break;
            case BLANK:
                out = getBlankHtml();
                break;
            case BOOLEAN:
                out = getBooleanHtml();
                break;
            case SHORT_FORM:
                out = getShortFormHtml();
                break;
            case MULTIPLE_CHOICE:
                out = getMultipleChoiceHtml();
        }
        return out;
    }

    private static String getMultipleChoiceHtml() {
        return null;
    }

    private static String getShortFormHtml() {
        return null;
    }

    private static String getBooleanHtml() {
        return null;
    }

    private static String getBlankHtml() {
        return null;
    }

    private static String getEssayHtml() {
        return "hello world";
    }
}
