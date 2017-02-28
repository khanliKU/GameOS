package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Question
{
    private String questionText;
    private String rightAnswer;
    private String[] choices;
    private int points;
    private boolean read = false;
    private boolean answered = false;

    public Question(String questionText, String rightAnswer, String[] choices, int points)
    {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.choices = choices;
        shuffleArray(choices);
        this.points = points;
    }

    public int getPoints()
    {
        return  points;
    }

    public boolean isAnswered()
    {
        return answered;
    }

    public boolean isRead()
    {
        return read;
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public String getRightAnswer()
    {
        return  rightAnswer;
    }

    public String[] getChoices()
    {
        return choices;
    }

    public void answer(String answer)
    {
        if (rightAnswer.equals(answer))
        {
            answered = true;
        }
    }

    // Implementing Fisher–Yates shuffle
    static void shuffleArray(String[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
