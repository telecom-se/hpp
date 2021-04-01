TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
    ./MatrixMultiplication.cpp

QMAKE_CXXFLAGS += -march=haswell
