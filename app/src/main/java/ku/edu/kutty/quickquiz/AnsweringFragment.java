package ku.edu.kutty.quickquiz;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnsweringFragment extends Fragment implements View.OnClickListener
{
	
	final Handler handler = new Handler();
	private Runnable countdownRunnable;
	int[] index;
	
	public AnsweringFragment()
	{
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		index = getArguments().getIntArray("index");
		return inflater.inflate(R.layout.fragment_answering, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		
		final int categoryIndex = index[0];
		final int questionIndex = index[1];
		final int points = (questionIndex+1) * 100;
		TextView questionText = (TextView) getActivity().findViewById(R.id.questionText);
		questionText.setText(QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getQuestionText());
		final TableLayout table = (TableLayout) getActivity().findViewById(R.id.answerTable);
		for (int index = 0; index < QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices().length; index++)
		{
			final TableRow choice = new TableRow(getActivity());
			choice.setId(index);
			TextView choiceText = new TextView(getActivity());
			choiceText.setGravity(Gravity.CENTER);
			choiceText.setTextSize(24);
			choiceText.setText(QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices()[index]);
			choice.addView(choiceText);
			choice.setGravity(Gravity.CENTER);
			if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAttempted()) {
				if (index == QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt()) {
					if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAnswered()) {
						choice.setBackgroundColor(Color.GREEN);
					} else {
						choice.setBackgroundColor(Color.YELLOW);
					}
				} else if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getRightAnswerIndex() == index) {
					choice.setBackgroundColor(Color.RED);
				}
			}
			choice.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View view)
				{
					if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt() < 0)
					{
						final int choiceIndex = view.getId();
						view.setBackgroundColor(Color.YELLOW);
						QuickQuiz.getInstance().answer(categoryIndex,questionIndex,choiceIndex,points);
						handler.removeCallbacksAndMessages(null);
						handler.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								updateColor(categoryIndex,questionIndex);
							}
						}, 1000);
						handler.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								MainActivity parent = (MainActivity) getActivity();
								parent.viewCategories();
							}
						}, 3000);
					}
				}
			});
			table.addView(choice);
		}
		final TextView countdownTextView = (TextView) getActivity().findViewById(R.id.countdownTextView);
		countdownTextView.setTextColor(Color.RED);
		countdownRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				countdownTextView.setText(Integer.toString(QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getTimeLeft()));
				if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].decrementTimeLeft())
				{
					handler.postDelayed(this, 1000);
				}
				else
				{
					MainActivity parent = (MainActivity) getActivity();
					parent.viewCategories();
				}
			}
		};
		countdownRunnable.run();
	}
	
	@Override
	public void onClick(View v)
	{
		
	}
	
	public void updateColor(int categoryIndex, int questionIndex)
	{
		for (int index = 0; index < QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getChoices().length; index++) {
			TableRow choice = (TableRow) getActivity().findViewById(index);
			if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAttempted()) {
				if (index == QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getAttempt()) {
					if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].isAnswered()) {
						choice.setBackgroundColor(Color.GREEN);
					} else {
						choice.setBackgroundColor(Color.YELLOW);
					}
				} else if (QuickQuiz.getInstance().getCategories()[categoryIndex].getQuestions()[questionIndex].getRightAnswerIndex() == index) {
					choice.setBackgroundColor(Color.RED);
				}
			}
		}
	}
}
