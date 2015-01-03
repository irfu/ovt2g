/*
 * OffscreenRenPanel.java
 *
 * Created on October 6, 2000, 1:25 PM
 */

package ovt.offscreen;

import ovt.*;
import ovt.util.*;
import ovt.beans.*;
import ovt.interfaces.*;
import ovt.graphics.*;

import vtk.*;

import java.awt.image.*;
import java.io.*;
import java.awt.*;



/**
 *
 * @author  ko
 * @version
 */
public class OffscreenRenPanel implements RenPanel {
    
    protected vtkOpenGLOffscreenRenderWindow rw = new vtkOpenGLOffscreenRenderWindow();
    protected vtkRenderer ren = new vtkRenderer();
    protected vtkCamera cam = null;
    protected vtkLight lgt = new vtkLight();
    int LightFollowCamera = 1;
    int windowset = 0;
    boolean rendering = false;
    protected static Object lock = new Object();
    
    static { System.loadLibrary("vtkJava"); }
    
    /** Creates new OffscreenRenPanel */
    public OffscreenRenPanel() {
        rw.AddRenderer(ren);
        rw.SetSize(200, 200);
        addOriginActor();
        //ren.SetBackground(0.9,0.9,0.9);
        ren.SetBackground( 1, 1, 1);
        vtkCamera camera = ren.GetActiveCamera();
        camera.SetPosition (10, 10, 1);
        camera.SetFocalPoint (0, 0, 0);
        //System.out.println("Setting clipping range...");
        camera.SetClippingRange(0.5, 200);
        camera.ComputeViewPlaneNormal();
        camera.SetViewUp (0, 0, 1);
        //camera.OrthogonalizeViewUp();
    }
    
    
    public void setSize(int x, int y) {
        rw.SetSize(x,y);
    }
    
    public int getWidth() {
        return rw.GetSize()[0];
    }
    
    public int getHeight() {
        return rw.GetSize()[1];
    }
    
    public vtkRenderWindow getRenderWindow() {
        return rw;
    }
    
    public vtkRenderer getRenderer() {
        return ren;
    }
    
    /** Returns the light, which is copuled to camera */
    public vtkLight getCameraLight() {
        return lgt;
    }
    
  /** The Rendering lock. Used for synchronization */
    public static Object getLock() {
        return lock;
    }
    
    public void Render() {
        synchronized (lock) {
            Log.log("Rendering ...", 3);
            if (!rendering)
            {
                rendering = true;
                if (ren.VisibleActorCount() == 0) return;
                if (rw != null)
                {
                    if (windowset == 0) {
                        // set the window id and the active camera
                        // what is it for ??? I'v remarked it
                        //  rw.SetWindowInfo(this.getWindowInfo());
                        cam = ren.GetActiveCamera();
                        ren.AddLight(lgt);
                        lgt.SetPosition(cam.GetPosition());
                        lgt.SetFocalPoint(cam.GetFocalPoint());
                        windowset = 1;
                    }
                    
                    if (this.LightFollowCamera == 1) {
                        lgt.SetPosition(cam.GetPosition());
                        lgt.SetFocalPoint(cam.GetFocalPoint());
                    }
                    Log.log("RenWin :: Render ...", 5);
                    System.out.print("RenWin :: Render ...");
                    rw.Render();
                    System.out.println("done!");
                    rendering = false;
                }
            }
        }
    }
    
    
    public synchronized Image getImage() {
        /*Log.log("OffscreenRenPanel :: getImage() ...", 5);
        try { // DBG
            if (true) return BmpDecoder.getImage("/usr/lib/tk8.3/demos/images/face.bmp");
        } catch (IOException e) {
            e.printStackTrace();
            if (true) return null;
        }
         */
        
        //ren.ResetCamera();
        Render();
        
        String tmpFile = Utils.getRandomFilename("/tmp/tmpOVTRenPanel", ".bmp");
        vtkBMPWriter writer = new vtkBMPWriter();
        vtkWindowToImageFilter windowToImageFilter = new vtkWindowToImageFilter();
        windowToImageFilter.SetInput(getRenderWindow());
        writer.SetInput(windowToImageFilter.GetOutput());
        writer.SetFileName(tmpFile);
         //DBG*/System.out.println("OffscrRenPanel::getImage(): calling write()");
         synchronized (lock) {
             writer.Write();
         }
         //DBG*/System.out.println("OffscrRenPanel::getImage(): write() returned!");
         
         Image img = null;
         try {
             img = BmpDecoder.getImage(tmpFile);
         } catch (IOException e) {
             e.printStackTrace();
         }
         
         new File(tmpFile).delete();
         return img;
    }
    
    public static void main(String[] args) {
        OffscreenRenPanel renPanel = new OffscreenRenPanel();
        
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
        
        vtkRenderer ren = renPanel.getRenderer();
        
        ren.AddActor(aSphere);
        ren.SetBackground(1,1,1); // Background color white
        
        System.out.print("Rendering ... ");
        renPanel.Render();
        System.out.println("done.");
        
        //Image img = renPanel.getImage();
        
        vtkPNMWriter writer = new vtkPNMWriter();
        vtkWindowToImageFilter windowToImageFilter = new vtkWindowToImageFilter();
        windowToImageFilter.SetInput(renPanel.getRenderWindow());
        
        writer.SetInput(windowToImageFilter.GetOutput());
        writer.SetFileName("largeImage.ppm");
        
        
        System.out.print("Writing immage ... ");
        writer.Write();
        //renWin.WriteImage("sphere.ppm");
        System.out.println("done.");
        
    }
    
    protected void addOriginActor() {
        vtkVectorText atext = new vtkVectorText();
        atext.SetText(". (0, 0, 0)");
        vtkPolyDataMapper mapper = new vtkPolyDataMapper();
        mapper.SetInput(atext.GetOutput());
        vtkFollower actor = new vtkFollower();
        actor.SetMapper(mapper);
        actor.SetScale(0.02, 0.02, 0.02);
        actor.AddPosition(0, 0, 0);
        actor.SetCamera(getRenderer().GetActiveCamera());
        actor.GetProperty().SetColor(0, 0, 0);
        getRenderer().AddActor(actor);
    }
    
    
    public void addEarth() {
        //vtkMapper.GlobalImmediateModeRenderingOn();
        // create actor
        // create sphere geometry
        vtkTexturedSphereSource tss = new vtkTexturedSphereSource();
        tss.SetRadius(1.0);
        tss.SetThetaResolution(40);
        tss.SetPhiResolution(40);
        
        // map to graphics library
        vtkPolyDataMapper earthMapper = new vtkPolyDataMapper();
        // on!
        //earthMapper.ImmediateModeRenderingOn();
        earthMapper.SetInput(tss.GetOutput());
        
        // actor coordinates geometry, properties, transformation
        vtkActor act = new vtkActor();
        act.SetMapper(earthMapper);
        
        // load in the texture map
        vtkTexture atext = new vtkTexture();
        vtkPNMReader pnmReader = new vtkPNMReader();
        pnmReader.SetFileName(OVTCore.ovtHomeDir + "/images/earth.ppm");
        atext.SetInput(pnmReader.GetOutput());
        atext.InterpolateOn();
        //act.SetTexture(atext);
        
        ren.AddActor(act);
    }
}
