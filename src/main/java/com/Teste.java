package com;

import com.dao.EmprestimoDao;
import com.model.Copia;
import com.model.Emprestimo;
import com.model.Leitor;
import com.model.Livro;
import com.util.EntityManagerUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
public class Teste {

    public static void main(String[] args){

        EntityManager em = EntityManagerUtil.getEntityManager();
//        Long idCopia = 8L;
//        Query query = em.createNativeQuery("select id from livro where id in (select livro_id from livro_copia where copias_id = " + idCopia + ")"); //From livro Where livro in(From copia where id = " + 8 + " );");
//        Livro livro = em.find(Livro.class, query.getSingleResult());

        Copia copia = em.find(Copia.class, 17);
//        List <Emprestimo> emprestimos = new EmprestimoDao().findByCopia(copia);
//        for(Emprestimo emp : emprestimos)
//            System.out.println(emp);
        Leitor leitor = em.find(Leitor.class, 8);

        EmprestimoDao emprestimoDao = new EmprestimoDao();
       Emprestimo emprestimo = new Emprestimo(LocalDate.now(), copia, leitor);
       emprestimoDao.create(emprestimo);
    }


}
