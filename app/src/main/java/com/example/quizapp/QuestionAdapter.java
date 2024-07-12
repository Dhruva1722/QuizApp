package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question, position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionNumberTextView;
        private final TextView questionTextView;
        private final RadioGroup optionsRadioGroup;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionNumberTextView = itemView.findViewById(R.id.question_number_text_view);
            questionTextView = itemView.findViewById(R.id.question_text_view);
            optionsRadioGroup = itemView.findViewById(R.id.options_radio_group);
        }

        public void bind(Question question, int position) {
            questionNumberTextView.setText(String.format("%d.", position + 1));
            questionTextView.setText(question.getQuestionText());
            optionsRadioGroup.removeAllViews();

            for (int i = 0; i < question.getOptions().length; i++) {
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(question.getOptions()[i]);
                radioButton.setId(i);
                radioButton.setChecked(question.getSelectedOptionIndex() == i);
                optionsRadioGroup.addView(radioButton);
            }

            optionsRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                question.setSelectedOptionIndex(checkedId);
            });
        }
    }
}