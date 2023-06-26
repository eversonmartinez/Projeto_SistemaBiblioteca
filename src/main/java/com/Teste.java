package com;

import com.dao.EmprestimoDao;
import com.dao.LivroDao;
import com.dao.UsuarioDao;
import com.model.*;
import com.util.EntityManagerUtil;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Teste {

    public static void main(String[] args) {

        EntityManager em = EntityManagerUtil.getEntityManager();
//        Long idCopia = 8L;
//        Query query = em.createNativeQuery("select id from livro where id in (select livro_id from livro_copia where copias_id = " + idCopia + ")"); //From livro Where livro in(From copia where id = " + 8 + " );");
//        Livro livro = em.find(Livro.class, query.getSingleResult());

//        Copia copia = em.find(Copia.class, 17);
//        List <Emprestimo> emprestimos = new EmprestimoDao().findByCopia(copia);
//        for(Emprestimo emp : emprestimos)
//            System.out.println(emp);
//        Leitor leitor = em.find(Leitor.class, 8);
//
//        EmprestimoDao emprestimoDao = new EmprestimoDao();
//       Emprestimo emprestimo = new Emprestimo(LocalDate.now(), copia, leitor);
//       emprestimoDao.create(emprestimo);

//        List <Emprestimo> emprestimos = new EmprestimoDao().findEmprestimosPassadosVencidos();
//        for(Emprestimo emp : emprestimos)
////            System.out.println(emp);
//        String usuario= "everson";
//        String senha= "123";

        //Query q = em.createQuery("from Usuario where login = \'" + usuario + "\' and senha = \'" + senha + "\'");
        //Usuario existe = (Usuario) q.getSingleResult();
//        UsuarioDao usuarioDao= new UsuarioDao();
//        System.out.println(usuarioDao.findByLogin(usuario, senha));

        //List<Livro> livros = new LivroDao().findByNomeLike("dom");
//        List<Livro> livros = new LivroDao().findByAno(1923);
//        for(Livro l : livros)
//            System.out.println(l);

//        List<Livro> listaAtual = new ArrayList<>();
//        listaAtual.add(em.find(Livro.class, 1));
//        listaAtual.add(em.find(Livro.class, 2));
//        listaAtual.add(em.find(Livro.class, 3));
//        listaAtual.add(em.find(Livro.class, 4));
//
//        List<Livro> listaNova = new ArrayList<>();
//        listaNova.add(em.find(Livro.class, 2));
//        listaNova.add(em.find(Livro.class, 4));
//
//        List<Livro> listaNovissima = new ArrayList<>();
//        boolean continua = false;
//
//        for(Livro existente : listaAtual){
//            for(Livro filtro : listaNova){
//                if(existente == filtro){
//                    listaNovissima.add(existente);
//                    continua = true;
//                }
//            }
//       }
//
//        if(!continua){
//            listaAtual.remove(listaAtual.size()-1);
//        }
//
//        for(Livro existente : listaNovissima)
//            System.out.println(existente);
//    }

        LivroDao livroDao = new LivroDao();

        //Query q = em.createQuery("select l from Livro l where l.autores.i");
        List<Livro> livros = livroDao.findByAutor(em.find(Autor.class, 1));

        for (Livro l : livros)
            System.out.println(l);
    }
}
