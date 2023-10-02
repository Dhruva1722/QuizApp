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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    private Button nextBtn, skipBtn, prevBtn , submitBtn;
    private int currentQuestionIndex = 0;

    // Declare the questions list here
    private List<Question> questions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        submitBtn = findViewById(R.id.submitButton);
        nextBtn = findViewById(R.id.nextButton);
        prevBtn = findViewById(R.id.prevButton);

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is 2 + 2?", Arrays.asList("3", "4", "5", "6")));
        questions.add(new Question("What is the capital of France?", Arrays.asList("London", "Paris", "Rome", "Berlin")));
        questions.add(new Question("What is the largest planet in our solar system?", Arrays.asList("Mars", "Jupiter", "Moon", "Venus")));
        questions.add(new Question("What is 5+5", Arrays.asList("7", "8", "9", "10")));


         quizAdapter = new QuizAdapter(questions);
        recyclerView.setAdapter(quizAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        recyclerView.setNestedScrollingEnabled(false);

//        skipBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showNextQuestion();
//            }
//        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int correctCount = 0;
                int wrongCount = 0;
                int skippedCount = 0;

                for (Question question : questions) {
                    if (question.getSelectedAnswer() == null) {
                        // Question was skipped
                        skippedCount++;
                    } else if (question.getSelectedAnswer().equals(question.getCorrectAnswer())) {
                        // Correct answer
                        correctCount++;
                    } else {
                        // Wrong answer
                        wrongCount++;
                    }
                }

                // Display the results using Toast or any other UI component
                String resultMessage = "Correct: " + correctCount + "\n" +
                        "Wrong: " + wrongCount + "\n" +
                        "Skipped: " + skippedCount;
                Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_LONG).show();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isAnswerSelected()) {
                      showNextQuestion();
                updateButtonVisibility();
                recyclerView.smoothScrollToPosition(currentQuestionIndex);
               }
                else {
                   Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousQuestion();
                updateButtonVisibility();
            }
        });
    }
    private boolean isAnswerSelected() {
        // Check if the current question index is valid
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            // Get the ViewHolder for the current question
            QuizAdapter.QuizViewHolder viewHolder = (QuizAdapter.QuizViewHolder) recyclerView.findViewHolderForAdapterPosition(currentQuestionIndex);

            // Check if any RadioButton within the RadioGroup is checked
            int checkedRadioButtonId = viewHolder.optionsGroup.getCheckedRadioButtonId();
            return checkedRadioButtonId != -1;
        } else {
            // Handle an invalid question index (out of bounds)
            return false;
        }
    }
    private void updateButtonVisibility() {
        if (currentQuestionIndex == 0) {
            prevBtn.setEnabled(false);
        } else {
            prevBtn.setEnabled(true);
        }
        if (currentQuestionIndex == quizAdapter.getItemCount() - 1) {
            nextBtn.setEnabled(false);
        } else {
            nextBtn.setEnabled(true);
        }
    }
    private void showNextQuestion() {
        if (currentQuestionIndex < quizAdapter.getItemCount() - 1) {
            currentQuestionIndex++;
            recyclerView.scrollToPosition(currentQuestionIndex);
        }
    }
    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            recyclerView.scrollToPosition(currentQuestionIndex);
        }
    }
}


