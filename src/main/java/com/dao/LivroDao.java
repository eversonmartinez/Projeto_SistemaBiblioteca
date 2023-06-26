package com.dao;

import com.model.Autor;
import com.model.Genero;
import com.model.Livro;

import jakarta.persistence.Query;

import java.util.List;

public class LivroDao extends Dao<Livro>{

    public LivroDao(){
        super(Livro.class);
    }

    public Livro findByIdCopia(Object idCopia){
        Query query = em.createNativeQuery("select id from livro where id in (select livro_id from livro_copia where copias_id = " + idCopia + ")"); //From livro Where livro in(From copia where id = " + 8 + " );");
        return em.find(Livro.class, query.getSingleResult());
    }

    public List<Livro> customQuery(String query){
        Query resultado = em.createQuery(query);
        return resultado.getResultList();
    }

    public List<Livro> findByNomeLike(String nome){
        Query query = em.createQuery("select l from Livro l where upper(l.nome) like upper('%" + nome + "%')");
        return query.getResultList();
    }

    public List<Livro> findByAno(Integer ano){
        Query query = em.createQuery("select l from Livro l where l.ano = " + ano );
        return query.getResultList();
    }

    public List<Livro> findByEdicao(String edicao){
        Query query = em.createQuery("select l from Livro l where l.edicao = '" + edicao + "'" );
        return query.getResultList();
    }

    public List<Livro> findByAutor(Autor autor){
        Query query = em.createQuery("select l from Livro l where " + autor.getId() + " member of l.autores" );
        //Query query = em.createNativeQuery("select * from livro where id in(select livro from livros_autor where autor = " + autor.getId() + ")" );
        //return (List<Livro>) query.getResultList();
        return query.getResultList();
    }

    public List<Livro> findByGenero(Genero genero){
        Query query = em.createQuery("select l from Livro l where l.genero.id = " + genero.getId() );
        return query.getResultList();
    }
}
