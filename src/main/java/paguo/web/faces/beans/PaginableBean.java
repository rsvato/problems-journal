package net.paguo.web.faces.beans;

import javax.faces.component.UIData;

/**
 * User: slava
 * Date: 21.12.2006
 * Time: 1:40:07
 * Version: $Id$
 */
public class PaginableBean {
    protected UIData itemsTable;
    protected static final Integer PAGE_SIZE = 5;

    public UIData getItemsTable() {
        return itemsTable;
    }

    public void setItemsTable(UIData itemsTable) {
        this.itemsTable = itemsTable;
    }

    public void turnPage(){
        int currentPosition = itemsTable.getFirst() + PAGE_SIZE;
        itemsTable.setFirst(currentPosition);
    }

    public void firstPage(){
        itemsTable.setFirst(0);
    }

    public void lastPage(){
        int count = itemsTable.getRowCount();
        int currentPosition = count - (count % PAGE_SIZE);
        itemsTable.setFirst(currentPosition);
    }

    public void previousPage(){
        int currentPosition = itemsTable.getFirst() - PAGE_SIZE;
        itemsTable.setFirst(currentPosition);
    }

    public boolean isHasNext(){
        return itemsTable.getFirst() < itemsTable.getRowCount() - PAGE_SIZE;
    }

    public boolean isHasPrevious(){
        return itemsTable.getFirst() >= PAGE_SIZE;
    }

    public int getPageSize(){
        return PAGE_SIZE;
    }
}
