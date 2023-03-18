#include "MyImageManipulation.hpp"

int main(int argc, char** argv)
{
  string srcImage  = "../big_image1.jpg";
  string destImage = "../big_image2.jpg";  /* MUST BE OF THE SAME SIZE!!!!*/

  MyImageManipulation manip;

  bool ok = manip.setImageSrc(srcImage);

  if (ok) {
    //// DEBUG
    // cout << ImageManipulation::MAX_CHANTYPE << endl;
    // cout << SCALE << endl;

    manip.displayImage("ORIGINAL image");
    manip.backupSrcImage();

    ////// Fill-Zero

    //manip.fillWithZero();
    //manip.displayImage("Normal Fill*0*");
    //manip.recoverSrcImage();

    //manip.fillWithZeroOptimized();
    //manip.displayImage("Optimized Fill*0*");
    //manip.recoverSrcImage();

    //manip.fillWithZeroSSE();
    //manip.displayImage("SSE Fill*0*");
    //manip.recoverSrcImage();

    //manip.fillWithZeroAVX();
    //manip.displayImage("AVX Fill*0*");
    //manip.recoverSrcImage();

    //// Fill-Color
    //// RGBA
    //manip.setTargetColor(1.0, 0.0, 0.0, 0.0);

    //manip.fillWithColor();
    //manip.displayImage("Normal Fill*C*");
    //manip.recoverSrcImage();

    //manip.fillWithColorOptimized();
    //manip.displayImage("Optimized Fill*C*");
    //manip.recoverSrcImage();

    //manip.fillWithColorSSE();
    //manip.displayImage("SSE Fill*C*");
    //manip.recoverSrcImage();

    //manip.fillWithColorAVX();
    //manip.displayImage("AVX Fill*C*");
    //manip.recoverSrcImage();

    //// Fade-0
    //manip.setAlpha(.8);

    //manip.fadeToZero();
    //manip.displayImage("Normal Fade*0*-1");
    //manip.fadeToZero();
    //manip.displayImage("Normal Fade*0*-2");
    //manip.fadeToZero();
    //manip.displayImage("Normal Fade*0*-3");
    //manip.recoverSrcImage();

    //manip.fadeToZeroAVX();
    //manip.displayImage("AVX Fade*0*-1");
    //manip.fadeToZeroAVX();
    //manip.displayImage("AVX Fade*0*-2");
    //manip.fadeToZeroAVX();
    //manip.displayImage("AVX Fade*0*-3");
    //manip.recoverSrcImage();

    ////// Fade-Color
    //manip.setAlpha(.7);
    //// %RGBA
    //manip.setTargetColor(0.0, 0.0, 1.0, 0.0);

    //manip.fadeToColor();
    //manip.displayImage("Normal Fade*C*-1");
    //manip.fadeToColor();
    //manip.displayImage("Normal Fade*C*-2");
    //manip.fadeToColor();
    //manip.displayImage("Normal Fade*C*-3");
    //manip.recoverSrcImage();

    //manip.fadeToColorAVX();
    //manip.displayImage("AVX Fade*C*-1");
    //manip.fadeToColorAVX();
    //manip.displayImage("AVX Fade*C*-2");
    //manip.fadeToColorAVX();
    //manip.displayImage("AVX Fade*C*-3");
    //manip.recoverSrcImage();

    //manip.setAlpha(.70);
    //manip.setImageDest(destImage);
    //manip.fadeToImage();
    //manip.displayImage("Normal Fade*I*-1");
    //manip.fadeToImage();
    //manip.displayImage("Normal Fade*I*-2");
    //manip.fadeToImage();
    //manip.displayImage("Normal Fade*I*-3");

    //manip.setImageDest(destImage);
    //manip.fadeToImageAVX();
    //manip.displayImage("AVX Fade*I*-1");
    //manip.fadeToImageAVX();
    //manip.displayImage("AVX Fade*I*-2");
    //manip.fadeToImageAVX();
    //manip.displayImage("AVX Fade*I*-3");

  }

  return 0;
}


// Final fading "animation"
int main_final(int argc, char** argv) {
    string srcImage  = "../big_image1.jpg";
    string destImage = "../big_image2.jpg";  /* MUST BE OF THE SAME SIZE!!!!*/

    MyImageManipulation manip;

    bool ok1 = manip.setImageSrc(srcImage);
    bool ok2 = manip.setImageDest(srcImage);

    if (ok1 && ok2) {
        // More fun testing
        manip.setImageSrc(srcImage);
        manip.backupSrcImage();
        manip.setImageDest(destImage);
        for(float alpha = 1.0 ; alpha > 0.0 ; alpha-=.05) {
            cout << "Computing image" << "\n";
            manip.setAlpha(alpha);
            manip.fadeToImageAVX();
            cout << "Displaying image" << "\n";
            manip.displayImage("AVX Animation - " + to_string(alpha), 500);
            manip.recoverSrcImage();
        }
    }
    return 0;
}
