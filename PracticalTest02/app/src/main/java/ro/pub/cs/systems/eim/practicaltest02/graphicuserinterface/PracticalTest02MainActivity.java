package ro.pub.cs.systems.eim.practicaltest02.graphicuserinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest02.R;
import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class PracticalTest02MainActivity extends AppCompatActivity {

    // Server widgets
    private EditText serverPortEditText = null;
    private Button connectButton = null;

    // Client widgets
    private EditText clientAddressEditText = null;
    private EditText clientPortEditText = null;
    private EditText cityEditText = null;
    private EditText cityEditText2 = null;
    private Spinner informationTypeSpinner = null;
    private Button getWeatherForecastButton = null;
    private Button getWeatherForecastButton2 = null;
    private TextView weatherForecastTextView = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
    private class ConnectButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Server port should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() != null) {
                serverThread.start();
            } else {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not creat server thread!");
            }

        }
    }

    private GetWeatherForecastButtonClickListener getWeatherForecastButtonClickListener = new GetWeatherForecastButtonClickListener();
    private class GetWeatherForecastButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort    = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty() ||
                    clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Client connection parameters should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (serverThread == null || !serverThread.isAlive()) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }

            String city = cityEditText.getText().toString();
            String city2 = cityEditText2.getText().toString();
            String informationType = "put";
            if (city == null || city.isEmpty() ||
                    informationType == null || informationType.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Parameters from client (city / information type) should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            weatherForecastTextView.setText("ceva");


            clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    city,
                    city2,
                    informationType,
                    weatherForecastTextView);
            clientThread.start();
        }
    }

    private GetWeatherForecastButtonClickListener2 getWeatherForecastButtonClickListener2 = new GetWeatherForecastButtonClickListener2();
    private class GetWeatherForecastButtonClickListener2 implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String clientAddress = clientAddressEditText.getText().toString();
            String clientPort    = clientPortEditText.getText().toString();
            if (clientAddress == null || clientAddress.isEmpty() ||
                    clientPort == null || clientPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Client connection parameters should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (serverThread == null || !serverThread.isAlive()) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
                return;
            }
            //Log.e(Constants.TAG, "[MAIN ACTIVITY] here");
            String city = cityEditText.getText().toString();
            String city2 = cityEditText2.getText().toString();
            String informationType = "get";
            if (city == null || city.isEmpty() ||
                    informationType == null || informationType.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Parameters from client (city / information type) should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            weatherForecastTextView.setText(Constants.EMPTY_STRING);


            clientThread = new ClientThread(
                    clientAddress,
                    Integer.parseInt(clientPort),
                    city,
                    city2,
                    informationType,
                    weatherForecastTextView);
            clientThread.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        connectButton = (Button)findViewById(R.id.connect_button);
        connectButton.setOnClickListener(connectButtonClickListener);

        clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        cityEditText = (EditText)findViewById(R.id.city_edit_text);
        cityEditText2 = (EditText)findViewById(R.id.city_edit_text2);
        //informationTypeSpinner = (Spinner)findViewById(R.id.information_type_spinner);
        getWeatherForecastButton = (Button)findViewById(R.id.get_weather_forecast_button);
        getWeatherForecastButton.setOnClickListener(getWeatherForecastButtonClickListener);

        getWeatherForecastButton2 = (Button)findViewById(R.id.get_weather_forecast_button2);
        getWeatherForecastButton2.setOnClickListener(getWeatherForecastButtonClickListener2);

        weatherForecastTextView = (TextView)findViewById(R.id.weather_forecast_text_view);
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
