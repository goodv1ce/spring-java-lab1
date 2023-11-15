package ua.goodvice.javaspringlab.entity;

import lombok.Data;

import java.util.Set;

@Data
public class Book {
    private final Long id;
    private final String title;
    private final Author author;
    private final Set<Keyword> keywords;
}
