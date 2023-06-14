package com.controller;

import com.dao.AutorDao;
import com.dao.GeneroDao;
import com.dao.LivroDao;
import com.dao.ProfessorDao;
import com.model.*;
import com.util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
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

    @FXML
    private TextField txtNomeAutor;
    @FXML
    private TextField txtSobrenomeAutor;

    @FXML
    private ListView<Autor> listaAutor;

    @FXML
    private TextField txtNomeGenero;

    @FXML
    private ListView<Genero> listaGenero;

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

                exibirLivros();
                limparCampos();
                listaLivro.getSelectionModel().select(null);
                txtId.setText(String.valueOf(novoLivro.getId() + 1L));
                Alerta.exibirInfo("Novo Livro Salvo com Sucesso!");
            }

            else{   //EDIÇÃO DE ALUNO EXISTENTE
                Alerta.exibirAviso("Observação", "Edição permitida apenas para os campos:\n" +
                        "-Cópias");

                if(!alterarObjetoSelecionado(listaLivro.getSelectionModel().getSelectedItem())){
                    Alerta.exibirErro("Alteração Inválida");
                    exibirLivros();
                    return;
                }

                if(livroDao.update(livroSelecionado)) {
                    Alerta.exibirInfo(livroDao.getMensagem());
                    limparCampos();
                    listaLivro.getSelectionModel().select(null);
                }
                else
                    Alerta.exibirErro("Erro ao editar", livroDao.getMensagem());

                exibirLivros();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private Boolean alterarObjetoSelecionado(Livro copiaComparada){
        Boolean alterado = false;

        //alterar cópias
        if(!txtCopia.getText().isEmpty())
            if(copiaComparada.getCopias().size() > Integer.parseInt(txtCopia.getText()){
                while(copiaComparada.getCopias().size() > Integer.parseInt(txtCopia.getText())
                    copiaComparada.remove;
                alterado = true;
        }

        return alterado;
    }

    @FXML
    private void btnNovo_click(){
        limparCampos();
        exibirProfessors();
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
                exibirProfessors();
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

    private void exibirLivros(){
        try{
            ObservableList<Professor> data = FXCollections.observableList(professorDao.findAll());
            listaLivro.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void listaLivro_keyPressed(KeyEvent event){

        exibirDados();
        txtNome.setEditable(false);
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtFormacao.setEditable(false);
    }

    @FXML
    private void listaLivro_mouseClicked(MouseEvent event){

        exibirDados();
        txtNome.setEditable(false);
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtFormacao.setEditable(false);
    }

    private void exibirDados(){
        Livro professorSelecionado = listaLivro.getSelectionModel().getSelectedItem();
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
        exibirLivro();
    }
}
