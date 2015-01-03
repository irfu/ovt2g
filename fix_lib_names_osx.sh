#!/bin/sh

PREF=libvtk
VEMA=5.4
VEMI=2
VERS=${VEMA}.${VEMI}
FRAMEWORK_PATH=Frameworks/VTK.framework/Versions/${VERS}/VTK
APP_PATH=/Users/yuri/Applications/ovt.app
VTK_BUILD=/Users/yuri/devel/VTKBuild/bin

LIB_LIST="Common\
 CommonJava\
 DICOMParser\
 Filtering\
 FilteringJava\
 Graphics\
 GraphicsJava\
 Hybrid\
 HybridJava\
 IO\
 IOJava\
 Imaging\
 ImagingJava\
 NetCDF\
 Rendering\
 RenderingJava\
 exoIIc\
 expat\
 freetype\
 ftgl\
 jpeg\
 metaio\
 png\
 sqlite\
 sys\
 tiff\
 verdict\
 zlib"

JAVA_LIBS="Common\
 Filtering\
 Graphics\
 Hybrid\
 IO\
 Imaging\
 Rendering"

[ ! -d "$VTK_BUILD" ] && echo "VTK_BUILD points to nonexistent $VTK_BUILD" && exit 1
[ ! -d "$APP_PATH" ] && echo "APP_PATH points to nonexistent $APP_PATH" && exit 1

FULL_FRMW_PATH=$APP_PATH/Contents/$FRAMEWORK_PATH

[ -d "$FULL_FRMW_PATH" ] && echo "Old libs are in the way. Please remove $FULL_FRMW_PATH" && exit 1

mkdir -p $FULL_FRMW_PATH || ( echo "Failed to create $FULL_FRMW_PATH" && exit 1)

for LIB in $LIB_LIST
do
	LIB_NAME=${PREF}${LIB}.${VEMA}.${VEMI}.dylib 
	LIB_NAM1=${PREF}${LIB}.${VEMA}.dylib 
	LIB_NAM2=${PREF}${LIB}.dylib 

	[ ! -e "$VTK_BUILD/$LIB_NAME" ] && echo "Cannot find $LIB_NAME" && continue
	/bin/echo -n "Processing $LIB_NAME... "
	cp $VTK_BUILD/$LIB_NAME $FULL_FRMW_PATH/
	(cd $FULL_FRMW_PATH && ln -s $LIB_NAME $LIB_NAM1 && ln -s $LIB_NAM1 $LIB_NAM2) 
	install_name_tool -id @executable_path/../$FRAMEWORK_PATH/$LIB_NAM1 $FULL_FRMW_PATH/$LIB_NAME
	UPD_LIBS=`otool -L $FULL_FRMW_PATH/$LIB_NAME |grep -v "@executable_path" | grep .${VEMA}.dylib | awk '{print $1}'`
	for LU in $UPD_LIBS
	do
		install_name_tool -change $LU @executable_path/../$FRAMEWORK_PATH/$LU $FULL_FRMW_PATH/$LIB_NAME
	done
	echo Done.
done

JAVA_PATH=$APP_PATH/Contents/Resources/Java

/bin/echo -n "Linking JNI libs... "
for JL in $JAVA_LIBS
do
	LIB_NAME=${PREF}${JL}Java 
	[ -e "$JAVA_PATH/$LIB_NAME.jnilib" ] && rm $JAVA_PATH/$LIB_NAME.jnilib 
	( cd $JAVA_PATH && ln -s ../../Frameworks/VTK.framework/VTK/$LIB_NAME.dylib $LIB_NAME.jnilib ) 
done
echo Done.
