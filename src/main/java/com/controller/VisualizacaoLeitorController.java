package com.controller;

import com.dao.AlunoDao;
import com.dao.LeitorDao;
import com.dao.ProfessorDao;
import com.model.Aluno;
import com.model.Leitor;
import com.model.Professor;
import com.model.Usuario;
import com.util.Alerta;
import com.util.Holder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizacaoLeitorController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFormacao;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Label  txtPerfil;

    private ProfessorDao professorDao = new ProfessorDao();
    private AlunoDao alunoDao = new AlunoDao();

    Usuario usuarioAtual;

    private void exibirDados(){
        Leitor leitor = new LeitorDao().findByIdUsuario(usuarioAtual.getId());

        if (alunoDao.findById(leitor.getId()) != null){
            Aluno aluno = alunoDao.findById(leitor.getId());
            txtPerfil.setText("Perfil Aluno");
            txtId.setText(aluno.getId().toString());
            txtNome.setText(aluno.getNome());
            txtTelefone.setText(aluno.getTelefone());
            txtEmail.setText(aluno.getEmail());
            txtMatricula.setText(aluno.getMatricula());
            if(aluno.getUsuario()!=null)
                txtUsuario.setText(aluno.getUsuario().getLogin());
        }

        else if(professorDao.findById(leitor.getId()) != null){
            Professor professor = professorDao.findById(leitor.getId());
            txtPerfil.setText("Perfil Professor");
            txtId.setText(professor.getId().toString());
            txtNome.setText(professor.getNome());
            txtTelefone.setText(professor.getTelefone());
            txtEmail.setText(professor.getEmail());
            txtFormacao.setText(professor.getFormacao());
            if(professor.getUsuario()!=null)
                txtUsuario.setText(professor.getUsuario().getLogin());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Holder holder = Holder.getInstance();
        usuarioAtual = (Usuario) holder.getObject();
        exibirDados();
    }
}
