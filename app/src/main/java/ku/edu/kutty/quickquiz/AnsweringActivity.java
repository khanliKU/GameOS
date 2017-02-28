package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnsweringActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answering);
		Intent receivedIntent = getIntent();
		int categoryIndex = receivedIntent.getIntExtra("category",-1);
		int questionIndex = receivedIntent.getIntExtra("question",-1);
		Log.d("Indexes", categoryIndex + " " + questionIndex);
		TextView questionText = (TextView) findViewById(R.id.questionText);
		questionText.setText(Categories.getInstace().getCategories()[categoryIndex].getQuestions()[questionIndex].getQuestionText());
	}

	public void goBack(View view)
	{
		Intent goBackIntent = new Intent(AnsweringActivity.this, QuestionSelection.class);
		startActivity(goBackIntent);
	}

}
