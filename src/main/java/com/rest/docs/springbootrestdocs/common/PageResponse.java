package com.rest.docs.springbootrestdocs.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageResponse<T> {

    private final long totalElements;
    private final int totalPages;
    private final int size;
    private final int number;
    private final int numberOfElements;
    private final boolean last;
    private final boolean first;
    private final boolean empty;
    private final List<T> content;

    public PageResponse(final Page<T> page) {
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
        this.content = page.getContent();
    }
}