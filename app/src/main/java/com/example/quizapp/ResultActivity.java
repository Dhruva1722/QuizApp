package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    private LottieAnimationView resultAnimationView;
    private ImageView resultImageView;
    private TextView resultMessageTextView;
    private TextView scoreTextView;
    private TextView correctAnswersTextView;
    private TextView wrongAnswersTextView;
    private TextView answersWithQuestionsTextView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultAnimationView = findViewById(R.id.result_animation_view);
        resultImageView = findViewById(R.id.result_image_view);
        resultMessageTextView = findViewById(R.id.result_message_text_view);
        scoreTextView = findViewById(R.id.score_text_view);
        correctAnswersTextView = findViewById(R.id.correct_answers_text_view);
        wrongAnswersTextView = findViewById(R.id.wrong_answers_text_view);
        answersWithQuestionsTextView = findViewById(R.id.answers_with_questions_text_view);
        restartButton = findViewById(R.id.restart_button);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        String[] correctAnswers = intent.getStringArrayExtra("correctAnswers");
        String[] wrongAnswers = intent.getStringArrayExtra("wrongAnswers");
        String[] questions = intent.getStringArrayExtra("questions");
        String[] correctAnswersForQuestions = intent.getStringArrayExtra("correctAnswersForQuestions");

        scoreTextView.setText("Your Score: " + score);
        correctAnswersTextView.setText("Correct Answers:\n" + Arrays.toString(correctAnswers).replace("[", "").replace("]", "").replace(",", "\n"));
        wrongAnswersTextView.setText("Wrong Answers:\n" + Arrays.toString(wrongAnswers).replace("[", "").replace("]", "").replace(",", "\n"));

        StringBuilder answersWithQuestions = new StringBuilder();
        for (int i = 0; i < questions.length; i++) {
            answersWithQuestions.append(questions[i]).append("\nCorrect Answer: ").append(correctAnswersForQuestions[i]).append("\n\n");
        }
        answersWithQuestionsTextView.setText("Answers with Questions:\n" + answersWithQuestions.toString());

        if (score > 5) {
            resultMessageTextView.setText("Excellent!");
            resultAnimationView.setAnimation(R.raw.excellent);
            resultAnimationView.setVisibility(View.VISIBLE);
            resultImageView.setVisibility(View.GONE);
        } else if (score == 5) {
            resultMessageTextView.setText("Good!");
            resultImageView.setImageResource(R.drawable.good);
            resultImageView.setVisibility(View.VISIBLE);
            resultAnimationView.setVisibility(View.GONE);
        } else {
            resultMessageTextView.setText("Very Bad!");
            resultImageView.setImageResource(R.drawable.sad);
            resultImageView.setVisibility(View.VISIBLE);
            resultAnimationView.setVisibility(View.GONE);
        }

        resultAnimationView.playAnimation();

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(ResultActivity.this, MainActivity.class);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restartIntent);
                finish();
            }
        });
    }
}