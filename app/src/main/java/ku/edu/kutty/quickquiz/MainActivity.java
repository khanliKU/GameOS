package ku.edu.kutty.quickquiz;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
	
	private boolean onTablet = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.game_list_frame, new GameListFragment(),"game_list_fragment").commit();
		
		// TODO: Fix initial views here, it is set this way to skip login for now
		if (findViewById(R.id.game_frame) != null)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			onTablet = true;
			viewCategories();
		}
		else
		{
			
		}
		
		ArrayList<Flag> flags = new ArrayList<>();
		String[] fileNames;
		try
		{
			fileNames = getAssets().list("MemoGameFlags");
			AssetManager assetManager = getAssets();
			for (String name:fileNames)
			{
				InputStream is = assetManager.open("MemoGameFlags/" + name);
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				flags.add(new Flag(name,bitmap));
			}
			MemoGame.initialize(flags.toArray(new Flag[0]));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void selectQuestion(int[] index)
	{
		if (index[1] <= QuickQuiz.getInstance().getCategories()[index[0]].getMaxAllowedIndex())
		{
			Bundle bundle = new Bundle();
			bundle.putIntArray("index",index);
			Fragment answeringFragment = new AnsweringFragment();
			answeringFragment.setArguments(bundle);
			if (onTablet)
			{
				getSupportFragmentManager().beginTransaction().replace(R.id.game_frame, answeringFragment,"answering_fragment").commit();
			}
			else
			{
				getSupportFragmentManager().beginTransaction().replace(R.id.game_list_frame, answeringFragment,"answering_fragment").commit();
			}
		}
	}
	
	public void viewCategories()
	{
		if (!checkEndGame()) {
			QuestionSelectionFragment qs = new QuestionSelectionFragment();
			if (onTablet) {
				getSupportFragmentManager().beginTransaction().replace(R.id.game_frame, qs, "question_selection_fragment").commit();
			}
			else {
				getSupportFragmentManager().beginTransaction().replace(R.id.game_list_frame, qs, "question_selection_fragment").commit();
			}
		}
	}
	
	private boolean checkEndGame()
	{
		boolean won = false;
		boolean lost = false;
		Bundle bundle = new Bundle();
		GameOverFragment gameOverFragment = new GameOverFragment();
		if (QuickQuiz.checkForLoss())
		{
			lost = true;
			bundle.putBoolean("won",false);
		}
		else if (QuickQuiz.checkForWin())
		{
			won = true;
			bundle.putBoolean("won",true);
		}
		if (won || lost) {
			gameOverFragment.setArguments(bundle);
			putFragment(gameOverFragment, "game_over_fragment");
			return true;
		}
		return false;
	}
	
	@Override
	public void onBackPressed()
	{
		if (onTablet)
		{
			if (getSupportFragmentManager().findFragmentByTag("answering_fragment") != null)
			{
				viewCategories();
			}
			else if (getSupportFragmentManager().findFragmentByTag("question_selection_fragment") != null)
			{
				
			}
		}
		else
		{
			if (getSupportFragmentManager().findFragmentByTag("answering_fragment") != null)
			{
				viewCategories();
			} else if (getSupportFragmentManager().findFragmentByTag("memo_game_fragment") != null ||
					getSupportFragmentManager().findFragmentByTag("question_selection_fragment") != null)
			{
				putFragment(new GameListFragment(), "game_list_fragmnet");
			}
		}
	}
	
	private void putFragment(Fragment fragment, String tag)
	{
		if (onTablet)
		{
			getSupportFragmentManager().beginTransaction().replace(R.id.game_frame, fragment,tag).commit();
		}
		else
		{
			getSupportFragmentManager().beginTransaction().replace(R.id.game_list_frame, fragment,tag).commit();
		}
	}
	
	public void viewMemoGame()
	{
		putFragment(new MemoGameFragment(),"memo_game_fragment");
	}
}
