package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.intermine.dataconversion.MockItemWriter;
import org.intermine.dataconversion.MockItemsTestCase;
import org.intermine.metadata.Model;

public class KeggOrthologuesConverterTest extends MockItemsTestCase
{
    Model model = Model.getInstanceByName("genomic");
    KeggOrthologuesConverter converter;
    MockItemWriter itemWriter;

    public KeggOrthologuesConverterTest(String arg) {
        super(arg);
    }

    public void setUp() throws Exception {
        super.setUp();
        itemWriter = new MockItemWriter(new HashMap());
        converter = new KeggOrthologuesConverter(itemWriter, model);
        MockIdResolverFactory resolverFactory = new MockIdResolverFactory("Gene");
        resolverFactory.addResolverEntry("7227", "FBgn001", Collections.singleton("CG18814"));
        resolverFactory.addResolverEntry("7227", "FBgn002", Collections.singleton("CG3481"));
        resolverFactory.addResolverEntry("7227", "FBgn003", Collections.singleton("CG3763"));
        converter.resolverFactory = resolverFactory;
    }

    public void testProcess() throws Exception {
        

        ClassLoader loader = getClass().getClassLoader();
        String input = IOUtils.toString(loader.getResourceAsStream("KeggOrthologuesConverterTest_src.txt"));

        converter.setKeggOrganisms("7227 9606 4932 7955 10090 10116 6239");
        converter.process(new StringReader(input));
        converter.close();

        // uncomment to write out a new target items file
        //writeItemsFile(itemWriter.getItems(), "kegg-tgt-items.xml");

        Set expected = readItemSet("KeggOrthologuesConverterTest_tgt.xml");
        assertEquals(expected, itemWriter.getItems());
    }
}
