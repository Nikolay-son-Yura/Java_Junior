package ru.gb.HomeWork.Lesson4;

/**
 * Используя hibernate, создать таблицы:
 * 1. Post (публикация) (id, title)
 * 2. PostComment (комментарий к публикации) (id, text, post_id)
 *
 * Написать стандартные CRUD-методы: создание, загрузка, удаление.
 *
 * Доп. задания:
 * 1. * В сущностях post и postComment добавить поля timestamp с датами.
 * 2. * Создать таблицу users(id, name) и в сущностях post и postComment добавить ссылку на юзера.
 * 3. * Реализовать методы:
 * 3.1 Загрузить все комментарии публикации
 * 3.2 Загрузить все публикации по идентификатору юзера
 * 3.3 ** Загрузить все комментарии по идентификатору юзера
 * 3.4 **** По идентификатору юзера загрузить юзеров, под чьими публикациями он оставлял комменты.
 * // userId -> List<User>
 *
 *
 * Замечание:
 * 1. Можно использовать ЛЮБУЮ базу данных (например, h2)
 * 2. Если запутаетесь, приходите в группу в телеграме или пишите мне @inchestnov в личку.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.gb.HomeWork.Lesson4.entity.GenerateTable ;
import ru.gb.HomeWork.Lesson4.entity.Post ;
import ru.gb.HomeWork.Lesson4.entity.PostComment ;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        GenerateTable generateTable=new GenerateTable();

        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        createTable(generateTable.getPosts(),sessionFactory);
        createTable(generateTable.getComments(),sessionFactory);
        insertTable(new Post(1L,"Post#1"),sessionFactory);
        insertTable(new PostComment(20L,"Смотреть всем", readTable(Post.class,1l,sessionFactory)),sessionFactory);
        System.out.println(readTable(Post.class, 1L, sessionFactory));




    }

    public static <T> T readTable(Class<T> className, long id, SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()){
            return session.find(className, id);
        }
    }

    private static <T> void deleteTable(T elem, SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(elem); // delete
            tx.commit();

        }
    }

     private static <T> void createTable(List<T> element,SessionFactory sessionFactory){
         try (Session session = sessionFactory.openSession()) {
             for (T t : element) {
                 session.beginTransaction();
                 session.save(element);
                 session.getTransaction().commit();

             }
         }
     }

     private static <T> void insertTable(T element,SessionFactory sessionFactory){
         try (Session session = sessionFactory.openSession()) {
             Transaction tx = session.beginTransaction();
             session.persist(element);
             tx.commit();
         }
     }
}
