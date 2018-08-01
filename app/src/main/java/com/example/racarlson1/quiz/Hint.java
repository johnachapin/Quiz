package com.example.racarlson1.quiz;

/**
 * Created by 2019ccurtis on 7/31/2018.
 */

public class Hint {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Hint(int textResId, boolean answerTrue)
    {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
