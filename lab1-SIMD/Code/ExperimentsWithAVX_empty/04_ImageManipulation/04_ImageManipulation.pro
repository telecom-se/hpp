TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

QMAKE_CXXFLAGS += -march=haswell

SOURCES += \
   ./ImageManipulation.cpp \ # ./ImageManipulation.hpp \
   ./MyImageManipulation.cpp \ # ./MyImageManipulation.hpp \
   ./TimeTesting.cpp ./VisualTesting.cpp

QMAKE_CXXFLAGS += -march=haswell
INCLUDEPATH += $$(OPENCV_DIR)/../../include /usr/include/opencv4/

# TO GET THIS LINE: ls -C1 /usr/lib/x86_64-linux-gnu/libopencv_*.so | xargs -I{} basename {} | sed 's/^lib//g' | sed "s/[.]so//g" | xargs -I{} echo '-l{}' | tr '\n' ' '
LIBS += -L$$(OPENCV_DIR)/lib -L/usr/lib/x86_64-linux-gnu/ \
    -lopencv_core \
    -lopencv_highgui \
    -lopencv_imgproc \
    -lopencv_imgcodecs \
    -lopencv_videoio \
    -lopencv_features2d \
    -lopencv_calib3d

# NOTE: also requires to set "Tools > Options > Build & Run > General > Projects Directory" to "Current Directory" to find the images
