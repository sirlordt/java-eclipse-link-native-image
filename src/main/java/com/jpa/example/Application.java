package com.jpa.example;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.URL;
// import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
//import java.util.List;

import com.jpa.example.models.Author;
import com.jpa.example.models.Book;
import com.jpa.example.models.MyObject;

public class Application {

    public static void main(String[] args) {

        Application instance = new Application();
                                                            //"persistence.xml"
        // //InputStream inputStream = instance.getFileAsIOStream("META-INF/persistence.xml");
        // InputStream inputStream = instance.getFileAsIOStream("test.xml");

        // if (inputStream != null) {
        
        //     printInputStream( inputStream );
        //     System.out.println( "Ok: test.xml" );

        // }

        //inputStream.close();
        InputStream inputStream = instance.getFileAsIOStream("META-INF/persistence.xml");

        if (inputStream != null) {
        
            //printInputStream( inputStream );
            System.out.println( "Ok: META-INF/persistence.xml" );
            try {
               
               inputStream.close();

            }
            catch ( Exception ex ) {

            }

        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Author author = new Author();
        author.Name = "Tomas Moreno";
        author.BirthYear = 1979;
        entityManager.persist(author);

        //var id = author.Id;

        findAuthorById( entityManager, author.Id );

        Book book = new Book();
        book.Title = "GraalVM/Native-Image the future of Java";
        book.PubDate = LocalDate.now();
        book.Author = author;
        entityManager.persist(book);

        findBookById( entityManager, book.Id );

        MyObject myObject = new MyObject();
        myObject.Data = "one";
        entityManager.persist(myObject);

        var id = myObject.Id;

        myObject = new MyObject();
        myObject.Data = "two";
        //myObject.setData("two");
        entityManager.persist(myObject);

        entityManager.getTransaction().commit();

        findObjectById(entityManager, id);
        queryWithJPQL(entityManager);
        typedQueryWithJPQL(entityManager);
        criteriaQuery(entityManager);
        queryNative(entityManager);

        System.out.println("Press Enter to continue");

        try {
        
            System.in.read();

        }
        catch( Exception ex ){

            //

        }

    }

    private InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    // private static void printInputStream(InputStream is) {

    //     try (InputStreamReader streamReader =
    //                 new InputStreamReader(is, StandardCharsets.UTF_8);
    //          BufferedReader reader = new BufferedReader(streamReader)) {

    //         String line;
    //         while ((line = reader.readLine()) != null) {
    //             System.out.println(line);
    //         }

    //     } catch ( Exception ex ) {
    //         ex.printStackTrace();
    //     }

    // }

    private static void findAuthorById(EntityManager entityManager, String id ) {
        System.out.println("----\nfinding author by id");
        var o = entityManager.find(Author.class, id ); //2L);
        System.out.println(o);
    }

    private static void findBookById(EntityManager entityManager, String id ) {
        System.out.println("----\nfinding book by id");
        var o = entityManager.find(Book.class, id ); //2L);
        System.out.println(o);
    }

    private static void findObjectById(EntityManager entityManager, String id ) {
        System.out.println("----\nfinding object by id");
        MyObject o = entityManager.find(MyObject.class, id ); //2L);
        System.out.println(o);
    }

    private static void queryWithJPQL(EntityManager entityManager) {
        System.out.println("----\nQuerying using JPQL");
        Query query = entityManager.createQuery("select t from MyObject t");
        var resultList1 = query.getResultList();
        System.out.println(resultList1);
    }

    private static void typedQueryWithJPQL(EntityManager entityManager) {
        System.out.println("----\nTyped Querying using JPQL");
        TypedQuery<MyObject> q =
                entityManager.createQuery("select t from MyObject t"
                        , MyObject.class);
        System.out.println(q.getResultList());
    }

    private static void queryNative(EntityManager entityManager) {
        System.out.println("----\nnative query");
        Query nativeQuery = entityManager.createNativeQuery("select * from MyObject");
        var resultList = nativeQuery.getResultList();
        for (Object o : resultList) {
            if (o.getClass().isArray()) {
                Object oa[] = (Object[]) o;
                System.out.println(Arrays.asList(oa));
            } else {
                System.out.println(o);
            }
        }
    }


    private static void criteriaQuery(EntityManager entityManager) {
        System.out.println("----\ncriteria query");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> query = cb.createQuery();
        CriteriaQuery<Object> select = query.select(query.from(MyObject.class));

        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
        System.out.println(typedQuery.getResultList());

    }
}