package com.lics.proyectou2lectorqr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MonitoringScreen extends Activity {

    private static final String TAG = "BlueTest5-MainActivity";
    private int mMaxChars = 50000;//Default
    private UUID mDeviceUUID;
    private BluetoothSocket mBTSocket;
    private ReadInput mReadThread = null;

    private final boolean mIsUserInitiatedDisconnect = false;

    private TextView mTxtReceive;
    private TextView testBtn;
    private ScrollView scrollView;

    private boolean mIsBluetoothConnected = false;
    private BluetoothDevice mDevice;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_screen);
        ActivityHelper.initialize(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mDevice = b.getParcelable(MainActivity2.DEVICE_EXTRA);
        mDeviceUUID = UUID.fromString(b.getString(MainActivity2.DEVICE_UUID));
        mMaxChars = b.getInt(MainActivity2.BUFFER_SIZE);
        Log.d(TAG, "Ready");
        mTxtReceive = (TextView) findViewById(R.id.txtReceive);
        testBtn = (TextView) findViewById(R.id.testBtn);
        scrollView = (ScrollView) findViewById(R.id.viewScroll);

        mTxtReceive.setText("-----------");

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopped");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    private class ReadInput implements Runnable {
        private boolean bStop = false;
        private final Thread t;

        public ReadInput() {
            t = new Thread(this, "Input Thread");
            t.start();
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;
            try {
                inputStream = mBTSocket.getInputStream();
                while (!bStop) {

                    byte[] buffer = new byte[256];
                    if (inputStream.available() > 2) {
                        int i = inputStream.read(buffer);

                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }

                        final String strInput = new String(buffer, 0, i);
                        mTxtReceive.setText(strInput);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!mTxtReceive.getText().toString().equals("")){ // && Float.parseFloat(mTxtReceive.getText().toString()) <38.0) {
                                    if(Float.parseFloat(mTxtReceive.getText().toString()) <38.0) {
                                        testBtn.setTextColor(Color.parseColor("#0000CD"));
                                    }
                                    else{
                                        testBtn.setTextColor(Color.parseColor("#8B0000"));
                                    }
                                    testBtn.setText(mTxtReceive.getText().toString());
                                    Intent intent = new Intent(MonitoringScreen.this, MainActivity.class);

                                    String temperatura_monitoring_screen = mTxtReceive.getText().toString();
                                    intent.putExtra("temperatura", temperatura_monitoring_screen);

                                    Handler handler = new Handler();

                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            mTxtReceive.setText("-----------");
                                            startActivity(intent);
                                        }
                                    }, 1000);

                                }
                                else{
                                    testBtn.setBackgroundColor(Color.parseColor("#FF0000"));
                                }

                                scrollView.post(new Runnable() { // Snippet from http://stackoverflow.com/a/4612082/1287554
                                    @Override
                                    public void run() {
                                        scrollView.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                            }
                        });
                    }
                    Thread.sleep(50);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        public void stop() {
            bStop = true;
        }
    }

    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning())
                    ;
                mReadThread = null;
            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MonitoringScreen.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                mConnectSuccessful = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!mConnectSuccessful) {
                Toast.makeText(getApplicationContext(), "Could not connect to device." +
                        " Is it a Serial device? Also check if the UUID is correct in the settings", Toast.LENGTH_LONG).show();
                finish();
            } else {
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput(); // Kick off input reader
            }
            progressDialog.dismiss();
        }
    }
}