package com.bruno.carlisting.services.implementations;

import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import com.bruno.carlisting.services.interfaces.PagingService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PagingServiceImpl implements PagingService {

    @Override
    public void validatePage(Page<?> page, String message) {
        if (page.getTotalElements() == 0) throw new ObjectNotFoundException(
                String.format("%s, and no entries were found in the database", message));
        else if (!page.hasContent()) throw new ObjectNotFoundException(
                String.format("%s, however there are %s entries up to page %s",
                        message, page.getTotalElements(), page.getTotalPages() - 1));
    }
}
