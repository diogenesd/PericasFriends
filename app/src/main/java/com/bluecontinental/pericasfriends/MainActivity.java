package com.bluecontinental.pericasfriends;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String ENDPOINT = "seu.servidor.br"; // colocar endereço do ENDPOINT do servidor
    private static int PORT_REQUEST_MESSAGE = 1012;          // colocar a porta configurada do servidor para TCP
    private static int PORT_SEND_MESSAGE = 1011;             // colocar a porta configurada do servidor para UDP
    private static final String SEND_MESSAGE = "SEND MESSAGE ";
    private static final String GET_USERS = "GET USERS ";
    private static final String GET_MESSAGE = "GET MESSAGE ";
    private static final String MESSAGE_BROADCAST = "0";
    public static final String ANONYMOUS = "anonymous";
    private SharedPreferences mSharedPreferences;
    private  Cliente mCliente;

    private Button mSendButton;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Este cliente
        mCliente = new Cliente("login", "senha");       // colocar seus dados de acesso ao servidor

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send messages on click.
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
            if(mCliente!=null)
                 new GetuserAsync().execute(mCliente);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView messageTextView;
        public TextView messengerTextView;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            // messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            //  messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            //  messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }

    public static void getMessage(Cliente cliente) throws UnknownHostException, IOException {

        Log.d(TAG, "O conectou ao servidor!");

        StringBuilder mensagens = new StringBuilder();			// GUARDAR MENSAGENS RECEBIDAS
        String report = get(cliente);							// GET COM SERVIDOR
        mensagens.append(report);

        while (!report.equals(":")) {							// SE RETORNO IGUAL ":" NÃO TEM MAIS MENSAGENS NO SERVIDOR
            report = get(cliente);							// FAZ NOVO GET
            mensagens.append("\n").append(report);
        }
        mensagens.append("EOF");								// INFORMA QUE ACABOU AS MENSAGENS

        Log.d(TAG, "Retorno do servidor!\n" + mensagens.toString());

    }

    public static String get(Cliente cliente) throws UnknownHostException, IOException{

        Socket socket = new Socket(ENDPOINT, PORT_REQUEST_MESSAGE);			  // CRIA SOCKET COM ENDEREÇO SERVIDOR E PORTA DE ACESSO

        PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // MONTA OUTPUT

        StringBuilder send = new StringBuilder();						      // MONTA O FORMATO DO PACOTE DE ENVIO
        send.append(GET_MESSAGE)
                .append(cliente.getId())
                .append(":")
                .append(cliente.getPassword());

        output.println(send.toString());									// ENVIA MENSAGEM

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // MONTA INPUT DE RETORNO
        String report = input.readLine();

        socket.close();

        return report;
    }

    public static void getUsers(Cliente cliente) throws UnknownHostException, IOException{

        Socket socket = new Socket(ENDPOINT, PORT_REQUEST_MESSAGE);			 	 // CRIA SOCKET COM ENDEREÇO SERVIDOR E PORTA DE ACESSO
        Log.d(TAG, "O conectou ao servidor!");

        PrintWriter output = new PrintWriter(socket.getOutputStream(), true); 	// MONTA OUTPUT

        StringBuilder builder = new StringBuilder();						 	// MONTA O FORMATO DO PACOTE DE ENVIO
        builder.append(GET_USERS)
                .append(cliente.getId())
                .append(":")
                .append(cliente.getPassword());

        output.println(builder.toString());									   // ENVIA MENSAGEM

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // MONTA INPUT DE RETORNO
        String response = input.readLine();

        Log.d(TAG,"Retorno do servidor!\n" + response.toString());

        socket.close();
    }

    public static void sendMessageUDP(Cliente  cliente, Cliente cSend, String mensagem) throws IOException{

        InetAddress addr = InetAddress.getByName(ENDPOINT);			// ENDEREÇO DO SERVIDOR
        StringBuilder builder = new StringBuilder();					// MONTA O FORMATO DA MENSAGEM DE ENVIO
        builder.append(SEND_MESSAGE)
                .append(cliente.getId())
                .append(":")
                .append(cliente.getPassword())
                .append(":")
                .append(cSend.getId())
                .append(":")
                .append(mensagem);

        byte[] msg = builder.toString().getBytes();
        DatagramPacket pkg = new DatagramPacket(			// MONTA O FORMATO DO PACOTE DE ENVIO
                msg,										// MENSAGEM EM BYTES
                msg.length, 								// TAMANHO DA MENSAGEM
                addr, 										// ENDEREÇO DO SERVIDOR
                PORT_SEND_MESSAGE); 						// PORTA DE ACESSO
        DatagramSocket ds = new DatagramSocket(); 			// CRIA O DATAGRAMSOCKET RESPONSAVEL PELO ENVIO UDP
        ds.send(pkg); 										// ENVIO AO SERVIDOR

        Log.d(TAG,"Mensagem enviada para: " + addr.getHostAddress() + "\n" +
                "Porta: " + PORT_SEND_MESSAGE + "\n" + "Mensagem: " + msg.toString());

        ds.close();  										// FECHA O DATAGRAMSOCKET

    }

    class GetuserAsync extends AsyncTask<Cliente, Void, String> {

        private ProgressDialog load;

        @Override
        protected void onPreExecute(){
            Log.i("AsyncTask", "Exibindo ProgressDialog na tela Thread: " + Thread.currentThread().getName());
            load = ProgressDialog.show(MainActivity.this, "Por favor Aguarde ...","Estabelecendo conexão ...");

        }


        @Override
        protected String doInBackground(Cliente... params) {

            Socket socket = null;			 	 // CRIA SOCKET COM ENDEREÇO SERVIDOR E PORTA DE ACESSO
            try {
                socket = new Socket(ENDPOINT, PORT_REQUEST_MESSAGE);

            Log.d(TAG, "O conectou ao servidor!");

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true); 	// MONTA OUTPUT

            StringBuilder builder = new StringBuilder();						 	// MONTA O FORMATO DO PACOTE DE ENVIO
            builder.append(GET_USERS)
                    .append(params[0].getId())
                    .append(":")
                    .append(params[0].getPassword());

            output.println(builder.toString());									   // ENVIA MENSAGEM

            BufferedReader input = null;                                            // MONTA INPUT DE RETORNO
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String response = input.readLine();
                    Log.d(TAG,"Retorno do servidor!\n" + response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                socket.close();

            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(String response){
            if(response!=null) {
                Log.i("AsyncTask", "Exibindo retorno do servidor:"+ response.toString()+ "| "+Thread.currentThread().getName());
            }else{
                Log.i("AsyncTask", "Erro ao conectar com servidor " + Thread.currentThread().getName());
            }
            Log.i("AsyncTask", "Tirando ProgressDialog da tela Thread: " + Thread.currentThread().getName());
            load.dismiss();
        }


    }

}
