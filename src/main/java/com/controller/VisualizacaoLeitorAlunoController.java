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

public class VisualizacaoLeitorAlunoController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtUsuario;

    private AlunoDao alunoDao = new AlunoDao();

    Usuario usuarioAtual;

    private void exibirDados(){
        Leitor leitor = new LeitorDao().findByIdUsuario(usuarioAtual.getId());

        if (alunoDao.findById(leitor.getId()) != null){
            Aluno aluno = alunoDao.findById(leitor.getId());
            txtId.setText(aluno.getId().toString());
            txtNome.setText(aluno.getNome());
            txtTelefone.setText(aluno.getTelefone());
            txtEmail.setText(aluno.getEmail());
            txtMatricula.setText(aluno.getMatricula());
            if(aluno.getUsuario()!=null)
                txtUsuario.setText(aluno.getUsuario().getLogin());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Holder holder = Holder.getInstance();
        usuarioAtual = (Usuario) holder.getObject();
        exibirDados();
    }
}
