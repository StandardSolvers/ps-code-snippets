package org.standardsolvers.pscodesnippets.intellij.dialog;

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
    private List<Ps> allPss;
    private String context;

    public void setContext(String context) {
        this.context = context;
    }

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
        JPanel centerPanel = new JPanel(new BorderLayout());
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
        refreshPsList();
        centerPanel.add(new JScrollPane(psList), BorderLayout.CENTER);
        psList.setSelectedIndex(0);
        SwingUtilities.invokeLater(() -> {
            psList.requestFocusInWindow();
            psList.requestFocus();
        });
        return centerPanel;
    }

    private void updatePsList() {
        String searchText = searchTextField.getText().toLowerCase();
        List<String> filteredPsNames = allPss.stream()
                .map(Ps::getName)
                .filter(className -> className.toLowerCase().contains(searchText))
                .toList();

        String[] array = filteredPsNames.toArray(new String[0]);
        psList.setListData(array);
        psList.setSelectedIndex(0);

    }

    private void refreshPsList() {
        PsManager psManager = PsManagerImplement.getInstance();
        allPss = psManager.findAll();
        List<String> classNames = allPss.stream()
                .map(Ps::getSimpleName)
                .toList();

        String[] array = classNames.toArray(new String[0]);
        psList.setSelectedIndex(0);
        psList.setListData(array);
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
            System.out.println("No ps selected");
        }
        super.doOKAction();
    }

}
