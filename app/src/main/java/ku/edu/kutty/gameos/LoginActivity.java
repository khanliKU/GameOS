package ku.edu.kutty.gameos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
	
		if ((getResources().getConfiguration().screenLayout &
				Configuration.SCREENLAYOUT_SIZE_MASK) ==
				Configuration.SCREENLAYOUT_SIZE_XLARGE)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

        if (User.getInstance() != null)
        {
            performSegue();
        }

        // initialize UI elements
        final EditText nicknameEditText = (EditText) findViewById(R.id.editTextNickname);

        nicknameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String nickname = nicknameEditText.getText().toString();
                    User.initialize(nickname);
                    if (nickname.length() > 0)
                    {
                        performSegue();
                    }
                }
                return false;
            }
        });
    }

    private void performSegue()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}