package com.controller;

import com.dao.LivroDao;
import com.model.Autor;
import com.model.Copia;
import com.model.Livro;
import com.util.Alerta;
import com.util.Holder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LivroCopiasController implements Initializable{

    @FXML
    private TextField txtId;

    @FXML
    private ListView<Copia> listaCopia;

    @FXML
    private ChoiceBox<Livro> ccbLivro;

    LivroDao livroDao = new LivroDao();
    private void exibirLivros(){
        try{
            ObservableList<Livro> data = FXCollections.observableList(livroDao.findAll());
            ccbLivro.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirCopias(ActionEvent event){
        try{
            limparCampos();
            Livro livro = ccbLivro.getValue();
            if(livro!=null) {
                ObservableList<Copia> data = FXCollections.observableList(livro.getCopias());
                listaCopia.setItems(data);
            }
            Holder holder = Holder.getInstance();
            CadastroLivroController controller = (CadastroLivroController) holder.getController();
            controller.exibirDados();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirDados(){
    Copia copiaSelecionada = listaCopia.getSelectionModel().getSelectedItem();
        if (copiaSelecionada != null) {
            limparCampos();
            txtId.setText(copiaSelecionada.getId().toString());
        }
    }

    private void limparCampos(){
        txtId.setText("");
    }

    @FXML
    private void btnAdicionar_click(ActionEvent event){
        try{
            Livro livroSelecionado = ccbLivro.getSelectionModel().getSelectedItem();
            if(livroSelecionado == null)
                return;

            livroSelecionado.adicionarCopia();

            if(!livroDao.update(livroSelecionado)){
                Alerta.exibirErro(livroDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(livroDao.getMensagem());
            limparCampos();
            exibirCopias(new ActionEvent());
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void btnExcluir_click(ActionEvent event){
        try{
            Livro livroSelecionado = ccbLivro.getSelectionModel().getSelectedItem();
            if(livroSelecionado == null)
                return;

            Copia copiaSelecionada = listaCopia.getSelectionModel().getSelectedItem();
            if(copiaSelecionada == null)
                return;

            livroSelecionado.removerCopia(copiaSelecionada);

            if(!livroDao.update(livroSelecionado)){
                Alerta.exibirErro(livroDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(livroDao.getMensagem());
            limparCampos();
            exibirCopias(new ActionEvent());
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaCopia_keyPressed(KeyEvent event){

        exibirDados();
    }

    @FXML
    private void listaCopia_mouseClicked(MouseEvent event){

        exibirDados();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exibirLivros();
        ccbLivro.setOnAction(this::exibirCopias);
        receiveData();
        //exibirCopias(new ActionEvent());

    }

    private void receiveData(){
        Holder holder = Holder.getInstance();
        Livro livroSelecionado = (Livro) holder.getObject();
        ccbLivro.getSelectionModel().select(livroSelecionado);
    }

}
