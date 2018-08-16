package atpl.com.texturevideoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends Activity implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener {
    final static String USERNAME = "admin";
    final static String PASSWORD = "camera";
    //final static String RTSP_URL = "rtsp://10.0.1.7:554/play1.sdp";
    final static String RTSP_URL = "rtsp://192.168.1.153:1935/live/myStream";

    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    public static String filepath="rtsp://192.168.1.44:1935/live/myStream";
    SurfaceView surfaceView;

    public boolean SURFACESTOPPED=false;

    public Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a full-screen black window.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.black);

        setContentView(R.layout.activity_main2);

        //playAgain=(Button)findViewById(R.id.btnPlayAgain);

     /*   playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //play();
                 runTime();

              *//*  surfaceView.invalidate();
                surfaceView.setWillNotDraw(false);

                Toast.makeText(Main2Activity.this,"Clicked",Toast.LENGTH_SHORT).show();
                play();*//*
            }
        });*/
        // Configure the view that renders live video.
        surfaceView =
                (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setFixedSize(320, 240);
    }

    /*
    SurfaceHolder.Callback
*/

    @Override
    public void surfaceChanged(
            SurfaceHolder sh, int f, int w, int h) {

            Toast.makeText(Main2Activity.this,"Surface changed",Toast.LENGTH_SHORT).show();
    }

    public void runTime(){

try {
    surfaceView.invalidate();
    surfaceView.setWillNotDraw(false);

    _mediaPlayer.release();

    play();

}catch (Exception ex){
    ex.printStackTrace();
}

      /*  if(_mediaPlayer.isPlaying()){

            Toast.makeText(Main2Activity.this,"Media playaer playing",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Main2Activity.this,"Media playaer is not playing",Toast.LENGTH_SHORT).show();
        }*/



    }

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        setListener();

        play();

       /* Context context = getApplicationContext();
        Map<String, String> headers = getRtspHeaders();
        Uri source = Uri.parse(RTSP_URL);

        try {
            // Specify the IP camera's URL and auth headers.
            _mediaPlayer.setDataSource(context, source, headers);

            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);
            _mediaPlayer.prepareAsync();
        }
        catch (Exception e) {}*/
    }


    void setListener(){

        _mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                surfaceView.invalidate();
                surfaceView.setWillNotDraw(false);


                play();
            }
        });


    }



    class MediaPlayerCustomize extends MediaPlayer implements MediaPlayer.OnCompletionListener {


        @Override
        public void pause() throws IllegalStateException {
            super.pause();
        }

        @Override
        public void onCompletion(MediaPlayer mp) {



        }

        @Override
        public void start() throws IllegalStateException {
            super.start();
        }



    }


    void play(){
        try {


              // _mediaPlayer=new MediaPlayer();

                //_mediaPlayer.setDisplay(_surfaceHolder);


                _mediaPlayer.setDataSource(filepath);



               // setListener();
/*

                _mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {

                        Toast.makeText(Main2Activity.this,"Error playing",Toast.LENGTH_SHORT).show();

                                return true;
                    }
                });
*/

                _mediaPlayer.prepare();




            //if (_mediaPlayer.getState() == Controller.Started)



        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _mediaPlayer.start();


    }


    @Override
    public void surfaceDestroyed(SurfaceHolder sh) {

        Toast.makeText(Main2Activity.this,"Surface destroyed",Toast.LENGTH_SHORT).show();

        _mediaPlayer.release();
    }



    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
        headers.put("Authorization", basicAuthValue);
        return headers;
    }

    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }
    /*
MediaPlayer.OnPreparedListener
*/
    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }




    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        _mediaPlayer.reset();
    }




}
