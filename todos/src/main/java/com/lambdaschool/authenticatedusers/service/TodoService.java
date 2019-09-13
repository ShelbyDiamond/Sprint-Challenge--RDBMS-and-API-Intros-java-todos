package com.lambdaschool.authenticatedusers.service;

import com.lambdaschool.authenticatedusers.model.Todo;
import com.lambdaschool.authenticatedusers.view.CountQuotes;

import java.util.ArrayList;
import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findTodoById(long id);

    List<Todo> findByUserName (String username);

    void delete(long id);

    Todo save(Todo todo);

    Todo update(Todo todo, long id);

    ArrayList<CountQuotes> getCountQuotes();
}

