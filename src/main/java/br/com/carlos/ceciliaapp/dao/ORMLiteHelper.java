package br.com.carlos.ceciliaapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.carlos.ceciliaapp.model.Grupo;
import br.com.carlos.ceciliaapp.model.Tarefa;
import br.com.carlos.ceciliaapp.model.TarefaPeriodicidade;
import br.com.carlos.ceciliaapp.model.Usuario;

/**
 * Created by Carlos Henrique on 1/23/2017.
 */

public class ORMLiteHelper extends OrmLiteSqliteOpenHelper {

    // Nome da base de dados.
    private static final String DATABASE_NAME = "ceciliaapp.db";

    // Versão da base de dados.
    private static final int DATABASE_VERSION = 21;

    // Caso você queria ter apenas uma instancia da base de dados.
    private static ORMLiteHelper mInstance = null;

    // Daos das tabelas.
    private Dao<Usuario, Integer> usuarioDao = null;
    private Dao<Grupo, Integer> grupoDao = null;
    private Dao<Tarefa, Integer> tarefaDao = null;
    private Dao<TarefaPeriodicidade, Integer> tarefaPeriodicidadeDao = null;

    private RuntimeExceptionDao<Usuario, Integer> usuarioRuntimeDao = null;
    private RuntimeExceptionDao<Grupo, Integer> grupoRuntimeDao = null;
    private RuntimeExceptionDao<Tarefa, Integer> tarefaRuntimeDao = null;
    private RuntimeExceptionDao<TarefaPeriodicidade, Integer> tarefaPeriodicidadeRuntimeDao = null;

    public ORMLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // TableUtils é responsábel por algumas operações sobre tabelas,
            // como, por exemplo, deletar/inserir tabelas.
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Grupo.class);
            TableUtils.createTable(connectionSource, TarefaPeriodicidade.class);
            TableUtils.createTable(connectionSource, Tarefa.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Tarefa.class, true);
            TableUtils.dropTable(connectionSource, TarefaPeriodicidade.class, true);
            TableUtils.dropTable(connectionSource, Grupo.class, true);
            TableUtils.dropTable(connectionSource, Usuario.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ORMLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ORMLiteHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public RuntimeExceptionDao<Usuario, Integer> getUsuarioRuntimeDao() {
        if (usuarioRuntimeDao == null) {
            usuarioRuntimeDao = getRuntimeExceptionDao(Usuario.class);
        }
        return usuarioRuntimeDao;
    }

    public Dao<Grupo, Integer> getGrupoDao() throws SQLException {
        if (grupoDao == null) {
            grupoDao = getDao(Grupo.class);
        }
        return grupoDao;
    }

    public RuntimeExceptionDao<Grupo, Integer> getGrupoRuntimeDao() {
        if (grupoRuntimeDao == null) {
            grupoRuntimeDao = getRuntimeExceptionDao(Grupo.class);
        }
        return grupoRuntimeDao;
    }

    public Dao<Tarefa, Integer> getTarefaDao() throws SQLException {
        if (tarefaDao == null) {
            tarefaDao = getDao(Tarefa.class);
        }
        return tarefaDao;
    }

    public RuntimeExceptionDao<Tarefa, Integer> getTarefaRuntimeDao() {
        if (tarefaRuntimeDao == null) {
            tarefaRuntimeDao = getRuntimeExceptionDao(Tarefa.class);
        }
        return tarefaRuntimeDao;
    }

    public Dao<TarefaPeriodicidade, Integer> getTarefaPeriodicidadeDao() throws SQLException {
        if (tarefaPeriodicidadeDao == null) {
            tarefaPeriodicidadeDao = getDao(TarefaPeriodicidade.class);
        }
        return tarefaPeriodicidadeDao;
    }

    public RuntimeExceptionDao<TarefaPeriodicidade, Integer> getTarefaPeriodicidadeRuntimeDao() {
        if (tarefaPeriodicidadeRuntimeDao == null) {
            tarefaPeriodicidadeRuntimeDao = getRuntimeExceptionDao(TarefaPeriodicidade.class);
        }
        return tarefaPeriodicidadeRuntimeDao;
    }
}
