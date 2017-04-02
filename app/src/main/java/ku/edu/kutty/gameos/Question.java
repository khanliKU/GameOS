package ku.edu.kutty.gameos;

class Question
{
    private String questionText;
    private String rightAnswer;
    private String[] choices;
    private boolean read = false;
	private int attempt = -1;
	private int timeLeft = 30;
    private boolean answered = false;
    private boolean attempted = false;

    Question(String questionText, String rightAnswer, String[] choices)
    {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.choices = choices;
        Utils.shuffleArray(choices);
    }

	int getTimeLeft() {
		return timeLeft;
	}

	boolean decrementTimeLeft()
	{
		if (timeLeft > 0)
		{
			timeLeft--;
			return true;
		}
		return false;
	}

	boolean isAnswered()
    {
        return answered;
    }

	boolean isAttempted() {
		return attempted;
	}

	boolean isRead()
    {
        return read;
    }

	int getAttempt() { return attempt; }

    String getQuestionText()
    {
		read = true;
        return questionText;
    }

    int getRightAnswerIndex()
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

    String[] getChoices()
    {
        return choices;
    }

    boolean answer(int choice, int points)
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
		for (String choice : choices) {
			result += choice + "\n";
		}
        return result;
    }
}
