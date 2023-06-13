package com.util;

import javafx.scene.control.Alert;

public class Alerta {

    public static void exibirErro(String mensagem){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(mensagem);
        alerta.show();
    }

    public static void exibirInfo(String mensagem){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(mensagem);
        alerta.show();
    }

    public static void exibirAviso(String mensagem){
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(mensagem);
        alerta.show();
    }

    public static String obterMensagemException(Exception e){
        while(e.getCause() != null)
            e = (Exception) e.getCause();

        return e.getMessage();
    }

}
