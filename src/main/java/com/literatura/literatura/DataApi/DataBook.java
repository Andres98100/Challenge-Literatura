package com.literatura.literatura.DataApi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DataAuthor> authors,
        @JsonAlias("languages") List<String> lenguages,
        @JsonAlias("download_count") int downloadCount
) {
    public DataBook {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        if (lenguages == null) {
            lenguages = new ArrayList<>();
        }
    }
}
