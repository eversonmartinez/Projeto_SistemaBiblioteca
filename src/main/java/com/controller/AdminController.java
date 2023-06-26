package com.controller;

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

public class AdminController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onAlunoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAluno.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Alunos");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onProfessorClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroProfessor.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Professores");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onLivroClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroLivro.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Livros");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onEmprestimoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Emprestimo.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        scene.getStylesheets().add("/style/Principal.css");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Empréstimos");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/icon.png")));
        stage.setScene(scene);
        stage.show();

    }

}
