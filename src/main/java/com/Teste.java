package com;

import com.model.Livro;
import com.util.EntityManagerUtil;
import jakarta.persistence.*;

import java.util.List;
public class Teste {

    public static void main(String[] args){

        EntityManager em = EntityManagerUtil.getEntityManager();
        Query query = em.createNativeQuery("select id from livro where id in (select livro_id from livro_copia where copias_id=8)"); //From livro Where livro in(From copia where id = " + 8 + " );");
        Livro livro = em.find(Livro.class, query.getSingleResult());
    }


}
