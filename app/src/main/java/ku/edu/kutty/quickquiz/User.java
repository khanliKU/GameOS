package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

public class User
{
    private static User instance = null;
    private String nickname;
    private Category[] progress;
    private int score;

    private User(String nickname)
    {
        this.nickname = nickname;
    }

    public static User getInstance()
    {
        return  instance;
    }

    public String getNickname()
    {
        return nickname;
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

    public static void initialize(String nickname)
    {
        if (instance == null)
        {
            instance = new User(nickname);
        }
    }

    public static void reset()
    {
        instance = null;
    }
}
