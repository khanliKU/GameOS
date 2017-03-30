package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

public class QuickQuiz
{
	private static QuickQuiz instance = null;
	private Category[] categories;
	private int score;

	private QuickQuiz(Category[] categories)
	{
		this.categories = categories;
	}

	public static QuickQuiz getInstance()
	{
		return instance;
	}

	public static void initialize(Category[] categories)
	{
		if (instance == null)
		{
			instance = new QuickQuiz(categories);
		}
	}

	public void answer(int categoryIndex, int questionIndex, int choiceIndex, int points)
	{
		this.categories[categoryIndex].answer(questionIndex,choiceIndex,points);
	}

	public Category[] getCategories()
	{
		return categories;
	}

	public static void reset()
	{
		instance = null;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void awardPoints(int points)
	{
		if (points > 0)
		{
			score += points;
		}
	}
}
