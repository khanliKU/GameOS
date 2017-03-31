package ku.edu.kutty.quickquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class GameOverFragment extends Fragment
{
	
	public GameOverFragment()
	{
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_game_over, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		TextView statusTextView = (TextView) getActivity().findViewById(R.id.status);
		TextView scoreTextView = (TextView) getActivity().findViewById(R.id.score);
		TextView nicknameTextView = (TextView) getActivity().findViewById(R.id.nickname);
		
		boolean won = getArguments().getBoolean("won");
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
		
		Button restartButton = (Button) getActivity().findViewById(R.id.restartButton);
		restartButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
			//	User.reset();
				QuickQuiz.reset();
				MainActivity parent = (MainActivity) getActivity();
				parent.viewCategories();
			}
		});
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}
