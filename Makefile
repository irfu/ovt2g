#
# Makefile.in to build OVT
#
# $Id: Makefile.in,v 1.22 2009/10/27 11:58:01 yuri Exp $
#

OVTVERSION = 2.3

top_srcdir = /home/yuri/devel/ovt2g
srcdir = ovt


prefix = /usr/local
exec_prefix = ${prefix}

bindir = ${exec_prefix}/bin
sbindir = ${exec_prefix}/sbin
libexecdir = ${exec_prefix}/libexec
datadir = ${prefix}/share
sysconfdir = ${prefix}/etc
localstatedir = ${prefix}/var
libdir = ${exec_prefix}/lib
infodir = ${prefix}/share/info
mandir = ${prefix}/share/man

ovt_bindir = ${bindir}
ovt_datadir = ${datadir}/ovt-${OVTVERSION}
ovt_libdir = ${libdir}

.SUFFIXES:
.SUFFIXES: .java .class .jar

SHELL = /usr/local/bin/bash
RM = rm
INSTALL = /usr/bin/install -c
INSTALL_DATA = ${INSTALL} -m 644
INSTALL_PROGRAM = ${INSTALL}
mkinstalldirs = $(top_srcdir)/mkinstalldirs

JAVAC = /usr/local/bin/jikes -bootclasspath /usr/local/diablo-jdk1.6.0/jre/lib/rt.jar -nowarn
JAVADOC = /usr/local/diablo-jdk1.6.0/bin/javadoc
JAR = /usr/local/diablo-jdk1.6.0/bin/jar

JAVACFLAGS=

# /path/to/vtk.jar or directory containing vtk/vtk*.class
VTK_CLASSPATH = /home/yuri/devel/ovt2g/lib/vtk.jar
# directory containing VTK shared libraries (libvtk*Java.so)
VTK_LIBS=

DOCDIR = @DOCDIR@

API_DOCS_DIR=/net/nest/usr/home/www/ovt/docs/classes/

#########################################################################
# our directory system 
#JAR_FILE = ovt-${OVTVERSION}
BIN_DIR = bin
DOC_DIR = docs
IM_DIR = images
LIB_DIR = lib
SRC_DIR = src
ODATA_DIR = odata
CONF_DIR = conf
MDATA_DIR = mdata
TEX_DIR = textures
UDATA_DIR = userdata
#########################################################################
# end of user defined variables
#
XML_JARS = $(top_srcdir)/$(LIB_DIR)/jaxp.jar:$(top_srcdir)/$(LIB_DIR)/parser.jar
VTK_OFSCR_JAR = $(top_srcdir)/$(LIB_DIR)/vtk31_offscr_only.jar
SERVLET_JAR = /usr/local/tomcat/lib/servlet.jar

CP = $(top_srcdir):${VTK_CLASSPATH}:$(XML_JARS):$(VTK_OFSCR_JAR):$(SERVLET_JAR):${CLASSPATH}

FILELIST = $(srcdir)/*.class $(srcdir)/*/*.class $(srcdir)/*/*/*.class

#########################################################################
#files
#

#bin
bin-files = $(BIN_DIR)/ovt $(BIN_DIR)/checktle
#lib
nobuild-jar = $(LIB_DIR)/jaxp.jar $(LIB_DIR)/parser.jar
ovt-lib = $(SRC_DIR)/libovt-$(OVTVERSION).so
ovt-jar = $(LIB_DIR)/ovt-$(OVTVERSION).jar
lib-files = $(ovt-jar) $(ovt-lib) $(nobuild-jar)
lib-files-build = $(ovt-jar) $(ovt-lib)
#conf
conf-files = $(CONF_DIR)/ovt.conf 
# docs
doc-files = $(DOC_DIR)/about.html $(DOC_DIR)/license.txt $(DOC_DIR)/vtk-license.txt
# mdata
mdata-files = $(MDATA_DIR)/coastline.dat $(MDATA_DIR)/igrf.d $(MDATA_DIR)/igrf_c.d
#textures
tex-files = $(TEX_DIR)/earth8km2000x1000.pnm $(TEX_DIR)/earth_BnW.pnm\
$(TEX_DIR)/earth_normal.pnm
#userdata
udata-files = $(UDATA_DIR)/MachNumber.dat $(UDATA_DIR)/DSTIndex.dat\
$(UDATA_DIR)/SWP.dat $(UDATA_DIR)/IMF.dat $(UDATA_DIR)/gb_stations.xml\
$(UDATA_DIR)/KPIndex.dat $(UDATA_DIR)/w96.dat

#########################################################################
#Build rules
#
all: lib

classes: ovt/XYZWindow.class 
	$(JAVAC) ${JAVACFLAGS} -classpath ${CP} ovt/test.java ovt/*BeanInfo.java ovt/*/*BeanInfo.java

lib: $(lib-files) 

jar: $(ovt-jar)

servlets: ovt/servlet/ServletEntity.class ovt/servlet/MaxNumberOfEntitiesReachedException.class ovt/servlet/OVTNotStartedException.class ovt/servlet/HtmlEditorManager.class ovt/servlet/HtmlCheckBoxEditor.class ovt/servlet/HtmlComboBox.class ovt/servlet/HtmlComboBoxEditor.class ovt/servlet/HtmlEditorManager.class ovt/servlet/HtmlForm.class ovt/servlet/HtmlHiddenEditor.class ovt/servlet/HtmlPanel.cl ass ovt/servlet/HtmlPropertyEditor.class ovt/servlet/HtmlSliderEditor.class ovt/ servlet/HtmlTextAreaEditor.class ovt/servlet/HtmlTextEditor.class ovt/servlet/MaxNumberOfEntitiesReachedException.class ovt/servlet/OVTServlet.class
	
$(ovt-jar): classes
	cd $(top_srcdir);\
	$(JAR) cvf $@ $(FILELIST) $(IM_DIR)/*.gif

$(ovt-lib):
	cd $(SRC_DIR);\
	$(MAKE)

%.class: %.java
	$(JAVAC) ${JAVACFLAGS} -classpath ${CP} $<
#########################################################################
# Install rules
#
#.if !exists($(VTK_LIBS)/$(SHLIB_PREFIX)vtkCommonJava$(SHLIB_SUFFIX))
ifeq ($(VTK_LIBS),)
install:
	$(ECHO) "Will not install because VTK shared libraries do not exist."
	$(ECHO) "Please run ./configure --with-vtk-libs=DIR"
	exit -1
else
install: install-bin install-lib install-odata install-conf install-mdata\
	install-docs install-tex install-udata install-addons

install-bin: $(bin-files)
	$(mkinstalldirs) $(ovt_bindir) ;\
	${INSTALL} -m 755 $(bin-files) $(ovt_bindir)

install-lib: install-jar $(ovt-lib)
	$(mkinstalldirs) $(ovt_libdir) ;\
	${INSTALL} -m 555 $(ovt-lib) $(ovt_libdir)
	
install-jar: $(nobuild-jar) $(ovt-jar)
	$(mkinstalldirs) $(ovt_datadir)/$(LIB_DIR) ;\
	${INSTALL} -m 644 $(nobuild-jar) $(ovt-jar) $(ovt_datadir)/$(LIB_DIR)

install-odata:
	$(mkinstalldirs) $(ovt_datadir)/$(ODATA_DIR) ;\
	${INSTALL} -m 644 $(ODATA_DIR)/*.tle $(ODATA_DIR)/*.spin \
	$(ODATA_DIR)/*.ltof $(ovt_datadir)/$(ODATA_DIR)

install-conf: $(conf-files)
	$(mkinstalldirs) $(ovt_datadir)/$(CONF_DIR) ;\
	${INSTALL} -m 644 $(conf-files) $(ovt_datadir)/$(CONF_DIR)
	
install-mdata: $(mdata-files)
	$(mkinstalldirs) $(ovt_datadir)/$(MDATA_DIR) ;\
	${INSTALL} -m 644 $(mdata-files) $(ovt_datadir)/$(MDATA_DIR)	

install-udata: $(udata-files)
	$(mkinstalldirs) $(ovt_datadir)/$(UDATA_DIR) ;\
	${INSTALL} -m 644 $(udata-files) $(ovt_datadir)/$(UDATA_DIR)

install-tex: $(tex-files)
	$(mkinstalldirs) $(ovt_datadir)/$(TEX_DIR) ;\
	${INSTALL} -m 644 $(tex-files) $(ovt_datadir)/$(TEX_DIR)

install-docs:
	$(mkinstalldirs) $(ovt_datadir)/$(DOC_DIR) ;\
	${INSTALL} -m 644 $(doc-files) $(ovt_datadir)/$(DOC_DIR)

install-addons:
	cd addons; $(MAKE) install 	

endif

#########################################################################
# Clean rules
#
clean: clean-classes clean-lib clean-src

clean-classes:
	cd $(top_srcdir);\
	$(RM) -f $(FILELIST)

clean-lib:
	cd $(top_srcdir);\
	$(RM) -f $(lib-files-build)

#clean-bin:
#	cd $(top_srcdir);\
#	$(RM) -f $(bin-files)

clean-src:
	cd $(SRC_DIR);\
	$(MAKE) clean

#other rules

backup:
	tar cvf ovtbackup.tar *.java *.bat ovtsettings satsconfig user.make calc datatypes events graphics gui interfaces mag msmodels objects print src
	gzip ovtbackup.tar

apidocs:
	javadoc -d $(API_DOCS_DIR) \
	-windowtitle "Orbit Visualization Tool API v$(OVTVERSION)" \
	-use ovt ovt.beans \
	ovt.datatype \
	ovt.event \
	ovt.graphics \
	ovt.gui \
	ovt.interfaces \
	ovt.mag \
	ovt.mag.editor \
	ovt.mag.model \
	ovt.model \
	ovt.object \
	ovt.offscreen \
	ovt.servlet \
	ovt.util
