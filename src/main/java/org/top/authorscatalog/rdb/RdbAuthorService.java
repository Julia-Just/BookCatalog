package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Author;
import org.top.authorscatalog.rdb.repository.AuthorRepository;
import org.top.authorscatalog.service.AuthorService;

import java.util.Optional;

// RdbAuthorService - имплементация AuthorService, работающая с СУБД
@Service
public class RdbAuthorService implements AuthorService {

    // репозиторий для выполнения операций
    private final AuthorRepository authorRepository;

    public RdbAuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Поиск всех авторов
    @Override
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    // Поиск автора по id
    @Override
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    // Добавление автора
    @Override
    public Optional<Author> save(Author author, Boolean forceSave) {
        Optional<Author> clone = authorRepository.findFirstByLastNameAndFirstNameAndPatronymic(author.getLastName(), author.getFirstName(), author.getPatronymic());
        if (clone.isEmpty() || forceSave) {
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    // Удаление автора
    @Override
    public Optional<Author> deleteById(Integer id) {
        Optional<Author> deleted = findById(id);
        if (deleted.isPresent()) {
            authorRepository.deleteById(id);
        }
        return deleted;
    }

    // Изменение в авторе
    @Override
    public Optional<Author> update(Author author) {
        // проверить, есть ли объект с таким id
        Optional<Author> updated = findById(author.getId());
        if (updated.isPresent()) {
            // если есть, то обновить его
            updated = Optional.of(authorRepository.save(author));
        }
            return updated;
    }

//    @Override
//    public Optional<Author> findByLastName(String lastName) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Author> findByFirstName(String FirstName) {
//        return Optional.empty();
//    }

    // поиск по фамилии
//     public Optional<Author> findByLastName(String lastName) {
//        Optional<Author> findLastName = authorRepository.;
//    }
}
