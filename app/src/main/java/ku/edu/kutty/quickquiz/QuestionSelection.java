package ku.edu.kutty.quickquiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
    // XML
        try
        {
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
            for (int i = 0;i < categoryList.getLength(); i++)
            {
                Node categoryNode = categoryList.item(i);
                if (categoryNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element categoryElement = (Element) categoryNode;
                    NodeList questionList = categoryElement.getElementsByTagName("Question");
                    questions = new ArrayList<Question>();
                    for (int j = 0; j < questionList.getLength(); j++)
                    {
                        Node questionNode = questionList.item(j);
                        if (questionNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element questionElement = (Element) questionNode;
                            questions.add(createQuestion(questionElement));
                        }
                    }
                    categories.add(new Category(getSingleValue("name",categoryElement), questions.toArray(new Question[questions.size()])));
                }
            }
            this.categories =  categories.toArray(new Category[categories.size()]);
        }
        catch (IOException e)
        {
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
/*
        for (int i = 0; i < categories.length; i++)
        {
            Log.d("category ", categories[i].toString());
        }
        Button button = new Button(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_question_selection);
        */
     // Layout
        LinearLayout parent = (LinearLayout) findViewById(R.id.parentLayout);
        LinearLayout[] categoryLayout = new LinearLayout[categories.length];
        for (int i = 0; i < categories.length; i++)
        {
            categoryLayout[i] = new LinearLayout(this);
            categoryLayout[i].setOrientation(LinearLayout.VERTICAL);
            TextView categoryName = new TextView(this);
            categoryName.setText(categories[i].getName());
            categoryName.setGravity(Gravity.CENTER);
            categoryLayout[i].addView(categoryName);
            /*
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            categoryName.setLayoutParams(params);
            */
            for (int j = categories[i].getQuestions().length - 1; j >= 0; j--)
            {
                Button questionButton = new Button(this);
                questionButton.setText(Integer.toString((j+1)*100));
//                questionButton.setTag(i + " " + j);
                questionButton.setOnClickListener(myOnClick(questionButton,i,j));
                categoryLayout[i].addView(questionButton);
            }
            parent.addView(categoryLayout[i]);
        }
    }

    View.OnClickListener myOnClick(final Button button, final int categoryIndex, final int questionIndex)
    {
        return new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Log.d("test: ",categoryIndex + " " + questionIndex);
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
}
