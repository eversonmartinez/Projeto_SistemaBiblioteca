package com.controller;

import com.dao.AlunoDao;
import com.dao.LeitorDao;
import com.dao.ProfessorDao;
import com.model.Aluno;
import com.model.Leitor;
import com.model.Professor;
import com.model.Usuario;
import com.util.Holder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizacaoLeitorProfessorController implements Initializable {

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
    private TextField txtUsuario;
    private ProfessorDao professorDao = new ProfessorDao();

    Usuario usuarioAtual;

    private void exibirDados(){
        Leitor leitor = new LeitorDao().findByIdUsuario(usuarioAtual.getId());

        if(professorDao.findById(leitor.getId()) != null){
            Professor professor = professorDao.findById(leitor.getId());
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
