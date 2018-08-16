package atpl.com.texturevideoview;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

public class VideoViewExample extends AppCompatActivity implements SurfaceHolder.Callback {


    String path;
    private MediaPlayer mp;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    boolean pausing = false;
    public static String filepath="rtsp://192.168.1.153:1935/live/myStream";

    boolean isPlaying=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_example);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        mPreview = (SurfaceView)findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.setFixedSize(800, 480);
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mp = new MediaPlayer();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

              /*  mp.release();
                mPreview.destroyDrawingCache();*/
                play();


                //Toast.makeText(VideoViewExample.this,"Media COmpleted",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        mp.setDisplay(holder);

        play();

        //play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


        Toast.makeText(VideoViewExample.this,"Surface error",Toast.LENGTH_SHORT).show();



    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {


        Toast.makeText(VideoViewExample.this,"Surface Destroyed",Toast.LENGTH_SHORT).show();

    }



    void play(){
        try {
            mp.setDataSource(filepath);

            mp.prepare();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }







    public  void check(){

        MediaPlayer mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.reset();
                return false;
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        try {
            mediaPlayer.setDataSource(filepath);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }




    }
}
