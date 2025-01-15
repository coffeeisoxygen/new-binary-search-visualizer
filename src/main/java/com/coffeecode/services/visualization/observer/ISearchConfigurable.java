package com.coffeecode.services.visualization.observer;

public interface ISearchConfigurable {

    /**
     * Configures the search visualization observer.
     *
     * @param observer The observer to receive search process notifications
     */
    void configureSearch(SearchObserver observer);
}
