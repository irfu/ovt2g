/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/HtmlSliderEditor.java,v $
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
 * HtmlSlider.java
 *
 * Created on July 5, 2001, 3:54 AM
 */

package ovt.servlet;

import ovt.beans.*;

/**
 *
 * @author  root
 * @version 
 */
public class HtmlSliderEditor extends HtmlTextEditor {
    
    private String urlStart;
    private double factor = 2.;
    private boolean multyply = false;

    /** Creates new HtmlSlider. urlStart can be  <CODE>/camera.jsp?panel=Camera</CODE>,
     * <CODE>factor</CODE> is a multyplication/incremential factor
     * if <CODE>multyply</CODE> is <CODE>true</CODE> - values used by "-" and "+" <A HREF> tags
     * are <CODE>value/factor</CODE> and <CODE>value*factor</CODE>, if <CODE>false</CODE> -
     * slider is incremential - values are : <CODE>value-factor</CODE> and <CODE>value + factor</CODE>
     */
    public HtmlSliderEditor(BasicPropertyDescriptor desc,String urlStart,double factor,boolean multyply) {
        super(desc);
        this.urlStart = urlStart;
        this.factor = factor;
        this.multyply = multyply;
    }
    
    public String toString() {
        String path = getDesc().getPropertyPathString();
        String value = getEd().getAsText();
        double v = ((Double)getEd().getValue()).doubleValue();
        double value1, value2;
        if (multyply) { 
            value1 = v / factor;
            value2 = v * factor;
        } else {
            value1 = v - factor;
            value2 = v + factor;
        }
        
        return "<A HREF=\""+urlStart+"&"+path+"="+value1+"\" onMouseOver=\"window.status='decrease'; return true;\" onMouseOut=\"window.status=''; return true;\">-</A>"
                +super.toString()+
                "<A HREF=\""+urlStart+"&"+path+"="+value2+"\" onMouseOver=\"window.status='increase'; return true;\" onMouseOut=\"window.status=''; return true;\">+</A>";
    }
    

}
