package atpl.com.texturevideoview;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.sprylab.android.widget.TextureVideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private TextureVideoView mVideoView;

    private Button mCaptureFrameButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVideoView = (TextureVideoView) findViewById(R.id.video_view);
        mCaptureFrameButton = (Button) findViewById(R.id.btn_capture_frame);
        mCaptureFrameButton.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(final View v) {
                                                       saveCurrentFrame();
                                                   }
                                               }
        );

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this,"Completed",Toast.LENGTH_SHORT).show();

                mVideoView.stopPlayback();

                initVideoView();
            }
        });

        initVideoView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoView != null) {
            mVideoView.stopPlayback();
            mVideoView = null;
        }
    }

    private void initVideoView() {
        mVideoView.setVideoURI(Uri.parse(getVideoPath()));
        //mVideoView.setVideoPath(getVideoPath());
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                startVideoPlayback();
                startVideoAnimation();
            }
        });
    }

    private void startVideoPlayback() {
        // "forces" anti-aliasing - but increases time for taking frames - so keep it disabled
        // mVideoView.setScaleX(1.00001f);

        try {
            mVideoView.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void startVideoAnimation() {
        mVideoView.animate().rotationBy(360.0f).setDuration(mVideoView.getDuration()).start();
    }

    private String getVideoPath() {
        //return "android.resource://" + getPackageName() + "/" + R.raw.video;
        //return "http://192.168.1.153:1935/live/myStream/playlist.m3u8";
        return "rtsp://192.168.1.153:1935/live/myStream";
    }

    private void saveCurrentFrame() {
        final Bitmap currentFrameBitmap = mVideoView.getBitmap();

        final File currentFrameFile = new File(getExternalFilesDir("frames"), "frame" + System.currentTimeMillis() + ".jpg");
        writeBitmapToFile(currentFrameBitmap, currentFrameFile);

        currentFrameBitmap.recycle();

        Toast.makeText(this, "Frame saved as " + currentFrameFile.getAbsolutePath() + ".", Toast.LENGTH_SHORT).show();
    }

    private void writeBitmapToFile(final Bitmap bitmap, final File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
        } catch (final IOException e) {
            Log.e(TAG, "Error writing bitmap to file.", e);
        }
    }
}
