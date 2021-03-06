/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/OVTTree.java,v $
  Date:      $Date: 2003/09/28 17:52:54 $
  Version:   $Revision: 1.3 $


Copyright (c) 2000-2003 OVT Team (Kristof Stasiewicz, Mykola Khotyaintsev,
Yuri Khotyaintsev)
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification is permitted provided that the following conditions are met:

 * No part of the software can be included in any commercial package without
written consent from the OVT team.

 * Redistributions of the source or binary code must retain the above
copyright notice, this list of conditions and the following disclaimer.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS
IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHORS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT OR
INDIRECT DAMAGES  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE.

OVT Team (http://ovt.irfu.se)   K. Stasiewicz, M. Khotyaintsev, Y.
Khotyaintsev

=========================================================================*/


/*
 * OVTTree.java
 *
 * Created on June 27, 2001, 3:07 AM
 */

package ovt.servlet;

import java.util.*;

/**
 *
 * @author  root
 * @version 
 */
public class OVTTree extends Object {

    private Vector expandedNodes = new Vector();
    
    /** Creates new OVTTree */
    public OVTTree() {
    }

    /** Getter for property expanded.
     * @return Value of property expanded.
 */
    public boolean isExpanded(String nodePath) {
        return expandedNodes.contains(nodePath);
    }
    
    /** Setter for property expanded.
     * @param expanded New value of property expanded.
 */
    public void setExpanded(String nodePath, boolean expanded) {
        if (expanded) expandedNodes.addElement(nodePath);
        else expandedNodes.removeElement(nodePath);
    }
    
}
