package ku.edu.kutty.gameos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameListFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_game_list, container, false);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		TableLayout tl = (TableLayout) getActivity().findViewById(R.id.game_list);
		
		View quickQuiz = tableChild("Quick Quiz");
		quickQuiz.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				MainActivity parent = (MainActivity) getActivity();
				parent.viewCategories();
			}
		});
		View memoGame = tableChild("Memo Game");
		memoGame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				MainActivity parent = (MainActivity) getActivity();
				parent.viewMemoGame();
			}
		});
		
		tl.addView(quickQuiz);
		tl.addView(memoGame);
	}
	
	private View tableChild(String gameName)
	{
		TableRow tr = new TableRow(getActivity());
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.game_list_row, tr, false);
		TextView tv = (TextView)v.findViewById(R.id.game_name);
		tv.setText(gameName);
		return v;
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}
