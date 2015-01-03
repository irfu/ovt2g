#
# Makefile.in to install OVT addons - KDE icons, ovt.desktop
#
# $Id: Makefile.in,v 1.1 2003/10/16 11:37:00 ko Exp $
#

OVTVERSION = 2.3

top_srcdir = /home/yuri/devel/ovt2g


prefix = /usr/local
exec_prefix = ${prefix}
mkinstalldirs = $(top_srcdir)/mkinstalldirs

kde_prefix = 
kde_appsdir = ${kde_prefix}/share/applnk
kde_icondir = ${kde_prefix}/share/icons

OVT_DESKTOP_BASE = $(kde_appsdir)/Edutainment/Science

INSTALL = /usr/bin/install -c
INSTALL_DATA = ${INSTALL} -m 644

all:

install:
	if [ -d $(kde_appsdir) ] ; then \
		$(mkinstalldirs) $(OVT_DESKTOP_BASE) ; \
		${INSTALL_DATA} ovt.desktop $(OVT_DESKTOP_BASE) ; \
	fi
	if [ -d $(kde_icondir) ] ; then \
		for i in 32 48 64 ; do \
			$(mkinstalldirs) $(kde_icondir)/hicolor/$$i"x"$$i/apps ; \
			${INSTALL_DATA} hi$$i-app-ovt.png $(kde_icondir)/hicolor/$$i"x"$$i/apps/ovt.png ; \
		done ; \
	fi
