package com.controller;

import com.dao.AlunoDao;
import com.dao.LeitorDao;
import com.model.Leitor;
import com.model.Usuario;
import com.util.Holder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable, Controller {

    Usuario usuarioLogado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLogado = (Usuario) Holder.getInstance().getObject();
    }

    @FXML
    private void onPerfilClick(ActionEvent event) throws IOException {
        Leitor leitor = new LeitorDao().findByIdUsuario(usuarioLogado.getId());
        Parent root;
        if (new AlunoDao().findById(leitor.getId()) != null) {
            root = FXMLLoader.load(getClass().getResource("/fxml/VisualizacaoLeitorAluno.fxml"));
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/fxml/VisualizacaoLeitorProfessor.fxml"));
        }

        Holder holder = Holder.getInstance();
        holder.setHandle(usuarioLogado, this);

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Leitor");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onLivroClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/VisualizacaoLivro.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Visualização de Livros");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onEmprestimoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/VisualizacaoEmprestimo.fxml"));

        Holder holder = Holder.getInstance();
        holder.setHandle(usuarioLogado, this);

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Empréstimos de " + new LeitorDao().findByIdUsuario(usuarioLogado.getId()));
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void refresh() {

    }
}
