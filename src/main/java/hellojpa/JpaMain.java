package hellojpa;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            Member member = new Member();
            member.setUsername("kime");
            member.setHomeAddress(new Address("11","22","33"));

            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("치킨");

            member.getAddressHistory().add(new Address("15","22","33"));
            member.getAddressHistory().add(new Address("15","226","336"));

            em.persist(member);

            Member member1 = em.find(Member.class, member.getId());
            //homeCity -> newCity
            Address homeAddress = member1.getHomeAddress();
            //치킨 -> 한식
            member1.getFavoriteFoods().remove("치킨");
            member1.getFavoriteFoods().add("한식");

            member1.getAddressHistory().remove(new Address("15","22","33"));
            member1.getAddressHistory().add(new Address("new city", "22", "33"));



            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            emf.close();
        }
    }

    private static void printAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
