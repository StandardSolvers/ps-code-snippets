package org.standardsolvers.pscodesnippets.panel;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.core.AlgorithmManagerImplement;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class SampleDialogWrapper extends DialogWrapper {

    private JTextField searchTextField;
    private JList<String> algorithmList;
    AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
    private List<Algorithm> allAlgorithms;
    private DefaultListModel<String> listModel;
    protected SampleDialogWrapper() {
        super(true);
        setTitle("PS 코드 목록");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        // Create a panel with BorderLayout
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Create a search text field
        searchTextField = new JTextField();
        centerPanel.add(searchTextField, BorderLayout.NORTH);

        algorithmList = new JList<>();
        algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAlgorithmList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAlgorithmList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components do not fire these events
            }
        });
        // Fetch and display the algorithm names
        refreshAlgorithmList();

        // Add the JList to the center panel
        centerPanel.add(new JScrollPane(algorithmList), BorderLayout.CENTER);

        // You can add more components to the centerPanel as neede

        return centerPanel;
    }

    private void updateAlgorithmList() {
        String searchText = searchTextField.getText().toLowerCase();

        List<String> filteredAlgorithmNames = allAlgorithms.stream()
                .map(algorithm -> algorithm.getClass().getSimpleName())
                .filter(className -> className.toLowerCase().contains(searchText))
                .collect(Collectors.toList());

//        listModel.clear();
//        for (String algorithmName : filteredAlgorithmNames) {
//            listModel.addElement(algorithmName);
//        }
    }

    private void refreshAlgorithmList() {
        allAlgorithms = algorithmManager.findAll();
        updateAlgorithmList();
    }

    @Override
    protected void doOKAction() {
        // Get the search text from the text field
        String searchText = searchTextField.getText().toLowerCase();

        // Filter algorithms based on the search text
        List<Algorithm> filteredAlgorithms = allAlgorithms.stream()
                .filter(algorithm -> algorithm.getClass().getSimpleName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Do something with the filtered algorithms (e.g., pass them to another method)
        // ...

        // Close the dialog
        super.doOKAction();
    }

    public String getSearchText() {
        // Getter method to retrieve the text from the searchTextField
        return searchTextField.getText();
    }
}
