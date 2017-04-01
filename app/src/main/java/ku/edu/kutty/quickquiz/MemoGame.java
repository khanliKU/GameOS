package ku.edu.kutty.quickquiz;

import java.util.Random;

class MemoGame
{
	private static MemoGame instance = null;
	private int score;
	private Flag[] flags;
	private int level = 0;
	private int lives = 4;
	
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
	
	Flag[] getFlags()
	{
		return flags;
	}
	
	Flag[] getRandomFlags()
	{
		int toRemember = level + 4;
		Random random = new Random();
		Flag[] flags = new Flag[toRemember * toRemember];
		for (int i = 0; i < flags.length ; i++)
		{
				flags[i] = this.flags[random.nextInt(this.flags.length)];
		}
		return flags;
	}
}
