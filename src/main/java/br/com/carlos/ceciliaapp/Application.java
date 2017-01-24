package br.com.carlos.ceciliaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.carlos.ceciliaapp.dao.ORMLiteHelper;
import br.com.carlos.ceciliaapp.enumeration.RequestAction;
import br.com.carlos.ceciliaapp.http.NetworkFragment;
import br.com.carlos.ceciliaapp.model.Grupo;
import br.com.carlos.ceciliaapp.model.Tarefa;
import br.com.carlos.ceciliaapp.model.Usuario;

/**
 * Created by Carlos Henrique on 1/7/2017.
 */
public class Application {

    private static final String host = "br.com.carlos.ceciliaapp.";
    public static Usuario currentUser;
    public static SharedPreferences preferences;

    public static void clearAllPreferences(){

    }

    public static void clearSpecifiedPreferences(String[] entries){
        SharedPreferences.Editor editor = preferences.edit();
        for (String entry : entries){
            editor.remove(entry);
        }
        editor.apply();
    }

    public static void logoff(){
        clearSpecifiedPreferences(
                new String[]{
                        host+"CURRENT_USER_ID",host+"CURRENT_USER_LOGIN",
                        host+"CURRENT_USER_TOKEN",host+"CURRENT_USER_NAME"
                }
        );
        currentUser = null;
    }

    public static void loadCurrentUser(){
        if(currentUser == null)
            currentUser = new Usuario();

        currentUser.setID(preferences.getLong(host + "CURRENT_USER_ID", -1));
        currentUser.setLOGIN(preferences.getString(host + "CURRENT_USER_LOGIN", null));
        currentUser.setNOME(preferences.getString(host + "CURRENT_USER_NAME", null));
        currentUser.setToken(preferences.getString(host + "CURRENT_USER_TOKEN", null));
    }

    public static boolean hasUserLoggedIn(){
        loadCurrentUser();
        if(currentUser.getID() != -1 && currentUser.getLOGIN() !=null
                && currentUser.getNOME() != null && currentUser.getToken() != null){
            return true;
        } else {
            currentUser = null;
            return false;
        }
    }

    public static void updateAllData(Context context){
        getData(context, "grupo", RequestAction.BUSCAR_TODOS_GRUPOS);
        getData(context, "user", RequestAction.BUSCAR_TODOS_USUARIOS);
        getData(context, "user/tarefas", RequestAction.BUSCAR_USUARIO_TAREFAS);
    }

    private static void update(Context context, String response, RequestAction action) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        switch (action){
            case BUSCAR_TODOS_GRUPOS:
                RuntimeExceptionDao<Grupo, Integer> grupoDao = ORMLiteHelper.getInstance(context).getGrupoRuntimeDao();
                List<Grupo> grupos = mapper.readValue(response, new TypeReference<List<Grupo>>(){});
                if(grupos != null) {
                    for (Grupo g : grupos) {
                        grupoDao.createOrUpdate(g);
                    }
                }
            break;
            case BUSCAR_TODOS_USUARIOS:
                RuntimeExceptionDao<Usuario, Integer> usuarioDao = ORMLiteHelper.getInstance(context).getUsuarioRuntimeDao();
                List<Usuario> usuarios = mapper.readValue(response, new TypeReference<List<Usuario>>(){});
                if(usuarios != null) {
                    for (Usuario u : usuarios) {
                        usuarioDao.createOrUpdate(u);
                    }
                }
            break;
            case BUSCAR_USUARIO_TAREFAS:
                RuntimeExceptionDao<Tarefa, Integer> tarefaDao = ORMLiteHelper.getInstance(context).getTarefaRuntimeDao();
                List<Tarefa> tarefas = mapper.readValue(response, new TypeReference<List<Tarefa>>(){});
                if(tarefas != null) {
                    for (Tarefa t : tarefas) {
                        tarefaDao.createOrUpdate(t);
                    }
                }
                break;
        }
    }

    public static void getData(final Context context, String url, final RequestAction performAfter){

        url = NetworkFragment.API_BASE+url;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        final Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("Authorization", Application.currentUser.getToken());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            update(context, response, performAfter);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(context, "Erro ao buscar dados: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Erro ao buscar dados: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaders;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
