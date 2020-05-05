#include "ImageManipulation.hpp"

// DONE : forced convertion of image to type that allows working with round number of pixels in each iteration of the loop
// NOTE: accessing individual pixels can be done with : mat.at<pixelType/chanType>(r,c) or cv::mat.ptr<pixelType/chanType>(r,c)

//const chanType ImageManipulation::MIN_CHANTYPE = std::numeric_limits<chanType>::lowest(); // what's the smallest number we can represent with chanType
const chanType ImageManipulation::MAX_CHANTYPE = /*255*/1.0/*TODO:automatize*/; // what's the biggest number we can represent with chanType
const int ImageManipulation::MAX_TIMING_ITERATIONS = 10; // Nb of time each method is run when timing it
const double ImageManipulation::SCALE_VALUE = 1./255.;

ImageManipulation::ImageManipulation() {
}

/** Returns true if loading of image was OK, false otherwise. */
bool ImageManipulation::setImage(Mat *imgDest, string filename) {
    // Read the image file
    *imgDest = imread(filename);

    if (_imageSrc.empty()) { // Check for failure
        cerr << "Could not open or find the image: '" << filename << "'" << endl;
        //system("pause"); //wait for any key press
        return false;
    }

    cout << "** ORIGINAL image is of type:   " << getImageType(*imgDest) << endl;
    cout << "** ORIGINAL image min/max:   "; displayMinMax(*imgDest);

    /// force type to IMAGE_FORMAT
    this->convertToTPFormat(imgDest);
    cout << "** CONVERTED image is of type :   " << getImageType(*imgDest) << endl;
    cout << "** CONVERTED image min/max:   "; displayMinMax(*imgDest);
    return true;
}
bool ImageManipulation::setImageSrc(string filename) {
    return(setImage(&_imageSrc, filename));
}
bool ImageManipulation::setImageDest(string filename) {
    return(setImage(&_imageDest, filename));
}


void ImageManipulation::displayImage(string title) {
    namedWindow(title, WINDOW_AUTOSIZE); // Create a window
    imshow(title, _imageSrc); // Show our image inside the created window.
    waitKey(0); // Wait for any keystroke in the window
    destroyWindow(title); //destroy the created window
}

long ImageManipulation::timeMethod(void (ImageManipulation::*methodToBeTimed)()) {
    long totalDuration = 0;
    this->backupSrcImage();

    for (int i = 0; i < MAX_TIMING_ITERATIONS; i++) {
        high_resolution_clock::time_point time1 = high_resolution_clock::now();

        (this->*methodToBeTimed)();

        high_resolution_clock::time_point time2 = high_resolution_clock::now();
        duration<double, std::milli> duration = time2 - time1;

        totalDuration += (long)(time2 - time1).count();

        // Uncomment to keep the last execution of the method as a side effect of timing the method
//      if (i!=MAX_TIMING_ITERATIONS-1) {
        this->recoverSrcImage();
        //        }
    }

    return(totalDuration / MAX_TIMING_ITERATIONS);
}

void ImageManipulation::backupSrcImage() {
    _imageSrcBackup = _imageSrc.clone();
}
void ImageManipulation::recoverSrcImage() {
    _imageSrc = _imageSrcBackup.clone();
}

// From: https://stackoverflow.com/questions/10167534/how-to-find-out-what-type-of-a-mat-object-is-with-mattype-in-opencv
string ImageManipulation::typeToString(int type) {
    string r;

    uchar depth = type & CV_MAT_DEPTH_MASK;
    uchar chans = 1 + (type >> CV_CN_SHIFT);

    switch (depth) {
    case CV_8U:  r = "8U"; break;
    case CV_8S:  r = "8S"; break;
    case CV_16U: r = "16U"; break;
    case CV_16S: r = "16S"; break;
    case CV_32S: r = "32S"; break;
    case CV_32F: r = "32F"; break;
    case CV_64F: r = "64F"; break;
    default:     r = "User"; break;
    }

    r += "C";
    r += (chans + '0');

    r += "(";
    r += _imageSrc.isContinuous() ? "" : "DIS";
    r += "cont)";

    return r;
}

/* pcA/R/G/B \in [0.0,1.0] */
void ImageManipulation::setTargetColor(double pcR, double pcG, double pcB, double pcA) {
    _targetR = (chanType) (pcR*ImageManipulation::MAX_CHANTYPE);
    _targetG = (chanType) (pcG*ImageManipulation::MAX_CHANTYPE);
    _targetB = (chanType) (pcB*ImageManipulation::MAX_CHANTYPE);
    _targetA = (chanType) (pcA*ImageManipulation::MAX_CHANTYPE);
}

/* percent \in [0.0,1.0] */
void ImageManipulation::setAlpha(float percent) {
    _alphaNum = percent*100.0;
    _alphaDen = 100.0;
}

string ImageManipulation::getImageType(const Mat &img) {
    return typeToString(img.type());
}

string ImageManipulation::getImageSrcType() {
    return getImageType(_imageSrc);
}

/** Convert to 4 Channels in RGBA order */
void ImageManipulation::convertToTPFormat(Mat *img) {
    Mat converted;

    // Add a channel
    cvtColor(*img, converted, COLOR_RGB2RGBA, 4);
    // Convert from 8UC4 to 32S(int)C4
    //converted.convertTo(converted, IMAGE_FORMAT);
    converted.convertTo(converted, IMAGE_FORMAT, SCALE/*scale*/, 0./*shift*/);

    img->release();
    *img = Mat(converted);
}

void ImageManipulation::displayMinMax(const Mat &img) {
    double minVal, maxVal;
    minMaxLoc(img, &minVal, &maxVal);
    cout << "minVal: " << minVal << "   // maxVal: " << maxVal << endl;
}

