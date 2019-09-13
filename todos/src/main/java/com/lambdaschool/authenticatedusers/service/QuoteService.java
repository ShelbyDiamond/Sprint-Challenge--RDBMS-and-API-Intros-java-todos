package com.lambdaschool.authenticatedusers.service;

import com.lambdaschool.authenticatedusers.model.Todo;
import com.lambdaschool.authenticatedusers.view.CountQuotes;

import java.util.ArrayList;
import java.util.List;

public interface QuoteService
{
    List<Todo> findAll();

    Todo findQuoteById(long id);

    List<Todo> findByUserName (String username);

    void delete(long id);

    Todo save(Todo quote);

    Todo update(Todo quote, long id);

    ArrayList<CountQuotes> getCountQuotes();
}
