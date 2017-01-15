package br.com.carlos.ceciliaapp;

import android.content.SharedPreferences;

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

        currentUser.setId(preferences.getLong(host + "CURRENT_USER_ID", -1));
        currentUser.setLogin(preferences.getString(host + "CURRENT_USER_LOGIN", null));
        currentUser.setNome(preferences.getString(host + "CURRENT_USER_NAME", null));
        currentUser.setToken(preferences.getString(host + "CURRENT_USER_TOKEN", null));
    }

    public static boolean hasUserLoggedIn(){
        loadCurrentUser();
        if(currentUser.getId() != -1 && currentUser.getLogin() !=null
                && currentUser.getNome() != null && currentUser.getToken() != null){
            return true;
        } else {
            currentUser = null;
            return false;
        }
    }

}
