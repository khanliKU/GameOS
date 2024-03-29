package ku.edu.kutty.gameos;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MemoGameFragment extends Fragment
{
	MainActivity parent;
	ImageView[] lives = new ImageView[4];
	TextView nicknameTextView;
	TextView scoreTextView;
	GridView choices;
	ArrayAdapter choiceAdapter;
	GridView rightAnswer;
	ArrayAdapter rightAnswerAdapter;
	Handler myHandler;
	Runnable delay = new Runnable()
	{
		@Override
		public void run()
		{
			rightAnswer.setVisibility(View.INVISIBLE);
			MemoGame.getInstance().waited();
		}
	};
	
	public MemoGameFragment()
	{
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_memo_game, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		parent = (MainActivity) getActivity();
		
		// get flags
		
		// get UI elements
		nicknameTextView = (TextView) parent.findViewById(R.id.nickname);
		scoreTextView = (TextView) parent.findViewById(R.id.score);
		lives[0] = (ImageView) parent.findViewById(R.id.life0);
		lives[1] = (ImageView) parent.findViewById(R.id.life1);
		lives[2] = (ImageView) parent.findViewById(R.id.life2);
		lives[3] = (ImageView) parent.findViewById(R.id.life3);
		choices = (GridView) parent.findViewById(R.id.choices);
		rightAnswer = (GridView) parent.findViewById(R.id.right_answer);
		myHandler = new Handler();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		rightAnswer.setVisibility(View.INVISIBLE);
		updateUI();
	}
	
	void updateUI()
	{
		parent.checkMemoGameEndGame();
		if (!MemoGame.getInstance().isAttempted() && MemoGame.getInstance().timeToWait != 0)
		{
			rightAnswer.setVisibility(View.VISIBLE);
			myHandler.postDelayed(delay, MemoGame.getInstance().getTimeToWait() * 1000);
		}
		nicknameTextView.setText(User.getInstance().getNickname());
		scoreTextView.setText("Score: " + Integer.toString(MemoGame.getInstance().getScore()));
		
		rightAnswer.setNumColumns(MemoGame.getInstance().getLevel() + 4);
		choices.setNumColumns(MemoGame.getInstance().getLevel() + 4);
		
		for (int i = 0; i < 4 ; i ++)
		{
			lives[i].setImageResource(R.drawable.hearth_empty);
		}
		
		for (int i = 0; i <= MemoGame.getInstance().getLives() ; i ++)
		{
			lives[i].setImageResource(R.drawable.hearth_full);
		}
		
		// set onclick listener for choices
		choices.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (MemoGame.getInstance().timeToWait == 0)
				{
					MemoGame.getInstance().select(position);
					updateUI();
				}
			}
		});
		
		choiceAdapter = new ArrayAdapter(getActivity(), R.layout.flag_choice, MemoGame.getInstance().getCurrentGameChoices())
		{
			@NonNull
			@Override
			public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
			{
				if (convertView == null)
				{
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.flag_choice,parent,false);
				}
				ImageView v = (ImageView) convertView;
				Flag flag = (Flag) getItem(position);
				v.setImageBitmap(flag.getBitmap());
				return v;
			}
		};
		
		choices.setAdapter(choiceAdapter);
		
		
		rightAnswerAdapter = new ArrayAdapter(getActivity(), R.layout.flag_choice, MemoGame.getInstance().getCurrentGameAnswers())
		{
			@NonNull
			@Override
			public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
			{
				if (convertView == null)
				{
					convertView = LayoutInflater.from(getContext()).inflate(R.layout.flag_choice,parent,false);
				}
				ImageView v = (ImageView) convertView;
				Flag flag = (Flag) getItem(position);
				v.setImageBitmap(flag.getBitmap());
				return v;
			}
		};
		
		rightAnswer.setAdapter(rightAnswerAdapter);
	}
}

// TODO: indicate user that the view is pressed