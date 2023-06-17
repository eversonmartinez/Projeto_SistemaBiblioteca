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
                    Alerta.exibirErro("Não foi possível salvar o aluno!", alunoDao.getMensagem());
                    return;
                }

                exibirAlunos();
                limparCampos();
                listaAluno.getSelectionModel().select(null);
                txtId.setText(String.valueOf(novoAluno.getId() + 1L));
                Alerta.exibirInfo("Novo Aluno Salvo com Sucesso!");
            }

            else{   //EDIÇÃO DE ALUNO EXISTENTE
                Alerta.exibirAviso("Observação", "Edição permitida apenas para os campos:\n" +
                        "-Telefone \n-Email\n-Senha\n-Usuário (Quando não houver)");

                if(!alterarObjetoSelecionado(listaAluno.getSelectionModel().getSelectedItem())){
                    Alerta.exibirErro("Alteração Inválida");
                    exibirAlunos();
                    return;
                }
//                if(!existeAlteracao(listaAluno.getSelectionModel().getSelectedItem().getId())){
//                    Alerta.exibirErro("Alteração Inválida");
//                    exibirAlunos();
//                    if(txtUsuario.getText().length() > 1){
//                        if(txtSenha.getText().length()>1)
//                            alunoSelecionado.alterarSenha(txtSenha.getText());
//                        else
//                            Alerta.exibirAviso("Uma senha deve ser informada");
//                    }
//
//                    else if(txtSenha.getText().length() > 1){
//                        Alerta.exibirAviso("O usuário deve ser informado");
//                    }
//                    return;
//                }
//
//                alunoSelecionado.setEmail(txtEmail.getText());
//                alunoSelecionado.setTelefone(txtTelefone.getText());
//                if (alunoSelecionado.getUsuario() == null && txtUsuario.getText().length()>1){
//                    if (txtSenha.getText().length() > 0)
//                        alunoSelecionado.criarUsuario(txtUsuario.getText(), txtSenha.getText());
//                }
//
//                else if(txtSenha.getText().length()>1)
//                    alunoSelecionado.alterarSenha(txtSenha.getText());

                if(alunoDao.update(alunoSelecionado)) {
                    Alerta.exibirInfo(alunoDao.getMensagem());
                    limparCampos();
                    listaAluno.getSelectionModel().select(null);
                }
                else
                    Alerta.exibirErro("Erro ao editar aluno", alunoDao.getMensagem());

                exibirAlunos();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private Boolean alterarObjetoSelecionado(Aluno alunoComparado){
        Boolean alterado = false;

        //alterar telefone
        if(!txtTelefone.getText().isEmpty())
            if(!alunoComparado.getTelefone().equals(txtTelefone.getText())){
                alunoComparado.setTelefone(txtTelefone.getText());
                alterado = true;
        }

        //alterar Email
        if(!txtEmail.getText().isEmpty())
            if(!alunoComparado.getEmail().equals(txtEmail.getText())){
                alunoComparado.setEmail(txtEmail.getText());
                alterado = true;
            }

        //alterar usuario
        //novo usuario
        if(alunoComparado.getUsuario() == null) {
            if (!txtUsuario.getText().isEmpty()) {
                if (!txtSenha.getText().isEmpty()) {
                    alunoComparado.criarUsuario(txtUsuario.getText(), txtSenha.getText());
                    alterado = true;
                }
                else
                    Alerta.exibirAviso("Uma senha deve ser informada");
            }
            else if(!txtSenha.getText().isEmpty()) {
                Alerta.exibirAviso("O usuário deve ser informado");
            }
        } else{ //nova senha
            if(alunoComparado.getUsuario().toString().equals(txtUsuario.getText())){
                if(!txtSenha.getText().isEmpty()){
                    if(!alunoComparado.getUsuario().getSenha().equals(txtSenha.getText())){
                        alunoComparado.alterarSenha(txtSenha.getText());
                        alterado = true;
                    }
                }
                else
                    Alerta.exibirAviso("Uma senha deve ser informada");
            }
        }
        return alterado;
    }
//    public Boolean existeAlteracao(Object id){
//        Aluno alunoComparado = alunoDao.findById(id);
//
//        if(!txtTelefone.getText().equals(alunoComparado.getTelefone()))
//            return true;
//
//        if(!txtEmail.getText().equals(alunoComparado.getEmail()))
//            return true;
//
//        if(txtUsuario.getText().length()>0){
//
//            if (alunoComparado.getUsuario()==null){
//                if (txtSenha.getText().length()>0)
//                    return true;
//            }
//            else if(alunoComparado.getUsuario().toString().equals(txtUsuario.getText()))
//                if(!txtSenha.getText().equals(alunoComparado.getUsuario().getSenha()))
//                    return true;
//        }
//
//        System.out.println("false");
//        return false;
//    }


    @FXML
    private void btnNovo_click(){
        limparCampos();
        exibirAlunos();
        txtNome.setEditable(true);
        txtUsuario.setEditable(true);
        txtMatricula.setEditable(true);
        listaAluno.getSelectionModel().select(null);
    }



    @FXML
    private void btnExcluir_click(ActionEvent event){
        try{
            Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
            if(alunoSelecionado == null)
                return;

            if(!alunoDao.delete(alunoSelecionado.getId())){
                Alerta.exibirErro(alunoDao.getMensagem());
                return;
            }
            Alerta.exibirInfo(alunoDao.getMensagem());
            btnNovo_click();
        }   catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void btnExcluirUsuario_click(ActionEvent event){
        try{
            Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
            if(alunoSelecionado != null && alunoSelecionado.getUsuario() != null) {
                alunoSelecionado.excluirUsuario();
                alunoDao.update(alunoSelecionado);
                exibirAlunos();
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
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtMatricula.setEditable(false);
    }

    @FXML
    private void listaAluno_mouseClicked(MouseEvent event){

        exibirDados();
        txtNome.setEditable(false);
        if(txtUsuario.getLength()>0)
            txtUsuario.setEditable(false);
        else
            txtUsuario.setEditable(true);
        txtMatricula.setEditable(false);
    }

    private void exibirDados(){
        Aluno alunoSelecionado = listaAluno.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            limparCampos();
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
