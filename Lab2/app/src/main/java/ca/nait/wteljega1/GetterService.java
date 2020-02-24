package ca.nait.wteljega1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class GetterService extends Service
{
    public static final String TAG = "GetterService";
    static final int DELAY = 10000;
    public static boolean bRun = false;
    private ChatThread thread;

    public GetterService()
    {
        Log.d(TAG, "In Constructor");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        bRun = true;
        thread.start();
        Log.d(TAG, "in onStartCommand()");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        bRun = false;
        thread.interrupt();
        thread = null;
        Log.d(TAG, "in onDestroy()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private class ChatThread extends Thread
    {
        public ChatThread()
        {
            super("ChatServiceThread");
        }

        public void run()
        {
            while(bRun == true)
            {
                try
                {
                    Log.d(TAG, "thread executed on cycle");
                    getFromServer();
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException ie)
                {
                    Log.d(TAG, "thread interrupted");
                    bRun = false;
                }
            }
        }
        public void getFromServer()
        {
            BufferedReader in = null;

            try
            {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://www.youcode.ca/JSONServlet")); /* IDK URL EXACTLY */
                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                while((line = in.readLine()) != null)
                {
                    Log.d(TAG, line);

                }
                in.close();
            }
            catch(Exception e)
            {
                Log.d(TAG, "error " + e);
            }
        }
    }
}
