package ru.roseurobank.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.roseurobank.util.HibernateUtil;

/**
 *
 * @author zadorozhniy276
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session s = sf.openSession();
        Transaction tx = s.beginTransaction();

        User u = new User();
        u.setName("Пушкин А.С.");
        final Long userId = (Long) s.save(u);

        tx.commit();
        s.close();

        CountDownLatch ready = new CountDownLatch(2);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                Session s1 = sf.openSession();
                Transaction tx1 = s1.beginTransaction();

                User u1 = (User) s1.get(User.class, userId, new LockOptions(LockMode.PESSIMISTIC_WRITE));
//                User u1 = (User) s1.get(User.class, userId, new LockOptions(LockMode.NONE));

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

                u1.setName("Лермонтов М.Ю.");
//                System.out.println("Before save: " + u1);
                Long id = (Long) s1.save(u1);
                System.out.println("After save: " + u1);
//                System.out.printf("%nId => %d%nName => %s%n%n", id, u1.getName());

                tx1.commit();
                s1.close();

                ready.countDown();
            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                Session s2 = sf.openSession();
                Transaction tx2 = s2.beginTransaction();
                
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//                }

                User u2 = (User) s2.get(User.class, userId);
                
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

                u2.setName("Тютчев Ф.И.");
//                System.out.println("Before save: " + u2);
                Long id = (Long) s2.save(u2);
                System.out.println("After save: " + u2);
//                System.out.printf("%nId => %d%nName => %s%n%n", id, u2.getName());

                tx2.commit();
                s2.close();
                
                ready.countDown();
            }
        });

        t1.start();
        t2.start();
//        t1.join();
//        t2.join();

        ready.await();

        sf.close();
    }
}
