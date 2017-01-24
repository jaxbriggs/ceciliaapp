package br.com.carlos.ceciliaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.enumeration.HttpMethod;
import br.com.carlos.ceciliaapp.http.DownloadCallback;
import br.com.carlos.ceciliaapp.http.NetworkFragment;
import br.com.carlos.ceciliaapp.model.Usuario;

public class LoginActivity extends AppCompatActivity implements DownloadCallback {

    //Vars
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    // UI references.
    private Button btnEntrar;
    private ProgressBar mProgressBar;
    private EditText edtxtLogin;
    private EditText edtxtSenha;
    private CheckBox chkContinuarConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Application.preferences = getPreferences(Context.MODE_PRIVATE);

        if(Application.hasUserLoggedIn()) {
            gotoTarefasActivity();
        }

        loadViews();

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "user/login?XDEBUG_SESSION_START=CARLOS-HENRIQUE$");

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject params = new JSONObject();

                try {
                    params.put("login", edtxtLogin.getText().toString());
                    params.put("senha", edtxtSenha.getText().toString());

                    startDownload(HttpMethod.POST, params);
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, "Erro ao converter login e/ou senha para JSON.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadViews(){
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        edtxtLogin = (EditText) findViewById(R.id.edtxtLogin);
        edtxtSenha = (EditText) findViewById(R.id.edtxtSenha);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarEntrar);
        chkContinuarConectado = (CheckBox) findViewById(R.id.chkContinuarConectado);
    }

    private void gotoTarefasActivity(){
        Intent intent  = new Intent(LoginActivity.this, TarefasActivity.class);
        /*
        intent.putExtra("SAIR", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        */
        startActivity(intent);
    }

    private void startDownload(HttpMethod method, JSONObject output) {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload(method, output);
            mDownloading = true;
            showProgress();
        }
    }

    private void showProgress(){
        mProgressBar.setVisibility(View.VISIBLE);
        btnEntrar.setVisibility(View.GONE);
    }

    private void hideProgress(){
        btnEntrar.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateFromDownload(Object result) {

        JSONObject obj = null;

        try {
            String json = result.toString();
            obj = new JSONObject(json);
            String token = obj.getString("token");

            Application.currentUser = new Usuario();
            Application.currentUser.setToken(token);
            Application.currentUser.setLOGIN(edtxtLogin.getText().toString());

            if(chkContinuarConectado.isChecked()){
                SharedPreferences.Editor editor = Application.preferences.edit();
                editor.putLong(getString(R.string.app_host)+"CURRENT_USER_ID", Application.currentUser.getID());
                editor.putString(getString(R.string.app_host) + "CURRENT_USER_NAME", Application.currentUser.getNOME());
                editor.putString(getString(R.string.app_host) + "CURRENT_USER_LOGIN", Application.currentUser.getLOGIN());
                editor.putString(getString(R.string.app_host) + "CURRENT_USER_TOKEN", Application.currentUser.getToken());
                editor.commit();
            }

            gotoTarefasActivity();

        } catch (JSONException ex) {

            if(obj == null){
                Toast.makeText(LoginActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText(LoginActivity.this, obj.getJSONObject("response").getString("body"), Toast.LENGTH_SHORT).show();
                } catch (Exception ex2) {
                    Toast.makeText(LoginActivity.this, "Resposta do servidor inválida.", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception ex) {
            Toast.makeText(LoginActivity.this, "Falha! Tente novamente.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                //...
                break;
            case Progress.CONNECT_SUCCESS:
                //...
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                //...
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                //...
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                //...
                break;
        }
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        hideProgress();
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }
}

