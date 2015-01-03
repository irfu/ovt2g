#
# This script sets the environement variables to run ovt servlet.
#
# It should be executed before running Tomcat
# in tomcat.sh add ". /usr/local/ovt2g/bin/set_servlet_envs.sh"
#
#CLASSPATH=.:/home/ko/lib/vtk31offscr.jar:/home/ko/ovt2g:/usr/j2sdk1_3_0/lib/tools.jar; export CLASSPATH

SERVLET_CLASSES=/usr/local/tomcat/lib/servlet.jar

OVT_HOME=/home/yuri/ovt2g; export OVT_HOME
OVT_USER_HOME=$OVT_HOME; export OVT_USER_HOME

JARS=$OVT_HOME/lib/jaxp.jar:$OVT_HOME/lib/parser.jar

CLASSPATH=.:$JARS:/usr/local/vtk/vtk.jar:/home/yuri/ovt2g:/usr/java/lib/tools.jar:$SERVLET_CLASSES; export CLASSPATH

LD_LIBRARY_PATH=/usr/local/lib:/home/yuri/ovt2g/lib; export LD_LIBRARY_PATH;
