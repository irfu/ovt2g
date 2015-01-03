/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlTextAreaEditor.java,v $
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
 * HtmlTextAreaEditor.java
 *
 * Created on July 2, 2001, 4:32 AM
 */

package ovt.servlet;

import ovt.beans.*;

/**
 *
 * @author  root
 * @version 
 */
public class HtmlTextAreaEditor extends HtmlPropertyEditor {

    /** Holds value of property rows. */
    private int rows = 5;
    
    /** Holds value of property cols. */
    private int cols = 10;
    
    /** Creates new HtmlTextAreaEditor */
    public HtmlTextAreaEditor(BasicPropertyDescriptor desc) {
        super(desc);
    }

    public String toString() {
        String name = getDesc().getPropertyPathString();
        String value = getEd().getAsText(); //WRAP=virtual
        return "<TEXTAREA NAME=\""+ name +"\" ROWS=\""+getRows()+"\" COLS=\""+getCols()+"\">" 
        + value + "</TEXTAREA> \n";
    }

    /** Getter for property rows.
     * @return Value of property rows.
 */
    public int getRows() {
        return rows;
    }
    
    /** Setter for property rows.
     * @param rows New value of property rows.
 */
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    /** Getter for property cols.
     * @return Value of property cols.
 */
    public int getCols() {
        return cols;
    }
    
    /** Setter for property cols.
     * @param cols New value of property cols.
 */
    public void setCols(int cols) {
        this.cols = cols;
    }
    
}

