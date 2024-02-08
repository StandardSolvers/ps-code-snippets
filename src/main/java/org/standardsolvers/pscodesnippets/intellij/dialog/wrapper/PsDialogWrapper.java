package org.standardsolvers.pscodesnippets.intellij.dialog.wrapper;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.standardsolvers.pscodesnippets.core.PsManager;
import org.standardsolvers.pscodesnippets.core.PsManagerImplement;
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
    private String context;
    // 다른 코드들

    public void setContext(String context) {
        this.context = context;
    }

    // 선택된 PS의 컨텍스트를 반환하는 메서드
    public String getContext() {
        return context;
    }
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
        int selectedIndex = psList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Ps selectedPs = allPss.get(selectedIndex);
            System.out.println("selectedPs = " + selectedPs);
            String context = selectedPs.getContext();
            setContext(context);
        } else {
            // No ps selected, handle accordingly
            System.out.println("No ps selected");
        }
        super.doOKAction();
    }

}
