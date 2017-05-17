package ku.edu.kutty.gameos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameListFragment extends Fragment
{
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	
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
				parent.viewQuickQuiz();
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
		
		mAuth = FirebaseAuth.getInstance();
		
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null)
				{
					// User is signed in
				}
				else
				{
					// User is signed out
					MainActivity activity = (MainActivity) getActivity();
					activity.logout();
				}
				// ...
			}
		};
		
		Button logoutButton = (Button) getActivity().findViewById(R.id.buttonLogout);
		logoutButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mAuth.signOut();
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		mAuth.addAuthStateListener(mAuthListener);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		if (mAuthListener != null)
		{
			mAuth.removeAuthStateListener(mAuthListener);
		}
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
