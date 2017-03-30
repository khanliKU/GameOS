package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class GameListActivity extends AppCompatActivity
{
	Category[] categories;
	private boolean onTablet = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		// XML
		if (QuickQuiz.getInstance() == null) {
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
				QuickQuiz.initialize(this.categories);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			}
			catch (SAXException e)
			{
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			}
		}
		else
		{
			categories = QuickQuiz.getInstance().getCategories();
		}
		
		GameListFragment gameList = new GameListFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.game_list_frame,gameList).commit();
		
		if (findViewById(R.id.game_frame) != null)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			onTablet = true;
			QuestionSelectionFragment qs = new QuestionSelectionFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.game_frame,qs).commit();
		}
		else
		{
			
		}
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
		for (int categoryIndex = 0; categoryIndex < QuickQuiz.getInstance().getCategories().length; categoryIndex++)
		{
			if (QuickQuiz.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex() !=
					QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions().length)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean checkForLoss()
	{
		for (int categoryIndex = 0; categoryIndex < QuickQuiz.getInstance().getCategories().length; categoryIndex++)
		{
			int maxAllowed = QuickQuiz.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex();
			if (maxAllowed == QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions().length ||
					!QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[maxAllowed].isAttempted())
			{
				return false;
			}
		}
		return true;
	}
	
	private void checkEndGame()
	{
		Intent gameOverIntent = new Intent(GameListActivity.this, GameOverActivity.class);
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
	}
	
	View.OnClickListener myOnClick(final Button button, final int categoryIndex, final int questionIndex)
	{
		return new View.OnClickListener()
		{
			public void onClick(View view)
			{
				if (questionIndex <= QuickQuiz.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex()) {
					Intent answerIntent = new Intent(GameListActivity.this, AnsweringActivity.class);
					answerIntent.putExtra("category", categoryIndex);
					answerIntent.putExtra("question", questionIndex);
					answerIntent.putExtra("points", 100 * (questionIndex + 1));
					startActivity(answerIntent);
				}
			}
		};
	}
}
