package ku.edu.kutty.quickquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestionSelectionFragment extends Fragment implements View.OnClickListener
{
	MainActivity parentView;
	
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
		Log.d("\nState: ","entered onCreateView\n");
		return inflater.inflate(R.layout.fragment_question_selection, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		Log.d("\nState: ","entered onViewCreated\n");
		super.onViewCreated(view, savedInstanceState);
		parentView = (MainActivity) getActivity();
		Log.d("\nState: ","parent set\n");
		createUI();
		Log.d("\nState: ","created UI\n");
		updateUI();
		Log.d("\nState: ","updated UI\n");
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
	
	@Override
	public void onClick(View v)
	{
		if (v.getTag() instanceof int[])
		{
			parentView.selectQuestion((int[]) v.getTag());
		}
		
	}
	
	void createUI()
	{
		LinearLayout parent = (LinearLayout) parentView.findViewById(R.id.parentLayout);
		parent.setGravity(Gravity.CENTER);
		LinearLayout[] categoryLayout = new LinearLayout[QuickQuiz.getInstance().getCategories().length];
		for (int i = 0; i < QuickQuiz.getInstance().getCategories().length; i++)
		{
			categoryLayout[i] = new LinearLayout(parentView);
			categoryLayout[i].setOrientation(LinearLayout.VERTICAL);
			TextView categoryName = new TextView(getActivity());
			categoryName.setText(QuickQuiz.getInstance().getCategories()[i].getName());
			categoryName.setGravity(Gravity.CENTER);
			categoryName.setTextSize(20);
			categoryLayout[i].addView(categoryName);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			params.weight = 1.0f;
			categoryLayout[i].setLayoutParams(params);
			
			for (int j = QuickQuiz.getInstance().getCategories()[i].getQuestions().length - 1; j >= 0; j--)
			{
				LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				buttonParams.weight = 1.0f;
				buttonParams.gravity = Gravity.CENTER;
				buttonParams.topMargin = 5;
				buttonParams.bottomMargin = 5;
				Button questionButton = new Button(parentView);
				int[] buttonIndex = {i,j};
				questionButton.setTag(buttonIndex);
				questionButton.setText(Integer.toString((j+1)*100));
				questionButton.setOnClickListener(this);
				questionButton.setBackgroundColor(Color.GRAY);
				questionButton.setLayoutParams(buttonParams);
				categoryLayout[i].addView(questionButton);
			}
			parent.addView(categoryLayout[i]);
		}
	}
	
	private void updateUI()
	{
		TextView nicknameTextView = (TextView) parentView.findViewById(R.id.nickname);
		TextView scoreTextView = (TextView) parentView.findViewById(R.id.score);
		scoreTextView.setText("Score: " + QuickQuiz.getInstance().getScore());
		nicknameTextView.setText(User.getInstance().getNickname());
		LinearLayout parent = (LinearLayout) parentView.findViewById(R.id.parentLayout);
		for (int i = 0; i < QuickQuiz.getInstance().getCategories().length; i++)
		{
			for (int j = QuickQuiz.getInstance().getCategories()[i].getQuestions().length - 1; j >= 0; j--)
			{
				
				if (QuickQuiz.getInstance().getCategories()[i].getQuestions()[j].isAnswered())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(QuickQuiz.getInstance().getCategories()[i].getQuestions().length - j).setBackgroundColor(Color.GREEN);
				}
				else if (QuickQuiz.getInstance().getCategories()[i].getQuestions()[j].isAttempted())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(QuickQuiz.getInstance().getCategories()[i].getQuestions().length - j).setBackgroundColor(Color.RED);
				}
				else if (QuickQuiz.getInstance().getCategories()[i].getQuestions()[j].isRead())
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(QuickQuiz.getInstance().getCategories()[i].getQuestions().length - j).setBackgroundColor(Color.BLUE);
				}
				else
				{
					((LinearLayout) parent.getChildAt(i)).getChildAt(QuickQuiz.getInstance().getCategories()[i].getQuestions().length - j).setBackgroundColor(Color.GRAY);
				}
			}
		}
	}
}
