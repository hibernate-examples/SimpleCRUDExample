import core.entity.Book;
import core.entity.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;

import java.util.List;

public class Launcher {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();


        Genre genre = new Genre();
        genre.setName("Detective");
        save(genre);

        Book book = new Book();
        book.setName("Old book#1");
        book.setGenre(genre);
        save(book);

        Book book2 = new Book();
        book2.setName("Some book#2");
        book2.setGenre(genre);
        save(book2);

        Book book3 = new Book();
        book3.setName("Some book#3");
        save(book3);

        book.setName("New book#1");
        update(book);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Book> books = session.createCriteria(Book.class)
                .addOrder(Order.asc("name"))
                .list();

        for (Book b : books) {
            System.out.println("\n\nName: " +b.getName());
            System.out.print("Genre: ");
            if(b.getGenre() != null) {
                System.out.print( b.getGenre().getName());
            }
        }

        session.getTransaction().commit();

        delete(book);

        sessionFactory.close();
    }

    private static void save(Object entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    private static void update(Object entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    private static void delete(Object entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
