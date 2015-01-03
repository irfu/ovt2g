package ovt.servlet;

import ovt.*;
import ovt.gui.*;
import ovt.mag.*;
import ovt.util.*;
import ovt.event.*;
import ovt.object.*;
import ovt.graphics.*;
//import ovt.datatype.*;
import ovt.offscreen.*;
import ovt.interfaces.*;

//import java.sql.*;
import java.io.*;
import java.util.*;
import java.beans.*;
import java.awt.*;
import java.awt.image.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;


public class OVTServlet extends HttpServlet {
    
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    System.setOut(System.err);
}

public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {
        
    HttpSession session = req.getSession(true);
    ServletEntity entity = (ServletEntity)session.getAttribute("entity");
    
    if (entity == null) ServletEntity.gotoJSPErrorPage(req, res, getServletContext(), "/ErrorPage.jsp", new OVTNotStartedException());
    
    String get = req.getParameter("get");
    if (get != null) {
        if (get.equals("RenPanelImage")) {
            // lets return an immage
            entity.sendRenPanelImage(res);
            return;
        } else if (get.equals("icon")) {
            entity.sendIcon(entity.getCore().getObject(req.getParameter("obj")), res);
            return;
        } else if (get.equals("LargeImage")) {
            // lets return an immage
            int magnification = 2;
            try {
                magnification = new Integer(((String)req.getParameter("magnification"))).intValue();
            } catch (NullPointerException e2) {
                ServletEntity.gotoJSPErrorPage(req, res, getServletContext(), "/ErrorPage.jsp", new JspException("magnification paramiter was not specified"));
            } catch (ClassCastException e3) {
                ServletEntity.gotoJSPErrorPage(req, res, getServletContext(), "/ErrorPage.jsp", new JspException("incorrect magnification paramiter"));
            }
            try {
                entity.sendLargeImage(res, magnification);
            } catch (IllegalArgumentException e4) {
                ServletEntity.gotoJSPErrorPage(req, res, getServletContext(), "/ErrorPage.jsp", e4);
            }
            return;
        }
    }
}


}

