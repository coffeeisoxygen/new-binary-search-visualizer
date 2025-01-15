package com.coffeecode;

import com.coffeecode.model.Dictionary;
import com.coffeecode.model.IDictionary;
import com.coffeecode.search.BinarySearch;
import com.coffeecode.search.SearchStrategy;
import com.coffeecode.services.file.FileService;
import com.coffeecode.services.file.IFileService;
import com.coffeecode.services.json.IJsonService;
import com.coffeecode.services.json.JsonService;
import com.coffeecode.viewmodel.DictionaryViewModel;

public class DictionaryManager {

    private final IJsonService jsonService;
    private final IFileService fileService;
    private final IDictionary dictionary;
    private final SearchStrategy searchStrategy;
    private final DictionaryViewModel viewModel;

    private DictionaryManager(Builder builder) {
        this.jsonService = builder.jsonService;
        this.fileService = builder.fileService;
        this.searchStrategy = builder.searchStrategy;
        this.dictionary = builder.dictionary;
        this.viewModel = new DictionaryViewModel(dictionary);
    }

    public static class Builder {

        private IJsonService jsonService = new JsonService();
        private IFileService fileService;
        private SearchStrategy searchStrategy = new BinarySearch();
        private IDictionary dictionary;
        private String dictionaryPath = "src/main/resources/vocabulary.json";
        private long maxFileSize = 10 * 1024 * 1024;

        public Builder withDictionaryPath(String path) {
            this.dictionaryPath = path;
            return this;
        }

        public Builder withMaxFileSize(long size) {
            this.maxFileSize = size;
            return this;
        }

        public Builder withJsonService(IJsonService service) {
            this.jsonService = service;
            return this;
        }

        public Builder withFileService(IFileService service) {
            this.fileService = service;
            return this;
        }

        public Builder withSearchStrategy(SearchStrategy strategy) {
            this.searchStrategy = strategy;
            return this;
        }

        public DictionaryManager build() {
            this.fileService = fileService != null ? fileService
                    : new FileService(dictionaryPath, maxFileSize, jsonService);
            this.dictionary = new Dictionary(searchStrategy, fileService);
            return new DictionaryManager(this);
        }
    }

    public DictionaryViewModel getViewModel() {
        return viewModel;
    }

    public IDictionary getDictionary() {
        return dictionary;
    }
}
