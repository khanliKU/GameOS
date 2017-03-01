package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
		final int points = receivedIntent.getIntExtra("points",0);
		TextView questionText = (TextView) findViewById(R.id.questionText);
		questionText.setText(Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getQuestionText());
		final TableLayout table = (TableLayout) findViewById(R.id.answerTable);
		for (int index = 0; index < Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices().length; index++)
		{
			final TableRow choice = new TableRow(this);
			choice.setId(index);
			TextView choiceText = new TextView(this);
			choiceText.setTextSize(20);
			choiceText.setText(Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices()[index]);
			choice.addView(choiceText);
			if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAttempted()) {
				if (index == Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt()) {
					if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAnswered()) {
						choice.setBackgroundColor(Color.GREEN);
					} else {
						choice.setBackgroundColor(Color.YELLOW);
					}
				} else if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getRightAnswerIndex() == index) {
					choice.setBackgroundColor(Color.RED);
				}
			}
			choice.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View view)
				{
					if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt() < 0)
					{
						final int choiceIndex = view.getId();
						view.setBackgroundColor(Color.YELLOW);
						Categories.getInstance().answer(categoryIndex,questionIndex,choiceIndex,points);
						final Handler handler = new Handler();
						handler.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								updateColor(categoryIndex,questionIndex);
							}
						}, 1000);
					}
				}
			});
			table.addView(choice);
		}
	}

	public void updateColor(int categoryIndex, int questionIndex)
	{
		for (int index = 0; index < Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices().length; index++) {
			TableRow choice = (TableRow) findViewById(index);
			if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAttempted()) {
				if (index == Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt()) {
					if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAnswered()) {
						choice.setBackgroundColor(Color.GREEN);
					} else {
						choice.setBackgroundColor(Color.YELLOW);
					}
				} else if (Categories.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getRightAnswerIndex() == index) {
					choice.setBackgroundColor(Color.RED);
				}
			}
		}
	}

	public void goBack(View view)
	{
		Intent goBackIntent = new Intent(AnsweringActivity.this, QuestionSelection.class);
		startActivity(goBackIntent);
	}

}
