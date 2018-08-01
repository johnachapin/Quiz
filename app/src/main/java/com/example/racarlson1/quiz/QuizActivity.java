package com.example.racarlson1.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mHintButton;
    private Button mNameButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;
    private TextView mNameTextView;
    private EditText mNameEditText;
    private int score = 0;
    private int maxScore = 0;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private String name;

    public static class User {


        public String u_name;
        public int u_score;

        public User(String inName, int inScore) {
            u_name = inName;
            u_score = inScore;
        }

    }



    private Questions[] mQuestionBank = new Questions[] {
            new Questions(R.string.question1, false),
            new Questions(R.string.question2, false),
            new Questions(R.string.question3, false),
            new Questions(R.string.question4, true),
            new Questions(R.string.question5, false),
    };

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            score++;
            mScoreTextView.setText("Score:" + score);
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
//*********************************  Permanent Global Score   **************************************
    private void storeScore(){
        //**************  Store score locally
        /*
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Score",score);
        editor.apply();*/

       // myRef = database.getReference("Score");
        //myRef.setValue(score);
        DatabaseReference usersRef = myRef.child("users");
        Map<String, Object> users = new HashMap<>();
        users.put(name, new User(name, score));
        usersRef.updateChildren(users);
    }

    private int getScore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt("Score",score);

    }
    //******************************  Get Name   **************************************************

    private String getName(){

        String userName = (mNameEditText).getText().toString();
        return userName;

    }
    private void displayLeaderBoard(){
        Intent i = new Intent(QuizActivity.this, LeaderActivity.class);
       // i.putExtra("HINT",mQuestionBank[mCurrentIndex].isAnswerTrue());
       // i.putExtra("questionIndex",mCurrentIndex);
        startActivity(i);
    }


//******************************************   Main  **********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mHintButton = (Button)findViewById(R.id.hint_button);
        mNameButton = (Button)findViewById(R.id.name_button);
        mNameTextView = (TextView) findViewById(R.id.enter_name);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mNameEditText = (EditText)findViewById(R.id.name_input);

        // ***************************    Write a message to the database
        // *** go to tools and firebase
        //
        database = FirebaseDatabase.getInstance();
        name = getName();
        Log.v("MMMMMMMMMMMMMMMMMMMMMMM", name);
        myRef = database.getReference("server/saving-data/fireblog");
        //myRef = database.getReference("Name");
        //myRef.setValue("name");

        mNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = getName();
                //Hiding sample button.
                mNameButton.setVisibility(View.GONE);
                mNameEditText.setVisibility(View.GONE);
                mNameTextView.setText("Good Luck " + name);
                /*  Below code for one datafield
                myRef = database.getReference("Name");
                myRef.setValue(name);*/

            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(QuizActivity.this,
                        R.string.incorrect_toast,
                        Toast.LENGTH_SHORT).show();*/
                checkAnswer(true);
                storeScore();
            }

        });

//TODO Add event listener  *********** see in todo tab at bottom
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*Toast.makeText(QuizActivity.this,
                        R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
                checkAnswer(false);
                storeScore();

            }
        });
//******************************   Open up New Screen   ***************************************
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i = new Intent(QuizActivity.this, HintActivity.class);
            i.putExtra("HINT",mQuestionBank[mCurrentIndex].isAnswerTrue());
                i.putExtra("questionIndex",mCurrentIndex);
            startActivity(i);
            }
        });


        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mCurrentIndex++;
                if(mCurrentIndex>=mQuestionBank.length){
                    storeScore();
                    mScoreTextView.setText("GAME OVER - Your Score: " + getScore());
                    displayLeaderBoard();
                }
                else {

                    updateQuestion();
                }
                //DEBUG CODE - shows up in the run tab (lower left)
                Log.v("MMMMMMMMMMMMMMMMMMMMMMM", String.valueOf(mCurrentIndex));

            }
        });

        updateQuestion();

    }
}
