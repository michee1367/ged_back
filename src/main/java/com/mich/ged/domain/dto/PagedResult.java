package com.mich.ged.domain.dto;
import java.util.List;
// Dans le domaine
public record PagedResult<T>(
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean isLast
) {
}