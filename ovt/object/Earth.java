/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/object/Earth.java,v $
  Date:      $Date: 2003/09/28 17:52:46 $
  Version:   $Revision: 2.13 $


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
 * Earth.java
 *
 * Created on March 22, 2000, 8:22 AM
 */
 
package ovt.object;

import ovt.*;
import ovt.mag.*;
import ovt.gui.*;
import ovt.util.*;
import ovt.event.*;
import ovt.beans.*;
import ovt.datatype.*;
import ovt.interfaces.*;
import ovt.object.editor.*;
import ovt.object.vtk.ProgrammableEarthSource;

import vtk.*;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.beans.*;
import javax.swing.*;
import java.awt.event.*;


/** 
 *
 * @author  root
 * @version 
 */
public class Earth extends SingleActorObject implements TimeChangeListener, 
                      CoordinateSystemChangeListener, MenuItemsSource, PositionSource  {

  public static final int NO_TEXTURE                = 0;
  public static final int BnW_TEXTURE               = 1; // Black & White
  public static final int NORMAL_TEXTURE            = 2; 
  public static final int HIGH_RESOLUTION_TEXTURE   = 3; 
  /** used by getPosition() method */
  private static final double[] zero_zero_zero = {0., 0., 0.};
  
  protected vtkTexture[] texture = new vtkTexture[4];
  
  /** Holds value of property textureType. */
  protected int textureType = NORMAL_TEXTURE;
  
  /** The type of the texture to be used in the case of no conflicts */
  protected int prefferedTextureType = NORMAL_TEXTURE;
  
  protected MenuPropertyEditor textureTypeEditor;
  
  public EarthGrid earthGrid;
  public CoastLine coastLine;

  
  /** Creates new Earth */
  public Earth(OVTCore core) {
    super(core, "The Earth", "images/globe.gif");
    setColor(java.awt.Color.white);
    Log.log("Earth :: init ... ", 5);
    Descriptors descriptors = super.getDescriptors();
    try {
        BasicPropertyDescriptor pd = new BasicPropertyDescriptor("textureType", this);
        pd.setDisplayName("textureType");
        String[] tags = {"No Texture", "Black & White", "Normal", "High Resolution"};
        textureTypeEditor = new MenuPropertyEditor(pd, tags);
        addPropertyChangeListener("textureType", textureTypeEditor);
        pd.setPropertyEditor(textureTypeEditor);
        descriptors.put(pd);
    } catch (IntrospectionException e2) {
        System.out.println(getClass().getName() + " -> " + e2.toString());
        System.exit(0);
    }
    
    initTextures();
    
    earthGrid = new EarthGrid(this);
    coastLine = new CoastLine(this);
    
    setVisible(true);
  }
  
  private void initTextures() {
    texture[0] = new vtkTexture();
    
    String[] textureFiles = { "", "earth_BnW.pnm", "earth_normal.pnm", "earth8km2000x1000.pnm" };
    for (int i=1; i<4; i++) {
        vtkPNMReader pnmReader = new vtkPNMReader();
            pnmReader.SetFileName(OVTCore.getOVTHomeDir()+"textures"+File.separator+textureFiles[i]);
         texture[i] = new vtkTexture();
         texture[i].SetInput(pnmReader.GetOutput());
         if (i != HIGH_RESOLUTION_TEXTURE) texture[i].InterpolateOn();
      }
  }

  
protected void validate() {
    Log.log("Recalculating Earth ...", 5);

      // create actor  
      // create sphere geometry
    vtkTexturedSphereSource tss = new vtkTexturedSphereSource();
      tss.SetRadius(1.0);
      tss.SetThetaResolution(40);
      tss.SetPhiResolution(40);

      // map to graphics library
    vtkPolyDataMapper earthMapper = new vtkPolyDataMapper();
      earthMapper.SetInput(tss.GetOutput());

    // actor coordinates geometry, properties, transformation
    actor = new vtkActor();
      actor.SetMapper(earthMapper);
      float[] rgb = ovt.util.Utils.getRGB(getColor());
      actor.GetProperty().SetColor(rgb[0], rgb[1], rgb[2]);

    // load in the texture map
        
    // avoid BUS ERROR bug in OpenGL. This should be fixed in future
    if (!OVTCore.isServer()) {
            actor.SetTexture(texture[getTextureType()]);
    }
    super.validate();
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
    earthGrid.setVisible(visible);
    coastLine.setVisible(visible);
  }
  
  protected void show() {
    super.show();
    rotate();
  }
  
  public void rotate() {
    Matrix3x3 m3x3 = getTrans(getMjd()).trans_matrix(getPolarCS(), getCS());
    actor.SetUserMatrix(m3x3.getVTKMatrix()); 
  }
  
  public void timeChanged(TimeEvent evt) {
    if (isVisible()) rotate(); 
    earthGrid.timeChanged(evt);
    coastLine.timeChanged(evt);
  }

  public void coordinateSystemChanged(CoordinateSystemEvent evt) {
    
    if (isVisible()) rotate();
          
    if (evt.getWindow() == Const.POLAR) {
      // it is not possible to show earth with continents 
      // if CS is not GEO
      if (evt.getNewCS() == CoordinateSystem.GEO) {
        setTextureType(prefferedTextureType);
        textureTypeEditor.setEnabled(BnW_TEXTURE, true);
        textureTypeEditor.setEnabled(NORMAL_TEXTURE, true);
        textureTypeEditor.setEnabled(HIGH_RESOLUTION_TEXTURE, true);
      } else { // new CS != GEO
        // if the privious CS was GEO - store textureType.
        if (evt.getOldCS() == CoordinateSystem.GEO) prefferedTextureType = getTextureType();
        setTextureType(NO_TEXTURE);
        textureTypeEditor.setEnabled(BnW_TEXTURE, false);
        textureTypeEditor.setEnabled(NORMAL_TEXTURE, false);
        textureTypeEditor.setEnabled(HIGH_RESOLUTION_TEXTURE, false);
      }
    }
    earthGrid.coordinateSystemChanged(evt);
    coastLine.coordinateSystemChanged(evt);
  }

  
  /** Getter for property textureType.
   * @return Value of property textureType.
   */
  public int getTextureType() {
    return textureType;
  }
  /** Setter for property textureType.
   * @param textureType New value of property textureType.
   *
   * @throws IllegalArgumentException
   */
  public void setTextureType(int textureType) throws IllegalArgumentException {
    int oldtextureType = this.textureType;
    if (oldtextureType == textureType) return; // nothing have changed
    if ((getPolarCS() != CoordinateSystem.GEO) && (textureType != NO_TEXTURE))
        throw new IllegalArgumentException("Continents cannot be displayed in the " +
            CoordinateSystem.getCoordSystem(getPolarCS()) + " coordinate system");
    
    this.textureType = textureType;
    if (actor != null) actor.SetTexture(texture[textureType]);
    propertyChangeSupport.firePropertyChange ("textureType", new Integer (oldtextureType), new Integer (textureType));
  }
  
  /** Is needed for Camera, returns { 0, 0, 0}. */
  public double[] getPosition() {
      return zero_zero_zero;
  }
  
  
  public JMenuItem[] getMenuItems() {
      JMenuItem item1 = new JMenuItem("Look at");
        item1.setFont(ovt.gui.Style.getMenuFont());
        item1.setEnabled(isEnabled());
        item1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                if (!isVisible()) setVisible(true);
                getCore().getCamera().setViewTo(Earth.this);
                getCore().Render();
            }
        });
     
      JMenu menu1 = new JMenu("Grid");
          menu1.setFont(ovt.gui.Style.getMenuFont());
          MenuUtils.addMenuItemsFromDescriptors(menu1, earthGrid, getCore());
          MenuUtils.addSeparator(menu1);
          MenuUtils.addMenuItemsFromSource(menu1, earthGrid);
      JMenu menu2 = new JMenu("Coastline");
          menu2.setFont(ovt.gui.Style.getMenuFont());
          MenuUtils.addMenuItemsFromDescriptors(menu2, coastLine, getCore());
          
      return new JMenuItem[]{ menu1, menu2, null, item1 };
  }  

  /** for XML */
  public EarthGrid getEarthGrid() { return earthGrid; }
  public CoastLine getCoastLine() { return coastLine; }
  
  
}




