import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Aluno;
import model.Leitor;
import model.Professor;
import model.Usuario;

public class Main {

    public static void main(String[]  args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaPU");
        EntityManager em = emf.createEntityManager();

//        Leitor leitor = new Leitor("Maria Angelica", "22999335642", "maria.angelica@ghotmail.com");
//        em.getTransaction().begin();
//        em.persist(leitor);
//
//        Leitor leitor = em.find(Leitor.class, 3);
//        em.remove(leitor);

        em.getTransaction().begin();
//        Aluno aluno = new Aluno("Everson", "22999549162", "eeveemon.sandstorm@gmail.com", "2101130031");
//        Professor professor = new Professor("Isac Mendes", "22998756423", "isac.mendes@hotmail.com", "Analista de Sistemas");
//        professor.setUsuario(new Usuario("isac", "123456"));
//        em.persist(aluno);
//        em.persist(professor);
//        Aluno aluno = new Aluno("Lorrainy Nunes", "22999004872", "lorrainy.nunes@gmail.com", "2301130031");
//        aluno.setUsuario(new Usuario("lolo", "1234"));
        Aluno aluno = em.find(Aluno.class,7);
        aluno.setUsuario(null);
        em.merge(aluno);
        em.getTransaction().commit();
        em.close();
    }
}
