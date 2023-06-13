package com.controller;

import com.dao.AlunoDao;
import com.model.Aluno;
import com.util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroAlunoController implements Initializable {

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
    @FXML
    private PasswordField txtSenha;
    @FXML
    private ListView<Aluno> listaAluno;

    private AlunoDao alunoDao = new AlunoDao();

    @FXML
    private void btnSalvar_click(ActionEvent event){
        try{
            Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
            if(alunoSelecionado == null){   //NOVO ALUNO
                if(txtNome.getText().length()<1){
                    Alerta.exibirErro("Campo \"Nome\" não pode ser vazio!");
                    return;
                }
                if(txtTelefone.getText().length()<1){
                    Alerta.exibirErro("Campo \"Telefone\" não pode ser vazio!");
                    return;
                }
                if(txtEmail.getText().length()<1){
                    Alerta.exibirErro("Campo \"Email\" não pode ser vazio!");
                    return;
                }
                if(txtMatricula.getText().length()<1){
                    Alerta.exibirErro("Campo \"Matrícula\" não pode ser vazio!");
                    return;
                }
                Aluno novoAluno = new Aluno(txtNome.getText(), txtTelefone.getText(), txtEmail.getText(), txtMatricula.getText());

                if(txtUsuario.getText().length() > 1){
                    if(txtSenha.getText().length()>1)
                        novoAluno.criarUsuario(txtUsuario.getText(), txtSenha.getText());
                    else
                        Alerta.exibirAviso("Uma senha deve ser informada");
                }

                else if(txtSenha.getText().length() > 1){
                    Alerta.exibirAviso("O usuário deve ser informado");
                }

                if(!alunoDao.create(novoAluno)){
                    Alerta.exibirErro("Não foi possível salvar o aluno!");
                    return;
                }

                exibirAlunos();
                limparCampos();
                txtId.setText(String.valueOf(novoAluno.getId() + 1L));
                Alerta.exibirInfo("Novo Aluno Salvo com Sucesso!");
            }

            else{   //EDIÇÃO DE ALUNO EXISTENTE
                Alerta.exibirAviso("Edição permitida apenas para os campos Telefone, Email, Senha");

                if(txtUsuario.getText().length() > 1){
                    if(txtSenha.getText().length()>1)
                        alunoSelecionado.alterarSenha(txtSenha.getText());
                    else
                        Alerta.exibirAviso("Uma senha deve ser informada");
                }

                else if(txtSenha.getText().length() > 1){
                    Alerta.exibirAviso("O usuário deve ser informado");
                }

                alunoSelecionado.setEmail(txtEmail.getText());
                alunoSelecionado.setTelefone(txtTelefone.getText());

                if(!existeAlteracao(alunoSelecionado, alunoSelecionado.getId())){
                    Alerta.exibirErro("Alteração Inválida");
                    exibirAlunos();
                    return;
                }

                if(alunoDao.update(alunoSelecionado)) {
                    Alerta.exibirInfo(alunoDao.getMensagem());
                    limparCampos();
                }
                else
                    Alerta.exibirErro(alunoDao.getMensagem());

                exibirAlunos();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public Boolean existeAlteracao(Aluno aluno, Object id){
        Aluno alunoComparado = alunoDao.findById(id);
        Boolean sim = false;

        if(aluno.getTelefone() != alunoComparado.getTelefone())
            sim = true;

        if(aluno.getEmail() != alunoComparado.getEmail())
            sim = true;

        if(!aluno.getUsuario().equals(alunoComparado.getUsuario()))
            sim = true;

        if(aluno.getUsuario().getSenha() != alunoComparado.getUsuario().getSenha())
            sim = true;

        System.out.println(sim);
        return sim;
    }


    @FXML
    private void btnNovo_click(ActionEvent event){
        limparCampos();
        txtNome.setEditable(true);
        txtUsuario.setEditable(true);
        txtMatricula.setEditable(true);
    }



    @FXML
    private void btnExcluir_click(ActionEvent event){
        try{
            Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
            if(!alunoDao.delete(alunoSelecionado.getId())){
                Alerta.exibirErro(alunoDao.getMensagem());
                return;
            }
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void limparCampos(){
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtUsuario.setText("");
        txtSenha.setText("");
    }

    private void exibirAlunos(){
        try{
            ObservableList<Aluno> data = FXCollections.observableList(alunoDao.findAll());
            listaAluno.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaAluno_keyPressed(KeyEvent event){

        exibirDados();
        txtNome.setEditable(false);
        txtUsuario.setEditable(false);
        txtMatricula.setEditable(false);
    }

    @FXML
    private void listaAluno_mouseClicked(MouseEvent event){

        exibirDados();
        txtNome.setEditable(false);
        txtUsuario.setEditable(false);
        txtMatricula.setEditable(false);
    }

    private void exibirDados(){
        Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            txtId.setText(alunoSelecionado.getId().toString());
            txtNome.setText(alunoSelecionado.getNome());
            txtTelefone.setText(alunoSelecionado.getTelefone());
            txtEmail.setText(alunoSelecionado.getEmail());
            txtMatricula.setText(alunoSelecionado.getMatricula());
            if(alunoSelecionado.getUsuario()!=null){
                txtUsuario.setText(alunoSelecionado.getUsuario().getLogin());
                txtSenha.setText(alunoSelecionado.getUsuario().getSenha());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exibirAlunos();
    }
}
