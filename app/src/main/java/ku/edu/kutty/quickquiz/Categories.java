package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

public class Categories
{
	private static Categories instace = null;
	private Category[] categories;

	private Categories(Category[] categories)
	{
		this.categories = categories;
	}

	public static Categories getInstace()
	{
		return instace;
	}

	public static void initialize(Category[] categories)
	{
		if (instace == null)
		{
			instace = new Categories(categories);
		}
	}

	public Category[] getCategories()
	{
		return categories;
	}
}
