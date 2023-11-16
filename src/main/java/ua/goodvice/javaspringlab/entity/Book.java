package ua.goodvice.javaspringlab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private  Long id;
    private  String title;
    private  Author author;
    private  Set<Keyword> keywords;
}
