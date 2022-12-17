package com.jpa.example;

// import jakarta.json.Json;
// import jakarta.json.JsonValue;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.net.URL;
// import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.List;
import java.util.HashMap;
import java.util.List;

//import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jpa.example.models.Author;
import com.jpa.example.models.Book;
import com.jpa.example.models.EntityA;
import com.jpa.example.models.EntityB;
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

        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
            try {
                persistEntity(emf);
                nativeQueries(emf);
                loadEntityA(emf);
                loadEntityB(emf);
            } 

            finally {
                emf.close();
            }
            System.out.println("Press Enter to continue");

            try {
            
                System.in.read();
    
            }
            catch( Exception ex ){
    
                //
    
            }
    
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Author author01 = new Author();
        author01.Name = "Tomas Moreno";
        author01.BirthYear = 1979;
        author01.bookList = new ArrayList<>();
        author01.ExtraData = new HashMap<>();
        author01.ExtraData.put( "Field1" , "Value1" );
        author01.ExtraData.put( "Field2" , "Value2" );
        //entityManager.persist(author01);

        Book book01 = new Book();
        book01.Title = "GraalVM/Native-Image the future of Java";
        book01.PubDate = LocalDate.now();
        book01.Author = author01;
        //book01.setAuthor( author01 );
        entityManager.persist(book01);

        Book book02 = new Book();
        book02.Title = "Java 2017";
        book02.PubDate = LocalDate.now();
        book02.Author = author01;
        //book02.setAuthor( author01 );
        entityManager.persist(book02);
        
        author01.bookList = Arrays.asList( book01, book02 );
        //author01.setBookList( Arrays.asList( book01, book02 ) );
        entityManager.persist(author01);

        findAuthorById( entityManager, author01.Id );
        findBookById( entityManager, book01.Id );

        Author author02 = new Author();
        author02.Name = "Loly Gomez";
        author02.BirthYear = 1980;
        entityManager.persist(author02);

        //var id = author.Id;

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
        System.out.println( "book list =>" + o.bookList.toString() );
    }

    private static void findBookById(EntityManager entityManager, String id ) {
        System.out.println("----\nfinding book by id");
        var o = entityManager.find(Book.class, id ); //2L);
        System.out.println(o);
        System.out.println( "author =>" + o.Author.toString() );
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

    public static void nativeQuery(EntityManager em, String s) {
        System.out.printf("-----------------------------%n'%s'%n",  s);
        Query query = em.createNativeQuery(s);
        List<?> list = query.getResultList();
        for (Object o : list) {
            if(o instanceof Object[]) {
                System.out.println(Arrays.toString((Object[]) o));
            }else{
                System.out.println(o);
            }
        }
    }

    private static void nativeQueries(EntityManagerFactory emf) {
        System.out.println("-- nativeQueries --");
        EntityManager em = emf.createEntityManager();
        Application.nativeQuery(em, "Select * from EntityA");
        Application.nativeQuery(em, "Select * from EntityB");
    }

    private static void persistEntity(EntityManagerFactory emf) {
        System.out.println("-- persistEntity --");
        EntityManager em = emf.createEntityManager();

        EntityB entityB1 = new EntityB();
        entityB1.setStrB("testStringB");

        EntityB entityB2 = new EntityB();
        entityB2.setStrB("testStringB2");

        EntityA entityA = new EntityA();
        entityA.setStrA("testStringA");
        entityA.setEntityBList(Arrays.asList(entityB1, entityB2));

        entityB1.setRefEntityA(entityA);
        entityB2.setRefEntityA(entityA);

        System.out.println("-- persisting entities --");
        System.out.printf(" %s%n entityA#entityBList: %s%n", entityA, entityA.getEntityBList());
        System.out.printf(" %s%n entityB1#refEntityA: %s%n", entityB1, entityB1.getRefEntityA());
        System.out.printf(" %s%n entityB2#refEntityA: %s%n", entityB2, entityB2.getRefEntityA());

        em.getTransaction().begin();
        em.persist(entityA);
        em.persist(entityB1);
        em.persist(entityB2);
        em.getTransaction().commit();

        em.close();
    }

    @SuppressWarnings("unchecked")
    private static void loadEntityA(EntityManagerFactory emf) {
        System.out.println("-- loadEntityA --");
        EntityManager em = emf.createEntityManager();
        List<EntityA> entityAList = em.createQuery("Select t from EntityA t").getResultList();
        entityAList.forEach(e -> System.out.printf(" %s%n entityA#entityBList: %s%n", e, e.getEntityBList()));
        em.close();
    }

    @SuppressWarnings("unchecked")
    private static void loadEntityB(EntityManagerFactory emf) {
        System.out.println("-- loadEntityB --");
        EntityManager em = emf.createEntityManager();
        List<EntityB> entityBList = em.createQuery("Select t from EntityB t").getResultList();
        entityBList.forEach(e -> System.out.printf(" %s%n entityB#refEntityA: %s%n", e, e.getRefEntityA()));
        em.close();
    }    
    
}