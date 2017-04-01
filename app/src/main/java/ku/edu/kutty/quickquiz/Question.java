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
	private int attempt = -1;
	private int timeleft = 30;
    private boolean answered = false;
    private boolean attempted = false;

    public Question(String questionText, String rightAnswer, String[] choices)
    {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.choices = choices;
        Utils.shuffleArray(choices);
    }

	public int getTimeLeft() {
		return timeleft;
	}

	public boolean decrementTimeLeft()
	{
		if (timeleft > 0)
		{
			timeleft--;
			return true;
		}
		return false;
	}

	public boolean isAnswered()
    {
        return answered;
    }

	public boolean isAttempted() {
		return attempted;
	}

	public boolean isRead()
    {
        return read;
    }

	public int getAttempt() { return attempt; }

    public String getQuestionText()
    {
		read = true;
        return questionText;
    }

    public int getRightAnswerIndex()
    {
        for (int index = 0; index < choices.length; index++)
		{
			if (choices[index].equals(rightAnswer))
			{
				return index;
			}
		}
		return  -1;
    }

    public String[] getChoices()
    {
        return choices;
    }

    public boolean answer(int choice, int points)
    {
		this.attempted = true;
		this.attempt = choice;
		if (choice == getRightAnswerIndex())
		{
			answered = true;
			QuickQuiz.getInstance().awardPoints(points);
			return true;
		}
		return false;
    }

    public void read()
    {
        this.read = true;
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
}
