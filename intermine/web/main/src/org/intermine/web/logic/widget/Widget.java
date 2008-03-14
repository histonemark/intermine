package org.intermine.web.logic.widget;

import java.util.Collection;

import org.intermine.objectstore.ObjectStore;
import org.intermine.web.logic.bag.InterMineBag;

/*
 * Copyright (C) 2002-2008 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

/**
 * Class representing a Widget in the WebApp
 * @author "Xavier Watkins"
 */
public abstract class Widget
{
    private int id;
    private String description;
    private String title;
    private String dataSetLoader;
    private String link;
    private String selectedExtraAttribute;
    
    /**
     * The Constructor
     */
    public Widget() {
        super();
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get the value of dataSetLoader
     * @return the value of dataSetLoader
     */
    public String getDataSetLoader() {
        return dataSetLoader;
    }


    /**
     * Set the value of dataSetLoader
     * @param dataSetLoader a String
     */
    public void setDataSetLoader(String dataSetLoader) {
        this.dataSetLoader = dataSetLoader;
    }
    
    /**
     * Get the link
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Set the link
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }
    
    /**
     * Process the data and create the widget
     * 
     * @param imBag the InterMineBag
     * @param os the ObjectStore
     */
    public abstract void process(InterMineBag imBag, ObjectStore os);

    /**
     * @return the getExtraAttributes
     */
    public abstract Collection getExtraAttributes(InterMineBag imBag, ObjectStore os) throws Exception;

    /**
     * @return the selectedExtraAttribute
     */
    public String getSelectedExtraAttribute() {
        return selectedExtraAttribute;
    }

    /**
     * @param selectedExtraAttribute the selectedExtraAttribute to set
     */
    public void setSelectedExtraAttribute(String selectedExtraAttribute) {
        this.selectedExtraAttribute = selectedExtraAttribute;
    }

}
