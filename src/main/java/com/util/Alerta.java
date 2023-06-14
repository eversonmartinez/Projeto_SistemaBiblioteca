package com.util;

import javafx.scene.control.Alert;

public class Alerta {

    public static void exibirErro(String titulo){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.show();
    }
    public static void exibirErro(String titulo, String mensagem){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void exibirInfo(String titulo){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.show();
    }

    public static void exibirAviso(String titulo){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.show();
    }

    public static void exibirAviso(String titulo, String mensagem){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static String obterMensagemException(Exception e){
        while(e.getCause() != null)
            e = (Exception) e.getCause();

        return e.getMessage();
    }

}
