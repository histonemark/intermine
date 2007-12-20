package org.flymine.web.widget;

/*
 * Copyright (C) 2002-2007 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.intermine.objectstore.query.ConstraintOp;

import org.intermine.metadata.Model;
import org.intermine.objectstore.ObjectStore;
import org.intermine.path.Path;
import org.intermine.web.logic.bag.InterMineBag;
import org.intermine.web.logic.query.Constraint;
import org.intermine.web.logic.query.MainHelper;
import org.intermine.web.logic.query.PathNode;
import org.intermine.web.logic.query.PathQuery;
import org.intermine.web.logic.widget.GraphCategoryURLGenerator;

import org.jfree.data.category.CategoryDataset;

/**
 * 
 * @author Julie Sullivan
 *
 */
public class FlyFishGraphURLGenerator implements GraphCategoryURLGenerator
{
    String bagName;
    
    /**
     * Creates a FlyAtlasGraphURLGenerator for the chart
     * @param bagName the bag name
     */
    public FlyFishGraphURLGenerator(String bagName) {
        super();
        this.bagName = bagName;
    }

    /**
     * {@inheritDoc}
     * @see org.jfree.chart.urls.CategoryURLGenerator#generateURL(
     *      org.jfree.data.category.CategoryDataset,
     *      int, int)
     */
    public String generateURL(CategoryDataset dataset, int series, int category) {
        StringBuffer sb = new StringBuffer("queryForGraphAction.do?bagName=" + bagName);
 
        String seriesName = (String) dataset.getRowKey(series);
        seriesName = seriesName.toLowerCase();
        Boolean expressed = Boolean.FALSE;
        if (seriesName.equals("expressed")) {
            expressed = Boolean.TRUE;
        }

        sb = new StringBuffer("queryForGraphAction.do?bagName=" + bagName);
        sb.append("&category=" + dataset.getColumnKey(category));
        sb.append("&series=" + expressed);
        sb.append("&urlGen=org.flymine.web.widget.FlyFishGraphURLGenerator");
        
        return sb.toString();
    }


    public PathQuery generatePathQuery(ObjectStore os,
                                       InterMineBag bag,
                                       String series, 
                                       String category) {
       
        Model model = os.getModel();
        PathQuery q = new PathQuery(model);
        
        List<Path> view = new ArrayList<Path>();
        view.add(MainHelper.makePath(model, q, "Gene.identifier"));
        view.add(MainHelper.makePath(model, q, "Gene.organismDbId"));
        view.add(MainHelper.makePath(model, q, "Gene.name"));
        view.add(MainHelper.makePath(model, q, "Gene.organism.name"));
        view.add(MainHelper.makePath(model, q, "Gene.mRNALocalisationResults.stage"));
        view.add(MainHelper.makePath(model, q, "Gene.mRNALocalisationResults.expressed"));
        
        q.setView(view);
        
        String bagType = bag.getType();
        ConstraintOp constraintOp = ConstraintOp.IN;
        String constraintValue = bag.getName();
        
        String label = null, id = null, code = q.getUnusedConstraintCode();
        Constraint c = new Constraint(constraintOp, constraintValue, false, label, code, id, null);
        q.addNode(bagType).getConstraints().add(c);
        
        // stage (series)
        constraintOp = ConstraintOp.EQUALS;
        code = q.getUnusedConstraintCode();
        PathNode stageNode = q.addNode("Gene.mRNALocalisationResults.stage");
        Constraint stageConstraint 
                        = new Constraint(constraintOp, series, false, label, code, id, null);
        stageNode.getConstraints().add(stageConstraint);
        
        // expressed (category)
        constraintOp = ConstraintOp.EQUALS;
        Boolean expressed = Boolean.FALSE;
        if (category.equals("true")) {
            expressed = Boolean.TRUE;
        }
        code = q.getUnusedConstraintCode();
        PathNode expressedNode = q.addNode("Gene.mRNALocalisationResults.expressed");
        Constraint expressedConstraint 
                        = new Constraint(constraintOp, expressed, false, label, code, id, null);
        expressedNode.getConstraints().add(expressedConstraint);
        
        q.setConstraintLogic("A and B and C");
        q.syncLogicExpression("and");
        
        return q; 
    }    
}
