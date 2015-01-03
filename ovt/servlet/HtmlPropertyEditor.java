/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlPropertyEditor.java,v $
  Date:      $Date: 2003/09/28 17:52:53 $
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
 * HtmlPropertyEditor.java
 *
 * Created on October 18, 2000, 2:46 PM
 */

package ovt.servlet;

import ovt.beans.*;
import ovt.interfaces.*;

import java.io.*;

/**
 *
 * @author  ko
 * @version 
 */
public abstract class HtmlPropertyEditor extends Object {
    
    protected OVTPropertyEditor ed = null;
    protected BasicPropertyDescriptor desc = null;
    /** Holds value of property ownerForm. */
    protected HtmlForm form = null;
    
    /** Holds value of property name. */
    protected String name;
    
    /** Holds value of property style. */
    private String style = null;
    
    /** Creates new HtmlPropertyEditor */
    public HtmlPropertyEditor(String name) {
        this.name = name;
    }
    
    /** Creates new HtmlPropertyEditor */
    public HtmlPropertyEditor(BasicPropertyDescriptor desc) {
        this.desc = desc;
        this.ed = desc.getPropertyEditor(); 
        this.name = desc.getPropertyPathString();
    }
    
    protected BasicPropertyDescriptor getDesc() {
        return desc;
    }
    
    protected OVTPropertyEditor getEd() {
        return ed;
    }
    
    public String getPropertyDisplayName() {
        if (desc != null) return desc.getDisplayName();
        else return "no display name";
    }
    
    public void onChangeSubmit(HtmlForm form) {
        this.form = form;
    }
    
    public HtmlForm getForm() {
        return form;
    }
    
    public boolean submitOnChange() {
        return (form != null);
    }
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }
    
 /** Getter for property style.
 * @return Value of property style.
 */
    public String getStyle() {
  return style;
    }
    
/** Sets the "class" css of the editor
 * @param style New value of property style.
 */
    public void setStyle(String style) {
  this.style = style;
    }
    
}
