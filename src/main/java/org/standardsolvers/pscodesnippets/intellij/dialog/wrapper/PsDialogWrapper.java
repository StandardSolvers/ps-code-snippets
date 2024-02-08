package org.standardsolvers.pscodesnippets.intellij.dialog.wrapper;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.standardsolvers.pscodesnippets.core.PsManager;
import org.standardsolvers.pscodesnippets.core.PsManagerImplement;
import org.standardsolvers.pscodesnippets.intellij.TitleCaseMacro;
import org.standardsolvers.pscodesnippets.solution.Ps;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class PsDialogWrapper extends DialogWrapper {

    private JTextField searchTextField;
    private JList<String> psList;


    PsManager psManager = PsManagerImplement.getInstance();
    private List<Ps> allPss;
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

        psList = new JList<>();
        psList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePsList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePsList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components do not fire these events
            }
        });
        // Fetch and display the ps names
        refreshPsList();


        centerPanel.add(new JScrollPane(psList), BorderLayout.CENTER);
        psList.setSelectedIndex(0);


        return centerPanel;
    }

    private void updatePsList() {
        String searchText = searchTextField.getText().toLowerCase();
        System.out.println("searchText @  =  " + searchText);

        List<String> filteredPsNames = allPss.stream()
                .map(Ps::getName)
                .filter(className -> className.toLowerCase().contains(searchText))
                .toList();

        String[] array = filteredPsNames.toArray(new String[0]);
        psList.setListData(array);
        psList.setSelectedIndex(0);
//
    }

    private void refreshPsList() {
        PsManager psManager = PsManagerImplement.getInstance();
        allPss = psManager.findAll();
        List<String> classNames = allPss.stream()
                .map(Ps::getName)
                .toList();


//        List<String> classNames = allPss.stream()
//                .map(ps -> ps.getClass().getSimpleName())
//                .toList();

        String[] array = classNames.toArray(new String[0]);
        psList.setSelectedIndex(0);
        psList.setListData(array);
        SwingUtilities.invokeLater(() -> {
            psList.requestFocusInWindow();
            psList.requestFocus();
        });
    }



    @Override
    protected void doOKAction() {
        System.out.println("ok 버튼 !! ");
        int selectedIndex = psList.getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedPsName = psList.getModel().getElementAt(selectedIndex);
            System.out.println("Selected Ps Name: " + selectedPsName);
            Ps selectedPs = allPss.get(selectedIndex);
            System.out.println("selectedPs = " + selectedPs);
            String context = selectedPs.getContext();

            // Create a TitleCaseMacro instance
            TitleCaseMacro titleCaseMacro = new TitleCaseMacro();

            // Create a result based on the selected ps's context
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
            // No ps selected, handle accordingly
            System.out.println("No ps selected");
        }
        super.doOKAction();
    }

}
