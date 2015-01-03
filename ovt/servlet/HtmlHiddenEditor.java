/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlHiddenEditor.java,v $
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
 * HtmlHiddenEditor.java
 *
 * Created on October 25, 2000, 8:09 PM
 */

package ovt.servlet;

/**
 *
 * @author  ko
 * @version 
 */
public class HtmlHiddenEditor extends HtmlPropertyEditor {
    
    protected String value;

    /** Creates new HtmlHiddenEditor */
    public HtmlHiddenEditor(String propertyName, String value) {
        super(propertyName);
        this.value = value;
    }
    
    public String toString() {
        return "<INPUT TYPE=\"hidden\" NAME=\"" + getName() + 
            "\" VALUE=\"" + value + "\">\n";
    }

}
