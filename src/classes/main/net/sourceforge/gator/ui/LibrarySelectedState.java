package net.sourceforge.gator.ui;

import javax.swing.JButton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LibrarySelectedState
{
    public static final int SELECT_LIBRARY_EVENT = 1;
    public static final int SELECT_CATEGORY_EVENT = 2;
    public static final int SELECT_ITEM_EVENT = 3;

    private static Log log = LogFactory.getLog("net.sourceforge.gator.ui.LibrarySelectedState");

    private static boolean initialised;

    private static LibrarySelected librarySelected;
    private static CategorySelected categorySelected;
    private static ItemSelected itemSelected;

    private JButton addCategoryBtn;
    private JButton addItemBtn;
    private JButton deleteCategoryOrItemBtn;

    private LibrarySelectedState()
    {
        if (!initialised) {
            initialised = true;
            librarySelected = new LibrarySelected();
            categorySelected = new CategorySelected();
            itemSelected = new ItemSelected();
        }
    }

    public static LibrarySelectedState start(JButton categoryBtn, JButton itemBtn, JButton categoryOrItemBtn)
    {
        LibrarySelectedState initState = new LibrarySelectedState();

        initState.addCategoryBtn = categoryBtn;
        initState.addItemBtn = itemBtn;
        initState.deleteCategoryOrItemBtn = categoryOrItemBtn;

        initState.librarySelected.enter();

        return initState.librarySelected;
    }

    protected LibrarySelectedState nextState(int event)
    {
        throw new IllegalAccessError();
    }

    public final LibrarySelectedState processEvent(int event)
    {
        log.trace("Processing event: " + event);

        LibrarySelectedState myNextState = nextState(event);

        log.trace("Next state determined");

        if (this != myNextState) {
            myNextState.enter();
        }

        return myNextState;
    }

    protected void enter()
    {
    }

    private class LibrarySelected extends LibrarySelectedState
    {
        protected LibrarySelectedState nextState(int event)
        {
            log.trace("LibrarySelected nextState fired");

            switch (event) {
                case SELECT_LIBRARY_EVENT:
                    return this;
                case SELECT_CATEGORY_EVENT:
                    return categorySelected;
                case SELECT_ITEM_EVENT:
                    return itemSelected;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter()
        {
            log.trace("LibrarySelected state entered");

            addCategoryBtn.setEnabled(true);
            addItemBtn.setEnabled(false);
            deleteCategoryOrItemBtn.setEnabled(false);
        }
    }

    private class CategorySelected extends LibrarySelectedState
    {
        protected LibrarySelectedState nextState(int event)
        {
            log.trace("CategorySelected nextState fired");

            switch (event) {
                case SELECT_LIBRARY_EVENT:
                    return librarySelected;
                case SELECT_CATEGORY_EVENT:
                    return this;
                case SELECT_ITEM_EVENT:
                    return itemSelected;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter()
        {
            log.trace("CategorySelected state entered");

            addCategoryBtn.setEnabled(true);
            addItemBtn.setEnabled(true);
            deleteCategoryOrItemBtn.setEnabled(true);
        }
    }

    private class ItemSelected extends LibrarySelectedState
    {
        protected LibrarySelectedState nextState(int event)
        {
            log.trace("ItemSelected nextState fired");

            switch (event) {
                case SELECT_LIBRARY_EVENT:
                    return librarySelected;
                case SELECT_CATEGORY_EVENT:
                    return categorySelected;
                case SELECT_ITEM_EVENT:
                    return this;
                default:
                    String msg = "unexpected event " + event;
                    throw new IllegalArgumentException(msg);
            }
        }

        protected void enter()
        {
            log.trace("ItemSelected state entered");

            addCategoryBtn.setEnabled(false);
            addItemBtn.setEnabled(false);
            deleteCategoryOrItemBtn.setEnabled(true);
        }
    }
}
