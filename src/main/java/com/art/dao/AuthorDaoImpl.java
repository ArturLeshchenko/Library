package com.art.dao;


import com.art.entity.Author;
import com.art.exception.SqlProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AuthorDaoImpl implements AuthorDao {
    private static final String FIND_ALL = "select * from author";
    private static final String FIND_BY_ID = "select * from author where id = ?";
    private static final String SAVE = "insert into author (first_name, last_name, middle_name, birth_date, death_date ) values (?, ?, ?, ?, ?)";
    private static final String DELETE = "delete from author where id=?";
    private static final String UPDATE = "update author set firstName=?, lastName=?, middleName=?, birth_date=?, death_date=?  where id=?";
    private final DataSource dataSource;

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Author author = handleResultSet(resultSet);
                authors.add(author);
            }
            return authors;
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
                Author author = handleResultSet(resultSet);
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
    private Author handleResultSet(ResultSet resultSet) throws SQLException {
        return new Author(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name"),
                resultSet.getDate("birth_date").toLocalDate(),
                resultSet.getDate("death_date").toLocalDate()
        );
    }
}
