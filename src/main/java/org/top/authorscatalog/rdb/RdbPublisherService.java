package org.top.authorscatalog.rdb;

import org.springframework.stereotype.Service;
import org.top.authorscatalog.entity.Publisher;
import org.top.authorscatalog.rdb.repository.PublisherRepository;
import org.top.authorscatalog.service.PublisherService;

import java.util.Optional;

// RdbPublisherService - имплементация PublisherService, работающая с СУБД
@Service
public class RdbPublisherService implements PublisherService {

    // репозиторий для выполнения операций

    private final PublisherRepository publisherRepository;

    public RdbPublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Iterable<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> findById(Integer id) {
        return publisherRepository.findById(id);
    }

    @Override
    public Optional<Publisher> save(Publisher publisher) {
        return Optional.of(publisherRepository.save(publisher));
    }

    @Override
    public Optional<Publisher> deleteById(Integer id) {
        Optional<Publisher> deleted = findById(id);
        if (deleted.isPresent()) {
            publisherRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Publisher> update(Publisher publisher) {
        // проверить, есть ли объект с таким id
        Optional<Publisher> updated = findById(publisher.getId());
        if (updated.isPresent()) {
            // если есть, то обновить его
            updated = Optional.of(publisherRepository.save(publisher));
        }
        return updated;
    }
}
