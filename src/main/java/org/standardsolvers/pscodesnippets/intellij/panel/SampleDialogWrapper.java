package org.standardsolvers.pscodesnippets.intellij.panel;

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
        algorithmList.setSelectedIndex(0);
        // You can add more components to the centerPanel as neede

        return centerPanel;
    }

    private void updateAlgorithmList() {
        String searchText = searchTextField.getText().toLowerCase();
        System.out.println("searchText @  =  " + searchText);

        List<String> filteredAlgorithmNames = allAlgorithms.stream()
                .map(algorithm -> algorithm.getClass().getSimpleName())
                .filter(className -> className.toLowerCase().contains(searchText))
                .toList();

        String[] array = filteredAlgorithmNames.toArray(new String[0]);
        algorithmList.setListData(array);
        algorithmList.setSelectedIndex(0);
//        listModel.clear();
//        for (String algorithmName : filteredAlgorithmNames) {
//            listModel.addElement(algorithmName);
//        }
    }

    private void refreshAlgorithmList() {
        AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
        allAlgorithms = algorithmManager.findAll();
        List<String> classNames = allAlgorithms.stream()
                .map(algorithm -> algorithm.getClass().getSimpleName())
                .toList();

        String[] array = classNames.toArray(new String[0]);
        algorithmList.setListData(array);
    }

    @Override
    protected void doOKAction() {
        System.out.println("ok 버튼 ");
        int selectedIndex = algorithmList.getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedAlgorithmName = algorithmList.getModel().getElementAt(selectedIndex);
            // Output the selected algorithm's name
            System.out.println("Selected Algorithm Name: " + selectedAlgorithmName);

            // Do something with the selected algorithm (e.g., pass it to another method)
            // ...
        } else {
            // No algorithm selected, handle accordingly
            System.out.println("No algorithm selected");
        }
        super.doOKAction();
    }

}
