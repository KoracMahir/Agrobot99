package com.example.myapplication;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import android.view.View.OnTouchListener;


public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ENABLE_BT=1;
    ImageButton b1,b2,b3,b4,b5,motor,cameral,camerar;
    ImageView camera,robotheadl,robotheadr;
    TextView t1,t2;
    TextView textView;
    SeekBar s1;
    public Thread thread;
    int naprijed=0;
    int nazad=0;
    int desno=0;
    int lijevo=0;
    char bluetooth_action='S';
    int motor_status=0;
    int camera_status=0;
    int camera_status1=0;
    String address = null;
    private ProgressDialog progress;
    private BluetoothAdapter BA;
    private BluetoothSocket BS = null;
    private boolean isBtConnected = false;
    private Set<BluetoothDevice> pairedDevices;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(ImageButton)findViewById(R.id.naprijed);
        b2=(ImageButton)findViewById(R.id.nazad);
        b3=(ImageButton)findViewById(R.id.desno);
        b4=(ImageButton)findViewById(R.id.lijevo);
        b5=(ImageButton)findViewById(R.id.imageButton5);
        motor=(ImageButton)findViewById(R.id.motor);
        cameral=(ImageButton)findViewById(R.id.cameraleft);
        camerar=(ImageButton)findViewById(R.id.cameraright);
        robotheadl=(ImageView)findViewById(R.id.robotl);
        robotheadr=(ImageView)findViewById(R.id.robotr);
        camera=(ImageView)findViewById(R.id.robot);

        textView =(TextView)findViewById(R.id.komanda);
        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);

        //receive the address of the bluetooth device
        Intent newint = getIntent();

        //Intent newint = new Intent(this, devicelist.class);
        address = newint.getStringExtra(devicelist.EXTRA_ADDRESS);


        //msg(address);
        if(address!=null) {

            new ConnectBT().execute(); //Call the class to connect
        }

        //commands to be sent to bluetooth
        b1.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        naprijed=1;
                        textView.setText("Komanda: F");
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b1_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        naprijed=0;
                        break;
                }
                return false;
            }

        });
        b2.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        nazad=1;
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b2_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        nazad=0;
                        break;
                }
                return false;
            }

        });

        b3.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        desno=1;
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b3_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        desno=0;
                        break;
                }
                return false;
            }

        });

        b4.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lijevo=1;
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b4_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        lijevo=0;
                        break;
                }
                return false;
            }

        });

        cameral.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                robotheadl.setVisibility(View.VISIBLE);
                camera.setVisibility(View.GONE);
                robotheadr.setVisibility(View.GONE);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        camera_status=1;
                        textView.setText("Komanda: Z");
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b3_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        camera_status=0;
                        break;
                }
                return false;
            }

        });
        camerar.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                robotheadl.setVisibility(View.GONE);
                camera.setVisibility(View.GONE);
                robotheadr.setVisibility(View.VISIBLE);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textView.setText("Komanda: N");
                        camera_status1=1;
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b3_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        camera_status1=0;
                        break;
                }
                return false;
            }

        });
        motor.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        motor_status=1;
                        break;
                    //case MotionEvent.ACTION_MOVE:
                    //    b4_status=1;
                    //    break;
                    case MotionEvent.ACTION_UP:
                        motor_status=0;
                        break;
                }
                return false;
            }

        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Disconnect();
                Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                b5.setImageResource(R.drawable.disconnected);
                //setContentView(R.layout.activity_devicelist);
                Intent intent = new Intent(MainActivity.this, devicelist.class);
                MainActivity.this.startActivity(intent);
            }
        });

        //send_movement_action();

        thread = new Thread() {
            public void run() {
                Looper.prepare();
                while(true)
                {

                    try {
                        send_movement_action();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


            }
        };

    }


    private void Disconnect()
    {
        if (BS!=null) //If the btSocket is busy
        {
            try
            {
                BS.close(); //close connection

            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }



    private void send_movement_action()
    {
        if(naprijed==1) {
            bluetooth_action = 'F';
            textView.setText("Komanda: F");
        }
        else if(nazad==1)
        {
            bluetooth_action = 'B';
            textView.setText("Komanda: B");
        }
        else if(desno==1)
        {
            bluetooth_action = 'L';
            textView.setText("Komanda: L");
        }
        else if(lijevo==1)
        {
            bluetooth_action = 'R';
            textView.setText("Komanda: R");
        }
        else if(motor_status==1)
        {
            bluetooth_action = 'V';
            textView.setText("Komanda: V");
        }
        else if(motor_status==0)
        {
            bluetooth_action = 'v';
            textView.setText("Komanda: v");
        }
        else if(camera_status==1)
        {
            bluetooth_action = 'Z'; //Right
            textView.setText("Komanda: Z");
        }
        else if(camera_status1==1)
        {
            bluetooth_action = 'N'; //Left
            textView.setText("Komanda: N");
        }
        else{
            bluetooth_action = 'S';
            textView.setText("Komanda: S");
        }

        if (BS!=null)
        {
            try
            {
                BS.getOutputStream().write(bluetooth_action);
                //BS.getOutputStream().write("F".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (BS == null || !isBtConnected)
                {
                    BA = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = BA.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    BS = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    BS.connect();//start connection

                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                Toast.makeText(MainActivity.this,  "Connected to: "+address, Toast.LENGTH_SHORT).show();
                b5.setImageResource(R.drawable.connected);
                isBtConnected = true;
                thread.start();
            }
            progress.dismiss();
        }





    }







}