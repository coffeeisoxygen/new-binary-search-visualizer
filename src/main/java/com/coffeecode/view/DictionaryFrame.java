// package com.coffeecode.view;

// import java.awt.BorderLayout;
// import java.awt.FlowLayout;

// import javax.swing.BoxLayout;
// import javax.swing.JButton;
// import javax.swing.JComboBox;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTextField;

// import com.coffeecode.model.Language;
// import com.coffeecode.tracker.SearchResult;
// import com.coffeecode.tracker.SearchStep;
// import com.coffeecode.viewmodel.DictionaryViewModel;

// public class DictionaryFrame extends JFrame {
//     private final DictionaryViewModel viewModel;
//     private JTextField searchField;
//     private JComboBox<Language> languageComboBox;
//     private JPanel stepsPanel;
//     private JLabel resultLabel;

//     public DictionaryFrame(DictionaryViewModel viewModel) {
//         this.viewModel = viewModel;
//         initializeUI();
//     }

//     private void initializeUI() {
//         setTitle("Dictionary Search Visualizer");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout(10, 10));

//         // Search Panel
//         JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//         searchField = new JTextField(20);
//         languageComboBox = new JComboBox<>(Language.values());
//         JButton searchButton = new JButton("Search");

//         searchPanel.add(searchField);
//         searchPanel.add(languageComboBox);
//         searchPanel.add(searchButton);

//         // Results Panel
//         stepsPanel = new JPanel();
//         stepsPanel.setLayout(new BoxLayout(stepsPanel, BoxLayout.Y_AXIS));
//         JScrollPane scrollPane = new JScrollPane(stepsPanel);
//         resultLabel = new JLabel();

//         // Add components
//         add(searchPanel, BorderLayout.NORTH);
//         add(scrollPane, BorderLayout.CENTER);
//         add(resultLabel, BorderLayout.SOUTH);

//         // Add listeners
//         searchButton.addActionListener(e -> performSearch());

//         // Set window properties
//         setSize(600, 400);
//         setLocationRelativeTo(null);
//     }

//     private void performSearch() {
//         String word = searchField.getText();
//         Language language = (Language) languageComboBox.getSelectedItem();

//         try {
//             SearchResult result = viewModel.search(word, language);
//             displaySearchResult(result);
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(this,
//                     e.getMessage(), "Error",
//                     JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     private void displaySearchResult(SearchResult result) {
//         stepsPanel.removeAll();

//         for (SearchStep step : result.getSteps()) {
//             stepsPanel.add(createStepPanel(step));
//         }

//         resultLabel.setText(result.isFound() ? "Found: " + result.getResult() : "Word not found!");

//         stepsPanel.revalidate();
//         stepsPanel.repaint();
//     }

//     private JPanel createStepPanel(SearchStep step) {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//         panel.add(new JLabel(String.format(
//                 "Low: %d, Mid: %d, High: %d, Word: %s",
//                 step.low(), step.mid(), step.high(), step.currentWord())));
//         return panel;
//     }
// }
