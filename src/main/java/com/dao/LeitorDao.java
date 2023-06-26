package com.dao;

import com.model.Leitor;
import jakarta.persistence.Query;

public class LeitorDao extends Dao<Leitor>{
    public LeitorDao(){
        super(Leitor.class);
    }

    public Leitor findByIdUsuario(Long id){
        Query query = em.createQuery("from Leitor where usuario.id = " + id);
        return (Leitor) query.getSingleResult();
    }
}
