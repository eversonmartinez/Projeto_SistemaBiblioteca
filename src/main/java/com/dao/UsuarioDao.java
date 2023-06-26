package com.dao;

import com.model.Usuario;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioDao extends Dao<Usuario>{

    public UsuarioDao(){
        super(Usuario.class);
    }

    public boolean loginUsuario(String usuario, String senha){
        try {
            Usuario existe;

            Query q = em.createQuery("select u from Usuario u where u.login = '" + usuario + "' and u.senha = '" + senha + "'");
            existe = (Usuario) q.getSingleResult();
            if (existe != null)
                return true;

            else
                return false;
        }catch (Exception ex){
            return false;
        }
    }

    public Usuario findByLogin(String usuario, String senha){
        Query q = em.createQuery("select u from Usuario u where u.login = '" + usuario + "' and u.senha = '" + senha + "'");
        return ((Usuario) q.getSingleResult());
    }

}

