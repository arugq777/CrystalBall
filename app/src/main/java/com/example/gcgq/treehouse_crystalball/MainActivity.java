package com.example.gcgq.treehouse_crystalball;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private CrystalBall mCrystalBall = new CrystalBall();
    private TextView mAnswerLabel;
    private Button mGetAnswerButton;
    private ImageView mCrystalBallImage;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign Views
        mAnswerLabel = (TextView)findViewById(R.id.main_textView);
        mGetAnswerButton =(Button)findViewById(R.id.main_button);
        mCrystalBallImage = (ImageView) findViewById((R.id.imageView));

        //setup Sensors
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                handleNewAnswer();
            }
        });

        mGetAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNewAnswer();
            }
        });

        Toast.makeText(this, "Huzzah. Activity successfully initialized.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(
                mShakeDetector,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_UI );
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }

    private void handleNewAnswer() {
        animateCrystalBall();
        mAnswerLabel.setText(mCrystalBall.getAnAnswer());
        animateAnswer();
        playSound();
    }

    private void animateCrystalBall(){
        mCrystalBallImage.setImageResource(R.drawable.ball_animation);
        AnimationDrawable aBallMination = (AnimationDrawable) mCrystalBallImage.getDrawable();
        if (aBallMination.isRunning()){
            aBallMination.stop();
        }
        aBallMination.start();
    }

    private void animateAnswer(){
        AlphaAnimation fadeInAnswer = new AlphaAnimation(0, 1);
        fadeInAnswer.setDuration(1500);
        fadeInAnswer.setFillAfter(true);
        mAnswerLabel.setAnimation(fadeInAnswer);
    }

    private void playSound(){
        MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
