/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/LargeImage.java,v $
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
 * LargeImage.java
 *
 * Created on November 2, 2000, 4:23 PM
 */

package ovt.servlet;

import ovt.util.*;
import ovt.beans.*;
import ovt.interfaces.*;
import ovt.graphics.*;

import vtk.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
/**
 *
 * @author  ko
 * @version 
 */
public class LargeImage extends java.lang.Object {
    vtkRenderLargeImage renderLarge = new vtkRenderLargeImage();
    vtkImageViewer viewer = new vtkImageViewer();
    /** Creates new LargeImage */
    public LargeImage(vtkRenderer ren) {
        renderLarge.SetInput(ren);
        
        /*viewer.SetInput(renderLarge.GetOutput());
        viewer.SetColorWindow(255);
        viewer.SetColorLevel(127.5);*/
    }
    
    public void setMagnification(int magnification) {
        renderLarge.SetMagnification(magnification);
    }
    
    /** lock - the synchronization lock */
    public Image getImage(Object lock) {
      synchronized  (lock) {
        renderLarge.Update();
      }
      Log.log("LargeImage :: getImage() ...", 5);
      //viewer.Render(); 
      String tmpFile = Utils.getRandomFilename("/tmp/tmpOVTLargeImage", ".bmp");
      vtkBMPWriter writer = new vtkBMPWriter();
         writer.SetInput(renderLarge.GetOutput());
         writer.SetFileName(tmpFile);
         writer.Write();
    
      Image img = null;
      try {
        img = BmpDecoder.getImage(tmpFile);    
      } catch (IOException e) { 
        e.printStackTrace(); 
      }
    
      new File(tmpFile).delete();
      return img;
  }
}
