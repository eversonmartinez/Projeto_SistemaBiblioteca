package com.controller;

import com.dao.LeitorDao;
import com.dao.UsuarioDao;
import com.model.Usuario;
import com.util.Alerta;
import com.util.Holder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, Controller {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private Rectangle efeitoUsuarioErrado;
    @FXML
    private Rectangle efeitoSenhaErrada;

    UsuarioDao usuarioDao = new UsuarioDao();

    @FXML
    private void btnEntrar_Click() throws IOException {
        if(txtUsuario.getText().isEmpty()) {
            Alerta.exibirErro("Informe o Usuário");
            return;
        }
        if(txtSenha.getText().isEmpty()){
            Alerta.exibirErro("Informe a Senha");
            return;
        }

        if(txtUsuario.getText().equals("admin") && txtSenha.getText().equals("admin")){
            //passando o livro selecionado para dentro de um "transportador", que no futuro será lido pela classe cópia

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));

            Scene scene = new Scene(root);
            scene.getRoot().setStyle("-fx-font-family: 'serif'");

            Stage stage = new Stage();
            stage.setTitle("Painel Administrativo");
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
            stage.setScene(scene);
            stage.show();

            desfazerErroLogin();

            return;
        }

        if(usuarioDao.loginUsuario(txtUsuario.getText(), txtSenha.getText())){
            //passando o livro selecionado para dentro de um "transportador", que no futuro será lido pela classe cópia
            Usuario u = usuarioDao.findByLogin(txtUsuario.getText(), txtSenha.getText());
            Holder holder = Holder.getInstance();
            holder.setHandle(u, this);

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Usuario.fxml"));

            Scene scene = new Scene(root);
            scene.getRoot().setStyle("-fx-font-family: 'serif'");
            scene.getStylesheets().add("/style/Principal.css");

            Stage stage = new Stage();
            stage.setTitle(new LeitorDao().findByIdUsuario(u.getId()).getNome());
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
            stage.setScene(scene);
            stage.show();
            desfazerErroLogin();
            return;
        }

        Alerta.exibirErro("Usuário e/ou Senha incorretos");
        erroLogin();
        //txtUsuario.setStyle("-fx-background-color:rgba(252,13,13,0.49)");
        //txtSenha.setStyle("-fx-background-color:rgba(252,13,13,0.49)");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void erroLogin(){
        efeitoUsuarioErrado.setOpacity(0.8);
        efeitoSenhaErrada.setOpacity(0.8);
    }

    private void desfazerErroLogin(){
        efeitoUsuarioErrado.setOpacity(0.0);
        efeitoSenhaErrada.setOpacity(0.0);
    }

    @Override
    public void refresh() {

    }
}
