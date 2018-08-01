package com.example.racarlson1.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {

    private Button mHintButton;
    private TextView mHintTextView;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private ImageView mHintImage;

    private String[] mHintPic = new String[] {
            "cat",
            "untitled",
            "bernini",
            "borromini",
            "pompeii"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);



        mHintTextView = (TextView) findViewById(R.id.question_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer);
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        mHintImage = (ImageView) findViewById(R.id.imageViewHint);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hint = getIntent().getBooleanExtra("HINT",false);
                int hintIndex = getIntent().getIntExtra("questionIndex",0);
                mAnswerTextView.setText(""+hint);
               // mHintImage.setImageResource(R.drawable.untitled);
                int drawableId = getResources().getIdentifier(mHintPic[hintIndex], "drawable", getPackageName());
                mHintImage.setImageResource(drawableId);

            }

        });


    }

    public void finish(View view) {
        this.finish();
    }
}
