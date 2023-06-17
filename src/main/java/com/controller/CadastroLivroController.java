package com.controller;

import com.dao.AutorDao;
import com.dao.GeneroDao;
import com.dao.LivroDao;
import com.model.*;
import com.util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroLivroController implements Initializable {

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
    private TextField txtCopia;

    @FXML
    private ListView<Livro> listaLivro;

    private LivroDao livroDao = new LivroDao();
    private AutorDao autorDao = new AutorDao();
    private GeneroDao generoDao = new GeneroDao();

    @FXML
    private void btnSalvar_click(ActionEvent event){
        try{
            Livro livroSelecionado = listaLivro.getSelectionModel().getSelectedItem();
            if(livroSelecionado == null){   //NOVO ALUNO
                if(txtNomeLivro.getText().length()<1){
                    Alerta.exibirErro("Campo \"Nome\" não pode ser vazio!");
                    return;
                }
                if(txtAno.getText().length()<1){
                    Alerta.exibirErro("Campo \"Ano\" não pode ser vazio!");
                    return;
                }
                if(txtEdicao.getText().length()<1){
                    Alerta.exibirErro("Campo \"Edição\" não pode ser vazio!");
                    return;
                }

                Livro novoLivro;
                if(txtCopia.getText().isEmpty()) {
                    novoLivro = new Livro(txtNomeLivro.getText(), Integer.parseInt(txtAno.getText()), txtEdicao.getText(),
                            cboGenero.getSelectionModel().getSelectedItem(), cboAutor.getSelectionModel().getSelectedItem());
                }
                else{
                    novoLivro = new Livro(txtNomeLivro.getText(), Integer.parseInt(txtAno.getText()), txtEdicao.getText(),
                            cboGenero.getSelectionModel().getSelectedItem(), cboAutor.getSelectionModel().getSelectedItem(),
                            Integer.parseInt(txtCopia.getText()));
                }


                if(!livroDao.create(novoLivro)){
                    Alerta.exibirErro("Não foi possível salvar o livro!", livroDao.getMensagem());
                    return;
                }

                refresh();
                limparCampos();
                listaLivro.getSelectionModel().select(null);
                txtId.setText(String.valueOf(novoLivro.getId() + 1L));
                Alerta.exibirInfo("Novo Livro Salvo com Sucesso!");
            }

            else{   //EDIÇÃO DE LIVRO EXISTENTE
                Alerta.exibirAviso("Edição não permitida");

//                if(!alterarObjetoSelecionado(listaLivro.getSelectionModel().getSelectedItem())){
//                    Alerta.exibirErro("Alteração Inválida");
//                    exibirLivros();
//                    return;
//                }
//
//                if(livroDao.update(livroSelecionado)) {
//                    Alerta.exibirInfo(livroDao.getMensagem());
//                    limparCampos();
//                    listaLivro.getSelectionModel().select(null);
//                }
//                else
//                    Alerta.exibirErro("Erro ao editar", livroDao.getMensagem());
//
                exibirLivros();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

//    private Boolean alterarObjetoSelecionado(Livro copiaComparada){
//        Boolean alterado = false;
//
//        //alterar cópias
//        if(!txtCopia.getText().isEmpty())
//            if(copiaComparada.getCopias().size() > Integer.parseInt(txtCopia.getText()){
//                while(copiaComparada.getCopias().size() > Integer.parseInt(txtCopia.getText())
//                    copiaComparada.remove;
//                alterado = true;
//        }
//
//        return alterado;
//    }

    @FXML
    private void btnNovo_click(){
        limparCampos();
        refresh();
        txtNomeLivro.setEditable(true);
        txtAno.setEditable(true);
        txtEdicao.setEditable(true);
        txtCopia.setEditable(true);
        listaLivro.getSelectionModel().select(null);
    }



    @FXML
    private void btnExcluir_click(ActionEvent event){
        try{
            Livro livroSelecionado = listaLivro.getSelectionModel().getSelectedItem();
            if(livroSelecionado == null)
                return;

            if(!livroDao.delete(livroSelecionado.getId())){
                Alerta.exibirErro(livroDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(livroDao.getMensagem());
            btnNovo_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void limparCampos(){
        txtId.setText("");
        txtNomeLivro.setText("");
        txtAno.setText("");
        txtEdicao.setText("");
        txtCopia.setText("");
        cboAutor.getSelectionModel().select(null);
        cboGenero.getSelectionModel().select(null);
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
            listaGenero.setItems(data);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void exibirAutores(){
        try{
            ObservableList<Autor> data = FXCollections.observableArrayList(autorDao.findAll());
            if(data!=null)
                cboAutor.setItems(data);
            listaAutor.setItems(data);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaLivro_keyPressed(KeyEvent event){

        exibirDados();
        txtNomeLivro.setEditable(false);
        txtAno.setEditable(false);
        txtEdicao.setEditable(false);
        txtCopia.setEditable(false);
    }

    @FXML
    private void listaLivro_mouseClicked(MouseEvent event){

        exibirDados();
        txtNomeLivro.setEditable(false);
        txtAno.setEditable(false);
        txtEdicao.setEditable(false);
        txtCopia.setEditable(false);
    }

    private void exibirDados(){
        Livro livroSelecionado = listaLivro.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            limparCampos();
            txtId.setText(livroSelecionado.getId().toString());
            txtNomeLivro.setText(livroSelecionado.getNome());
            txtAno.setText(livroSelecionado.getAno().toString());
            txtEdicao.setText(livroSelecionado.getEdicao());
            txtCopia.setText(String.valueOf(livroSelecionado.getCopias().size()));
            cboGenero.getSelectionModel().select(livroSelecionado.getGenero());
            cboAutor.getSelectionModel().select(livroSelecionado.getAutor());
        }
    }

    private void refresh(){
        exibirLivros();
        exibirGeneros();
        exibirAutores();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }




    //----------------------------------------TAB DE GÊNERO//

    @FXML
    private TextField txtNomeGenero;
    @FXML
    private ListView<Genero> listaGenero;
    @FXML
    private TextField txtIdGenero;

    @FXML
    private void btnSalvarGenero_click(ActionEvent event){
        try{
            Genero generoSelecionado = listaGenero.getSelectionModel().getSelectedItem();
            if(generoSelecionado == null){
                if(txtNomeGenero.getText().length()<1){
                    Alerta.exibirErro("Campo \"Nome\" não pode ser vazio!");
                    return;
                }

                Genero novoGenero = new Genero(txtNomeGenero.getText());
                if(!generoDao.create(novoGenero)){
                    Alerta.exibirErro("Não foi possível salvar o gênero!", generoDao.getMensagem());
                    return;
                }

                refresh();
                limparCamposGenero();
                listaGenero.getSelectionModel().select(null);
                txtIdGenero.setText(String.valueOf(novoGenero.getId() + 1L));
                Alerta.exibirInfo("Novo Gênero Salvo com Sucesso!");
            }

            else{
                Alerta.exibirAviso("Edição não permitida");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnNovoGenero_click(){
        limparCamposGenero();
        refresh();
        txtNomeGenero.setEditable(true);
        listaGenero.getSelectionModel().select(null);
    }



    @FXML
    private void btnExcluirGenero_click(ActionEvent event){
        try{
            Genero generoSelecionado = listaGenero.getSelectionModel().getSelectedItem();
            if(generoSelecionado == null)
                return;

            if(!generoDao.delete(generoSelecionado.getId())){
                Alerta.exibirErro(generoDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(generoDao.getMensagem());
            btnNovoGenero_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void limparCamposGenero(){
        txtIdGenero.setText("");
        txtNomeGenero.setText("");
    }

    @FXML
    private void listaGenero_keyPressed(KeyEvent event){
        exibirDadosGenero();
        txtNomeGenero.setEditable(false);
    }

    @FXML
    private void listaGenero_mouseClicked(MouseEvent event){
        exibirDadosGenero();
        txtNomeGenero.setEditable(false);
    }

    private void exibirDadosGenero(){
        Genero generoSelecionado = listaGenero.getSelectionModel().getSelectedItem();
        if (generoSelecionado != null) {
            limparCampos();
            txtIdGenero.setText(generoSelecionado.getId().toString());
            txtNomeGenero.setText(generoSelecionado.getNome());
        }
    }

    //----------------------------------------TAB DE AUTOR//
    @FXML
    private TextField txtNomeAutor;
    @FXML
    private TextField txtSobrenomeAutor;
    @FXML
    private ListView<Autor> listaAutor;
    @FXML
    private TextField txtIdAutor;

    @FXML
    private void btnSalvarAutor_click(ActionEvent event){
        try{
            Autor autorSelecionado = listaAutor.getSelectionModel().getSelectedItem();
            if(autorSelecionado == null){
                if(txtNomeAutor.getText().length()<1){
                    Alerta.exibirErro("Campo \"Nome\" não pode ser vazio!");
                    return;
                }
                if(txtSobrenomeAutor.getText().length()<1){
                    Alerta.exibirErro("Campo \"Sobrenome\" não pode ser vazio!");
                    return;
                }

                Autor novoAutor = new Autor(txtNomeAutor.getText(), txtSobrenomeAutor.getText());
                if(!autorDao.create(novoAutor)){
                    Alerta.exibirErro("Não foi possível salvar o autor!", autorDao.getMensagem());
                    return;
                }

                refresh();
                limparCamposAutor();
                listaAutor.getSelectionModel().select(null);
                txtIdAutor.setText(String.valueOf(novoAutor.getId() + 1L));
                Alerta.exibirInfo("Novo Autor Salvo com Sucesso!");
            }

            else{
                Alerta.exibirAviso("Edição não permitida");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnNovoAutor_click(){
        limparCamposAutor();
        refresh();
        txtNomeAutor.setEditable(true);
        txtSobrenomeAutor.setEditable(true);
        listaAutor.getSelectionModel().select(null);
    }



    @FXML
    private void btnExcluirAutor_click(ActionEvent event){
        try{
            Autor autorSelecionado = listaAutor.getSelectionModel().getSelectedItem();
            if (autorSelecionado == null)
                return;

            if(!autorDao.delete(autorSelecionado.getId())){
                Alerta.exibirErro(autorDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(autorDao.getMensagem());
            btnNovoAutor_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void limparCamposAutor(){
        txtIdAutor.setText("");
        txtNomeAutor.setText("");
        txtSobrenomeAutor.setText("");
    }

    @FXML
    private void listaAutor_keyPressed(KeyEvent event){
        exibirDadosAutor();
        txtNomeAutor.setEditable(false);
        txtSobrenomeAutor.setEditable(false);
    }

    @FXML
    private void listaAutor_mouseClicked(MouseEvent event){
        exibirDadosAutor();
        txtNomeAutor.setEditable(false);
        txtSobrenomeAutor.setEditable(false);
    }

    private void exibirDadosAutor(){
        Autor autorSelecionado = listaAutor.getSelectionModel().getSelectedItem();
        if (autorSelecionado != null) {
            limparCamposAutor();
            txtIdAutor.setText(autorSelecionado.getId().toString());
            txtNomeAutor.setText(autorSelecionado.getNome());
            txtSobrenomeAutor.setText(autorSelecionado.getSobreNome());
        }
    }
}
