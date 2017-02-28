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
    private boolean read = false;
    private boolean answered = false;

    public Question(String questionText, String rightAnswer, String[] choices)
    {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.choices = choices;
        shuffleArray(choices);
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

    @Override
    public String toString()
    {
        String result = questionText + "\n";
        for (int i=0; i < choices.length; i++)
        {
            result += choices[i] + "\n";
        }
        return result;
    }

    // Implementing Fisher–Yates shuffle
    static <T> void shuffleArray(T[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            T a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
