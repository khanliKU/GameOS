package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

class User
{
    private static User instance = null;
    private String nickname;

    private User(String nickname)
    {
        this.nickname = nickname;
    }

    static User getInstance()
    {
        return  instance;
    }

    String getNickname()
    {
        return nickname;
    }

    static void initialize(String nickname)
    {
        if (instance == null)
        {
            instance = new User(nickname);
        }
    }
}
