package com.jameshartley.astronomy.astronomyquiz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Current question number
    int currQuestionNumber = 1;
    int score = 0;
    boolean finished = false;

    //Create variables used to store references to question and answer views.
    TextView questionNumberView;
    TextView questionBodyView;
    CheckBox answer1CheckBox;
    CheckBox answer2CheckBox;
    CheckBox answer3CheckBox;
    CheckBox answer4CheckBox;
    RadioButton answer1RadioButton;
    RadioButton answer2RadioButton;
    RadioButton answer3RadioButton;
    RadioButton answer4RadioButton;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    Button submitButton;
    ImageView mainImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get references to our layout elements and store them in the appropriate variables.
        questionNumberView = (TextView) findViewById(R.id.questionNumberID);
        questionBodyView = (TextView) findViewById(R.id.questionBodyID);
        answer1CheckBox = (CheckBox) findViewById(R.id.answer1CheckBoxID);
        answer2CheckBox = (CheckBox) findViewById(R.id.answer2CheckBoxID);
        answer3CheckBox = (CheckBox) findViewById(R.id.answer3CheckBoxID);
        answer4CheckBox = (CheckBox) findViewById(R.id.answer4CheckBoxID);
        answer1RadioButton = (RadioButton) findViewById(R.id.answer1RadioButtonID);
        answer2RadioButton = (RadioButton) findViewById(R.id.answer2RadioButtonID);
        answer3RadioButton = (RadioButton) findViewById(R.id.answer3RadioButtonID);
        answer4RadioButton = (RadioButton) findViewById(R.id.answer4RadioButtonID);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        mainImageView = (ImageView) findViewById(R.id.main_picture);
        submitButton = (Button) findViewById(R.id.submit_button_id);

        loadQuestion(currQuestionNumber);
    }

    void loadQuestion(int questionNumber) {
        questionNumberView.setText("Question " + questionNumber);

        //Set the question body, using getIdentifier to concatenate the resource name with questionNumber
        questionBodyView.setText(getResources().getIdentifier("question" + questionNumber, "string", getPackageName()));

        //Set the image to the one stores in strings.xml
        mainImageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("picture" + currQuestionNumber, "drawable", getPackageName())));

        //Reset all selections
        resetChecks();

        //check if its multiple choice
        boolean isMultipleChoice = getResources().getBoolean(getResources().getIdentifier("isMultipleChoice" + currQuestionNumber, "bool", getPackageName()));
        boolean isTextField = getResources().getBoolean(getResources().getIdentifier("isTextField" + currQuestionNumber, "bool", getPackageName()));

        if (isMultipleChoice) {
            //Set the CheckBox text's using getIdentifier to concatenate the resource name with questionNumber
            answer1CheckBox.setText(getResources().getIdentifier("answer" + questionNumber + "x1", "string", getPackageName()));
            answer2CheckBox.setText(getResources().getIdentifier("answer" + questionNumber + "x2", "string", getPackageName()));
            answer3CheckBox.setText(getResources().getIdentifier("answer" + questionNumber + "x3", "string", getPackageName()));
            answer4CheckBox.setText(getResources().getIdentifier("answer" + questionNumber + "x4", "string", getPackageName()));

            //Set RadioGroup visibility to gone since this is multiple choice
            findViewById(R.id.radioGroup1).setVisibility(View.GONE);
            findViewById(R.id.radioGroup2).setVisibility(View.GONE);

            //Set CheckBox visibility to visible incase it is already GONE
            answer1CheckBox.setVisibility(View.VISIBLE);
            answer2CheckBox.setVisibility(View.VISIBLE);
            answer3CheckBox.setVisibility(View.VISIBLE);
            answer4CheckBox.setVisibility(View.VISIBLE);

            //Set edittext to gone
            findViewById(R.id.edit_text).setVisibility(View.GONE);

        } else if (!isMultipleChoice && !isTextField) {
            //Set the RadioButton text's using getIdentifier to concatenate the resource name with questionNumber
            answer1RadioButton.setText(getResources().getIdentifier("answer" + questionNumber + "x1", "string", getPackageName()));
            answer2RadioButton.setText(getResources().getIdentifier("answer" + questionNumber + "x2", "string", getPackageName()));
            answer3RadioButton.setText(getResources().getIdentifier("answer" + questionNumber + "x3", "string", getPackageName()));
            answer4RadioButton.setText(getResources().getIdentifier("answer" + questionNumber + "x4", "string", getPackageName()));

            //Set CheckBox visibility to gone since this is not multiple choice
            answer1CheckBox.setVisibility(View.GONE);
            answer2CheckBox.setVisibility(View.GONE);
            answer3CheckBox.setVisibility(View.GONE);
            answer4CheckBox.setVisibility(View.GONE);

            //Set edittext to gone
            findViewById(R.id.edit_text).setVisibility(View.GONE);

            //Set RadioGroup visibility to visible incase it is already GONE
            findViewById(R.id.radioGroup1).setVisibility(View.VISIBLE);
            findViewById(R.id.radioGroup2).setVisibility(View.VISIBLE);
        } else if (!isMultipleChoice && isTextField) {
            //Set RadioGroup visibility to gone since this is textfield
            findViewById(R.id.radioGroup1).setVisibility(View.GONE);
            findViewById(R.id.radioGroup2).setVisibility(View.GONE);

            //Set CheckBox visibility to gone since this is textfield
            answer1CheckBox.setVisibility(View.GONE);
            answer2CheckBox.setVisibility(View.GONE);
            answer3CheckBox.setVisibility(View.GONE);
            answer4CheckBox.setVisibility(View.GONE);

            //Show our text field
            findViewById(R.id.edit_text).setVisibility(View.VISIBLE);
        }
    }

    void radioButtonClicked(View view) {
        //Since were using two radiogroups in layout we need to deslect the appropriate group so only one radio button out of the 4 is selected.
        if (view.getParent() == radioGroup1) {
            radioGroup2.clearCheck();
        } else if (view.getParent() == radioGroup2) {
            radioGroup1.clearCheck();
        }
    }

    void resetChecks() {
        //Clear all selections
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        answer1CheckBox.setChecked(false);
        answer2CheckBox.setChecked(false);
        answer3CheckBox.setChecked(false);
        answer4CheckBox.setChecked(false);
    }

    void submitButton(View view) {
        if (finished == true) {
            submitButton.setText("Submit");
            currQuestionNumber = 1;
            finished = false;
            score = 0;
            loadQuestion(currQuestionNumber);
        } else {

            //Get correct answer
            String correctAnswer = getResources().getString(getResources().getIdentifier("correct" + currQuestionNumber, "string", getPackageName()));

            //Check if multiple choice and edittext-type answer
            boolean multipleChoiceAnswer = getResources().getBoolean(getResources().getIdentifier("isMultipleChoice" + currQuestionNumber, "bool", getPackageName()));
            boolean isTextFieldAnswer = getResources().getBoolean(getResources().getIdentifier("isTextField" + currQuestionNumber, "bool", getPackageName()));

            //Get selected answer
            String selectedAnswer = new String();

            if (multipleChoiceAnswer) {
                if (answer1CheckBox.isChecked()) {
                    selectedAnswer = (String) answer1CheckBox.getText();
                }
                if (answer2CheckBox.isChecked()) {
                    selectedAnswer = selectedAnswer + (String) answer2CheckBox.getText();
                }
                if (answer3CheckBox.isChecked()) {
                    selectedAnswer = selectedAnswer + (String) answer3CheckBox.getText();
                }
                if (answer4CheckBox.isChecked()) {
                    selectedAnswer = selectedAnswer + (String) answer4CheckBox.getText();
                }
            } else if (!multipleChoiceAnswer && !isTextFieldAnswer) {
                if (answer1RadioButton.isChecked()) {
                    selectedAnswer = (String) answer1RadioButton.getText();
                } else if (answer2RadioButton.isChecked()) {
                    selectedAnswer = (String) answer2RadioButton.getText();
                } else if (answer3RadioButton.isChecked()) {
                    selectedAnswer = (String) answer3RadioButton.getText();
                } else if (answer4RadioButton.isChecked()) {
                    selectedAnswer = (String) answer4RadioButton.getText();
                }
            } else if (!multipleChoiceAnswer && isTextFieldAnswer) {
                //If its an edit text-type answer.
                EditText editTextView = (EditText) findViewById(R.id.edit_text);
                selectedAnswer = editTextView.getText().toString();
            }

            //Check if we made the correct selection
            if (selectedAnswer.matches(correctAnswer) || selectedAnswer.equalsIgnoreCase(correctAnswer)) {
                score++;
            }

            //Increase currQuestionNumber by 1 then load the new question, if were not done.
            if (currQuestionNumber < getResources().getInteger(R.integer.maxQuestions)) {
                currQuestionNumber++;
                loadQuestion(currQuestionNumber);
            } else {
                finished = true;
                displayMessage("Finished! You got " + score + " out of " + getResources().getInteger(R.integer.maxQuestions) + " questions right!");
                submitButton.setText("Start Over");
            }
        }
    }

    void displayMessage(CharSequence message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
