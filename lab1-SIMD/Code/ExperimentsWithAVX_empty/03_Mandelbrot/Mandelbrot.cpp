// Inspired from: https://software.intel.com/en-us/articles/introduction-to-intel-advanced-vector-extensions

// Standard outputs/strings
#include <iostream>
#include <string>

// TODO: include AVX intrinsics

#include "utils.hpp" // Utility functions to extract values from __m128&__m256i

using namespace std;


// Compute Mandelbrot (Basic C++ with objects)
#include <complex>
void mandelbrotCPU(float x1, float y1, float x2, float y2, int width, int height, int maxIters, unsigned short *image) {
  float dx = (x2-x1)/width, dy = (y2-y1)/height;
  for (int j = 0; j < height; j++)
    for (int i = 0; i < width; i++) {
      complex<float> c (x1+dx*i, y1+dy*j), z(0,0);
      unsigned short count = 0;
      while ((count++ < maxIters) && (norm(z) < 4.0))
        z = z*z+c;
      *image++ = count;
    }
}

// Compute Mandelbrot (Basic C++ with simple floats)
void mandelbrotCPU2(float x1, float y1, float x2, float y2, int width, int height, int maxIters, unsigned short *image) {
  // TODO: implement
}

// Compute Mandelbrot with SSE
void mandelbrotSSE(float x1, float y1, float x2, float y2, int width, int height, int maxIters, unsigned short *image) {
  // TODO: implement
}

// Compute Mandelbrot with AVX
void mandelbrotAVX(float x1, float y1, float x2, float y2, int width, int height, int maxIters, unsigned short *image) {
  // TODO: implement
}

// Display images
#include <opencv2/opencv.hpp>
// COLORMAP_AUTUMN = 0,
// COLORMAP_BONE = 1,
// COLORMAP_JET = 2,
// COLORMAP_WINTER = 3,
// COLORMAP_RAINBOW = 4,
// COLORMAP_OCEAN = 5,
// COLORMAP_SUMMER = 6,
// COLORMAP_SPRING = 7,
// COLORMAP_COOL = 8,
// COLORMAP_HSV = 9,
// COLORMAP_PINK = 10,
// COLORMAP_HOT = 11,
// COLORMAP_PARULA = 12
using namespace cv;
void displayImage(string title, unsigned short *image, int size) {
  // Transform image to OpenCV type
  Mat colored(size, size*2, CV_8UC1, image, Mat::AUTO_STEP/*sizeof(unsigned short)*size*/);  //HACK: unsigned short = 2U = 2*8 = 16 bits => should be size*size*CV16
  applyColorMap(colored, colored, COLORMAP_JET);
  // Show the image & wait for keypress
  namedWindow(title);
  imshow(title, colored);
  waitKey(0);
  destroyWindow(title);
}


int main(int argc, char **argv) {
  int size = 200;

  unsigned short *image1 = new unsigned short[size*size];
  unsigned short *image2 = new unsigned short[size*size];
  unsigned short *image3 = new unsigned short[size*size];
  unsigned short *image4 = new unsigned short[size*size];

  mandelbrotCPU(0.29768f, 0.48364f, 0.29778f, 0.48354f, size, size, 4096, image1);
  cout << "displaying CPU" << endl;
  displayImage("Normal CPU", image1, size);

  mandelbrotCPU2(0.29768f, 0.48364f, 0.29778f, 0.48354f, size, size, 4096, image2);
  cout << "displaying CPU-Optimized" << endl;
  displayImage("CPU Optimized", image2, size);

  mandelbrotSSE(0.29768f, 0.48364f, 0.29778f, 0.48354f, size, size, 4096, image3);
  cout << "displaying SSE" << endl;
  displayImage("SSE", image3, size);

  mandelbrotAVX(0.29768f, 0.48364f, 0.29778f, 0.48354f, size, size, 4096, image4);
  cout << "displaying AVX" << endl;
  displayImage("AVX", image4, size);

  delete [] image1;
  delete [] image2;
  delete [] image3;
  delete [] image4;
}
