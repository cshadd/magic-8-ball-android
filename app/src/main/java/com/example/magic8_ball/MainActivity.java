package com.example.magic8_ball;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Ball ball;

    public MainActivity() {
        super();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ball = new Ball();

        final Button ballClearButton = (Button)findViewById(R.id.ball_clear_button);
        final ImageView ballImageView = (ImageView)findViewById(R.id.ball_image_view);
        final TextView ballResponseTextView = (TextView)findViewById(R.id.ball_response_text_view);
        final Button ballShakeButton = (Button)findViewById(R.id.ball_shake_button);
        final ImageView ballTriangleImageView = (ImageView)findViewById(R.id.ball_triangle_image_view);

        ballClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballImageView.setImageResource(R.drawable.ball);
                ballResponseTextView.setText("");
                ballTriangleImageView.setVisibility(View.INVISIBLE);
            }
        });
        ballShakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballImageView.setImageResource(R.drawable.ball_2);
                ballResponseTextView.setText(ball.shake());
                ballTriangleImageView.setVisibility(View.VISIBLE);
            }
        });
        ballTriangleImageView.setVisibility(View.INVISIBLE);
    }
}
