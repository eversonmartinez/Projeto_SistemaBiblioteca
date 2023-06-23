package com.dao;

import com.model.Copia;
import com.model.Emprestimo;
import com.model.Leitor;
import com.util.Alerta;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoDao extends Dao<Emprestimo> {

    public EmprestimoDao() {
        super(Emprestimo.class);
    }

    @Override
    public Boolean create(Emprestimo entity){
        try {
            if(emprestimoSimilar(entity) == true)
                return false;

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

    @Override
    public Boolean update(Emprestimo entity){
        try {
            if(emprestimoSimilar(entity) == true) {
                return false;
            }
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

    private Boolean emprestimoSimilar(Emprestimo emprestimoNovo){
        Copia copiaComparada = emprestimoNovo.getCopia();
        List<Emprestimo> emprestimos= findByCopia(copiaComparada);

        for(Emprestimo emprestimoAnterior : emprestimos) {

            //Já existe um empréstimo pendente antes desse novo empréstimo, algum que não foi devolvido
            if (emprestimoAnterior.getDataEntrega() == null && emprestimoAnterior.getData().isBefore(emprestimoNovo.getData())) {
                    mensagem = "Um empréstimo concorrente já existe: ID " + emprestimoAnterior.getId();
                    return true;
            }

            //Esse empréstimo novo consta como não entregue, mas um empréstimo já foi feito com data posterior
            if (emprestimoNovo.getDataEntrega() == null && emprestimoNovo.getData().isBefore(emprestimoAnterior.getData())) {
                    mensagem = "Um empréstimo concorrente já existe: ID " + emprestimoAnterior.getId();
                    return true;
            }

            //O empréstimo novo está tentando ser feito entre o período de algum outro empréstimo
            if (emprestimoAnterior.getData().isBefore(emprestimoNovo.getData()) || emprestimoAnterior.getData().isEqual(emprestimoNovo.getData())) {
                if (emprestimoAnterior.getDataEntrega().isAfter(emprestimoNovo.getData())) {
                    mensagem = "Um empréstimo concorrente já existe: ID " + emprestimoAnterior.getId();
                    return true;
                }
            }

            //O empréstimo novo está tentando ser criado com uma data de entrega em que existe um outro empréstimo
            if (emprestimoNovo.getData().isBefore(emprestimoAnterior.getData()) && emprestimoNovo.getDataEntrega().isAfter(emprestimoAnterior.getData())) {
                    //emprestimoNovo.getData().isEqual(emprestimoAnterior.getData())) {
                    mensagem = "Um empréstimo concorrente já existe: ID " + emprestimoAnterior.getId();
                    return true;
            }

        }

        return false;
    }

    //alerta gambiarra:
    public Boolean edicaoDataEntrega(Emprestimo emprestimoNovo, LocalDate novaData) {
        Copia copiaComparada = emprestimoNovo.getCopia();
        List<Emprestimo> emprestimos = findByCopia(copiaComparada);

        for (Emprestimo emprestimoAnterior : emprestimos) {
            if (emprestimoNovo.getData().isBefore(emprestimoAnterior.getData()) && novaData.isAfter(emprestimoAnterior.getData())) {
                //emprestimoNovo.getData().isEqual(emprestimoAnterior.getData())) {
                mensagem = "Um empréstimo concorrente já existe: ID " + emprestimoAnterior.getId();
                return false;
            }
        }

        return true;

    }

    public List<Emprestimo> findByCopia(Copia copia){
        Query query = em.createQuery("From Emprestimo Where copia.id = " + copia.getId());
        return query.getResultList();
    }

    public List<Emprestimo> findByCopia(Long idCopia){
        Query query = em.createQuery("From Emprestimo Where copia.id = " + idCopia);
        return query.getResultList();
    }

    public List<Emprestimo> findCurrents(){
        Query query = em.createQuery("From Emprestimo Where dataEntrega = null");
        return query.getResultList();
    }

    public List<Emprestimo> findByLeitor(Leitor leitor){
        Query query = em.createQuery("From Emprestimo Where leitor.id = " + leitor.getId());
        return query.getResultList();
    }

    public List<Emprestimo> findByLeitor(Long idLeitor){
        Query query = em.createQuery("From Emprestimo Where leitor.id = " + idLeitor);
        return query.getResultList();
    }
}
