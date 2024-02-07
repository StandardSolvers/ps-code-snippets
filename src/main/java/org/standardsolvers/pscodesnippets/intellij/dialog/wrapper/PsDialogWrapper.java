package org.standardsolvers.pscodesnippets.intellij.dialog.wrapper;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.standardsolvers.pscodesnippets.core.AlgorithmManager;
import org.standardsolvers.pscodesnippets.core.AlgorithmManagerImplement;
import org.standardsolvers.pscodesnippets.intellij.TitleCaseMacro;
import org.standardsolvers.pscodesnippets.solution.Algorithm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class PsDialogWrapper extends DialogWrapper {

    private JTextField searchTextField;
    private JList<String> algorithmList;


    AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
    private List<Algorithm> allAlgorithms;
    private DefaultListModel<String> listModel;
    public PsDialogWrapper() {
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


        centerPanel.add(new JScrollPane(algorithmList), BorderLayout.CENTER);
        algorithmList.setSelectedIndex(0);


        return centerPanel;
    }

    private void updateAlgorithmList() {
        String searchText = searchTextField.getText().toLowerCase();
        System.out.println("searchText @  =  " + searchText);

        List<String> filteredAlgorithmNames = allAlgorithms.stream()
                .map(Algorithm::getName)
                .filter(className -> className.toLowerCase().contains(searchText))
                .toList();

        String[] array = filteredAlgorithmNames.toArray(new String[0]);
        algorithmList.setListData(array);
        algorithmList.setSelectedIndex(0);
//
    }

    private void refreshAlgorithmList() {
        AlgorithmManager algorithmManager = AlgorithmManagerImplement.getInstance();
        allAlgorithms = algorithmManager.findAll();
        List<String> classNames = allAlgorithms.stream()
                .map(Algorithm::getName)
                .toList();


//        List<String> classNames = allAlgorithms.stream()
//                .map(algorithm -> algorithm.getClass().getSimpleName())
//                .toList();

        String[] array = classNames.toArray(new String[0]);
        algorithmList.setSelectedIndex(0);
        algorithmList.setListData(array);
        SwingUtilities.invokeLater(() -> {
            algorithmList.requestFocusInWindow();
            algorithmList.requestFocus();
        });
    }



    @Override
    protected void doOKAction() {
        System.out.println("ok 버튼 !! ");
        int selectedIndex = algorithmList.getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedAlgorithmName = algorithmList.getModel().getElementAt(selectedIndex);
            System.out.println("Selected Algorithm Name: " + selectedAlgorithmName);
            Algorithm selectedAlgorithm = allAlgorithms.get(selectedIndex);
            System.out.println("selectedAlgorithm = " + selectedAlgorithm);
            String context = selectedAlgorithm.getContext();

            // Create a TitleCaseMacro instance
            TitleCaseMacro titleCaseMacro = new TitleCaseMacro();

            // Create a result based on the selected algorithm's context
            Result result = titleCaseMacro.calculateResult(new Expression[]{new TextExpression(context)}, (ExpressionContext) ExpressionContext.SELECTION, true);

            if (result != null && result instanceof TextResult) {
                // Get the transformed context
                String transformedContext = ((TextResult) result).getText();

                // Print the transformed context
                System.out.println("Transformed Context: " + transformedContext);

                // Now you can use the transformed context as needed
                // For example, you can insert it into the code area of IntelliJ IDEA
                // Or display it in a dialog
            }
        } else {
            // No algorithm selected, handle accordingly
            System.out.println("No algorithm selected");
        }
        super.doOKAction();
    }

}
