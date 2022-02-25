#include "MyImageManipulation.hpp"

MyImageManipulation::MyImageManipulation() : ImageManipulation() {
}

const int MyImageManipulation::MAX_TIMING_ITERATIONS = 4; // Nb of time each method is run when timing it

long MyImageManipulation::timeMethod(void (MyImageManipulation::*methodToBeTimed)()) {
  long totalDuration = 0;
  this->backupSrcImage();

  for (int i = 0; i < MAX_TIMING_ITERATIONS; i++) {
    high_resolution_clock::time_point time1 = high_resolution_clock::now();

    (this->*methodToBeTimed)();

    high_resolution_clock::time_point time2 = high_resolution_clock::now();
    milliseconds duration = duration_cast<milliseconds>(time2 - time1);
    totalDuration += (long)duration.count();

    // Uncomment to keep the last execution of the method as a side effect of timing the method
    //      if (i!=MAX_TIMING_ITERATIONS-1) {
    this->recoverSrcImage();
    //        }
  }

  return(totalDuration / MAX_TIMING_ITERATIONS);
}


//// Fill-Zero

void MyImageManipulation::fillWithZero() {
  //TODO: implement
}

void MyImageManipulation::fillWithZeroOptimized() {
  //TODO: implement
}

const int NB_CHANTYPE_IN_128 = 128 / (8 * sizeof(chanType));  // 4 floats = 4*(8*4Ui64) bits = 128 bits = 1 SSE register = 1 pixel
void MyImageManipulation::fillWithZeroSSE() {
  //TODO: implement
}

const int NB_CHANTYPE_IN_256 = 256 / (8 * sizeof(chanType)); // 8 floats = 8*(8*4Ui64) bits = 256 bits = 1 AVX register = 2 pixels
void MyImageManipulation::fillWithZeroAVX() {
  //TODO: implement
}

///// Fill-Color

void MyImageManipulation::fillWithColor() {
  //TODO: implement
}

void MyImageManipulation::fillWithColorOptimized() {
  //TODO: implement
}

// Reuses NB_CHANTYPE_IN_128
void MyImageManipulation::fillWithColorSSE() {
  //TODO: implement
}

// Reuses NB_CHANTYPE_IN_256
void MyImageManipulation::fillWithColorAVX() {
  //TODO: implement
}

//// Fade-0

void MyImageManipulation::fadeToZero() {
  //TODO: implement
}

// Reuses NB_CHANTYPE_IN_256
void MyImageManipulation::fadeToZeroAVX() {
  //TODO: implement
}


//// Fade-Color

void MyImageManipulation::fadeToColor() {
  //TODO: implement
}

// Reuses NB_CHANTYPE_IN_256
void MyImageManipulation::fadeToColorAVX() {
  //TODO: implement
}


/** Both images MUST be of the SAME dimensions */
void MyImageManipulation::fadeToImage() {
  //TODO: implement
}



/* Images MUST be of the SAME dimensions */
void MyImageManipulation::fadeToImageAVX() {
  //TODO: implement
}
