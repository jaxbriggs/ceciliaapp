package br.com.carlos.ceciliaapp.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;

import br.com.carlos.ceciliaapp.R;

public class NovaTarefaActivity extends AppCompatActivity {

    private EditText edtxtPeriodoInicio;
    private Spinner spinnerPeriodicidadeTarefa;
    private LinearLayout periodoCustomizadoContainer;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Nova Tarefa");

        getViews();

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
                            // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        }
                    },mYear, mMonth, mDay);
                    mDatePicker.setTitle("Selecione a data");
                    mDatePicker.show();
                    v.clearFocus();
                }

            }
        });

        spinnerPeriodicidadeTarefa.setOnItemSelectedListener(new CustomOnItemSelectedListener());

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getViews(){
        this.edtxtPeriodoInicio = (EditText)findViewById(R.id.edtxtPeriodoInicio);
        this.spinnerPeriodicidadeTarefa = (Spinner) findViewById(R.id.spinnerPeriodicidadeTarefa);
        this.periodoCustomizadoContainer = (LinearLayout) findViewById(R.id.periodoCustomizadoContainer);
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if(pos == 3){
                periodoCustomizadoContainer.setVisibility(View.VISIBLE);
            } else {
                periodoCustomizadoContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

}
