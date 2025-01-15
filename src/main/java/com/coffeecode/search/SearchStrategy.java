package com.coffeecode.search;

import com.coffeecode.model.Vocabulary;
import com.coffeecode.model.Language;
import java.util.List;

public interface SearchStrategy {

    SearchResult search(String word, List<Vocabulary> data, Language language);
}
