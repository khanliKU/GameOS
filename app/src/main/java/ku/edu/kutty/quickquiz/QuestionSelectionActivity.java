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


public class QuestionSelectionActivity extends AppCompatActivity
{

    Category[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_selection);
		TextView nicknameTextView = (TextView) findViewById(R.id.nickname);
		nicknameTextView.setText(User.getInstance().getNickname());
		
//		createUI();
	//	updateUI();
	//	checkEndGame();
    }
    
    

	@Override
	protected void onRestart()
	{
		super.onRestart();
	//	updateUI();
	//	checkEndGame();
	}

	View.OnClickListener myOnClick(final Button button, final int categoryIndex, final int questionIndex)
    {
        return new View.OnClickListener()
        {
            public void onClick(View view)
            {
				if (questionIndex <= QuickQuiz.getInstance().getCategories()[categoryIndex].getMaxAllowedIndex()) {
					Intent answerIntent = new Intent(QuestionSelectionActivity.this, AnsweringActivity.class);
					answerIntent.putExtra("category", categoryIndex);
					answerIntent.putExtra("question", questionIndex);
					answerIntent.putExtra("points", 100 * (questionIndex + 1));
					startActivity(answerIntent);
				}
            }
        };
    }
}
