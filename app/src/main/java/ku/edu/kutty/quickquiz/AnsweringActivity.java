package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AnsweringActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answering);
		Intent receivedIntent = getIntent();
		final int categoryIndex = receivedIntent.getIntExtra("category",-1);
		final int questionIndex = receivedIntent.getIntExtra("question",-1);
		Log.d("Indexes", categoryIndex + " " + questionIndex);
		TextView questionText = (TextView) findViewById(R.id.questionText);
		questionText.setText(Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getQuestionText());
		final TableLayout table = (TableLayout) findViewById(R.id.answerTable);
		for (int i = 0; i < Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices().length; i++)
		{
			final TableRow choice = new TableRow(this);
			choice.setId(i);
			TextView choiceText = new TextView(this);
			choiceText.setTextSize(20);
			choiceText.setText(Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices()[i]);
			choice.addView(choiceText);
			if (i == Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt())
			{
				choice.setBackgroundColor(Color.GREEN);
			}
			choice.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View view)
				{
					if (Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt() >= 0)
					{
						int choiceIndex = view.getId();
						Log.d("asd: ", Integer.toString(view.getId()));
						view.setBackgroundColor(Color.YELLOW);
						Categories.getInstace().answer(categoryIndex,questionIndex,choiceIndex);
					}
				}
			});
			table.addView(choice);
		}
	}

	public void goBack(View view)
	{
		Intent goBackIntent = new Intent(AnsweringActivity.this, QuestionSelection.class);
		startActivity(goBackIntent);
	}

}
