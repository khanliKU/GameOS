package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class QuestionSelection extends AppCompatActivity
{

    Category[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_selection);
		TextView nicknameTextView = (TextView) findViewById(R.id.nickname);
		nicknameTextView.setText(User.getInstance().getNickname());
		TextView scoreTextView = (TextView) findViewById(R.id.score);
		scoreTextView.setText("Score: " + User.getInstance().getScore());
    // XML
		if (Categories.getInstance() == null) {
			try {
				ArrayList<Question> questions;
				ArrayList<Category> categories;
				InputStream is = getAssets().open("data.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(is);
				Element element = doc.getDocumentElement();
				element.normalize();
				NodeList categoryList = doc.getElementsByTagName("Category");
				categories = new ArrayList<Category>();
				for (int i = 0; i < categoryList.getLength(); i++) {
					Node categoryNode = categoryList.item(i);
					if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
						Element categoryElement = (Element) categoryNode;
						NodeList questionList = categoryElement.getElementsByTagName("Question");
						questions = new ArrayList<Question>();
						for (int j = 0; j < questionList.getLength(); j++) {
							Node questionNode = questionList.item(j);
							if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
								Element questionElement = (Element) questionNode;
								questions.add(createQuestion(questionElement));
							}
						}
						categories.add(new Category(getSingleValue("name", categoryElement), questions.toArray(new Question[questions.size()])));
					}
				}
				this.categories = categories.toArray(new Category[categories.size()]);
				Categories.initialize(this.categories);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			} catch (SAXException e) {
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			}
		}
		else
		{
			categories = Categories.getInstance().getCategories();
		}

     // Layout
        LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
		parent.setGravity(Gravity.CENTER);
        LinearLayout[] categoryLayout = new LinearLayout[categories.length];
        for (int i = 0; i < categories.length; i++)
        {
            categoryLayout[i] = new LinearLayout(this);
            categoryLayout[i].setOrientation(LinearLayout.VERTICAL);
            TextView categoryName = new TextView(this);
            categoryName.setText(categories[i].getName());
            categoryName.setGravity(Gravity.CENTER);
			categoryName.setTextSize(20);
            categoryLayout[i].addView(categoryName);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            categoryLayout[i].setLayoutParams(params);

            for (int j = categories[i].getQuestions().length - 1; j >= 0; j--)
            {
                Button questionButton = new Button(this);
                questionButton.setText(Integer.toString((j+1)*100));
                questionButton.setOnClickListener(myOnClick(questionButton,i,j));
				questionButton.setBackgroundColor(Color.GRAY);
				questionButton.setLayoutParams(params);
                categoryLayout[i].addView(questionButton);
            }
            parent.addView(categoryLayout[i]);
        }
    }

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Intent gameOverIntent = new Intent(QuestionSelection.this, GameOverActivity.class);
		if (checkForLoss())
		{
			gameOverIntent.putExtra("won",false);
			startActivity(gameOverIntent);
			finish();
		}
		else if (checkForWin())
		{
			gameOverIntent.putExtra("won",true);
			startActivity(gameOverIntent);
			finish();
		}
		updateUI();
	}

	private void updateUI()
	{
		LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
		for (int i = 0; i < categories.length; i++)
		{
			for (int j = categories[i].getQuestions().length - 1; j >= 0; j--)
			{

				if (categories[i].getQuestions()[j].isAnswered())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(categories[i].getQuestions().length - j).setBackgroundColor(Color.GREEN);
				}
				else if (categories[i].getQuestions()[j].isAttempted())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(categories[i].getQuestions().length - j).setBackgroundColor(Color.RED);
				}
				else if (categories[i].getQuestions()[j].isRead())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(categories[i].getQuestions().length - j).setBackgroundColor(Color.BLUE);
				}
				else
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(categories[i].getQuestions().length - j).setBackgroundColor(Color.GRAY);
				}
			}
		}
	}

	View.OnClickListener myOnClick(final Button button, final int categoryIndex, final int questionIndex)
    {
        return new View.OnClickListener()
        {
            public void onClick(View view)
            {
				if (questionIndex <= Categories.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex()) {
					Intent answerIntent = new Intent(QuestionSelection.this, AnsweringActivity.class);
					answerIntent.putExtra("category", categoryIndex);
					answerIntent.putExtra("question", questionIndex);
					answerIntent.putExtra("points", 100 * (questionIndex + 1));
					startActivity(answerIntent);
				}
            }
        };
    }

    private Question createQuestion(Element questionElement)
    {
        Question result;
        String text, answer;
        String[] choices;
        text = getSingleValue("text",questionElement);
        answer = getSingleValue("answer",questionElement);
        choices = getMultipleValue("choice",questionElement);
        choices[3] = answer;
        result = new Question(text,answer,choices);
        return result;
    }

    private String getSingleValue(String tag, Element element)
    {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    private String[] getMultipleValue(String tag, Element element)
    {
        String[] result;
        NodeList nodeList = element.getElementsByTagName(tag);
        result = new String[nodeList.getLength() + 1];
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            result[i] = nodeList.item(i).getTextContent();
        }
        return result;
    }

	private boolean checkForWin()
	{
		for (int categoryIndex = 0; categoryIndex < Categories.getInstance().getCategories().length; categoryIndex++)
		{
			if (Categories.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex() !=
					Categories.getInstance().getCategories()[categoryIndex].getQuestions().length)
			{
				return false;
			}
		}
		return true;
	}

	private boolean checkForLoss()
	{
		for (int categoryIndex = 0; categoryIndex < Categories.getInstance().getCategories().length; categoryIndex++)
		{
			int maxAllowed = Categories.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex();
			if (maxAllowed == Categories.getInstance().getCategories()[categoryIndex].getQuestions().length ||
					!Categories.getInstance().getCategories()[categoryIndex].getQuestions()[maxAllowed].isAttempted())
			{
				return false;
			}
		}
		return true;
	}
}
