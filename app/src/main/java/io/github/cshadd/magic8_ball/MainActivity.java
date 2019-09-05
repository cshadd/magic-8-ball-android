package io.github.cshadd.magic8_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Sensor accelerometer;
    private Ball ball;
    private Button ballAskButton;
    private Button ballClearButton;
    private ImageView ballImageView;
    private TextView ballResponseTextView;
    private ImageView ballTriangleImageView;
    private Animation fadeInAnimation;
    private SensorEventListener accelerometerListener;
    private List<Sensor> sensorList;
    private SensorManager sensorManager;
    private Vibrator vibrator;

    public MainActivity() {
        super();
        return;
    }

    public void ask() {
        if (ballImageView != null && ballTriangleImageView != null
                && ballResponseTextView != null && vibrator != null) {
            ballImageView.setImageResource(R.drawable.ball_2);
            ballTriangleImageView.setAlpha(1.0f);
            ballTriangleImageView.startAnimation(fadeInAnimation);
            ballResponseTextView.setAlpha(1.0f);
            ballResponseTextView.startAnimation(fadeInAnimation);
            ballResponseTextView.setText(ball.shake());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                vibrator.vibrate(500);
            }
        }
        return;
    }

    public void clear() {
        if (ballImageView != null && ballTriangleImageView != null
                && ballResponseTextView != null && vibrator != null) {
            ballImageView.setImageResource(R.drawable.ball);
            ballResponseTextView.setAlpha(0.0f);
            ballTriangleImageView.setAlpha(0.0f);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                vibrator.vibrate(100);
            }
            return;
        }
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ball = new Ball();
        ballAskButton = (Button)findViewById(R.id.ball_ask_button);
        ballClearButton = (Button)findViewById(R.id.ball_clear_button);
        ballImageView = (ImageView)findViewById(R.id.ball_image_view);
        ballResponseTextView = (TextView)findViewById(R.id.ball_response_text_view);
        ballTriangleImageView = (ImageView)findViewById(R.id.ball_triangle_image_view);
        ballResponseTextView.setAlpha(0.0f);
        ballTriangleImageView.setAlpha(0.0f);
        fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        accelerometerListener = new SensorEventListener() {
            private boolean flipped = false;

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                return;
            }

            @Override
            public void onSensorChanged(SensorEvent arg0) {
                final float x = arg0.values[1];
                final float y = arg0.values[0];
                final float z = arg0.values[2];
                Log.d("Noga", "x:" + x + ", y:" + y + ", z:" + z);
                if (z < -10){
                    if (!flipped) {
                        ask();
                        flipped = true;
                    }
                }
                else if (z >= 0) {
                    flipped = false;
                }
            }
        };

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0) {
            accelerometer = sensorList.get(0);
        }
        else {
            accelerometer = null;
        }

        ballAskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask();
                return;
            }
        });
        ballClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                return;
            }
        });
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null){
            sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (accelerometer != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
        return;
    }
}
