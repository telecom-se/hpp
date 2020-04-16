TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
    ./FusedMultAdd.cpp

QMAKE_CXXFLAGS += -march=haswell
