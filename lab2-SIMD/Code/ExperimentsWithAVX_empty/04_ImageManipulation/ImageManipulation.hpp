#ifndef __IMAGE_MANIPULATION__
#define __IMAGE_MANIPULATION__

#include <iostream>
#include <string>

#include <ctime>
#include <ratio>
#include <chrono>

#include <opencv2/opencv.hpp>

#include <immintrin.h>

using namespace cv;
using namespace std;
using namespace std::chrono;

typedef float chanType;
typedef Vec4f pixelType;
typedef __m128 sseType;
typedef __m256 avxType;
#define IMAGE_FORMAT CV_32FC4
#define SCALE ImageManipulation::SCALE_VALUE

class ImageManipulation {
public:
    static const int MAX_TIMING_ITERATIONS;
    //static const chanType MIN_CHANTYPE;
    static const chanType MAX_CHANTYPE;
    static const double SCALE_VALUE;

    ImageManipulation();

    bool setImageSrc(string filename);
    string getImageSrcType();
    void displayImage(string title);

    long timeMethod(void (ImageManipulation::*methodToBeTimed)());
    void backupSrcImage();
    void recoverSrcImage();

    virtual void fillWithZero() = 0;           // dummy 'for' loop
    virtual void fillWithZeroOptimized() = 0;  // loop on pointer
    virtual void fillWithZeroSSE() = 0;        // use SSE registers
    virtual void fillWithZeroAVX() = 0;        // use AVX registers

    void setTargetColor(double pcA, double pcR, double pcG, double pcB);
    virtual void fillWithColor() = 0;          // dummy 'for' loop
    virtual void fillWithColorOptimized() = 0; // loop on pointer
    virtual void fillWithColorSSE() = 0;       // use sse registers
    virtual void fillWithColorAVX() = 0;       // use avx registers

    ///** Decreases pixels intensity towards 0 */
    void setAlpha(float percent);
    virtual void fadeToZero() = 0;
    // loop on pointer
    virtual void fadeToZeroAVX() = 0;        // use AVX registers

    ///** Decreases pixels intensity towards a constant color */
    // reuses setTargetColor()
    virtual void fadeToColor() = 0;        // loop on pointer
    virtual void fadeToColorAVX() = 0;     // use AVX registers

    ///** Decreases pixels intensity towards the color of the same pixel in the other image */
    /** Both images MUST be of the SAME dimensions */
    bool setImageDest(string filename);
    virtual void fadeToImage() = 0;       // loop on pointers
    virtual void fadeToImageAVX() = 0;    // use AVX pointers

    //////////////////// Optional

    //virtual void toGrey();      // loop on pointers
    //virtual void toGreyAVX();   // use AVX pointers

    //void setConvMatrix(double convMat[][]);
    //virtual void convolute();    // loop on pointers
    //virtual void convoluteAVX(); // use AVX pointers

protected:
    Mat _imageSrc;  /*alignas(alignof(int))*/
    Mat _imageSrcBackup;
    Mat _imageDest;
    chanType _targetA, _targetR, _targetG, _targetB;
    chanType _alphaNum, _alphaDen;

    string typeToString(int type);
    bool setImage(Mat *imgDest, string filename);
    void convertToTPFormat(Mat *Img);
    void displayMinMax(const Mat &img);
    string getImageType(const Mat &img);
};

#endif
