package com.example.magic8_ball;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        final Button ballAskButton = (Button)findViewById(R.id.ball_ask_button);
        final Button ballClearButton = (Button)findViewById(R.id.ball_clear_button);
        final ImageView ballImageView = (ImageView)findViewById(R.id.ball_image_view);
        final TextView ballResponseTextView = (TextView)findViewById(R.id.ball_response_text_view);
        final ImageView ballTriangleImageView = (ImageView)findViewById(R.id.ball_triangle_image_view);
        ballResponseTextView.setAlpha(0.0f);
        ballTriangleImageView.setAlpha(0.0f);

        ballAskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballImageView.setImageResource(R.drawable.ball_2);
                ballTriangleImageView.setAlpha(1.0f);
                ballTriangleImageView.startAnimation(fadeInAnimation);
                ballResponseTextView.setAlpha(1.0f);
                ballResponseTextView.startAnimation(fadeInAnimation);
                ballResponseTextView.setText(ball.shake());

            }
        });
        ballClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballImageView.setImageResource(R.drawable.ball);
                ballResponseTextView.setAlpha(0.0f);
                ballTriangleImageView.setAlpha(0.0f);
            }
        });
    }
}
