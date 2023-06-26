package com.controller;

import com.dao.EmprestimoDao;
import com.dao.LeitorDao;
import com.dao.LivroDao;
import com.model.*;
import com.util.Alerta;
import com.util.Holder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizacaoEmprestimoController implements Initializable, Controller {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLivro;

    @FXML
    private TextField txtCopia;

    @FXML
    private DatePicker dtpData;

    @FXML
    private DatePicker dtpPrevisao;

    @FXML
    private CheckBox cbEntregue;

    @FXML
    private DatePicker dtpEntrega;

    @FXML
    private ListView<Emprestimo> listaEmprestimo;

    @FXML
    private ListView<Emprestimo> listaEmprestimoAtual;

    @FXML
    private TextField txtContagem;

    EmprestimoDao emprestimoDao = new EmprestimoDao();
    LivroDao livroDao = new LivroDao();
    LeitorDao leitorDao = new LeitorDao();
    Usuario usuarioLogado;

    private void atualizarCheckBox(){
        LocalDate data = listaEmprestimo.getSelectionModel().getSelectedItem().getDataEntrega();
        if(data != null) {
            cbEntregue.selectedProperty().set(true);
            dtpEntrega.setValue(data);
        }
        else
            cbEntregue.selectedProperty().set(false);
        marcacaoCheckBox(new ActionEvent());
    }

    @FXML
    private void marcacaoCheckBox(ActionEvent event){
        if(cbEntregue.isSelected())
            cbEntregue.setText("Sim");
        else
            cbEntregue.setText("Não");
    }


    private void limparCampos(){
        txtId.setText("");
        txtLivro.setText("");
        txtCopia.setText("");
        cbEntregue.selectedProperty().set(false);
        marcacaoCheckBox(new ActionEvent());
        dtpData.setValue(null);
        dtpPrevisao.setValue(null);
        dtpEntrega.setValue(null);
    }

    private void exibirDados(){
        Emprestimo emprestimoSelecionado = listaEmprestimo.getSelectionModel().getSelectedItem();
        if (emprestimoSelecionado != null) {
            limparCampos();
            txtId.setText(emprestimoSelecionado.getId().toString());
            txtLivro.setText((livroDao.findByIdCopia(emprestimoSelecionado.getCopia().getId())).getNome());
            txtCopia.setText(emprestimoSelecionado.getCopia().toString());
            dtpData.setValue(emprestimoSelecionado.getData());
            dtpPrevisao.setValue(emprestimoSelecionado.getDataPrevistaEntrega());
            if(emprestimoSelecionado.getDataEntrega() != null)
                dtpEntrega.setValue(emprestimoSelecionado.getDataEntrega());
            atualizarCheckBox();
        }
    }

    private void exibirDadosListaAtual(){
        Emprestimo emprestimoSelecionado = listaEmprestimoAtual.getSelectionModel().getSelectedItem();
        if (emprestimoSelecionado != null) {
            limparCampos();
            txtId.setText(emprestimoSelecionado.getId().toString());
            txtLivro.setText((livroDao.findByIdCopia(emprestimoSelecionado.getCopia().getId())).getNome());
            txtCopia.setText(emprestimoSelecionado.getCopia().toString());
            dtpData.setValue(emprestimoSelecionado.getData());
            dtpPrevisao.setValue(emprestimoSelecionado.getDataPrevistaEntrega());
            if(emprestimoSelecionado.getDataEntrega() != null)
                dtpEntrega.setValue(emprestimoSelecionado.getDataEntrega());
            atualizarCheckBox();
        }
    }

    private void exibirEmprestimos(){
        try{
            ObservableList<Emprestimo> data = FXCollections.observableList(emprestimoDao.findByUsuario(usuarioLogado));
            listaEmprestimo.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirEmprestimosAtuais(){
        try{
            List<Emprestimo> todosEmprestimos = emprestimoDao.findByUsuario(usuarioLogado);
            List<Emprestimo> emprestimosAbertos = new ArrayList<>();
            for(Emprestimo emp : todosEmprestimos){
                if(emp.getDataEntrega() == null)
                    emprestimosAbertos.add(emp);
            }
            ObservableList<Emprestimo> data = FXCollections.observableList(emprestimosAbertos);
            listaEmprestimoAtual.setItems(data);
            txtContagem.setText(String.valueOf(emprestimosAbertos.size()));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void verificarLimiteEmprestimos(){
        if(Integer.valueOf(txtContagem.getText()) == 5){
            txtContagem.setStyle("-fx-text-inner-color: red");
        }
        else
            txtContagem.setStyle("-fx-text-inner-color: inherited");
    }


    @FXML
    private void listaEmprestimo_keyPressed(KeyEvent event){
        limparCampos();
        exibirDados();
    }

    @FXML
    private void listaEmprestimo_mouseClicked(MouseEvent event){
        limparCampos();
        exibirDados();
    }

    @FXML
    private void listaEmprestimoAtual_keyPressed(KeyEvent event){
        limparCampos();
        exibirDadosListaAtual();
    }

    @FXML
    private void listaEmprestimoAtual_mouseClicked(MouseEvent event){
        limparCampos();
        exibirDadosListaAtual();
    }

    @Override
    public void refresh() {
        exibirEmprestimos();
        exibirEmprestimosAtuais();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLogado = (Usuario) Holder.getInstance().getObject();
        refresh();
    }
}
