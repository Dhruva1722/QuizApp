package com.example.testquizapp;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private int correctOptionIndex;
    private int selectedOptionIndex = -1; // Initially, no option is selected

    public Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public int getSelectedOptionIndex() {
        return selectedOptionIndex;
    }

    public void setSelectedOptionIndex(int selectedOptionIndex) {
        this.selectedOptionIndex = selectedOptionIndex;
    }
}
