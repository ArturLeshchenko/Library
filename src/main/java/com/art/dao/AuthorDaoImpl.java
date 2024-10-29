package com.art.dao;


import com.art.entity.Author;
import com.art.entity.Book;
import com.art.exception.SqlProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class AuthorDaoImpl implements AuthorDao {
    private static final String FIND_ALL = "with limited_authors as (select author.id as author_id, first_name, middle_name, last_name, birth_date, death_date\n" +
            "     from author limit ? offset ?)\n" +
            "select limited_authors.*, books.id as book_id, books.title from limited_authors left join books ON limited_authors.author_id = books.author_id;";
    private static final String FIND_BY_ID = "select * from author where id = ?";
    private static final String SAVE = "insert into author (first_name, last_name, middle_name, birth_date, death_date ) values (?, ?, ?, ?, ?)";
    private static final String DELETE = "delete from author where id=?";
    private static final String UPDATE = "update author set first_name=?, last_name=?, middle_name=?, birth_date=?, death_date=?  where id=?";
    private final DataSource dataSource;

    @Override
    public List<Author> findAll(int limit, int offset) {
        Map<Long, Author> authors = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author currentAuthor = handleResultSetForAuthor(resultSet);
                if (!authors.containsKey(currentAuthor.getId())) {
                    List<Book> books = currentAuthor.getBooks();
                    Book currentBook = handleResultSetForBook(resultSet);
                    books.add(currentBook);
                    authors.put(currentAuthor.getId(), currentAuthor);
                } else {
                    Author author = authors.get(currentAuthor.getId());
                    List<Book> books = author.getBooks();
                    Book currentBook = handleResultSetForBook(resultSet);
                    books.add(currentBook);
                }
            }
            return new ArrayList<>(authors.values());

        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Author author = handleResultSetForAuthor(resultSet);
                return Optional.of(author);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public Author save(Author author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            fillStatement(preparedStatement, author);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                author.setId(generatedKeys.getLong(1));
            }
            return author;
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    @Override
    public Author update(Long id, Author author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            fillStatement(preparedStatement, author);
            preparedStatement.setLong(6, id);
            author.setId(id);
            preparedStatement.executeUpdate();
            return author;
        } catch (SQLException e) {
            throw new SqlProcessingException(e);
        }
    }

    private void fillStatement(PreparedStatement preparedStatement, Author author) throws SQLException {
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2, author.getLastName());
        preparedStatement.setString(3, author.getMiddleName());
        preparedStatement.setDate(4, Date.valueOf(author.getBirthDate()));
        preparedStatement.setDate(5, Date.valueOf(author.getDeathDate()));
    }

    private Author handleResultSetForAuthor(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getLong("author_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name"),
                resultSet.getDate("birth_date").toLocalDate(),
                resultSet.getDate("death_date").toLocalDate()
        );
        // Сделать такой же метод (выше) для книги)
    }

    private Book handleResultSetForBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("book_id"),
                resultSet.getString("title")
        );
    }

}
