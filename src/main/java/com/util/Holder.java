package com.util;

import com.controller.Controller;

/*Classe que eu criei para passar objetos entre uma tela e outra, para determinados objetivos.
  Essa classe carrega consigo um controller, para possibilitar um refresh da tela que está passando
  o objeto para a outra, e também o determinado objeto.
  A classe é estática, então não pode ser instanciada e seus objetos serão vistos por qualquer outra
  classe que a chame
*/

public final class Holder {
    private Object object;
    private Controller controller;
    private final static Holder INSTANCE = new Holder();

    public void setObject(Object object ){
        this.object = object;
    }

    private Holder(){}

    public static Holder getInstance(){
        return INSTANCE;
    }

    public void setController(Controller controller ){
        this.controller = controller;
    }

    public void setHandle(Object object, Controller controller){
        this.object = object;
        this.controller = controller;
    }

    public Object getObject(){
        return this.object;
    }

    public Object getController(){
        return this.controller;
    }

}
