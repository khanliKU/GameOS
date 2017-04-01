package ku.edu.kutty.quickquiz;

import android.os.Bundle;
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

import java.lang.reflect.Array;
import java.util.Arrays;

public class MemoGameFragment extends Fragment
{
	Flag[] allFlags;
	Flag[] rightFlags;
	MainActivity parent;
	ImageView[] lives = new ImageView[4];
	TextView nicknameTextView;
	TextView scoreTextView;
	GridView choices;
	ArrayAdapter choiceAdapter;
	ArrayAdapter rightAnswerAdapter;
	boolean[] selected;
	int numSelected = 0;
	//TODO: game logic
	
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
		allFlags = MemoGame.getInstance().getRandomFlags();
		rightFlags = new Flag[MemoGame.getInstance().getLevel() + 4 ];
		for (int i = 0 ; i < rightFlags.length ; i++)
		{
			rightFlags[i] = allFlags[i];
		}
		Utils.shuffleArray(allFlags);
		selected = new boolean[allFlags.length];
		Arrays.fill(selected, Boolean.FALSE);
		
		// UI
		nicknameTextView = (TextView) parent.findViewById(R.id.nickname);
		scoreTextView = (TextView) parent.findViewById(R.id.score);
		lives[0] = (ImageView) parent.findViewById(R.id.life0);
		lives[1] = (ImageView) parent.findViewById(R.id.life1);
		lives[2] = (ImageView) parent.findViewById(R.id.life2);
		lives[3] = (ImageView) parent.findViewById(R.id.life3);
		choices = (GridView) parent.findViewById(R.id.choices);
		choices.setNumColumns(MemoGame.getInstance().getLevel() + 4);
		choices.setPadding(5,5,5,5);
		choices.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selected[position] = !selected[position];
				if (selected[position])
				{
					numSelected++;
					// TODO: check for win
				}
				else
				{
					numSelected--;
				}
			}
		});
		
		choiceAdapter = new ArrayAdapter(getActivity(), R.layout.flag_choice, allFlags)
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
		
		rightAnswerAdapter = new ArrayAdapter(getActivity(), R.layout.flag_choice, rightFlags)
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
		
		updateUI();
	}
	
	void updateUI()
	{
		nicknameTextView.setText(User.getInstance().getNickname());
		scoreTextView.setText("Score: " + Integer.toString(MemoGame.getInstance().getScore()));
		for (int i = 0; i < MemoGame.getInstance().getLives() ; i ++)
		{
			lives[i].setImageResource(R.drawable.hearth_full);
		}
		choices.setAdapter(choiceAdapter);
	}
	
	
	
}
