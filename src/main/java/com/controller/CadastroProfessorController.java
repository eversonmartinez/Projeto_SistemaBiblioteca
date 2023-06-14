package com.controller;

import com.dao.ProfessorDao;
import com.model.Professor;
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

public class CadastroProfessorController implements Initializable {

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
    @FXML
    private PasswordField txtSenha;
    @FXML
    private ListView<Professor> listaProfessor;

    private ProfessorDao professorDao = new ProfessorDao();

    @FXML
    private void btnSalvar_click(ActionEvent event){
        try{
            Professor professorSelecionado = listaProfessor.getSelectionModel().getSelectedItem();
            if(professorSelecionado == null){   //NOVO ALUNO
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
                if(txtFormacao.getText().length()<1){
                    Alerta.exibirErro("Campo \"Formação\" não pode ser vazio!");
                    return;
                }
                Professor novoProfessor = new Professor(txtNome.getText(), txtTelefone.getText(), txtEmail.getText(), txtFormacao.getText());

                if(txtUsuario.getText().length() > 1){
                    if(txtSenha.getText().length()>1)
                        novoProfessor.criarUsuario(txtUsuario.getText(), txtSenha.getText());
                    else
                        Alerta.exibirAviso("Uma senha deve ser informada");
                }

                else if(txtSenha.getText().length() > 1){
                    Alerta.exibirAviso("O usuário deve ser informado");
                }

                if(!professorDao.create(novoProfessor)){
                    Alerta.exibirErro("Não foi possível salvar o professor!", professorDao.getMensagem());
                    return;
                }

                exibirProfessores();
                limparCampos();
                listaProfessor.getSelectionModel().select(null);
                txtId.setText(String.valueOf(novoProfessor.getId() + 1L));
                Alerta.exibirInfo("Novo Professor Salvo com Sucesso!");
            }

            else{   //EDIÇÃO DE ALUNO EXISTENTE
                Alerta.exibirAviso("Observação", "Edição permitida apenas para os campos:\n" +
                        "-Telefone \n-Email\n-Senha\n-Usuário (Quando não houver)");

                if(!alterarObjetoSelecionado(listaProfessor.getSelectionModel().getSelectedItem())){
                    Alerta.exibirErro("Alteração Inválida");
                    exibirProfessores();
                    return;
                }

                if(professorDao.update(professorSelecionado)) {
                    Alerta.exibirInfo(professorDao.getMensagem());
                    limparCampos();
                    listaProfessor.getSelectionModel().select(null);
                }
                else
                    Alerta.exibirErro("Erro ao editar", professorDao.getMensagem());

                exibirProfessores();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private Boolean alterarObjetoSelecionado(Professor professorComparado){
        Boolean alterado = false;

        //alterar telefone
        if(!txtTelefone.getText().isEmpty())
            if(!professorComparado.getTelefone().equals(txtTelefone.getText())){
                professorComparado.setTelefone(txtTelefone.getText());
                alterado = true;
        }

        //alterar Email
        if(!txtEmail.getText().isEmpty())
            if(!professorComparado.getEmail().equals(txtEmail.getText())){
                professorComparado.setEmail(txtEmail.getText());
                alterado = true;
            }

        //alterar usuario
        //novo usuario
        if(professorComparado.getUsuario() == null) {
            if (!txtUsuario.getText().isEmpty()) {
                if (!txtSenha.getText().isEmpty()) {
                    professorComparado.criarUsuario(txtUsuario.getText(), txtSenha.getText());
                    alterado = true;
                }
                else
                    Alerta.exibirAviso("Uma senha deve ser informada");
            }
            else if(!txtSenha.getText().isEmpty()) {
                Alerta.exibirAviso("O usuário deve ser informado");
            }
        } else{ //nova senha
            if(professorComparado.getUsuario().toString().equals(txtUsuario.getText())){
                if(!txtSenha.getText().isEmpty()){
                    if(!professorComparado.getUsuario().getSenha().equals(txtSenha.getText())){
                        professorComparado.alterarSenha(txtSenha.getText());
                        alterado = true;
                    }
                }
                else
                    Alerta.exibirAviso("Uma senha deve ser informada");
            }
        }
        return alterado;
    }

    @FXML
    private void btnNovo_click(){
        limparCampos();
        exibirProfessores();
        txtNome.setEditable(true);
        txtUsuario.setEditable(true);
        txtFormacao.setEditable(true);
        listaProfessor.getSelectionModel().select(null);
    }



    @FXML
    private void btnExcluir_click(ActionEvent event){
        try{
            Professor professorSelecionado = listaProfessor.getSelectionModel().getSelectedItem();
            if(!professorDao.delete(professorSelecionado.getId())){
                Alerta.exibirErro(professorDao.getMensagem());
                return;
            }
            btnNovo_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirUsuario_click(ActionEvent event){
        try{
            Professor professorSelecionado = listaProfessor.getSelectionModel().getSelectedItem();
            if(professorSelecionado != null && professorSelecionado.getUsuario() != null) {
                professorSelecionado.excluirUsuario();
                professorDao.update(professorSelecionado);
                exibirProfessores();
                exibirDados();
                Alerta.exibirInfo("Usuário Excluído");
            }else
                Alerta.exibirErro("Não foi possível excluir esse usuário");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private void limparCampos(){
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtFormacao.setText("");
        txtUsuario.setText("");
        txtSenha.setText("");
    }

    private void exibirProfessores(){
        try{
            ObservableList<Professor> data = FXCollections.observableList(professorDao.findAll());
            listaProfessor.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaProfessor_keyPressed(KeyEvent event){

        exibirDados();
        txtNome.setEditable(false);
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtFormacao.setEditable(false);
    }

    @FXML
    private void listaProfessor_mouseClicked(MouseEvent event){

        exibirDados();
        txtNome.setEditable(false);
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtFormacao.setEditable(false);
    }

    private void exibirDados(){
        Professor professorSelecionado = listaProfessor.getSelectionModel().getSelectedItem();
        if (professorSelecionado != null) {
            limparCampos();
            txtId.setText(professorSelecionado.getId().toString());
            txtNome.setText(professorSelecionado.getNome());
            txtTelefone.setText(professorSelecionado.getTelefone());
            txtEmail.setText(professorSelecionado.getEmail());
            txtFormacao.setText(professorSelecionado.getFormacao());
            if(professorSelecionado.getUsuario()!=null){
                txtUsuario.setText(professorSelecionado.getUsuario().getLogin());
                txtSenha.setText(professorSelecionado.getUsuario().getSenha());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exibirProfessores();
    }
}
