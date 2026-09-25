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
    public static <T> PagedResult<T> empty() {
        return new PagedResult<>(List.of(), 1, 0, 0, 0, true);
    }
}