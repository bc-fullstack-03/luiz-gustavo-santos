package com.parrot.backend.data.model;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse<T>{
  private List<T> content;
  private int pageNumber;
  private int pageSize;
  private int totalPages;
  private long totalElements;
}
