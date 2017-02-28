package ku.edu.kutty.quickquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class QuestionSelection extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_selection);

        Question test;

        try
        {
            InputStream is = getAssets().open("data.xml");
            /*
            int streamLength = is.available();
            byte[] streamData = new byte[streamLength];
            is.read(streamData);
            String xmlString = new String(streamData);
            Log.d("xml file ", xmlString);
            */
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList categoryList = doc.getElementsByTagName("Category");

            for (int i = 0;i < categoryList.getLength(); i++)
            {
                Node categoryNode = categoryList.item(i);
                if (categoryNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element categoryElement = (Element) categoryNode;
                    NodeList questionList = categoryElement.getElementsByTagName("Question");
                    for (int j = 0; j < questionList.getLength(); j++)
                    {
                        Node questionNode = questionList.item(j);
                        if (questionNode.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element questionElement = (Element) questionNode;
//                            Log.d("Element Value:", getQuestionData("text",questionElement));
                            test = createQuestion(questionElement);
                            Log.d("test: ", test.toString());
                        }
                    }
                }
            }
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
        Button button = new Button(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_question_selection);
        */
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
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        result = new String[nodeList.getLength() + 1];
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            result[i] = nodeList.item(i).getNodeValue();
        }
        return result;
    }
}
