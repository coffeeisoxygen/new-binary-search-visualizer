package com.coffeecode.services.sort;

import java.util.List;

import com.coffeecode.model.Language;
import com.coffeecode.model.Vocabulary;

public interface ISortService {

    List<Vocabulary> sortByLanguage(List<Vocabulary> vocabularies, Language language);
}
