package com.skosarev.libraryboot.repository;

import com.skosarev.libraryboot.model.Book;
import com.skosarev.libraryboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Modifying
    @Query("update Book set person = null where id=?1")
    void release(int id);

    @Modifying
    @Query("update Book set person = ?2 where id=?1")
    void assign(int id, Person person);

    List<Book> findByTitleContaining(String title);
}
