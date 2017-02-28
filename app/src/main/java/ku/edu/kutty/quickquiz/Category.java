package ku.edu.kutty.quickquiz;

/**
 * Created by kutty on 28/02/2017.
 */

public class Category
{
    private String name;
    private Question[] questions;

    public Category(String name, Question[] questions)
    {
        this.name = name;
        this.questions = questions;
        Question.shuffleArray(this.questions);
    }

    public String getName()
    {
        return name;
    }

    public Question[] getQuestions()
    {
        return  questions;
    }
}
