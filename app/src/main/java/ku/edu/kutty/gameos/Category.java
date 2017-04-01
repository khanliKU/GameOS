package ku.edu.kutty.gameos;

public class Category
{
    private String name;
    private Question[] questions;
    private int maxAllowedIndex = 0;

    public Category(String name, Question[] questions)
    {
        this.name = name;
        this.questions = questions;
        Utils.shuffleArray(this.questions);
    }

    public int getMaxAllowedIndex() {
        return maxAllowedIndex;
    }

    public String getName()
    {
        return name;
    }

    public Question[] getQuestions()
    {
        return  questions;
    }

    public void answer(int questionIndex, int choiceIndex, int points)
    {
        if (questions[questionIndex].answer(choiceIndex, points))
        {
            maxAllowedIndex++;
        }
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
