TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

QMAKE_CXXFLAGS += -march=haswell

SOURCES += \
    ./Mandelbrot.cpp \
    ./utils.cpp ./utils.hpp

QMAKE_CXXFLAGS += -march=haswell
INCLUDES += -I/usr/include/opencv2/
LIBS += -L/usr/lib/x86_64-linux-gnu/ -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_calib3d -lopencv_ccalib -lopencv_core -lopencv_datasets -lopencv_dpm -lopencv_face -lopencv_features2d -lopencv_flann -lopencv_freetype -lopencv_fuzzy -lopencv_hdf -lopencv_highgui -lopencv_imgcodecs -lopencv_imgproc -lopencv_line_descriptor -lopencv_ml -lopencv_objdetect -lopencv_optflow -lopencv_phase_unwrapping -lopencv_photo -lopencv_plot -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_shape -lopencv_stereo -lopencv_stitching -lopencv_structured_light -lopencv_superres -lopencv_surface_matching -lopencv_text -lopencv_video -lopencv_videoio -lopencv_videostab -lopencv_viz -lopencv_ximgproc -lopencv_xobjdetect -lopencv_xphoto
