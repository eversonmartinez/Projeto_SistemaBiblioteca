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
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmprestimoController implements Initializable, Controller {

    @FXML
    private TextField txtId;

    @FXML
    private ComboBox<Leitor> cboLeitor;

    @FXML
    private ComboBox<Livro> cboLivro;

    @FXML
    private ComboBox<Copia> cboCopia;

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

    EmprestimoDao emprestimoDao = new EmprestimoDao();
    LivroDao livroDao = new LivroDao();
    LeitorDao leitorDao = new LeitorDao();

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
        cboLeitor.getSelectionModel().select(null);
        cboLivro.getSelectionModel().select(null);
        cboCopia.setItems(null);
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
            cboLeitor.getSelectionModel().select(emprestimoSelecionado.getLeitor());
            cboLivro.getSelectionModel().select(livroDao.findByIdCopia(emprestimoSelecionado.getCopia().getId()));
            cboCopia.getSelectionModel().select(emprestimoSelecionado.getCopia());
            dtpData.setValue(emprestimoSelecionado.getData());
            dtpPrevisao.setValue(emprestimoSelecionado.getDataPrevistaEntrega());
            if(emprestimoSelecionado.getDataEntrega() != null)
                dtpEntrega.setValue(emprestimoSelecionado.getDataEntrega());
            atualizarCheckBox();
        }
    }

    private void exibirLivros(){
        try{
            ObservableList<Livro> data = FXCollections.observableArrayList(livroDao.findAll());
            if(data!=null)
                cboLivro.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void exibirCopias(){
        try{
            Livro livro = cboLivro.getSelectionModel().getSelectedItem();
            if(livro!=null) {
                ObservableList<Copia> data = FXCollections.observableArrayList(livro.getCopias());
                if (data != null)
                    cboCopia.setItems(data);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirLeitores(){
        try{
            ObservableList<Leitor> data = FXCollections.observableList(leitorDao.findAll());
            cboLeitor.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void exibirEmprestimos(){
        try{
            ObservableList<Emprestimo> data = FXCollections.observableList(emprestimoDao.findAll());
            listaEmprestimo.setItems(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnSalvar_click(){
        try{
            Emprestimo emprestimoSelecionado = listaEmprestimo.getSelectionModel().getSelectedItem();
            if(emprestimoSelecionado == null){   //NOVO EMPRÉSTIMO
                if(cboLeitor.getSelectionModel().isEmpty()){
                    Alerta.exibirErro("Campo \"Leitor\" deve ser selecionado!");
                    return;
                }
                if(cboLivro.getSelectionModel().isEmpty()){
                    Alerta.exibirErro("Campo \"Livro\" deve ser selecionado!");
                    return;
                }
                if(cboCopia.getSelectionModel().isEmpty()){
                    Alerta.exibirErro("Campo \"Copia\" deve ser selecionado!");
                    return;
                }

                Emprestimo novoEmprestimo = new Emprestimo(dtpData.getValue(), cboCopia.getSelectionModel().getSelectedItem(),
                        cboLeitor.getSelectionModel().getSelectedItem());

                if(cbEntregue.isSelected()) {
                    if (dtpEntrega.getValue() != null)
                        novoEmprestimo.setDataEntrega(dtpEntrega.getValue());
                }

                if(!emprestimoDao.create(novoEmprestimo)){
                    Alerta.exibirErro("Não foi possível criar o empréstimo!", emprestimoDao.getMensagem());
                    return;
                }

                refresh();
                limparCampos();
                listaEmprestimo.getSelectionModel().select(null);
                txtId.setText(String.valueOf(novoEmprestimo.getId() + 1L));
                Alerta.exibirInfo("Novo Empréstimo Criado com Sucesso!");
            }

            else{   //EDIÇÃO DE EMPRÉSTIMO EXISTENTE
                Alerta.exibirAviso("Atenção", "A única operação permitida é a de definir uma data de entrega");

                if(cbEntregue.isSelected()){
                    if(dtpEntrega.getValue() != null) {
                        if (dtpEntrega.getValue().isAfter(emprestimoSelecionado.getData())) {
                            if (emprestimoDao.edicaoDataEntrega(emprestimoSelecionado, dtpEntrega.getValue()) == true) {
                                emprestimoSelecionado.setDataEntrega(dtpEntrega.getValue());

                                if (!emprestimoDao.update(emprestimoSelecionado)) {
                                    Alerta.exibirErro("Não foi possível atualizar o empréstimo!", emprestimoDao.getMensagem());
                                    refresh();
                                    exibirDados();
                                    return;
                                }
                                limparCampos();
                                refresh();
                                listaEmprestimo.getSelectionModel().select(null);
                                Alerta.exibirInfo("Empréstimo atualizado!");
                                return;
                            } else {
                                Alerta.exibirErro("A data de entrega deve ser posterior à data emprestada");
                                exibirDados();
                                return;
                            }
                        }
                    }
                    else{
                        Alerta.exibirErro("Informe a data!");
                        return;
                    }
                }

                Alerta.exibirErro("Operação não permitida!");
                exibirDados();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void btnNovo_click(){
        limparCampos();
        refresh();
        listaEmprestimo.getSelectionModel().select(null);
    }

    @FXML
    private void btnLeitor_click() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAluno.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Alunos");
        stage.setScene(scene);
        stage.show();

        root = FXMLLoader.load(getClass().getResource("/fxml/CadastroProfessor.fxml"));
        scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        stage = new Stage();
        stage.setTitle("Cadastro de Professores");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnExcluir_click(){
        try{
            Emprestimo emprestimoSelecionado = listaEmprestimo.getSelectionModel().getSelectedItem();
            if(emprestimoSelecionado == null)
                return;

            if(!emprestimoDao.delete(emprestimoSelecionado.getId())){
                Alerta.exibirErro(emprestimoDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(emprestimoDao.getMensagem());
            btnNovo_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
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

    @Override
    public void refresh() {
        exibirLivros();
        exibirLeitores();
        exibirEmprestimos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }
}
