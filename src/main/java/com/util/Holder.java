package com.util;

import com.controller.Controller;

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
