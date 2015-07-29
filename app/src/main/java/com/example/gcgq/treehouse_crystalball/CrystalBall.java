package com.example.gcgq.treehouse_crystalball;

import java.util.Random;

/**
 * Created by gcgq on 5/30/2015.
 */
public class CrystalBall {
    public String[] mPossibleAnswers = {
            "Yes", "No", "Maybe",
            "Yeah", "Nuh-uh", "Meh",
            "Affirmative", "Negative", "Indeterminate",
            "All signs point to yes", "All signs point to stop", "All signs point to yield",
            "Answer hazy, try again later", "Answer clear, but your mind is not", "Answer: 42",
            "Pay for the app and I'll say 'yes' to everything"
    };
    public String getAnAnswer(){
        String answer ="";
        Random rnd = new Random();
        answer = mPossibleAnswers[rnd.nextInt(mPossibleAnswers.length)];
        return answer;
    }
}
