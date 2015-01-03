/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/test.java,v $
  Date:      $Date: 2009/10/27 11:56:36 $
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
$Id: test.java,v 2.4 2009/10/27 11:56:36 yuri Exp $
$Source: /stor/devel/ovt2g/ovt/test.java,v $  
*/

package ovt;

import vtk.*;
import java.awt.*;
import java.awt.event.*;

public class test {

public static void main (String[] args)
{
  Frame window = new Frame("Test Sphere");
  window.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  window.addNotify();


  vtkPanel renPanel = new vtkPanel();
  renPanel.setSize(400,400);
  window.removeAll();
  window.add(renPanel);

  // create sphere geometry
  vtkSphereSource sphere = new vtkSphereSource();
  sphere.SetRadius(1.0);
  sphere.SetThetaResolution(18);
  sphere.SetPhiResolution(18);

  // map to graphics library
  vtkPolyDataMapper map = new vtkPolyDataMapper();
  map.SetInput(sphere.GetOutput());

  // actor coordinates geometry, properties, transformation
  vtkActor aSphere = new vtkActor();
  aSphere.SetMapper(map);
  aSphere.GetProperty().SetColor(0,0,1); // sphere color blue

  vtkRenderer ren1 = renPanel.GetRenderer();
  ren1.AddActor(aSphere);
  ren1.SetBackground(1,1,1); // Background color white

  window.pack();
  window.setVisible(true);
}
}
