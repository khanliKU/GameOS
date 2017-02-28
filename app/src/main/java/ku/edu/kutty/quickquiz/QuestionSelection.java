package ku.edu.kutty.quickquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;


public class QuestionSelection extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_selection);

        Button button = new Button(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_question_selection);

    }
}
