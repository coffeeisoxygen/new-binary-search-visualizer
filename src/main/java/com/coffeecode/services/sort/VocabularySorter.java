package com.coffeecode.services.sort;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public class VocabularySorter {

    public List<Vocabulary> sort(List<Vocabulary> vocabularies, Language language) {
        return vocabularies.stream()
                .sorted((v1, v2) -> String.CASE_INSENSITIVE_ORDER.compare(
                language.getWord(v1),
                language.getWord(v2)))
                .toList();
    }

}
