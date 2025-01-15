package com.coffeecode.services.visualization.terminal;

import java.util.List;
import java.util.Scanner;

import com.coffeecode.model.Language;
import com.coffeecode.services.search.result.SearchResult;

public class TerminalHandler {

    private static final Scanner scanner = new Scanner(System.in);
    private final TerminalFormatter formatter;

    public TerminalHandler(TerminalFormatter formatter) {
        this.formatter = formatter;
    }

    public void displayWelcomeScreen(List<String> englishWords, List<String> indonesianWords) {
        System.out.println(formatter.formatWelcomeScreen(englishWords, indonesianWords));
    }

    public String getSearchWord() {
        while (true) {
            System.out.print(formatter.formatInputPrompt());
            String input = scanner.nextLine().trim();

            if (isQuitCommand(input)) {
                return "quit";
            }
            if (isValidWord(input)) {
                return input;
            }

            displayError("Invalid input. Use only letters, spaces, and hyphens.");
        }
    }

    public Language getSearchLanguage() {
        while (true) {
            System.out.println(formatter.formatLanguagePrompt());
            String choice = scanner.nextLine().trim();

            if (isQuitCommand(choice)) {
                return null;
            }
            if (choice.equals("1")) {
                return Language.ENGLISH;
            }
            if (choice.equals("2")) {
                return Language.INDONESIAN;
            }

            displayError("Invalid choice. Enter 1 for English or 2 for Indonesian.");
        }
    }

    private boolean isQuitCommand(String input) {
        return "quit".equalsIgnoreCase(input);
    }

    private boolean isValidWord(String input) {
        return input.matches("[a-zA-Z\\s-]+");
    }

    private void displayError(String message) {
        System.out.println(formatter.formatError(message));
    }

    public void displaySearchResult(SearchResult result, int comparisons, double timeMs) {
        System.out.println(formatter.formatSearchSummary(result, comparisons, timeMs));
    }
}
