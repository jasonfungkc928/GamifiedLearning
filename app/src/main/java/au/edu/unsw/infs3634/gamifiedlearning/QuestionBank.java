package au.edu.unsw.infs3634.gamifiedlearning;

import java.util.ArrayList;

public class QuestionBank {

    public QuestionBank(String question, int qId) {
        this.question = question;
        this.qId = qId;
    }

    private String question;
    private int qId;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQId() {
        return qId;
    }

    public void setQId(int qId) {
        this.qId = qId;
    }


    public static ArrayList<QuestionBank> getQuestions() {
        ArrayList<QuestionBank> questions = new ArrayList();
        questions.add(new QuestionBank("How tall are they?", 1));
        questions.add(new QuestionBank("How heavy are they?", 2));
        questions.add(new QuestionBank("What position do they play?", 3));
        questions.add(new QuestionBank("What team do they play for?", 4));
        return questions;
    }

   /* public static QuestionBank getQuestion(int qId) {
        ArrayList<QuestionBank> questions = QuestionBank.getQuestions();
        for (final QuestionBank questionBank : questions) {
            if (questionBank.getQId() == qId) {
                return question;
            }
        }
        return questions.get(questions.size()-1);

    }*/
}

