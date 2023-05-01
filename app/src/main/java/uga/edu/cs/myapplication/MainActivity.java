package uga.edu.cs.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/*

 */
public class MainActivity extends AppCompatActivity  {
    private static Context mContext;

    private final String dbg = "MAIN_ACTIVITY";
    public DatabaseManager manager = new DatabaseManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }


}
