package ku.edu.kutty.quickquiz;

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
		for (int i = 0; i < 5; i++)
		{
			tl.addView(tableChild());
		}
	}
	
	private View tableChild() {
		TableRow tr = new TableRow(getActivity());
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.game_list_row, tr, false);
		//want to get childs of row for example TextView, get it like this
		TextView tv = (TextView)v.findViewById(R.id.game_name);
		tv.setText("This is another awesome way for dynamic custom layouts.");
		return v;//have to return View child, so return made 'v'
	}
	
	@Override
	public void onDetach()
	{
		super.onDetach();
	}
}
