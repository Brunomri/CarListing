package com.bruno.carlisting.services;

import org.springframework.data.domain.Page;

public interface PagingService {

    void validatePage(Page<?> page);
}
