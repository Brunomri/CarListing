package com.bruno.carlisting.services.interfaces;

import org.springframework.data.domain.Page;

public interface PagingService {

    void validatePage(Page<?> page, String message);
}
