package testing;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;

public class FormController {

    private static final String SELECTED_VALUE = "yes" ;
    private static final String UNSELECTED_VALUE = "no" ;

    @FXML
    private GridPane form ;

    private final Map<String, Control> controls = new HashMap<>();
    private final List<String> ids = new ArrayList<>();

    public void initialize() {
        for (Node node : form.getChildren()) {
            if (GridPane.getColumnIndex(node) == 1) { // all form controls are in column 1
                if (node instanceof Control) {
                    String id = node.getId();
                    controls.put(id, (Control)node);
                    ids.add(id);
                }
            }
        }
    }

    public List<String> getIds() {
        return Collections.unmodifiableList(ids);
    }

    public String getUserValue(String id) throws ReflectiveOperationException {
        Control control = controls.get(id);
        if (control == null) throw new IllegalArgumentException("No control with id "+id);
        return getValueForControl(control);
    }

    private String getValueForControl(Control control) throws ReflectiveOperationException {
        if (isTextControl(control)) {
            return getTextControlValue(control);
        } else if (isSelectable(control)) {
            return getSelectableValue(control);
        } else if (hasSelectionModel(control)) {
            return getSelectedValue(control);
        }
        throw new IllegalArgumentException("Unsupported control class: "+control.getClass().getName());
    }

    private boolean isTextControl(Control control) {
        // TextAreas, TextFields, etc:
        return control instanceof TextInputControl ;
    }

    private String getTextControlValue(Control control) {
        return ((TextInputControl) control).getText();
    }

    private boolean isSelectable(Control control) {
        // ToggleButtons, CheckBoxes...
        for (Method method :  control.getClass().getMethods()) {
            if (method.getName().equals("isSelected") 
                    && method.getReturnType() == boolean.class) {
                return true ;
            }
        }
        return false ;
    }

    private String getSelectableValue(Control control) throws ReflectiveOperationException {
        Method isSelectedMethod = control.getClass().getMethod("isSelected");
        boolean selected = (Boolean) isSelectedMethod.invoke(control);
        if (selected) {
            return SELECTED_VALUE ;
        } else {
            return UNSELECTED_VALUE ;
        }
    }

    private boolean hasSelectionModel(Control control) {
        // ComboBoxes, ListViews, TableViews, etc:
        for (Method method : control.getClass().getMethods()) {
            if (method.getName().equals("getSelectionModel")) {
                return true ;
            }
        }
        return false ;
    }

    private String getSelectedValue(Control control) throws ReflectiveOperationException  {
        Method selectionModelMethod = control.getClass().getMethod("getSelectionModel");
        SelectionModel<?> selectionModel = (SelectionModel<?>) selectionModelMethod.invoke(control);
        Object selectedItem = selectionModel.getSelectedItem();
        if (selectedItem == null) {
            return "" ;
        } else {
            return selectedItem.toString();
        }
    }
}