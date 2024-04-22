package com.example.rmandwilks;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button nextButton;
    private Button restartButton;

    private List<String> questions;
    private List<List<String>> options;
    private List<Integer> correctAnswers;
    private int currentQuestionIndex = 0;
    private boolean quizCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        nextButton = findViewById(R.id.nextButton);
        restartButton = findViewById(R.id.restartButton);

        initializeQuestions();

        nextButton.setEnabled(true);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answersRadioGroup.getCheckedRadioButtonId() == -1 || quizCompleted) {
                    Toast.makeText(MainActivity.this, "Proszę wybrać odpowiedź.", Toast.LENGTH_SHORT).show();
                } else {
                    checkAnswer();
                    nextButton.setEnabled(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestionIndex++;
                            if (currentQuestionIndex < questions.size()) {
                                showQuestion();
                            } else {
                                showQuizCompleted();
                            }
                            nextButton.setEnabled(true);
                        }
                    }, 1000);
                }
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = 0;
                showQuestion();
                restartButton.setVisibility(View.GONE);
                quizCompleted = false;
                nextButton.setEnabled(true);
            }
        });
    }
    private void initializeQuestions() {
        questions = new ArrayList<>();
        options = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        questions.add("Kto napisał powieść \"Zbrodnia i kara\"?");
        List<String> options1 = new ArrayList<>();
        options1.add("Fiodor Dostojewski");
        options1.add("Leo Tolstoj");
        options1.add("Anton Czechow");
        options1.add("Gabriel Garcia Marquez");
        options.add(options1);
        correctAnswers.add(0);

        questions.add("Jak nazywa się największa rzeka w Polsce?");
        List<String> options2 = new ArrayList<>();
        options2.add("Wisła");
        options2.add("Odra");
        options2.add("Warta");
        options2.add("Dunaj");
        options.add(options2);
        correctAnswers.add(0);

        questions.add("Kto namalował obraz Mona Lisa?");
        List<String> options3 = new ArrayList<>();
        options3.add("Leonardo da Vinci");
        options3.add("Michelangelo");
        options3.add("Pablo Picasso");
        options3.add("Vincent van Gogh");
        options.add(options3);
        correctAnswers.add(0);

        questions.add("Kto wynalazł żarówkę?");
        List<String> options4 = new ArrayList<>();
        options4.add("Thomas Edison");
        options4.add("Nikola Tesla");
        options4.add("Alexander Graham Bell");
        options4.add("Galileo Galilei");
        options.add(options4);
        correctAnswers.add(0);

        questions.add("Który kontynent jest najmniejszy?");
        List<String> options5 = new ArrayList<>();
        options5.add("Antarktyda");
        options5.add("Ameryka Południowa");
        options5.add("Europa");
        options5.add("Australia");
        options.add(options5);
        correctAnswers.add(3);

        questions.add("Jak nazywa się miesiąc przed styczniem?");
        List<String> options6 = new ArrayList<>();
        options6.add("Lipiec");
        options6.add("Luty");
        options6.add("Marzec");
        options6.add("Grudzień");
        options.add(options6);
        correctAnswers.add(3);

        questions.add("Kto był pierwszym prezydentem USA?");
        List<String> options7 = new ArrayList<>();
        options7.add("George Washington");
        options7.add("Thomas Jefferson");
        options7.add("John Adams");
        options7.add("Benjamin Franklin");
        options.add(options7);
        correctAnswers.add(0);

        questions.add("Jaka jest stolica Francji?");
        List<String> options8 = new ArrayList<>();
        options8.add("Berlin");
        options8.add("Londyn");
        options8.add("Paryż");
        options8.add("Rzym");
        options.add(options8);
        correctAnswers.add(2);

        questions.add("Ile wynosi 8 do kwadratu?");
        List<String> options9 = new ArrayList<>();
        options9.add("12");
        options9.add("48");
        options9.add("64");
        options9.add("96");
        options.add(options9);
        correctAnswers.add(2);

        questions.add("Ile wynosi liczba Pi");
        List<String> options10 = new ArrayList<>();
        options10.add("3.33");
        options10.add("9.81 * 10^-19");
        options10.add("3.14");
        options10.add("0.0000069");
        options.add(options10);
        correctAnswers.add(2);

        showQuestion();
    }


    private void showQuestion() {
        questionTextView.setText(questions.get(currentQuestionIndex));
        answersRadioGroup.removeAllViews();
        List<String> currentOptions = options.get(currentQuestionIndex);
        for (int i = 0; i < currentOptions.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(currentOptions.get(i));
            radioButton.setId(View.generateViewId());
            answersRadioGroup.addView(radioButton);
        }
    }

    private void checkAnswer() {
        int selectedRadioButtonId = answersRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        if (selectedRadioButton != null) {
            String selectedAnswer = selectedRadioButton.getText().toString();
            int correctAnswerIndex = correctAnswers.get(currentQuestionIndex);
            String correctAnswer = options.get(currentQuestionIndex).get(correctAnswerIndex);
            if (selectedAnswer.equals(correctAnswer)) {
                Toast.makeText(this, "Poprawna odpowiedź!", Toast.LENGTH_SHORT).show();
                selectedRadioButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                Toast.makeText(this, "Niepoprawna odpowiedź!", Toast.LENGTH_SHORT).show();
                selectedRadioButton.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                RadioButton correctRadioButton = findViewById(answersRadioGroup.getChildAt(correctAnswerIndex).getId());
                if (correctRadioButton != null) {
                    correctRadioButton.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                }
            }
        }
    }

    private void showQuizCompleted() {
        Toast.makeText(this, "Quiz zakończony.", Toast.LENGTH_LONG).show();
        restartButton.setVisibility(View.VISIBLE);
        quizCompleted = true;
    }
}