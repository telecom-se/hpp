# Required Software

To participate in the SIMD/MMX/SSE/AVX labs, you will need:
- A C/C++ compiler
- A code editor (some IDEs already include a compiler)
- The OpenCV library (used to display images in exercises 3&4)

The instructions below will help you install and configure these tools.

## Preliminary note

Last year, when everybody could work on the machines provided by TSE,
everybody used Windows 7 and Visual Studio 2015 to do the labs, as
these software are pre-installed.

This year, because of the COVID-19 crisis, we are all at home, some
with very bad internet connexion, others with bad computers.

Unfortunately, to install Visual Studio, you need:
- A large band connexion (VS2019 requires downloading ~20GB!)
- A stable connexion (VS2019's installer does not know how to recover
  from network interruptions and re-downloads most packages when this
  occurs, thus the installation is endless with a bad network)
- A well equiped computer (VS2019 requires at least 2GB RAM...)
- Windows (i.e. at least additional 4GB RAM...)

As a consequence, I have adapted the labs and they now can be done
under Windows or Linux with very lightweight software (~200MB).

I have personally resolved the labs under the following environments:
- Text Editor + Manual compilation
- **Code::Blocks**
- VSCode + C/C++ Extension
- Visual Studio 2019 (Windows Only)

**For this year, I'll ask you as much as possible to use Code::Blocks, as it will **

You'll find below instructions to install these software.

## Software Installation

In the following, the first link is generally the direct link to
download the last version of the tool. The other links are here for
reference (where to find this download link).

- C/C++ compiler [Code::Blocks includes a compiler]
  - GCC / MinGW ~50
    https://osdn.net/dl/mingw/mingw-get-setup.exe
    https://osdn.net/projects/mingw/releases/
    [w64 versions...]
    https://sourceforge.net/projects/mingw-w64/files/Toolchains%20targetting%20Win32/Personal%20Builds/mingw-builds/installer/mingw-w64-install.exe/download
    https://mingw-w64.org/doku.php/download
  - **or** [**NOT TESTED**] LLVM / CLang
    https://github.com/llvm/llvm-project/releases/download/llvmorg-10.0.0/LLVM-10.0.0-win64.exe
    https://releases.llvm.org/download.html

-  OpenCV 3.4.x (ATTENTION je n'ai pas testé avec la 4.x.x)
   https://sourceforge.net/projects/opencvlibrary/files/3.4.10/opencv-3.4.10-vc14_vc15.exe/download
   https://opencv.org/releases/

- A code editor
  - Code::Blocks ~150Mo avec le compilateur
    https://www.fosshub.com/Code-Blocks.html?dwl=codeblocks-20.03mingw-setup.exe )
    https://www.youtube.com/watch?v=aS5_jrIbKmA
  - VSCode
    https://sourceforge.net/projects/opencvlibrary/files/3.4.10/opencv-3.4.10-vc14_vc15.exe/download
    https://www.youtube.com/watch?v=DIw02CaEusY


# Compilation Manuelle
/usr/bin/g++ -march=haswell \
              -g Mandelbrot.cpp utils.cpp\
              -o Mandel.exe\
              -L/usr/lib/x86_64-linux-gnu/\
              -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_calib3d -lopencv_ccalib -lopencv_core -lopencv_datasets -lopencv_dpm -lopencv_face -lopencv_features2d -lopencv_flann -lopencv_freetype -lopencv_fuzzy -lopencv_hdf -lopencv_highgui -lopencv_imgcodecs -lopencv_imgproc -lopencv_line_descriptor -lopencv_ml -lopencv_objdetect -lopencv_optflow -lopencv_phase_unwrapping -lopencv_photo -lopencv_plot -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_shape -lopencv_stereo -lopencv_stitching -lopencv_structured_light -lopencv_superres -lopencv_surface_matching -lopencv_text -lopencv_video -lopencv_videoio -lopencv_videostab -lopencv_viz -lopencv_ximgproc -lopencv_xobjdetect -lopencv_xphoto
