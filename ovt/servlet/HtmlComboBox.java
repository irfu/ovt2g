/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlComboBox.java,v $
  Date:      $Date: 2003/09/28 17:52:53 $
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
 * HtmlComboBox.java
 *
 * Created on July 5, 2001, 5:46 AM
 */

package ovt.servlet;

/**
 *
 * @author  root
 * @version 
 */
public class HtmlComboBox extends Object {
    private String name;
    private String[] tags;
    private String currentValue;
    
    /** Creates new HtmlComboBox */
    public HtmlComboBox(String name, String[] tags, String currentValue) {
        this.tags = tags;
        this.name = name;
        this.currentValue = currentValue;
    }

    public String toString() {
        String res="<SELECT NAME=\"" + name + "\">";
        
        for (int i=0; i<tags.length; i++) {
            
            res+= "<OPTION VALUE=\"" + tags[i] +"\" " +
                ( isChecked(tags[i]) ? "SELECTED" : "") + " >" + 
                tags[i] + "\n";
        }
        res+= "</SELECT>\n";
        return res;
    }
    
    public boolean isChecked(String item) {
      return currentValue.equals(item);
    }
    
}
