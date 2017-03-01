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

    public void answer(int questionIndex, int choiceIndex)
    {
        questions[questionIndex].answer(choiceIndex);
    }

    @Override
    public String toString()
    {
        String result = name + "\n";
        for (int i = 0; i < questions.length; i++)
        {
            result += questions[i].toString();
        }
        return result;
    }
}
