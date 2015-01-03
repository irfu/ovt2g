/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlTextEditor.java,v $
  Date:      $Date: 2003/09/28 17:52:54 $
  Version:   $Revision: 2.5 $


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
 * HtmlTextEditor.java
 *
 * Created on October 22, 2000, 9:12 PM
 */

package ovt.servlet;

import ovt.beans.*;

/**
 *
 * @author  ko
 * @version 
 */
public class HtmlTextEditor extends HtmlPropertyEditor {

    /** Holds value of property size. */
    private int size = -1;
    
    /** Holds value of property maxLength. */
    private int maxLength = -1;
    
    /** Creates new HtmlTextEditor */
    public HtmlTextEditor(BasicPropertyDescriptor desc) {
        super(desc);
    }
    

    
    
    public String toString() {
        String name = getDesc().getPropertyPathString();
        String value = getEd().getAsText();
        String style = (getStyle() != null) ? " class=\""+getStyle()+"\"" : "";
        String siz = (getSize() != -1) ? " size=\""+getSize()+"\"" : "";
        String ml = (getMaxLength() != -1) ? " maxlength=\""+getMaxLength()+"\"" : "";
        return "<INPUT TYPE=\"text\" NAME=\""+ name +"\" VALUE=\"" + value + "\""+style+siz+ml+"> \n";
    }

    /** Getter for property size.
 * @return Value of property size.
 */
    public int getSize() {
  return size;
    }
    
    /** Setter for property size.
 * @param size New value of property size.
 */
    public void setSize(int size) {
  this.size = size;
    }
    
    /** Getter for property maxLength.
 * @return Value of property maxLength.
 */
    public int getMaxLength() {
  return maxLength;
    }
    
    /** Setter for property maxLength.
 * @param maxLength New value of property maxLength.
 */
    public void setMaxLength(int maxLength) {
  this.maxLength = maxLength;
    }
    
}
