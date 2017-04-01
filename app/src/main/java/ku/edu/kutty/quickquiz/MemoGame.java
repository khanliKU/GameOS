package ku.edu.kutty.quickquiz;

import java.util.Arrays;
import java.util.Random;

class MemoGame
{
	private static MemoGame instance = null;
	private int score;
	private Flag[] flags;
	private int level = 0;
	private int lives = 4;
	private boolean[] selected;
	int numSelected = 0;
	int timeToWait;
	boolean attempted = false;
	boolean isViewed = false;
	
	private Flag[] currentGameChoices;
	private Flag[] currentGameAnswers;
	
	private MemoGame(Flag[] flags)
	{
		this.flags = flags;
	}
	
	static MemoGame getInstance()
	{
		return instance;
	}
	
	static void initialize(Flag[] flags)
	{
		if (instance == null)
		{
			instance = new MemoGame(flags);
			instance.reset();
			instance.randomFlags();
		}
	}
	
	void reset()
	{
		this.score = 0;
		this.lives = 3;
	}
	
	int getLevel()
	{
		return level;
	}
	
	int getLives()
	{
		return lives;
	}
	
	int getScore()
	{
		return score;
	}
	
	private void randomFlags()
	{
		int toRemember = level + 4;
		Random random = new Random();
		Flag[] flags = new Flag[toRemember * toRemember];
		for (int i = 0; i < flags.length ; i++)
		{
				flags[i] = this.flags[random.nextInt(this.flags.length)];
		}
		currentGameChoices = flags;
		currentGameAnswers = new Flag[MemoGame.getInstance().getLevel() + 4 ];
		for (int i = 0 ; i < currentGameAnswers.length ; i++)
		{
			currentGameAnswers[i] = currentGameChoices[i];
		}
		Utils.shuffleArray(currentGameChoices);
		instance.selected = new boolean[instance.currentGameChoices.length];
		Arrays.fill(instance.selected, Boolean.FALSE);
		numSelected = 0;
		timeToWait = 5;
		attempted = false;
	}
	
	public Flag[] getCurrentGameAnswers()
	{
		return currentGameAnswers;
	}
	
	public Flag[] getCurrentGameChoices()
	{
		return currentGameChoices;
	}
	
	void select(int position)
	{
		attempted = true;
		selected[position] = !selected[position];
		if (selected[position])
		{
			numSelected++;
		}
		else
		{
			numSelected--;
		}
		if (numSelected == level + 4)
		{
			if (checkForWin(0))
			{
				level ++ ;
				score += 100;
			}
			else
			{
				lives--;
			}
			randomFlags();
		}
	}
	
	boolean checkForWin(int index)
	{
		if (index == getLevel() + 4)
		{
			return true;
		}
		else
		{
			for (int i = 0; i < currentGameChoices.length; i++)
			{
				if (currentGameAnswers[index].getName().equals(currentGameChoices[i].getName())
						&& selected[i])
				{
					return checkForWin(index + 1);
				}
			}
		}
		return false;
	}
	
	int getTimeToWait()
	{
		return timeToWait;
	}
	
	void waited()
	{
		timeToWait = 0;
	}
	
	boolean isAttempted()
	{
		return attempted;
	}
	
	static void view()
	{
		instance.isViewed = true;
	}
	
	boolean isViewed()
	{
		return isViewed;
	}
}
