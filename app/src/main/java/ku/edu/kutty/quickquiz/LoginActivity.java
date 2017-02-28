package ku.edu.kutty.quickquiz;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // initialize UI elements
        final Button startButton = (Button) findViewById(R.id.buttonStart);
        final EditText nicknameEditText = (EditText) findViewById(R.id.editTextNickname);
        startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String nickname = nicknameEditText.getText().toString();
                if (nickname.length() > 0)
                {
                    User.initialize(nickname);
                    Log.d("name: ", User.getInstance().getNickname());
                    Log.d("score: ", Integer.toString(User.getInstance().getScore()));
                }
            }
        });
    }
}