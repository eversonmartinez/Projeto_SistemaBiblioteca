package com.dao;

import com.util.Alerta;
import com.util.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class Dao<T> implements Serializable {

    protected EntityManager em;
    protected Class<T> entity;
    @Getter
    protected String mensagem;

    public Dao(Class<T> entity){
        em = EntityManagerUtil.getEntityManager();
        this.entity = entity;
    }

    public Boolean create(T entity){
        try {
            begin();
            em.persist(entity);
            commit();
            mensagem = "Objeto persistido com sucesso!";
            return true;

        }   catch(Exception ex) {
            mensagem = (Alerta.obterMensagemException(ex));
            return false;
        }
    }

    public Boolean update(T entity){
        try {
            begin();
            em.merge(entity);
            commit();
            mensagem = "Objeto editado com sucesso!";
            return true;

        } catch (Exception ex){
            mensagem = Alerta.obterMensagemException(ex);
            return false;
        }
    }

    public Boolean delete(Object id){
        try {
            T objeto = findById(id);

            if (objeto == null)
                return null;

            begin();
            em.remove(objeto);
            commit();

            mensagem = "Objeto removido!";
            return true;

        } catch(Exception ex){
            mensagem = "Erro ao remover: " + Alerta.obterMensagemException(ex);
            return false;
        }
    }

    public T findById(Object id){
        return em.find(entity, id);
    }

    public List<T> findAll(){
        Query query = em.createQuery("From " + entity.getName());
        return query.getResultList();
    }

    public Dao<T> begin(){
        em.getTransaction().begin();
        return this;
    }

    public Dao<T> commit(){
        em.getTransaction().commit();
        return this;
    }

}
