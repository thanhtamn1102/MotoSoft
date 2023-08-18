package com.example.fashionshop.cus_comp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is a TextField which implements an "autocomplete" functionality,
 * based on a supplied list of entries.<p>
 *
 * If the entered text matches a part of any of the supplied entries these are
 * going to be displayed in a popup. Further the matching part of the entry is
 * going to be displayed in a special style, defined by
 * {@link #textOccurenceStyle textOccurenceStyle}. The maximum number of
 * displayed entries in the popup is defined by
 * {@link #maxEntries maxEntries}.<br>
 * By default the pattern matching is not case-sensitive. This behaviour is
 * defined by the {@link #caseSensitive caseSensitive}
 * .<p>
 *
 * The AutoCompleteTextField also has a List of
 * {@link #filteredEntries filteredEntries} that is equal to the search results
 * if search results are not empty, or {@link #filteredEntries filteredEntries}
 * is equal to {@link #entries entries} otherwise. If
 * {@link #popupHidden popupHidden} is set to true no popup is going to be
 * shown. This list can be used to bind all entries to another node (a ListView
 * for example) in the following way:
 * <pre>
 * <code>
 * AutoCompleteTextField auto = new AutoCompleteTextField(entries);
 * auto.setPopupHidden(true);
 * SimpleListProperty filteredEntries = new SimpleListProperty(auto.getFilteredEntries());
 * listView.itemsProperty().bind(filteredEntries);
 * </code>
 * </pre>
 *
 * @author Caleb Brinkman
 * @author Fabian Ochmann
 * @param <S>
 */
public class AutoCompleteTextField<S> extends TextField
{

    private final ObjectProperty<S> lastSelectedItem = new SimpleObjectProperty<>();

    /**
     * The existing autocomplete entries.
     */
    private final ObservableList<S> entries;

    /**
     * The set of filtered entries:<br>
     * Equal to the search results if search results are not empty, equal to
     * {@link #entries entries} otherwise.
     */
    private ObservableList<S> filteredEntries
            = FXCollections.observableArrayList();

    /**
     * The popup used to select an entry.
     */
    private ContextMenu entriesPopup;
    private CustomMenuItem menuItem;

    /**
     * Indicates whether the search is case sensitive or not. <br>
     * Default: false
     */
    private boolean caseSensitive = false;

    /**
     * Indicates whether the Popup should be hidden or displayed. Use this if
     * you want to filter an existing list/set (for example values of a
     * {@link ListView ListView}). Do this by binding
     * {@link #getFilteredEntries() getFilteredEntries()} to the list/set.
     */
    private boolean popupHidden = false;

    /**
     * The CSS style that should be applied on the parts in the popup that match
     * the entered text. <br>
     * Default: "-fx-font-weight: bold; -fx-fill: red;"
     * <p>
     * Note: This style is going to be applied on an
     * {@link Text Text} instance. See the <i>JavaFX CSS
     * Reference Guide</i> for available CSS Propeties.
     */
    private String textOccurenceStyle = "-fx-font-weight: bold; "
            + "-fx-fill: red;";

    /**
     * The maximum Number of entries displayed in the popup.<br>
     * Default: 10
     */
    private int maxEntries = 5;

    /**
     * Construct a new AutoCompleteTextField.
     *
     * @param entrySet
     */
    public AutoCompleteTextField(ObservableList<S> entrySet)
    {
        super();

        this.entries = (entrySet == null ? FXCollections.observableArrayList() : entrySet);
        this.filteredEntries.addAll(entries);

        entriesPopup = new ContextMenu();
        entriesPopup.getStyleClass().add("product-search-view");

        textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String newValue) ->
        {

            if (getText() == null || getText().length() == 0) {
                filteredEntries.clear();
                filteredEntries.addAll(entries);
                entriesPopup.hide();
            }
            else {
                LinkedList<S> searchResult = new LinkedList<>();
                //Check if the entered Text is part of some entry
//                String text1 = getText();
//                Pattern pattern;
//                if (isCaseSensitive())
//                {
//                    pattern = Pattern.compile(".*" + text1 + ".*");
//                } else
//                {
//                    pattern = Pattern.compile(".*" + text1 + ".*", Pattern.CASE_INSENSITIVE);
//                }
                for (S entry : entries)
                {
//                    Matcher matcher = pattern.matcher(entry.toString());
//                    if (matcher.matches())
//                    {
//                        searchResult.add(entry);
//                    }
                    if(entry.toString().toLowerCase().contains(newValue.toLowerCase())) {
                        searchResult.add(entry);
                    }
                }
                if (!entries.isEmpty())
                {
                    filteredEntries.clear();
                    filteredEntries.addAll(searchResult);
                    //Only show popup if not in filter mode
                    if (!isPopupHidden())
                    {
                        populatePopup(searchResult, newValue);
                        if (!entriesPopup.isShowing()) {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    }
                } else
                {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) ->
        {
            entriesPopup.hide();
        });

    }

    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public ObservableList<S> getEntries()
    {
        return entries;
    }

    /**
     * Populate the entry set with the given search results. Display is limited
     * to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<S> searchResult, String text) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int count = Math.min(searchResult.size(), getMaxEntries());
        for (int i = 0; i < count; i++)
        {
            final S itemObject = searchResult.get(i);

            Node node = updateItem(itemObject, text);
            CustomMenuItem item = new CustomMenuItem(node, true);
            item.setOnAction((ActionEvent actionEvent) ->
            {
                lastSelectedItem.set(itemObject);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    public Node updateItem(S object, String text) {
        String result = object.toString();
        int occurence;

        if (isCaseSensitive())
            occurence = result.indexOf(text);
        else
            occurence = result.toLowerCase().indexOf(text.toLowerCase());

        Text pre = new Text(result.substring(0, occurence));
        Text in = new Text(result.substring(occurence, occurence + text.length()));
        in.setStyle(getTextOccurenceStyle());
        Text post = new Text(result.substring(occurence + text.length(), result.length()));

        TextFlow entryFlow = new TextFlow(pre, in, post);
        entryFlow.setStyle("-fx-font-family: " + getFont().getName() + "; -fx-font-size: " + (getFont().getSize()) + ";");

        return entryFlow;
    };

    public S getLastSelectedObject()
    {
        return lastSelectedItem.get();
    }

    public ContextMenu getEntryMenu()
    {
        return entriesPopup;
    }

    public boolean isCaseSensitive()
    {
        return caseSensitive;
    }

    public String getTextOccurenceStyle()
    {
        return textOccurenceStyle;
    }

    public void setCaseSensitive(boolean caseSensitive)
    {
        this.caseSensitive = caseSensitive;
    }

    public void setTextOccurenceStyle(String textOccurenceStyle)
    {
        this.textOccurenceStyle = textOccurenceStyle;
    }

    public boolean isPopupHidden()
    {
        return popupHidden;
    }

    public void setPopupHidden(boolean popupHidden)
    {
        this.popupHidden = popupHidden;
    }

    public ObservableList<S> getFilteredEntries()
    {
        return filteredEntries;
    }

    public int getMaxEntries()
    {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries)
    {
        this.maxEntries = maxEntries;
    }
}
