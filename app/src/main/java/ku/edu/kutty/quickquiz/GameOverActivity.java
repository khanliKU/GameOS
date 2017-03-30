package ku.edu.kutty.quickquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);

		TextView statusTextView = (TextView) findViewById(R.id.status);
		TextView scoreTextView = (TextView) findViewById(R.id.score);
		TextView nicknameTextView = (TextView) findViewById(R.id.nickname);

		boolean won = getIntent().getBooleanExtra("won",false);
		if (won)
		{
			statusTextView.setText("You Won!");
			statusTextView.setTextColor(Color.GREEN);
		}
		else
		{
			statusTextView.setText("Game Over!");
			statusTextView.setTextColor(Color.RED);
		}
		scoreTextView.setText("Your score:\n" + QuickQuiz.getInstance().getScore());
		nicknameTextView.setText(User.getInstance().getNickname());

		Button restartButton = (Button) findViewById(R.id.restartButton);
		restartButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				User.reset();
				QuickQuiz.reset();
				Intent restartIntent = new Intent(GameOverActivity.this ,LoginActivity.class);
				startActivity(restartIntent);
				finish();
			}
		});

	}
}
