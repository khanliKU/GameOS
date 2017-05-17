package ku.edu.kutty.gameos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity
{
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
		
		mAuth = FirebaseAuth.getInstance();
	
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null)
				{
					// User is signed in
					performSegue();
				}
				else
				{
					// User is signed out
					
				}
				// ...
			}
		};
	
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
        final EditText emailEditText = (EditText) findViewById(R.id.editTextNickname);
        final EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);
		final Button loginButton = (Button) findViewById(R.id.buttonLogin);
		final Button signupButton = (Button) findViewById(R.id.buttonSignup);
		
		loginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String email = emailEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				if (email.length() > 0 && password.length() > 0)
				{
//					User.initialize(nickname);
					
					//TODO: login
					mAuth.signInWithEmailAndPassword(email, password)
						 .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
							 @Override
							 public void onComplete(@NonNull Task<AuthResult> task) {
								 Log.d("Login", "signInWithEmail:onComplete:" + task.isSuccessful());
							
								 // If sign in fails, display a message to the user. If sign in succeeds
								 // the auth state listener will be notified and logic to handle the
								 // signed in user can be handled in the listener.
								 if (!task.isSuccessful()) {
									 Log.w("Login", "signInWithEmail:failed", task.getException());
									 Toast.makeText(LoginActivity.this, R.string.auth_failed,
													Toast.LENGTH_SHORT).show();
								 }
							
								 // ...
							 }
						 });
				}
			}
		});
	
		signupButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String email = emailEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				if (email.length() > 0 && password.length() > 0)
				{
//					User.initialize(nickname);
				
					//TODO: signup
					mAuth.createUserWithEmailAndPassword(email, password)
						 .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
						 {
							 @Override
							 public void onComplete(@NonNull Task<AuthResult> task) {
								 Log.d("Signup", "createUserWithEmail:onComplete:" + task.isSuccessful());
							
								 // If sign in fails, display a message to the user. If sign in succeeds
								 // the auth state listener will be notified and logic to handle the
								 // signed in user can be handled in the listener.
								 if (!task.isSuccessful())
								 {
									 Toast.makeText(LoginActivity.this, R.string.auth_failed,
													Toast.LENGTH_SHORT).show();
								 }
							
								 // ...
							 }
						 });
				}
			}
		});
    }
	
	@Override
	protected void onStart()
	{
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		if (mAuthListener != null)
		{
			mAuth.removeAuthStateListener(mAuthListener);
		}
	}
	
	private void performSegue()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}