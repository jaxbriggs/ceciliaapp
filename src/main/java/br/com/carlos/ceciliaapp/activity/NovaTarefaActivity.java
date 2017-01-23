package br.com.carlos.ceciliaapp.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.carlos.ceciliaapp.Application;
import br.com.carlos.ceciliaapp.R;
import br.com.carlos.ceciliaapp.enumeration.HttpMethod;
import br.com.carlos.ceciliaapp.enumeration.RequestAction;
import br.com.carlos.ceciliaapp.http.DownloadCallback;
import br.com.carlos.ceciliaapp.http.NetworkFragment;
import br.com.carlos.ceciliaapp.model.Grupo;
import br.com.carlos.ceciliaapp.model.Tarefa;
import br.com.carlos.ceciliaapp.model.TarefaPeriodicidade;
import br.com.carlos.ceciliaapp.model.Usuario;

public class NovaTarefaActivity extends AppCompatActivity implements DownloadCallback {

    //Vars
    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    private EditText edtxtTituloNovaTarefa;

    private Spinner spinnerPeriodicidadeTarefa;

    //Customizado
    private EditText edtxtPeriodoInicio;
    private EditText edtxtPeriodoIntervalo;

    //Semanal
    private Spinner spinnerDiaSemana;

    //Mensal
    private EditText edtxtDiaMes;

    private Spinner spinnerTarefaGrupo;
    private Spinner spinnerTarefaResponsavel;

    private LinearLayout periodoCustomizadoContainer;
    private LinearLayout periodoMensalContainer;
    private LinearLayout periodoSemanalContainer;

    private Button btnCadastrarTarefa;

    private int mYear, mMonth, mDay;
    private Date customizadoInicioDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Nova Tarefa");

        getViews();

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), null);

        //Configura selecao de data de inicio (Customizado)
        edtxtPeriodoInicio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //To show current date in the datepicker
                    Calendar mcurrentDate=Calendar.getInstance();
                    mYear=mcurrentDate.get(Calendar.YEAR);
                    mMonth=mcurrentDate.get(Calendar.MONTH);
                    mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker=new DatePickerDialog(NovaTarefaActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            Calendar c = Calendar.getInstance();
                            c.set(selectedyear, selectedmonth, selectedday);
                            customizadoInicioDate = c.getTime();

                            edtxtPeriodoInicio.setText(selectedday+"/"+(selectedmonth+1)+"/"+selectedyear);
                            edtxtPeriodoInicio.clearFocus();
                        }
                    },mYear, mMonth, mDay);
                    mDatePicker.setTitle("Selecione a data");
                    mDatePicker.show();
                }
            }
        });

        //Configura a visualizacao de acordo com a periodicidade
        spinnerPeriodicidadeTarefa.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        btnCadastrarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectMapper mapper = new ObjectMapper();
                Tarefa tarefa = new Tarefa();
                tarefa.setTitulo(edtxtTituloNovaTarefa.getText().toString());

                tarefa.setGrupo((Grupo) spinnerTarefaGrupo.getSelectedItem());
                tarefa.setResponsavel((Usuario) spinnerTarefaResponsavel.getSelectedItem());
                tarefa.setUsuarioId(Application.currentUser.getId());

                TarefaPeriodicidade periodicidade = new TarefaPeriodicidade();
                if(spinnerPeriodicidadeTarefa.getSelectedItemPosition() == 0){
                    periodicidade.setDiaSemana(null);
                    periodicidade.setDiaMes(null);
                    periodicidade.setDiaPartir(null);
                    periodicidade.setPasso((short)1);
                } else if(spinnerPeriodicidadeTarefa.getSelectedItemPosition() == 1) {
                    periodicidade.setDiaSemana((short)(spinnerDiaSemana.getSelectedItemPosition()+1));
                    periodicidade.setDiaMes(null);
                    periodicidade.setDiaPartir(null);
                    periodicidade.setPasso(null);
                } else if(spinnerPeriodicidadeTarefa.getSelectedItemPosition() == 2) {
                    periodicidade.setDiaSemana(null);
                    periodicidade.setDiaMes(Short.parseShort(edtxtDiaMes.getText().toString()));
                    periodicidade.setDiaPartir(null);
                    periodicidade.setPasso(null);
                } else if(spinnerPeriodicidadeTarefa.getSelectedItemPosition() == 3) {
                    periodicidade.setDiaSemana(null);
                    periodicidade.setDiaMes(null);
                    periodicidade.setDiaPartir(customizadoInicioDate);
                    periodicidade.setPasso(Short.parseShort(edtxtPeriodoIntervalo.getText().toString()));
                }

                tarefa.setPeriodicidade(periodicidade);

                try {
                    //Object to JSON in String
                    String tarefaJson = mapper.writeValueAsString(tarefa);
                    JSONObject params = new JSONObject();

                    params.put("tarefa", tarefaJson);

                    startDownload(HttpMethod.PUT, params, "tarefa?XDEBUG_SESSION_START=CARLOS-HENRIQUE$", RequestAction.CADASTRAR_TAREFA);
                } catch (Exception ex) {
                    Toast.makeText(NovaTarefaActivity.this, "Erro ao converter tarefa para JSON.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Popula spinner de grupo

        mNetworkFragment.simpleParallelRequest("grupo?XDEBUG_SESSION_START=CARLOS-HENRIQUE$", RequestAction.BUSCAR_TODOS_GRUPOS);
        mNetworkFragment.simpleParallelRequest("user?XDEBUG_SESSION_START=CARLOS-HENRIQUE$", RequestAction.BUSCAR_TODOS_USUARIOS);
    }

    private void startDownload(HttpMethod method, JSONObject output, String url, RequestAction actionPerformed) {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            if(url != null){
                mNetworkFragment.setmUrlString(url);
            }
            mNetworkFragment.startDownload(method, output, actionPerformed);
            mDownloading = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe_avaliacoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_voltar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getViews(){
        this.edtxtPeriodoInicio = (EditText)findViewById(R.id.edtxtPeriodoInicio);
        this.edtxtTituloNovaTarefa = (EditText)findViewById(R.id.edtxtTituloNovaTarefa);
        this.edtxtPeriodoIntervalo = (EditText)findViewById(R.id.edtxtPeriodoIntervalo);
        this.edtxtDiaMes = (EditText)findViewById(R.id.edtxtDiaMes);

        this.periodoCustomizadoContainer = (LinearLayout) findViewById(R.id.periodoCustomizadoContainer);
        this.periodoMensalContainer = (LinearLayout) findViewById(R.id.periodoMensalContainer);
        this.periodoSemanalContainer = (LinearLayout) findViewById(R.id.periodoSemanalContainer);

        this.btnCadastrarTarefa = (Button) findViewById(R.id.btnCadastrarTarefa);

        this.spinnerTarefaGrupo = (Spinner) findViewById(R.id.spinnerTarefaGrupo);
        this.spinnerTarefaResponsavel = (Spinner) findViewById(R.id.spinnerTarefaResponsavel);
        this.spinnerDiaSemana = (Spinner) findViewById(R.id.spinnerDiaSemana);
        this.spinnerPeriodicidadeTarefa = (Spinner) findViewById(R.id.spinnerPeriodicidadeTarefa);
    }

    @Override
    public void updateFromDownload(Object result, RequestAction actionPerformed) {

        switch (actionPerformed){
            case CADASTRAR_TAREFA:

                JSONObject obj = null;

                try {
                    String json = result.toString();
                    obj = new JSONObject((new JSONObject(json)).getString("response"));
                    if(obj.getInt("status") == 200) {
                        Toast.makeText(NovaTarefaActivity.this, obj.getString("body"), Toast.LENGTH_SHORT).show();
                        limparForm();
                    } else {
                        Toast.makeText(NovaTarefaActivity.this, "Falha ao cadastrar tarefa: " + obj.getString("body"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(NovaTarefaActivity.this, "Falha ao cadastrar tarefa.", Toast.LENGTH_SHORT).show();
                }
            break;
            case BUSCAR_TODOS_GRUPOS:

                JSONArray array = null;

                try {
                    String json = result.toString();

                    array = new JSONArray(json);

                    List<Grupo> grupos = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++) {
                        Grupo g = new Grupo();
                        g.setId(array.getJSONObject(i).getLong("ID"));
                        g.setNome(array.getJSONObject(i).getString("NOME"));
                        g.setDtCadastro(new SimpleDateFormat("yyyy-MM-dd").parse(array.getJSONObject(i).getString("DT_CADASTRO")));
                        grupos.add(g);
                    }

                    GrupoArrayAdapter grupoArrayAdapter = new GrupoArrayAdapter(NovaTarefaActivity.this, grupos);
                    spinnerTarefaGrupo.setAdapter(grupoArrayAdapter);

                } catch (Exception ex) {
                    Toast.makeText(NovaTarefaActivity.this, "Falha buscar grupos.", Toast.LENGTH_LONG).show();
                }

            break;
            case BUSCAR_TODOS_USUARIOS:

                JSONArray responsaveisArray = null;

                try {
                    String json = result.toString();

                    responsaveisArray = new JSONArray(json);

                    //Popula spinner de responsaveis
                    List<Usuario> responsaveis = new ArrayList<>();
                    for(int i = 0; i < responsaveisArray.length(); i++) {
                        Usuario u = new Usuario();
                        u.setId(responsaveisArray.getJSONObject(i).getLong("id"));
                        u.setNome(responsaveisArray.getJSONObject(i).getString("nome"));
                        u.setLogin(responsaveisArray.getJSONObject(i).getString("login"));
                        responsaveis.add(u);
                    }

                    ResponsavelArrayAdapter responsavelArrayAdapter = new ResponsavelArrayAdapter(NovaTarefaActivity.this, responsaveis);
                    spinnerTarefaResponsavel.setAdapter(responsavelArrayAdapter);

                } catch (Exception ex) {
                    Toast.makeText(NovaTarefaActivity.this, "Falha buscar responsáveis.", Toast.LENGTH_LONG).show();
                }

            break;
        }

    }

    private void limparForm(){
        this.edtxtTituloNovaTarefa.setText("");

        this.spinnerPeriodicidadeTarefa.setSelection(0);
        periodoCustomizadoContainer.setVisibility(View.GONE);
        periodoSemanalContainer.setVisibility(View.GONE);
        periodoMensalContainer.setVisibility(View.GONE);
        edtxtPeriodoInicio.setText("");
        edtxtPeriodoIntervalo.setText("");
        edtxtDiaMes.setText("");
        spinnerDiaSemana.setSelection(0);

        this.spinnerTarefaGrupo.setSelection(0);
        this.spinnerTarefaResponsavel.setSelection(0);
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

    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    /*INNER CLASSES*/
    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if(pos == 1) {
                //Semanal
                periodoCustomizadoContainer.setVisibility(View.GONE);
                periodoSemanalContainer.setVisibility(View.VISIBLE);
                periodoMensalContainer.setVisibility(View.GONE);

                edtxtPeriodoInicio.setText("");
                edtxtPeriodoIntervalo.setText("");
                edtxtDiaMes.setText("");
            } else if(pos == 2){
                //Mensal
                periodoCustomizadoContainer.setVisibility(View.GONE);
                periodoSemanalContainer.setVisibility(View.GONE);
                periodoMensalContainer.setVisibility(View.VISIBLE);

                spinnerDiaSemana.setSelection(0);

                edtxtPeriodoInicio.setText("");
                edtxtPeriodoIntervalo.setText("");

            } else if(pos == 3){
                //Customizado
                periodoCustomizadoContainer.setVisibility(View.VISIBLE);
                periodoSemanalContainer.setVisibility(View.GONE);
                periodoMensalContainer.setVisibility(View.GONE);

                edtxtDiaMes.setText("");
                spinnerDiaSemana.setSelection(0);

            } else {
                periodoCustomizadoContainer.setVisibility(View.GONE);
                periodoSemanalContainer.setVisibility(View.GONE);
                periodoMensalContainer.setVisibility(View.GONE);
                edtxtPeriodoInicio.setText("");
                edtxtPeriodoIntervalo.setText("");
                edtxtDiaMes.setText("");
                spinnerDiaSemana.setSelection(0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public class GrupoArrayAdapter extends ArrayAdapter<Grupo> {

        private List<Grupo> grupos;
        private Context mContext;

        public GrupoArrayAdapter(Context context, List<Grupo> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            this.grupos = objects;
            this.mContext = context;
        }

        @Nullable
        @Override
        public long getItemId(int position) {
            if(grupos != null && grupos.get(position) != null) {
                return grupos.get(position).getId();
            } else {
                return -1L;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView lbl = (TextView) v.findViewById(android.R.id.text1);
            lbl.setText(grupos.get(position).getNome());
            return v;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView lbl = (TextView) v.findViewById(android.R.id.text1);
            lbl.setText(grupos.get(position).getNome());
            return v;
        }
    }

    public class ResponsavelArrayAdapter extends ArrayAdapter<Usuario> {

        private List<Usuario> responsaveis;
        private Context mContext;

        public ResponsavelArrayAdapter(Context context, List<Usuario> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            this.responsaveis = objects;
            this.mContext = context;
        }

        @Nullable
        @Override
        public long getItemId(int position) {
            if(responsaveis != null && responsaveis.get(position) != null) {
                return responsaveis.get(position).getId();
            } else {
                return -1L;
            }
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView lbl = (TextView) v.findViewById(android.R.id.text1);
            lbl.setText(responsaveis.get(position).getNome() + " (" + responsaveis.get(position).getLogin() + ")");
            return v;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
                v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView lbl = (TextView) v.findViewById(android.R.id.text1);
            lbl.setText(responsaveis.get(position).getNome() + " (" + responsaveis.get(position).getLogin() + ")");
            return v;
        }
    }

}
