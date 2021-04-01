TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

QMAKE_CXXFLAGS += -march=haswell

SOURCES += \
   ./ImageManipulation.cpp ./ImageManipulation.hpp \
   ./MyImageManipulation.cpp ./MyImageManipulation.hpp \
   ./TimeTesting.cpp ./VisualTesting.cpp

QMAKE_CXXFLAGS += -march=haswell
INCLUDEPATH += /usr/include/opencv4/
# TO GET THIS LINE: ls -C1 /usr/lib/x86_64-linux-gnu/libopencv_*.so | xargs -I{} basename {} | sed 's/^lib//g' | sed "s/[.]so//g" | xargs -I{} echo '-l{}' | tr '\n' ' '
LIBS += -L/usr/lib/x86_64-linux-gnu/ -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_calib3d -lopencv_ccalib -lopencv_core -lopencv_datasets -lopencv_dnn -lopencv_dnn_objdetect -lopencv_dnn_superres -lopencv_dpm -lopencv_face -lopencv_features2d -lopencv_flann -lopencv_freetype -lopencv_fuzzy -lopencv_hdf -lopencv_hfs -lopencv_highgui -lopencv_img_hash -lopencv_imgcodecs -lopencv_imgproc -lopencv_line_descriptor -lopencv_ml -lopencv_objdetect -lopencv_optflow -lopencv_phase_unwrapping -lopencv_photo -lopencv_plot -lopencv_quality -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_shape -lopencv_stereo -lopencv_stitching -lopencv_structured_light -lopencv_superres -lopencv_surface_matching -lopencv_text -lopencv_tracking -lopencv_video -lopencv_videoio -lopencv_videostab -lopencv_viz -lopencv_ximgproc -lopencv_xobjdetect -lopencv_xphoto


# NOTE: also requires to set "Tools > Options > Build & Run > General > Projects Directory" to "Current Directory" to find the images
