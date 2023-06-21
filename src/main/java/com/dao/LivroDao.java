package com.dao;

import com.model.Livro;

import jakarta.persistence.Query;

public class LivroDao extends Dao<Livro>{

    public LivroDao(){
        super(Livro.class);
    }

    public Livro findByIdCopia(Object idCopia){
        Query query = em.createNativeQuery("select id from livro where id in (select livro_id from livro_copia where copias_id= " + idCopia); //From livro Where livro in(From copia where id = " + 8 + " );");
        return em.find(Livro.class, query.getSingleResult());
    }
}
