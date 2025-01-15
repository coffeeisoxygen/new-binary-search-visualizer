package com.coffeecode.builder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffeecode.exception.DictionaryException;
import com.coffeecode.exception.ErrorCode;
import com.coffeecode.model.Dictionary;
import com.coffeecode.model.Vocabulary;
import com.coffeecode.services.dictionary.DictionaryService;
import com.coffeecode.services.file.FileService;
import com.coffeecode.services.file.IFileService;
import com.coffeecode.services.json.IJsonService;
import com.coffeecode.services.json.JsonService;
import com.coffeecode.services.search.ISearchService;
import com.coffeecode.services.search.SearchService;
import com.coffeecode.services.search.strategy.BinarySearch;
import com.coffeecode.services.sort.ISortService;
import com.coffeecode.services.sort.SortService;
import com.coffeecode.services.visualization.observer.DefaultSearchObserver;
import com.coffeecode.services.visualization.observer.SearchObserver;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class DictionaryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryBuilder.class);
    private String dictionaryPath = "src/main/resources/vocabulary.json";
    private long maxFileSize = 10 * 1024 * 1024;
    private ISearchService searchService;
    private ISortService sortService;
    private IFileService fileService;
    private IJsonService jsonService;
    private SearchObserver searchObserver = new DefaultSearchObserver();

    public DictionaryBuilder withDictionaryPath(String path) {
        this.dictionaryPath = path;
        return this;
    }

    public DictionaryBuilder withMaxFileSize(long size) {
        this.maxFileSize = size;
        return this;
    }

    public DictionaryBuilder withServices(
            ISearchService searchService,
            ISortService sortService,
            IFileService fileService,
            IJsonService jsonService) {
        this.searchService = searchService;
        this.sortService = sortService;
        this.fileService = fileService;
        this.jsonService = jsonService;
        return this;
    }

    public DictionaryBuilder withSearchObserver(SearchObserver observer) {
        this.searchObserver = observer != null ? observer : new DefaultSearchObserver();
        return this;
    }

    public DictionaryViewModel build() {
        try {
            validateConfiguration();
            initializeServices();

            List<Vocabulary> vocabularies = loadVocabularies();
            Dictionary dictionary = createDictionary(vocabularies);
            DictionaryService service = new DictionaryService(dictionary);

            logger.info("Successfully built dictionary with {} entries", vocabularies.size());
            return new DictionaryViewModel(service);
        } catch (Exception e) {
            logger.error("Failed to build dictionary: {}", e.getMessage());
            throw new DictionaryException(ErrorCode.INITIALIZATION_ERROR,
                    "Failed to initialize dictionary: " + e.getMessage());
        }
    }

    private void validateConfiguration() {
        if (dictionaryPath == null || dictionaryPath.trim().isEmpty()) {
            throw new DictionaryException(ErrorCode.INVALID_CONFIGURATION,
                    "Dictionary path cannot be empty");
        }
        if (maxFileSize <= 0) {
            throw new DictionaryException(ErrorCode.INVALID_CONFIGURATION,
                    "Max file size must be positive");
        }
    }

    private List<Vocabulary> loadVocabularies() {
        var file = fileService.getDefaultDictionaryFile();
        return jsonService.parseVocabularyFile(file);
    }

    private Dictionary createDictionary(List<Vocabulary> vocabularies) {
        return new Dictionary(searchService, vocabularies, sortService);
    }

    private void initializeServices() {
        this.jsonService = jsonService != null ? jsonService : new JsonService();
        this.fileService = fileService != null ? fileService
                : new FileService(dictionaryPath, maxFileSize);
        this.searchService = searchService != null ? searchService
                : createSearchService();
        this.sortService = sortService != null ? sortService : new SortService();
    }

    private SearchService createSearchService() {
        BinarySearch binarySearch = new BinarySearch(searchObserver);
        return new SearchService(binarySearch);
    }
}
