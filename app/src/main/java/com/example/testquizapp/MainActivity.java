package com.example.testquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private Button submitButton;
    private Button nextButton;
    private Button prevButton;

    private String[] questions;
    private String[][] options;
    private int[] correctAnswers;
    private int currentQuestionIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questions = new String[]{
                "What is the capital of France?",
                "Which planet is known as the Red Planet?",
                // Add more questions here...
        };

        options = new String[][]{
                {"Paris", "London", "Berlin", "Madrid"},
                {"Mars", "Venus", "Jupiter", "Earth"},
                // Add more options for each question here...
        };
        correctAnswers = new int[]{
                0, // Index of the correct answer for the first question (e.g., Paris)
                0, // Index of the correct answer for the second question (e.g., Mars)
                // Add more correct answers here...
        };



        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        questions = getResources().getStringArray(R.array.questions);
        options = new String[][]{
                getResources().getStringArray(R.array.options1),
                getResources().getStringArray(R.array.options2),
                getResources().getStringArray(R.array.options3)
        };
        correctAnswers = getResources().getIntArray(R.array.correct_answers);


        quizAdapter = new QuizAdapter(questions, options, correctAnswers);
        recyclerView.setAdapter(quizAdapter);


        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);

        updateButtonsVisibility();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextQuestion();
                recyclerView.smoothScrollToPosition(currentQuestionIndex);
            }

        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
            }
        });
    }

    private void checkAnswer() {
        int selectedAnswer = quizAdapter.getSelectedAnswer(currentQuestionIndex);
        if (selectedAnswer == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedAnswer == correctAnswers[currentQuestionIndex]) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong answer. Try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void showNextQuestion() {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            quizAdapter.setSelectedAnswer(currentQuestionIndex, -1); // Clear selected answer for the new question
            updateButtonsVisibility();
            Log.d("QuizApp", "Current Question Index: " + currentQuestionIndex);
        } else {
            // Optionally, you can show a message to indicate that it's the last question
            Log.d("QuizApp", "Last Question Reached");
        }
    }


    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            quizAdapter.setSelectedAnswer(currentQuestionIndex, -1); // Clear selected answer for the new question
            updateButtonsVisibility();
        }
    }

    private void updateButtonsVisibility() {
        prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.length - 1);
        submitButton.setVisibility(currentQuestionIndex == questions.length - 1 ? View.VISIBLE : View.GONE);
    }
}
