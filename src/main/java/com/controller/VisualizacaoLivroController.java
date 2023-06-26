package com.controller;

import com.dao.AutorDao;
import com.dao.GeneroDao;
import com.dao.LivroDao;
import com.model.Autor;
import com.model.Genero;
import com.model.Livro;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizacaoLivroController implements Initializable, Controller {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNomeLivro;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtEdicao;
    @FXML
    private ComboBox<Genero> cboGenero;

    @FXML
    private ComboBox<Autor> cboAutor;

    @FXML
    private ListView<Livro> listaLivro;

    private LivroDao livroDao = new LivroDao();
    private AutorDao autorDao = new AutorDao();
    private GeneroDao generoDao = new GeneroDao();

    @FXML
    private void btnPesquisar_click(){
        try{
            if(!txtId.getText().isEmpty()){
                Livro livro = livroDao.findById(Long.parseLong(txtId.getText()));
                if(livro == null) {
                    listaLivro.setItems(null);
                }
                else {
                    ObservableList<Livro> data = FXCollections.observableArrayList(livro);
                    listaLivro.setItems(data);
                }
                return;
            }

            Boolean primeiroFiltro = true;
            List<Livro> livros = new ArrayList<>();

            if(!txtNomeLivro.getText().isEmpty()){
               primeiroFiltro = false;
               List<Livro> adicionar = livroDao.findByNomeLike(txtNomeLivro.getText());
               for(Livro livroNovo : adicionar){
                   livros.add(livroNovo);
               }
            }

            if(!txtAno.getText().isEmpty()){
                List<Livro> adicionar = livroDao.findByAno(Integer.parseInt(txtAno.getText()));

                if(primeiroFiltro == true){
                    primeiroFiltro = false;
                    for(Livro livroNovo : adicionar){
                        livros.add(livroNovo);
                    }
                }

                else {
                    livros = intersecaoListas(livros, adicionar);
                }
            }

            if(!txtEdicao.getText().isEmpty()){
                List<Livro> adicionar = livroDao.findByEdicao(txtEdicao.getText());

                if(primeiroFiltro){
                    primeiroFiltro = false;
                    for(Livro livroNovo : adicionar){
                        livros.add(livroNovo);
                    }
                }

                else {
                    livros = intersecaoListas(livros, adicionar);
                }
            }

            if(cboGenero.getSelectionModel().getSelectedItem()!=null){
                List<Livro> adicionar = livroDao.findByGenero(cboGenero.getSelectionModel().getSelectedItem());

                if(primeiroFiltro == true){
                    primeiroFiltro = false;
                    for(Livro livroNovo : adicionar){
                        livros.add(livroNovo);
                    }
                }

                else {
                    livros = intersecaoListas(livros, adicionar);
                }
            }

            if(cboAutor.getSelectionModel().getSelectedItem()!=null){
                List<Livro> adicionar = livroDao.findByAutor(cboAutor.getSelectionModel().getSelectedItem());

                if(primeiroFiltro == true){
                    primeiroFiltro = false;
                    for(Livro livroNovo : adicionar){
                        livros.add(livroNovo);
                    }
                }

                else {
                    livros = intersecaoListas(livros, adicionar);
                }
            }

            ObservableList<Livro> data;

            if(livros.size()==0) {
                if(primeiroFiltro == true) {
                    data = FXCollections.observableArrayList(livroDao.findAll());
                }

                else
                    data = null;
            }

            else
                data = FXCollections.observableArrayList(livros);

            listaLivro.setItems(null);
            listaLivro.setItems(data);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private List<Livro> intersecaoListas(List<Livro> listaAtual, List<Livro> listaNova){

        ArrayList<Livro> listaFiltrada= new ArrayList<>();

        for(Livro existente : listaAtual){
            for(Livro filtro : listaNova){
                if(existente == filtro){
                    listaFiltrada.add(existente);
                }
            }
        }
        listaAtual.removeAll(listaAtual);
        listaAtual.addAll(listaFiltrada);

        return listaFiltrada;
    }

    @FXML
    private void btnLimpar_click(){
        limparCampos();
    }

    private void limparCampos(){
        txtId.setText("");
        txtNomeLivro.setText("");
        txtAno.setText("");
        txtEdicao.setText("");
        cboAutor.getSelectionModel().select(null);
        cboGenero.getSelectionModel().select(null);
        exibirLivros();
    }

    private void exibirLivros(){
        try{
            ObservableList<Livro> data = FXCollections.observableList(livroDao.findAll());
            listaLivro.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirGeneros(){
        try{
            ObservableList<Genero> data = FXCollections.observableArrayList(generoDao.findAll());
            if(data!=null)
                cboGenero.setItems(data);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void exibirAutores(){
        try{
            ObservableList<Autor> data = FXCollections.observableArrayList(autorDao.findAll());
            if(data!=null)
                cboAutor.setItems(data);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaLivro_keyPressed(KeyEvent event){

    }

    @FXML
    private void listaLivro_mouseClicked(MouseEvent event){

    }

    public void refresh(){
        exibirLivros();
        exibirGeneros();
        exibirAutores();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }


}
