package com.art.dao;

import com.art.entity.Book;
import com.art.exception.SqlProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private static final String FIND_BOOK = "select * from books where author_id=?";

    private final DataSource dataSource;

    @Override
    public List<Book> findByAuthorId(Long authorId) {
        List<Book> books = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK)) {
            preparedStatement.setLong(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title")
                );
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }
}
