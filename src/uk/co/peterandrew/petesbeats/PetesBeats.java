package uk.co.peterandrew.petesbeats;

import uk.co.peterandrew.petesbeats.SoundGridView.GridClickListener;
import uk.co.peterandrew.petesbeats.R;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;

public class PetesBeats extends Activity implements GridClickListener {
	private SoundGridView soundGridView;
    private SoundPool mSoundPool;
    private Integer sounds[] = new Integer[5];
    private Boolean mStates[][] = new Boolean[5][16];
	private Handler mHandler;
    private int currStep = 0;
    private boolean running = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        soundGridView = new SoundGridView(this);
        soundGridView.setGridClickListener(this);
        setContentView(soundGridView);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
    	mHandler = new Handler();
    	
    	for (int i = 0; i < 5; i++) {
    		for (int j = 0; j < 16; j++) {
    			mStates[i][j] = false;
    		}
    	}
    	
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sounds[0] = mSoundPool.load(this.getApplicationContext(), R.raw.kick, 1);
        sounds[1] = mSoundPool.load(this.getApplicationContext(), R.raw.snare, 1);
        sounds[2] = mSoundPool.load(this.getApplicationContext(), R.raw.hi_hat_closed, 1);
        sounds[3] = mSoundPool.load(this.getApplicationContext(), R.raw.hi_hat_open, 1);
        sounds[4] = mSoundPool.load(this.getApplicationContext(), R.raw.cowbell, 1);
    }
    
    protected void onResume() {
    	super.onResume();
    	running = true;
		mHandler.postDelayed(onTimerEvent, 10);
    }
    
    protected void onPause() {
    	running = false;
    	super.onPause();
    }
    
    public void onGridClick(int x, int y) {
		mStates[y][x] = !mStates[y][x];
    }  
    
    private Runnable onTimerEvent = new Runnable() {	
    	public void run() {
    		if (running) {
    			for (int i=0; i < 5; i++) {
    				if (mStates[i][currStep]) {
    					mSoundPool.play(sounds[i], 1.0f, 1.0f, 0, 0, 1.0f);
    				}
    			}
    			soundGridView.step(currStep);
    			
    			if (++currStep > 15) {
    				currStep = 0;
    			}
    			mHandler.postDelayed(this, 125);
    		}
    	}
    };  
}