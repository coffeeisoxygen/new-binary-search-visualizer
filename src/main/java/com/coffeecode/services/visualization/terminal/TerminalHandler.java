package com.coffeecode.services.visualization.terminal;

import java.util.List;
import java.util.Scanner;

import com.coffeecode.model.Language;

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

            if ("quit".equalsIgnoreCase(input)) {
                return input;
            }
            if (input.matches("[a-zA-Z\\s-]+")) {
                return input;
            }

            System.out.println(formatter.formatError("Invalid input. Use only letters, spaces, and hyphens."));
        }
    }

    public Language getSearchLanguage() {
        while (true) {
            System.out.println(formatter.formatLanguagePrompt());
            String choice = scanner.nextLine().trim();

            if ("quit".equalsIgnoreCase(choice)) {
                return null;
            }
            if ("1".equals(choice)) {
                return Language.ENGLISH;
            }
            if ("2".equals(choice)) {
                return Language.INDONESIAN;
            }

            System.out.println(formatter.formatError("Invalid choice. Enter 1 for English or 2 for Indonesian."));
        }
    }
}
