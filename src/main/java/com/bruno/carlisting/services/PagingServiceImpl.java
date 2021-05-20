package com.bruno.carlisting.services;

import com.bruno.carlisting.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PagingServiceImpl implements PagingService {

    @Override
    public void validatePage(Page<?> page) {
        if (page.getTotalElements() == 0) throw new ObjectNotFoundException("This query returned no entries");
        else if (!page.hasContent()) throw new ObjectNotFoundException("There is no content in this page");
    }
}
