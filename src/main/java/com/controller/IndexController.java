package com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onAlunoClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAluno.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Alunos");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void onProfessorClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroProfessor.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Professores");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onLivroClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroLivro.fxml"));

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");

        Stage stage = new Stage();
        stage.setTitle("Cadastro de Livros");
        stage.setScene(scene);
        stage.show();

    }
}
