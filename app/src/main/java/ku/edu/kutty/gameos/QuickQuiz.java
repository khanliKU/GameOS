package ku.edu.kutty.gameos;

import android.util.Log;
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

/*
 * This class governs the Quick Quiz implementation.
 * It keeps all the categories and questions.
 * It implements singleton pattern.
 */

class QuickQuiz
{
	private static QuickQuiz instance = null;
	private static Category[] categories;
	private int score;

	private QuickQuiz(Category[] categories)
	{
		QuickQuiz.categories = categories;
	}

	static QuickQuiz getInstance()
	{
		if (instance == null)
		{
			try
			{
				ArrayList<Question> questions;
				ArrayList<Category> categories;
				QuickQuiz dummy = new QuickQuiz(new Category[2]);
				InputStream is = dummy.getClass().getClassLoader().getResourceAsStream("assets/data.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(is);
				Element element = doc.getDocumentElement();
				element.normalize();
				NodeList categoryList = doc.getElementsByTagName("Category");
				categories = new ArrayList<>();
				for (int i = 0; i < categoryList.getLength(); i++) {
					Node categoryNode = categoryList.item(i);
					if (categoryNode.getNodeType() == Node.ELEMENT_NODE) {
						Element categoryElement = (Element) categoryNode;
						NodeList questionList = categoryElement.getElementsByTagName("Question");
						questions = new ArrayList<>();
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
				instance = new QuickQuiz(categories.toArray(new Category[categories.size()]));
			} catch (IOException | ParserConfigurationException | SAXException e)
			{
				e.printStackTrace();
				Log.d("Error: ", e.toString());
			}
		}
			
		return instance;
	}

	void answer(int categoryIndex, int questionIndex, int choiceIndex, int points)
	{
		categories[categoryIndex].answer(questionIndex,choiceIndex,points);
	}

	Category[] getCategories()
	{
		return categories;
	}

	static void reset()
	{
		instance = null;
	}
	
	int getScore()
	{
		return score;
	}
	
	void awardPoints(int points)
	{
		if (points > 0)
		{
			score += points;
		}
	}
	
	private static Question createQuestion(Element questionElement)
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
	
	private static String getSingleValue(String tag, Element element)
	{
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}
	
	private static String[] getMultipleValue(String tag, Element element)
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
	
	
	static boolean checkForWin()
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
	
	static boolean checkForLoss()
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
}
