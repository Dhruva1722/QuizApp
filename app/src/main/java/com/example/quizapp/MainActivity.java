package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Question> questions;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button submitButton = findViewById(R.id.submit_button);

        questions = new ArrayList<>();
        loadQuestions();

        adapter = new QuestionAdapter(questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuiz();
            }
        });
    }

    private void loadQuestions() {
        questions.add(new Question("Which country won the FIFA World Cup in 2018?", new String[]{"France", "Brazil", "Germany", "Argentina"}, 0));
        questions.add(new Question("Who is known as the fastest man in the world?", new String[]{"Usain Bolt", "Tyson Gay", "Yohan Blake", "Justin Gatlin"}, 0));
        questions.add(new Question("Which sport is known as 'The Beautiful Game'?", new String[]{"Soccer", "Basketball", "Tennis", "Cricket"}, 0));
        questions.add(new Question("In which sport would you perform a slam dunk?", new String[]{"Basketball", "Volleyball", "Tennis", "Badminton"}, 0));
        questions.add(new Question("Who holds the record for the most home runs in a single MLB season?", new String[]{"Barry Bonds", "Babe Ruth", "Hank Aaron", "Mark McGwire"}, 0));
        questions.add(new Question("Which country has won the most Olympic gold medals in hockey?", new String[]{"Canada", "Russia", "USA", "Sweden"}, 0));
        questions.add(new Question("In tennis, what is the term for a score of zero?", new String[]{"Love", "Zero", "Duck", "Nil"}, 0));
        questions.add(new Question("Which golfer is known as 'The Golden Bear'?", new String[]{"Jack Nicklaus", "Tiger Woods", "Arnold Palmer", "Gary Player"}, 0));
        questions.add(new Question("Which country won the first ever Rugby World Cup in 1987?", new String[]{"New Zealand", "Australia", "England", "South Africa"}, 0));
        questions.add(new Question("Who is the NBA all-time leading scorer?", new String[]{"Kareem Abdul-Jabbar", "Karl Malone", "LeBron James", "Michael Jordan"}, 0));
    }

    private void submitQuiz() {
        int score = 0;
        boolean allQuestionsAnswered = true;
        ArrayList<String> correctAnswersList = new ArrayList<>();
        ArrayList<String> wrongAnswersList = new ArrayList<>();
        ArrayList<String> questionsList = new ArrayList<>();
        ArrayList<String> correctAnswersForQuestionsList = new ArrayList<>();

        for (Question question : questions) {
            if (question.getSelectedOptionIndex() == -1) {
                allQuestionsAnswered = false;
                break;
            }

            questionsList.add(question.getQuestionText());
            correctAnswersForQuestionsList.add(question.getOptions()[question.getCorrectAnswerIndex()]);

            if (question.getSelectedOptionIndex() == question.getCorrectAnswerIndex()) {
                score++;
                correctAnswersList.add(question.getQuestionText());
            } else {
                wrongAnswersList.add(question.getQuestionText());
            }
        }

        if (!allQuestionsAnswered) {
            Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("correctAnswers", correctAnswersList.toArray(new String[0]));
        intent.putExtra("wrongAnswers", wrongAnswersList.toArray(new String[0]));
        intent.putExtra("questions", questionsList.toArray(new String[0]));
        intent.putExtra("correctAnswersForQuestions", correctAnswersForQuestionsList.toArray(new String[0]));
        startActivity(intent);
    }

}