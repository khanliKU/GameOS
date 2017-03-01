package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

public class Categories
{
	private static Categories instance = null;
	private Category[] categories;

	private Categories(Category[] categories)
	{
		this.categories = categories;
	}

	public static Categories getInstance()
	{
		return instance;
	}

	public static void initialize(Category[] categories)
	{
		if (instance == null)
		{
			instance = new Categories(categories);
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
}
