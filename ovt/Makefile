# Makefile for Orbit Visualization Tool
#
#  Source:    $Source: /stor/devel/ovt2g/ovt/Makefile,v $
#  Date:      $Date: 2006/04/19 11:42:16 $
#  Version:   $Revision: 2.11 $
#
# Please set this variables
#

top_srcdir = ${HOME}/ovt-2.3
srcdir = ovt
VPATH = $(srcdir)

.SUFFIXES:
.SUFFIXES: .java .class .jar

VTK_JAR = /home/ko/ovt-2.3/lib/vtk.jar

JAVAC = jikes -bootclasspath /usr/local/jdk1.3.1/jre/lib/rt.jar 

DOCDIR = /home/yuri/ovtdocs

#
# end of user defined variables
#
XML_JARS = $(top_srcdir)/lib/jaxp.jar:$(top_srcdir)/lib/parser.jar
VTK_OFSCR_JAR = $(top_srcdir)/lib/vtk31_offscr_only.jar
SERVLET_JAR = /usr/local/tomcat/lib/servlet.jar

CP = ${CLASSPATH}:$(top_srcdir):${VTK_JAR}:$(XML_JARS):$(VTK_OFSCR_JAR):$(SERVLET_JAR)

JAVADOC = javadoc
JAR = jar

FILELIST = $(srcdir)/*.class $(srcdir)/*/*.class $(srcdir)/*/*/*.class

JAR_FILE = ovt
LIBDIR = lib

all: XYZWindow.class

jar: $(LIBDIR)/$(JAR_FILE).jar

servlets: servlet/ServletEntity.class

clean:
	cd $(top_srcdir);\
	rm -f $(FILELIST)
	
$(LIBDIR)/$(JAR_FILE).jar: all
	cd $(top_srcdir);\
	$(JAR) cvf $@ images/*.gif $(FILELIST)

%.class: %.java
	$(JAVAC) -classpath ${CP} $<

#other rules

backup:
	tar cvf ovtbackup.tar *.java *.bat ovtsettings satsconfig user.make calc datatypes events graphics gui interfaces mag msmodels objects print src
	gzip ovtbackup.tar

docs:
	$(JAVADOC) -d $(DOCDIR) ovt ovt.gui ovt.beans ovt.object ovt.object.editor ovt.mag ovt.mag.model ovt.mag.editor ovt.util ovt.datatype ovt.interfaces ovt.event
