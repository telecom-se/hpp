#include "ImageManipulation.hpp"
#include "TimeTesting.h"

void displayTime(string methodName, long time) {
    cout << "Time to compute " << ImageManipulation::MAX_TIMING_ITERATIONS << " " << methodName << ": " << time << endl;
}

void timeFillZeros(ImageManipulation manip) {
    long time = manip.timeMethod(&ImageManipulation::fillWithZero);
    displayTime("fillZeroNormal", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithZeroOptimized);
    displayTime("fillZeroOptimized", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithZeroSSE);
    displayTime("fillZeroSSE", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithZeroAVX);
    displayTime("fillZeroAVX", time);
}

void timeFillColors(ImageManipulation manip) {
    manip.setTargetColor(128, 255, 128, 128);
    long time = manip.timeMethod(&ImageManipulation::fillWithColor);
    displayTime("fillColorNormal", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithColorOptimized);
    displayTime("fillColorOptimized", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithColorSSE);
    displayTime("fillColorSSE", time);
    long time = manip.timeMethod(&ImageManipulation::fillWithColorAVX);
    displayTime("fillColorAVX", time);
}

void timeFadeZeros(ImageManipulation manip) {
    manip.setAlpha(.8);
    long time = manip.timeMethod(&ImageManipulation::fadeToZero);
    displayTime("fadeToZeroNormal", time);
    long time = manip.timeMethod(&ImageManipulation::fadeToZeroAVX);
    displayTime("fadeToZeroAVX", time);
}

void timeFadeColors(ImageManipulation manip) {
    manip.setAlpha(.6);
    manip.setTargetColor(0, 0, ImageManipulation::MAX_CHANTYPE, 0);
    long time = manip.timeMethod(&ImageManipulation::fadeToColor);
    displayTime("fadeToColorNormal", time);
    long time = manip.timeMethod(&ImageManipulation::fadeToColorAVX);
    displayTime("fadeToColorAVX", time);
}

void timeFadeImages(ImageManipulation manip) {
    manip.setTargetImage("C:/Users/Utilportable/Downloads/big_image2.jpg");
    long time = manip.timeMethod(&ImageManipulation::fadeToImage);
    displayTime("fadeToImageNormal", time);
    long time = manip.timeMethod(&ImageManipulation::fadeToImageAVX);
    displayTime("fadeToImageAVX", time);
}

int main2(int argc, char** argv)  // Rename into "main" to use it
{
    string filename = "C:/Users/Utilportable/Downloads/big_image.jpg";
    ImageManipulation manip;
    manip.loadImage(filename);
    cout << "** Image is of type :   " << manip.getImageSrcType() << endl;

    timeFillZeros(manip);
    cout << "****************************************" << endl;

    timeFillColors(manip);
    cout << "****************************************" << endl;

    timeFadeZeros(manip);
    cout << "****************************************" << endl;

    timeFadeColors(manip);
    cout << "****************************************" << endl;

    timeFadeImages(manip);
    cout << "****************************************" << endl;

    return 0;
}
