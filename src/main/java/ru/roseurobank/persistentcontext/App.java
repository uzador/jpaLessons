package ru.roseurobank.persistentcontext;

import org.hibernate.*;
import ru.roseurobank.util.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        Session s = sf.openSession();
        Transaction t = s.beginTransaction();

        Category c = new Category();
        c.setName("Electronics");

        Category c1 = new Category();
        c1.setName("Computers");
        c1.setParentCategory(c);

        Category c2 = new Category();
        c2.setName("Cellphones");
        c2.setParentCategory(c);

        Long computerId = (Long) s.save(c);
        s.save(c1);
        s.save(c2);

        t.commit();
        s.close();

        Session s1 = sf.openSession();
        Transaction t1 = s1.beginTransaction();

        //persistence by reachability
        Category computer = (Category) s1.load(Category.class, computerId);
        Category laptops = new Category();
        laptops.setName("Laptops");
        laptops.setParentCategory(computer);
        computer.getChildCategories().add(laptops);

        List<Category> categoryList = s1.createQuery("from Category").list();
        categoryList.forEach(
                cat -> {
                    System.out.print(cat);
                    System.out.printf(", parent={" + cat.getParentCategory() + "}");
                    System.out.print(
                            cat.getChildCategories().stream()
                                    .map(Category::toString)
                                    .collect(Collectors.joining("; ", ", child=[", "]\n"))
                    );
                });


        t1.commit();
        s1.close();

//        Session s3 = sf.openSession();
//        Transaction t3 = s3.beginTransaction();
//
//        IntStream.range(0, 10_000)
//                .forEach(i -> {
//                    Category category = new Category();
//                    category.setName("Category " + i);
//                    s3.save(category);
//                    if (i % 100 == 0) {
//                        s3.flush();
//                        s3.clear();
//                    }
//                });
        //bulk update directly in the database
//        Query q = s3.createQuery("update Category i set i.parentCategory = :isParent");
//        q.setEntity("isParent", computer);
//        int updatedCategories = q.executeUpdate();
//        System.out.println("Updated: " + updatedCategories);

        //bulk delete directly in the database
//        Query q1 = s3.createQuery("delete Category i where i.name like :isCategory");
//        q1.setString("isCategory", "%Category%");
//        int deletedCategories = q1.executeUpdate();
//        System.out.println("Deleted: " + deletedCategories);

//        t3.commit();
//        s3.close();

        //bulk update without persistence context
//        StatelessSession s4 = sf.openStatelessSession();
//        Transaction t4 = s4.beginTransaction();
//        ScrollableResults cursor =
//                s4.createQuery("from Category").scroll();
//        while (cursor.next()) {
//            Category category = (Category) cursor.get(0);
//            category.setName("OOOPS!!!");
//            s4.update(category);
//        }
//        t4.commit();
//        s4.close();

        sf.close();
    }
}
