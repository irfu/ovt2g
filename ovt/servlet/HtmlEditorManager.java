/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlEditorManager.java,v $
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
 * HtmlEditorManager.java
 *
 * Created on October 18, 2000, 2:34 PM
 */

package ovt.servlet;

import ovt.*;
import ovt.beans.*;
import ovt.util.*;
import ovt.interfaces.*;

/**
 *
 * @author  ko
 * @version 
 */
public class HtmlEditorManager extends Object {
    
    
    /** Creates new HtmlEditorManager */
    public HtmlEditorManager() {
    }
    
    public static HtmlPropertyEditor getEditor(OVTCore core, String propPath)
            throws IllegalArgumentException {
        return getEditor(core.getPD(propPath));
    }
    
    public static HtmlPropertyEditor getEditor(BasicPropertyDescriptor desc) {
        if (desc == null) {
            new Exception("Desc == null").printStackTrace(System.out);
        }
        Log.log("HtmlEditorManager :: getEditor for " + desc.getDisplayName(), 5);
        OVTPropertyEditor ed = desc.getPropertyEditor(); //OLEG
        return getEditor(ed, desc);
    }
    
    /** This method is introduce to recurse WindowedPropertyEditor */
    private static HtmlPropertyEditor getEditor(OVTPropertyEditor ed, BasicPropertyDescriptor desc) {
        if (ed instanceof MenuPropertyEditor) {
            MenuPropertyEditor mpe = (MenuPropertyEditor)ed;
            switch (mpe.getType()) {
                case MenuPropertyEditor.CHECKBOX : return new HtmlComboBoxEditor(desc);
                // to be implemented later
                case MenuPropertyEditor.RADIOBUTTON : return new HtmlComboBoxEditor(desc);
                // in swith case - make ComboBox editor
                case MenuPropertyEditor.SWITCH : return new HtmlComboBoxEditor(desc);
            }
        } else if (ed instanceof ComboBoxPropertyEditor) {
            return new HtmlComboBoxEditor(desc);
        } else if (ed instanceof CheckBoxPropertyEditor) {
            return new HtmlComboBoxEditor(desc);
        } else if (ed instanceof TextAreaEditor) {
            return new HtmlTextAreaEditor(desc);
        } else if (ed instanceof WindowedPropertyEditor) {
            // the real editor is incapsulated 
            Log.log("ed instanceof WindowedPropertyEditor");
            return getEditor(((WindowedPropertyEditor)ed).getInnerPropertyEditor(), desc);
        }
        // else 
        return new HtmlTextEditor(desc);
    }

}
