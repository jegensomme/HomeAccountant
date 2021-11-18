package ru.jegensomme.homeaccountant.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.repository.UserRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final CrudUserRepository crudRepository;

    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    @Transactional
    @Override
    public User save(@NonNull User user) {
        return crudRepository.save(user);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) > 0;
    }

    @Override
    public @Nullable User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public @Nullable User getByEmail(@NonNull String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }
}
