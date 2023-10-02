package com.example.testquizapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Question> questions;

    public QuizAdapter(List<Question> questions) {
        this.questions = questions;
    }


    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_item, parent, false);

        Log.d("QuizAdapter", "onCreateViewHolder called"); // Add this line for debugging

        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.QuizViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.questionText.setText(question.getQuestionText());

        for (int i = 0; i < holder.optionsGroup.getChildCount(); i++) {
            final RadioButton option = (RadioButton) holder.optionsGroup.getChildAt(i);
            option.setText(question.getOptions().get(i));

            // Set a click listener to track selected answers
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the selected answer for this question
                    question.setSelectedAnswer(option.getText().toString());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return questions.size();
    }


    public class QuizViewHolder extends ViewHolder {
        TextView questionText;
        RadioGroup optionsGroup;
        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            optionsGroup = itemView.findViewById(R.id.optionsGroup);
        }
    }
}