package com.lambdaschool.authenticatedusers.service;

import com.lambdaschool.authenticatedusers.model.Todo;
import com.lambdaschool.authenticatedusers.repository.QuoteRepository;
import com.lambdaschool.authenticatedusers.view.CountQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "quoteService")
public class QuoteServiceImpl implements QuoteService
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
    public Todo findQuoteById(long id)
    {
        return quoterepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (quoterepos.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (quoterepos.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                quoterepos.deleteById(id);
            } else
            {
                throw new EntityNotFoundException(Long.toString(id) + " " + authentication.getName());
            }
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Todo save(Todo quote)
    {
        return quoterepos.save(quote);
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
    public Todo update(Todo quote, long id)
    {
            Todo newQuote = quoterepos.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

            if (quote.getQuote() != null)
            {
                newQuote.setQuote(quote.getQuote());
            }

            if (quote.getUser() != null)
            {
                newQuote.setUser(quote.getUser());
            }

            return quoterepos.save(newQuote);
    }
}
