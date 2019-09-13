package com.lambdaschool.authenticatedusers.service;

import com.lambdaschool.authenticatedusers.model.Todo;
import com.lambdaschool.authenticatedusers.repository.QuoteRepository;
import com.lambdaschool.authenticatedusers.view.CountQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "quoteService")
public class TodoServiceImpl implements TodoService
{
    @Autowired
    private QuoteRepository quoterepos;

    @Override
    public ArrayList<CountQuotes> getCountQuotes()
    {
        return quoterepos.getCountQuotes();
    }

    @Override
    public List<Todo> findAll()
    {
        List<Todo> list = new ArrayList<>();
        quoterepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findTodoById(long id)
    {
        return quoterepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (quoterepos.findById(id).isPresent())
        {
            quoterepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Todo save(Todo todo)
    {
        return quoterepos.save(todo);
    }

    @Override
    public List<Todo> findByUserName(String username)
    {
        List<Todo> list = new ArrayList<>();
        quoterepos.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(q -> !q.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }

    @Override
    public Todo update(Todo todo, long id)
    {
        Todo newTodo = quoterepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (todo.getTodo() != null)
        {
            newTodo.setTodo(todo.getTodo());
        }

        if (todo.getUser() != null)
        {
            newTodo.setUser(todo.getUser());
        }

        return quoterepos.save(newTodo);
    }
}
