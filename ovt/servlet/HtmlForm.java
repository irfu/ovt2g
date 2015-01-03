/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlForm.java,v $
  Date:      $Date: 2003/09/28 17:52:53 $
  Version:   $Revision: 2.4 $


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
 * HtmlForm.java
 *
 * Created on October 25, 2000, 6:44 PM
 */

package ovt.servlet;

import java.beans.*;
import java.util.*;

/**
 *
 * @author  ko
 * @version 
 */
public class HtmlForm extends Object {
    
    protected Vector components = new Vector();
    
    protected String action;
    /** Holds value of property name. */
    protected String name = "";
    /** Creates new HtmlForm */
    public HtmlForm(String action) {
        this.action = action;
    }
    
    public HtmlForm(String action, String name) {
        this.action = action;
        this.name = name;
    }
    
    public void add(HtmlPropertyEditor ed) {
        components.addElement(ed);
    }
    
    public String toString() {
        String res = "<FORM NAME=\""+ getName() +"\" ACTION=\""+ getAction() +
        "\" METHOD=\"POST\">";
        Enumeration e = components.elements();
        HtmlPropertyEditor ed;
        while (e.hasMoreElements()) {
            ed = (HtmlPropertyEditor)e.nextElement();
            res += ed.toString();
        }
        // close the tag
        res+="</FORM>";
        return res;
    }

    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        // set some unique name if no is provided
        if (name.equals("")) name = "form" + Math.abs(new Random().nextInt());
        return name;
    }
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAction() {
        return action;
    }
}
