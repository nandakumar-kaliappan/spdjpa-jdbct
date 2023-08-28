package com.knkweb.sdjpajdbct;

import com.knkweb.sdjpajdbct.dao.AuthorDao;
import com.knkweb.sdjpajdbct.dao.AuthorDaoImpl;
import com.knkweb.sdjpajdbct.dao.BookDao;
import com.knkweb.sdjpajdbct.dao.BookDaoImpl;
import com.knkweb.sdjpajdbct.domain.Book;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BookDaoImpl.class, AuthorDaoImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void updateBook() {
//        given
        Book book =Book.builder()
                .title("panjali sabatham 2")
                .isbn("no isbn")
                .publisher("TN Govt")
                .build();
        Book bookSaved = bookDao.saveNewBook(book);
        assertThat(bookSaved).isNotNull();

//        when : updating
        bookSaved.setIsbn("56734765287");
        Book bookUpdated = bookDao.updateBook(bookSaved);

//        then
        AssertionsForClassTypes.assertThat(bookUpdated).isNotNull();
        AssertionsForClassTypes.assertThat(bookSaved).isEqualTo(bookUpdated);

        Book bookFound = bookDao.findById(bookUpdated.getId());

        AssertionsForClassTypes.assertThat(bookFound).isNotNull();
        AssertionsForClassTypes.assertThat(bookFound).isEqualTo(bookUpdated);

    }

    @Test
    @Order(4)
    void deleteBookById() {

//        given
        Book book =Book.builder()
                .title("panjali sabatham 2")
                .isbn("no isbn")
                .publisher("TN Govt")
                .build();
        Book bookSaved = bookDao.saveNewBook(book);
        assertThat(bookSaved).isNotNull();

//        when: Deleting
        bookDao.deleteBookById(bookSaved.getId());

//        then
        Book bookFound = bookDao.findById(bookSaved.getId());
        AssertionsForClassTypes.assertThat(bookFound).isNull();



    }

    @Test
    @Order(3)
    void saveNewBook() {
        Book book =Book.builder()
                    .title("panjali sabatham")
                    .isbn("no isbn")
                    .publisher("TN Govt")
                    .build();

        Book bookSaved = bookDao.saveNewBook(book);

        AssertionsForClassTypes.assertThat(bookSaved).isNotNull();
        AssertionsForClassTypes.assertThat(bookSaved.getId()).isNotNegative();

        Book bookFound = bookDao.findById(bookSaved.getId());

        AssertionsForClassTypes.assertThat(bookFound).isNotNull();
        AssertionsForClassTypes.assertThat(bookFound).isEqualTo(bookSaved);

    }

    @Test
    @Order(2)
    void findByTitle() {
        Book bookFound = bookDao.findById(1L);
        AssertionsForClassTypes.assertThat(bookFound).isNotNull();

        Book bookFoundByTitle = bookDao.findByTitle(bookFound.getTitle());

        AssertionsForClassTypes.assertThat(bookFoundByTitle).isNotNull();
        AssertionsForClassTypes.assertThat(bookFoundByTitle.getId()).isEqualTo(1L);
        AssertionsForClassTypes.assertThat(bookFoundByTitle.getTitle()).isEqualTo(bookFound.getTitle());

    }

    @Test
    @Order(1)
    void findByid() {
        Book bookFound = bookDao.findById(1L);

        AssertionsForClassTypes.assertThat(bookFound).isNotNull();
        AssertionsForClassTypes.assertThat(bookFound.getId()).isEqualTo(1L);
    }
}
