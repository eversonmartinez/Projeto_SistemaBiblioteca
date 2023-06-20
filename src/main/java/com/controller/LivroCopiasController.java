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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LivroCopiasController implements Initializable, Controller{

    @FXML
    private TextField txtId;

    @FXML
    private ListView<Copia> listaCopia;

    @FXML
    private ChoiceBox<Livro> ccbLivro;

    @FXML
    private CheckBox cbEmprestavel;

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
            cbEmprestavel.selectedProperty().set(copiaSelecionada.getEmprestavel());
            atualizarCheckBox();
        }
    }

    private void atualizarCheckBox(){
        cbEmprestavel.selectedProperty().set(listaCopia.getSelectionModel().getSelectedItem().getEmprestavel());
        marcarcaoCheckBox(new ActionEvent());
    }

    private void marcarcaoCheckBox(ActionEvent event){
        if(cbEmprestavel.isSelected())
            cbEmprestavel.setText("Sim");
        else
            cbEmprestavel.setText("Não");
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

            if(!livroSelecionado.removerCopia(copiaSelecionada)) {
                Alerta.exibirErro("Deve existir no mínimo 1 cópia não emprestável");
                return;
            }

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
    private void btnSalvar_click(){
        Boolean opcao = cbEmprestavel.isSelected();
        Livro livroSelecionado = ccbLivro.getSelectionModel().getSelectedItem();
        Copia copiaSelecionada = listaCopia.getSelectionModel().getSelectedItem();
        int index = listaCopia.getSelectionModel().getSelectedIndex();
        if(opcao != copiaSelecionada.getEmprestavel()){
            //copiaSelecionada.setEmprestavel(opcao);

            if(!livroSelecionado.modificarEmprestabilidadeCopia(index, opcao)) {
                Alerta.exibirErro("Deve existir no mínimo 1 cópia não emprestável");
                exibirDados();
                return;
            }
            livroDao.update(livroSelecionado);
            Alerta.exibirInfo("Emprestabilidade da cópia modificada!");
            limparCampos();
            exibirCopias(new ActionEvent());
            return;
        }
        Alerta.exibirErro("Operação não permitida!");
        exibirDados();
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
        cbEmprestavel.setOnAction(this::marcarcaoCheckBox);

    }

    //recebe o livro selecionado que está dentro de um holder, classe que carrega uma informação entre telas.
    //Essa informação veio da classe cadastro de livros
    private void receiveData(){
        Holder holder = Holder.getInstance();
        Livro livroSelecionado = (Livro) holder.getObject();
        ccbLivro.getSelectionModel().select(livroSelecionado);
    }

    @Override
    public void refresh() {
        exibirCopias(new ActionEvent());
        exibirLivros();
    }
}
