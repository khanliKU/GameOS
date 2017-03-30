package ku.edu.kutty.quickquiz;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionSelectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionSelectionFragment extends Fragment
{
	GameListActivity parentView;
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	private OnFragmentInteractionListener mListener;
	
	public QuestionSelectionFragment()
	{
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment QuestionSelectionFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static QuestionSelectionFragment newInstance(String param1, String param2)
	{
		QuestionSelectionFragment fragment = new QuestionSelectionFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
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
		parentView = (GameListActivity) getActivity();
		Log.d("\nState: ","parent set\n");
		createUI();
		Log.d("\nState: ","created UI\n");
		updateUI();
		Log.d("\nState: ","updated UI\n");
	}
	
	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri)
	{
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener
	{
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
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
				questionButton.setText(Integer.toString((j+1)*100));
				questionButton.setOnClickListener(parentView.myOnClick(questionButton,i,j));
				questionButton.setBackgroundColor(Color.GRAY);
				questionButton.setLayoutParams(buttonParams);
				categoryLayout[i].addView(questionButton);
			}
			parent.addView(categoryLayout[i]);
		}
	}
	
	private void updateUI()
	{
		TextView scoreTextView = (TextView) parentView.findViewById(R.id.score);
		scoreTextView.setText("Score: " + QuickQuiz.getInstance().getScore());
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
