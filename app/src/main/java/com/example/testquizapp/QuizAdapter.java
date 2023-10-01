package com.example.testquizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private String[] questions;
    private String[][] options;
    private int[] correctAnswers;
    private int[] selectedAnswers;


    public QuizAdapter(String[] questions, String[][] options, int[] correctAnswers) {
        this.questions = questions;
        this.options = options;
        this.correctAnswers = correctAnswers;
        this.selectedAnswers = new int[questions.length];
        // Initialize selectedAnswers with -1 to indicate no answer selected
        for (int i = 0; i < selectedAnswers.length; i++) {
            selectedAnswers[i] = -1;
        }
    }


    @NonNull
    @Override
    public QuizAdapter.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.QuizViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return questions.length;
    }

    public int getSelectedAnswer(int position) {
        return selectedAnswers[position];
    }

    public void setSelectedAnswer(int position, int answerIndex) {
        selectedAnswers[position] = answerIndex;
        notifyDataSetChanged();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        private RadioGroup optionRadioGroup;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            optionRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
        }

        public void bind(final int position) {
            optionRadioGroup.removeAllViews(); // Clear existing radio buttons

            for (int i = 0; i < options[position].length; i++) {
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(options[position][i]);
                radioButton.setId(i); // Set the ID to the index for reference
                optionRadioGroup.addView(radioButton);

                // Check the radio button if it matches the selected answer
                radioButton.setChecked(selectedAnswers[position] == i);

                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Update the selected answer when a radio button is clicked
                        setSelectedAnswer(position, v.getId());
                    }
                });
            }
        }
    }
}