package ru.roseurobank.hqlfetch;

import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import ru.roseurobank.util.HibernateUtil;

import java.util.List;

/**
 * Created by zadorozhniy276 on 17.03.2015.
 */
public class App {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session s = sf.openSession();
        Transaction tx = s.beginTransaction();

//        for (int i = 0; i < 3; i++) {
//            Role role = new Role();
//            role.setName("Engineer" + i);
//
//            User user = new User();
//            user.setName("User" + i);
//            user.addRole(role);
//
//            s.save(user);
//        }

        //HQL
//        Query query = s.createQuery("from User as u Where u.name like :fname");
//        query.setString("fname", "%User%");
//        List<User> users = query.list();
//        for(User user : users) {
//            System.out.println(user);
//        }

        //QBC
//        Criteria qbc = s.createCriteria(User.class);
//        qbc.add(Restrictions.like("name", "User2"));
//        List<User> qbcList = qbc.list();
//        for (User u : qbcList) {
//            System.out.println(u);
//        }

        //QBE
//        Criteria qbe = s.createCriteria(User.class);
//        User exampleUser = new User();
//        exampleUser.setName("User1");
//        qbe.add(Example.create(exampleUser));
//        List<User> qbeList = qbe.list();
//        for (User u : qbeList) {
//            System.out.println(u);
//        }

        //Plan
        //Load Proxy by ID
        User loadUser = (User) s.load(User.class, new Long(1));
        Hibernate.initialize(loadUser);
        if (loadUser instanceof User)
            System.out.println("User loaded");
        if (loadUser instanceof HibernateProxy)
            System.out.println("Proxy loaded");
//        System.out.println(loadUser.getRols().size());

        //Get real object by ID
//        User getUser = (User) s.get(User.class, new Long(2));
//        System.out.println("Size: " + getUser.getRols().size()); // lazy = "extra"
//        if (getUser instanceof HibernateProxy)
//            System.out.println("get Proxy");
//        if (getUser instanceof User)
//            System.out.println("get User");

        //Strategy
//        List<User> users = s.createQuery("from User").list();
//        System.out.println(((User) users.get(0)).getRols());
//        System.out.println(((User) users.get(1)).getRols());
//        System.out.println(((User) users.get(2)).getRols());

//        String UPDATE = "Insert Into User (name) values (:fName)";
//        Query query = s.createQuery(UPDATE);
//        query.setString("fName", "MyName");
//        System.out.println("Inserted: " + query.executeUpdate());

        tx.commit();
        s.close();

        sf.close();
    }
}
