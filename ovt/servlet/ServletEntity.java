/*=========================================================================

  Program:   Orbit Visualization Tool
  Source:    $Source: /stor/devel/ovt2g/ovt/servlet/ServletEntity.java,v $
  Date:      $Date: 2003/09/28 17:52:54 $
  Version:   $Revision: 2.7 $


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
 * ServletEntity.java
 *
 * Created on October 18, 2000, 12:05 PM
 */

package ovt.servlet;

import ovt.*;
import ovt.gui.*;
import ovt.mag.*;
import ovt.util.*;
import ovt.event.*;
import ovt.object.*;
import ovt.graphics.*;
import ovt.datatype.*;
import ovt.offscreen.*;
import ovt.interfaces.*;

import java.io.*;
import java.util.*;
import java.beans.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.reflect.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpUtils.*;

/**
 *
 * @author  ko
 * @version
 */
public class ServletEntity implements HttpSessionBindingListener {
    static Object lock = new Object();
    static int numberOfEntities = 0;
    OVTCore core;
    OffscreenRenPanel renPanel;
    OVTTree tree;
    static final String[] reservedWords = {"get", "obj", "panel"};
    //OVTServlet servlet;
    
  /** Creates new ServletEntity */
    public ServletEntity() {
        //this.servlet = servlet;
        renPanel = new OffscreenRenPanel();
        renPanel.setSize(400, 400);
        Log.log("Offscreen Panel created.\n", 5);
        /*String ovt_home = System.getenv("OVT_HOME");
        String ovt_user_dir = System.getenv("OVT_USER_DIR");
        System.setProperty("ovt.home", ovt_home);
        System.setProperty("ovt.user", ovt_user_dir);*/
        synchronized(lock) {
            //DBG*/System.out.println("Creating core");
            core = new OVTCore(renPanel);
            //DBG*/System.out.println("Core created");
        }
        
        Log.log("OVTHomeDir="+core.getOVTHomeDir());
        
        // create tree
        tree = new OVTTree();
        
        float[] rgb = core.getBackgroundColor().getRGBColorComponents(null);
        renPanel.getRenderer().SetBackground(rgb[0], rgb[1], rgb[2]);
        
        core.getCamera().setProjection(Camera.PARALLEL_PROJECTION);
        //Log.log("Campera.projection="+core.getCamera().getProjection());
        core.getCamera().setViewFrom(Camera.VIEW_FROM_Y);
        core.getCamera().setParallelScale(16);
        core.sunLight.setVisible(false);
        core.getOutputLabel().setVisible(true);
        core.getCoordinateSystem().showInTree(true);
        core.getMagProps().showInTree(true);
        //core.groundStations.showInTree(false);
        //core.getOutputLabel().showInTree(false);
        //core.getCamera().reset();
        getTree().setExpanded("OVT.Satellites", true);
        
        Log.log("OVTServlet :: init done.", 5);
        //DBG*/System.out.println("ServletEntity: construction done.");
    }
    
    
    public OVTCore getCore() {
        return core;
    }
    
    public OffscreenRenPanel getRenPanel() {
        return renPanel;
    }
    
    public OVTTree getTree() {
        return tree;
    }
    
    public void process(HttpServletRequest req, HttpServletResponse res, ServletContext context)
    throws IOException, ServletException, JspException {
        
        Log.log("Processing parameters... ", 5);
        //DBG*/System.out.println("ServletEntity::process() started");
        
        // tree operations
        // check if the node whant's to be expanded/closed
        String expand = req.getParameter("expand");
        if (expand != null) {
            try {
                boolean value = new Boolean(expand).booleanValue();
                getTree().setExpanded(req.getParameter("obj"), value);
                Log.log("expand="+value+" obj='"+req.getParameter("obj")+"'");
                req.removeAttribute("expand");
                req.removeAttribute("obj");
            } catch (Exception e2) {
                throw new JspException(e2.getMessage());
            }
            return;
        }
        
        Enumeration e = req.getParameterNames();
        String paramName;
        while(e.hasMoreElements()) {
            paramName = (String)e.nextElement();
            //DBG*/System.out.println("processing parameter: " + paramName);
            if (!Utils.inTheList(paramName, reservedWords)) {
                try {
                    Log.log("setting core.setAsText(" + new PropertyPath(paramName) + ", " + req.getParameter(paramName) + ")", 0);
                    // set property
                    core.setAsText(new PropertyPath(paramName), req.getParameter(paramName));
                    // check weather the object which has the property has fireChangeMethod.
                    // If so - add it to fireChangeMethodSources
                    /*objectName = paramName.substring(paramName.indexOf(".")+1, paramName.lastIndexOf(".")-1);
                    Log.log("Object name='"+objectName+"'");
                    Object obj = core.getObject(objectName);
                    Log.log("Object ='"+obj+"'");
                    if (obj instanceof FireChangeMethodSource) {
                        Log.log(objectName+" added to fire list");
                        fireChangeMethodSources.addElement(obj);
                    }*/
                    //DBG*/System.out.println("core.setAsText() finished");
                } catch (PropertyVetoException e2) {
                    Log.log("Error: " + e2.getMessage(), 0);
                    //DBG*/System.out.println("Error: " + e2.getMessage());
                    throw new JspException(e2.getMessage());
                } catch (IllegalArgumentException ignore) {
                    Log.log("Ignoring : " + ignore.getMessage(), 0);
                    ignore.printStackTrace();
                    //DBG*/System.out.println("Ignoring : " + ignore.getMessage());
                }
            }
        }
        
        //DBG*/System.out.println("ServletEntity::process() finished");
    }
    
    
    
    void sendIcon(OVTObject obj, HttpServletResponse res) {
        //DBG*/System.out.println("ServletEntity::sendRenPanelImage called:");
        //DBG*/new Exception().printStackTrace();
        Log.log("Sending GIF ...", 0);
        try {
            res.setContentType("image/gif");
            
            ServletOutputStream out = res.getOutputStream();
            javax.swing.ImageIcon icon = obj.getIcon();
            GifEncoder enc = new GifEncoder(icon.getImage(), out);
                enc.encode();
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("IOException : " + e);
        }
    }
    
    void sendRenPanelImage(HttpServletResponse res) {
        //DBG*/System.out.println("ServletEntity::sendRenPanelImage called:");
        //DBG*/new Exception().printStackTrace();
        Log.log("Sending RenPanel Image ...", 0);
        try {
            res.setContentType("image/jpeg");
            
            ServletOutputStream out = res.getOutputStream();
            Log.log("Getitng image ..", 0);
            //DBG*/System.out.println("ServletEntity::SendRenPanelImage: calling renPanel::getImage()");
            Image img = renPanel.getImage();
            //DBG*/System.out.println("ServletEntity::SendRenPanelImage: renPanel::getImage() finished");
            
            //JpegEncoder enc = new JpegEncoder(img, 90, out);
            //enc.Compress();
            
            Log.log("image created. creating encoder ...", 0);
            JpegEncoder enc = new JpegEncoder(img, 90, out);
            Log.log("Compressing", 5);
            enc.Compress();    //enc.encode();
            Log.log("Done.", 5);
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("IOException : " + e);
        }
    }
    
    void sendLargeImage(HttpServletResponse res, int magnification) throws IllegalArgumentException {
        if (magnification > 5) throw new IllegalArgumentException("Magnification > 5 is prohibited!");
        Log.log("Sending Large Image ...", 5);
        boolean captionVisible = core.getOutputLabel().isVisible();
        Camera cam = core.getCamera();
        int projection = cam.getProjection();
        
        if (projection != Camera.PERSPECTIVE_PROJECTION) {
            cam.setProjection(Camera.PERSPECTIVE_PROJECTION);
            // set R based on R
            double r = cam.getParallelScale()/Math.tan(Utils.toRadians(0.5*cam.getViewAngle()));
            cam.setR(r);
        }
        
        // hide caption to avoid 16 captions on large image
        if (captionVisible) core.getOutputLabel().setVisible(false);
        
        try {
            res.setContentType("image/jpeg");
            
            ServletOutputStream out = res.getOutputStream();
            Log.log("Getitng image ..", 5);
            LargeImage li = new LargeImage(renPanel.getRenderer());
            li.setMagnification(magnification);
            Image img = li.getImage(renPanel.getLock());
            //Log.log("image created. creating encoder ...", 5);
            JpegEncoder enc = new JpegEncoder(img, 90, out);
            //Log.log("encoder created", 5);
            enc.Compress();
            //Log.log("Compressed", 5);
            //GifEncoder enc = new GifEncoder(renPanel.getImage(), out);
            //enc.encode();
        } catch (IOException e) {
            e.printStackTrace();
            Log.log("IOException : " + e);
        }
        
        // restore projection type 
        if (projection != Camera.PERSPECTIVE_PROJECTION) cam.setProjection(projection);
            
        // restore caption visibility state
        if (captionVisible) core.getOutputLabel().setVisible(true);
    }
    
    
    public static void gotoJSPErrorPage(HttpServletRequest req, HttpServletResponse res,
    ServletContext context, String errorPageUrlString, Throwable throwable)
    throws ServletException, IOException {
        req.setAttribute("javax.servlet.jsp.jspException", throwable);
        context.getRequestDispatcher(errorPageUrlString).forward(req, res);
    }
    
    /** Returns the name of a customizer page for obj or null*/
    public static String getCustomizerPage(OVTObject obj, HttpServletRequest request) {
        String objName = obj.getClass().getName();
        // return "Sat.jsp" from "ovt.object.Sat"
        String res = objName.substring(objName.lastIndexOf(".")+1, objName.length()) + ".jsp";
        // if Sat.jsp doesn't exist - return null.
        if (new File(request.getRealPath("")+File.separator+res).exists()) return res;
        else return null;
    }
    
    public static String getCB() {
        return "/servlet/ovt";
    }
    
    public static String getImagesDir() {
        return "/img/";
    }
    
    
    public static synchronized void changeNumberOfEntities(int inc) {
        if (inc > 0) numberOfEntities++;
        else numberOfEntities--;
    }
    
    public static int getNumberOfEntities() {
        return numberOfEntities;
    }
    
    public static int getMaxNumberOfEntities() {
        return 5;
    }
    
/** This method is executed when session expires */
    public void valueUnbound(HttpSessionBindingEvent evt) {
        Log.log("Session expired!!!");
        changeNumberOfEntities(-1);
    }
    
/** This method is executed when servlet entity is being added to session */
    public void valueBound(HttpSessionBindingEvent evt) {
        Log.log("Session created!!!");
        changeNumberOfEntities(+1);
    }
    
}
