/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlPanel.java,v $
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
 * HtmlPanel.java
 *
 * Created on October 12, 2000, 3:33 PM
 */

package ovt.servlet;

import ovt.*;
import ovt.util.*;

import java.io.*;
/**
 *
 * @author  ko
 * @version 
 */
public class HtmlPanel extends Object {

    /** Holds value of property core. */
    protected OVTCore core;
    
    /** Holds value of property name. */
    protected String name;
    
    /** Creates new HtmlPanel */
    public HtmlPanel(OVTCore core, String name) {
        this.core = core;
        this.name = name;
    }

    /** Getter for property core.
     * @return Value of property core.
     */
    public OVTCore getCore() {
        return core;
    }
    /** Setter for property core.
     * @param core New value of property core.
     */
    public void setCore(OVTCore core) {
        this.core = core;
    }
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void printHtml(PrintWriter out) {
        Log.log(getName() + " :: printHtml", 5);
        printHeader(out);
        printBody(out);
        printFooter(out);
    }
    
    public void printHeader(PrintWriter out) {
        out.println("<HTML><HEAD><TITLE>" + getName() + "</TITLE></HEAD>");
        out.println("<BODY BGCOLOR=\"#ffffff\">");
    }
    
    public void printBody(PrintWriter out){
        
    }
    
    public void printFooter(PrintWriter out) {
        out.println("</BODY></HTML>");
    }
}
