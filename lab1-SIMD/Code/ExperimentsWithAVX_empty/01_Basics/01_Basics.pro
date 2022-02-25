TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += FusedMultAdd.cpp

INCLUDEPATH += /usr/lib/gcc/x86_64-linux-gnu/9/include/ /usr/include/c++/9


QMAKE_CXXFLAGS += -march=haswell
