package ku.edu.kutty.quickquiz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ku.edu.kutty.quickquiz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoGameFragment extends Fragment
{
	
	
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
	
}
