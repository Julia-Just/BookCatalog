package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Genre;
import org.top.authorscatalog.rdb.repository.GenreRepository;
import org.top.authorscatalog.service.GenreService;

import java.util.Optional;

// RdbGenreService - имплементация GenreService, работающая с СУБД
@Service
public class RdbGenreService implements GenreService {
    
    // репозиторий для выполнения операций
    private final GenreRepository genreRepository;

    public RdbGenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> save(Genre genre) {
        return Optional.of(genreRepository.save(genre));
    }

    @Override
    public Optional<Genre> deleteById(Integer id) {
        Optional<Genre> deleted = findById(id);
        if (deleted.isPresent()) {
            genreRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Genre> update(Genre genre) {
        // проверить, есть ли объект с таким id
        Optional<Genre> updated = findById(genre.getId());
        if (updated.isPresent()) {
            // если есть, то обновить его
            updated = Optional.of(genreRepository.save(genre));
        }
        return updated;
    }
}
