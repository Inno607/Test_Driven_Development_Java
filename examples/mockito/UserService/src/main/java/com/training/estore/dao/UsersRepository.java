package com.training.estore.dao;


import com.training.estore.model.User;

public interface UsersRepository {
    boolean save(User user);
}
