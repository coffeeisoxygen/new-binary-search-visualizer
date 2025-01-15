package com.coffeecode.search;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public interface SearchStrategy {

    SearchResult search(String word, List<Vocabulary> data, Language language);
}
