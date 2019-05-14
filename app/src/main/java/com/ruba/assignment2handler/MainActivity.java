package com.ruba.assignment2handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button handlerExampleButton;

    private TextView handlerExampleTextView;

    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Android Application - Handler Example");

        // This handler is used to handle child thread message from main thread message queue.
        mainThreadHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1)
                {
                    // Update view component text, this is allowed.
                    handlerExampleTextView.setText("Above button has been clicked.");
                }
            }
        };

        // Get the button.
        handlerExampleButton = (Button)findViewById(R.id.handlerExampleButton);
        handlerExampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a child thread when button is clicked.
                WorkerThread workerThread = new WorkerThread();
                workerThread.start();
            }
        });

        // Get the TextView which show button click message.
        handlerExampleTextView = (TextView)findViewById(R.id.handlerExampleTextView);
    }

    // This is a child thread class.
    private class WorkerThread extends Thread{
        @Override
        public void run() {
            // Create a message in child thread.
            Message childThreadMessage = new Message();
            childThreadMessage.what = 1;
            // Put the message in main thread message queue.
            mainThreadHandler.sendMessage(childThreadMessage);
        }
    }
}