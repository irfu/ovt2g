/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/MaxNumberOfEntitiesReachedException.java,v $
  Date:      $Date: 2003/09/28 17:52:54 $
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
 * MaxNumerOfEnitiesReachedException.java
 *
 * Created on October 31, 2000, 1:41 PM
 */

package ovt.servlet;

import javax.servlet.jsp.JspException;

/**
 *
 * @author  ko
 * @version 
 */
public class MaxNumberOfEntitiesReachedException extends JspException {

    /**
     * Creates new <code>MaxNumerOfEnitiesReachedException</code> without detail message.
     */
    public MaxNumberOfEntitiesReachedException() {
        super("Maximum number of OVT's reached.");
    }


    /**
     * Constructs an <code>MaxNumerOfEnitiesReachedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MaxNumberOfEntitiesReachedException(String msg) {
        super(msg);
    }
}
