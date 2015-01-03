/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlCheckBoxEditor.java,v $
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
 * HtmlCheckBoxEditor.java
 *
 * Created on October 18, 2000, 2:53 PM
 */

package ovt.servlet;

import ovt.beans.*;

import java.io.*;

/**
 * THIS CLASS IS A FAKE!!!! IT IS NO TT WORKING!!!!
 * @author  ko
 * @version 
 */
public class HtmlCheckBoxEditor extends HtmlPropertyEditor {

    /** Creates new HtmlCheckBoxEditor */
    public HtmlCheckBoxEditor(BasicPropertyDescriptor desc) {
        super(desc);
    }

    public String toString() {
        String res="";
        String[] tags = ((BasicPropertyEditor)getEd()).getTags();
        res = "<INPUT TYPE=\"checkbox\" NAME=\""+ 
                getDesc().getPropertyPathString()+"\" VALUE=\"" +
                
                ( isChecked() ? "CHECKED" : "") + " >" + 
                getDesc().getDisplayName();
        return res;
    }
    
    public boolean isChecked() {
      return((Boolean)getEd().getValue()).booleanValue();
    }
}
