# Required Software

To participate in the SIMD/MMX/SSE/AVX labs, you will need:
- A C/C++ compiler
- A code editor (some IDEs already include a compiler)
- The OpenCV library (used to display images in exercises 3&4)

The instructions below will help you install and configure these tools.

## Preliminary note

Last year, when everybody could work on the machines provided by TSE,
everybody used Windows 7 and Visual Studio 2015(?) to do the labs, as
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

I have personally solved the labs under the following environments:
- Text/Code Editor + Manual compilation
- Code::Blocks
- QtCreator
- VSCode + C/C++ Extension
- Visual Studio 2019 (Windows Only)

In the zip file provided with the skeleton code you'll complete, there
are project files for these tools. **Unfortunately, you might be using
another OS, another compiler, another installation path for OpenCV,
etc. than I did when I created them. Thus these files will need to be
adapted.**

**For this year, I'll ask you as much as possible to use QTCreator, as
it will ease my job if everybody uses the same tool.**

Below, you'll find instructions to install these software.

## Software Installation

In the following, the [DWL] link is generally the direct link to
download the last version of the tool. The [REF] link is here for
reference (where to find the [DWL] link. There is also a [YT!] link to
a youtube video that explains how to install the tool.

- A code editor
  - Code::Blocks [install the version that includes the compiler (MinGW-32bits)]
    - [DWL] https://www.fosshub.com/Code-Blocks.html?dwl=codeblocks-20.03mingw-setup.exe
    - [REF] http://www.codeblocks.org/downloads
    - [YT!] https://www.youtube.com/watch?v=aS5_jrIbKmA

  - QtCreator [requires a Qt account] (during install, select Qt5>MinGW-64bits and MinGW-64bits compiler)
    - [DWL] https://www.qt.io/download-qt-installer
    - [REF] http://qt.io/
    - [YT!] TODO https://www.youtube.com/watch?v=XpRAaw-vZNU

  - VSCode [requires 1. an external compiler & 2. the C/C++ extension]
    - [DWL] https://az764295.vo.msecnd.net/stable/a9f8623ec050e5f0b44cc8ce8204a1455884749f/VSCodeUserSetup-x64-1.44.1.exe
    - [REF] https://code.visualstudio.com/Download
    - [DWL] https://github.com/microsoft/vscode-cpptools/releases/download/0.27.0/cpptools-win32.vsix [can be installed from within VSCode!]
    - [REF] https://github.com/microsoft/vscode-cpptools/releases/
    - [YT!] https://www.youtube.com/watch?v=DIw02CaEusY

-  OpenCV 3.4.x (**ATTENTION il s'agit d'une version spéciale compilée pour MinGW**)
   - 64bits
     - [DWL] https://github.com/huihut/OpenCV-MinGW-Build/archive/OpenCV-3.4.8-x64.zip
   - 32bits
     - [DWL] https://github.com/huihut/OpenCV-MinGW-Build/archive/OpenCV-3.4.9.zip
   - [REF] https://github.com/huihut/OpenCV-MinGW-Build

- [*If your IDE does not include one*] C/C++ compiler
  - GCC / MinGW
    - [REF] http://mingw.org/category/wiki/download
    - 64 bits
      - [DWL] https://sourceforge.net/projects/mingw-w64/files/Toolchains%20targetting%20Win32/Personal%20Builds/mingw-builds/installer/mingw-w64-install.exe/download
      - [REF] https://mingw-w64.org/doku.php/download
    - 32 bits
      - [DWL] https://osdn.net/projects/mingw/downloads/68260/mingw-get-setup.exe/
      - [REF] https://osdn.net/projects/mingw/releases/p15522
    - [YT!] https://www.youtube.com/watch?v=sXW2VLrQ3Bs [you can uncheck ada/fortran/objc installation]

# Configuration of the tools

Whatever the tools you have installed, you'll need to configure:
1. The compiler
   1. to use the special SIMD instructions (i.e. compile for an
   architecture that is more evolved than the default i386)
   2. to find the OpenCV's include/header (.h) files
2. The linker:
   - to find the OpenCV's (`.dll`/`.so`) libraries files

I'll try below to provide the instruction for each tool.

# Compilation Manuelle

For reference, I give you below the format of the commandline that you
would have to write if you would compile manually, as it provides the
options you'll have to configure with your IDE.

```
/usr/bin/g++ \
              -march=haswell \                          # (1a) type of target machine [haswell is old but supports all the instructions we need]
              -g <file1>.cpp <file...>.cpp <filen>.cpp\ # cpp files to compile [BEWARE THE ORDER]
              -o <file>.exe \                           # name of the executable to produce
              -I/usr/include/                           # (1b) dir where to find OpenCV's include (.h) files
              -L/usr/lib/x86_64-linux-gnu/\             # (2) dir where to find OpenCV's lib (dll/so) files
              -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_calib3d -lopencv_ccalib -lopencv_core -lopencv_datasets -lopencv_dpm -lopencv_face -lopencv_features2d -lopencv_flann -lopencv_freetype -lopencv_fuzzy -lopencv_hdf -lopencv_highgui -lopencv_imgcodecs -lopencv_imgproc -lopencv_line_descriptor -lopencv_ml -lopencv_objdetect -lopencv_optflow -lopencv_phase_unwrapping -lopencv_photo -lopencv_plot -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_shape -lopencv_stereo -lopencv_stitching -lopencv_structured_light -lopencv_superres -lopencv_surface_matching -lopencv_text -lopencv_video -lopencv_videoio -lopencv_videostab -lopencv_viz -lopencv_ximgproc -lopencv_xobjdetect -lopencv_xphoto                                                # the OpenCV libraries themselves [format is -l<name of the lib file minus 'lib' prefix and minus '.dll/so' suffix>]
                                                         # [might be simply -lopencv_worldxxx with some installations]
```



## Code::Blocks [BEWARE: create a Console application]

Right click on project > "Build Options" > **Select the whole project** (NOT Release/Debug)

+ For the compiler (1.1. architecture)
  - Select "Compiler Settings" tab
  - You could set it in the "Compiler Flags" panel
  - But there is no "Haswell" architecture choice, => in "Other compiler options" panel, add `-march=haswell`

+ For the compiler (1b. includes)
  - Select "Search directories" tab
  - Click "Add" button and use the dialogbox to find OpenCV's `include` dir

+ For the linker (2. libs)
  - Select "Linker Settings" tab
  - Under the "Link libraries" click "Add" button
  - In the dialog box that appears, find OpenCV's `lib` dir
  - NOTE: in this dialogbox, you can select multiple (lib) files at once



## QtCreator  [BEWARE: create a Non-Qt Console application]

When you create a project, QtCreator creates a `.pro` file with all
the configs in it.

You'll add a few lines to this file.

+ For the compiler (1.1. architecture)
  `QMAKE_CXXFLAGS += -march=haswell`


+ For the compiler (1b. includes)
  `INCLUDES += -I/usr/include/opencv2/`

+ For the linker (2. libs)
```
  LIBS += -L/usr/lib/x86_64-linux-gnu/ \
             -lopencv_aruco -lopencv_bgsegm -lopencv_bioinspired -lopencv_calib3d -lopencv_ccalib -lopencv_core -lopencv_datasets -lopencv_dpm -lopencv_face -lopencv_features2d -lopencv_flann -lopencv_freetype -lopencv_fuzzy -lopencv_hdf -lopencv_highgui -lopencv_imgcodecs -lopencv_imgproc -lopencv_line_descriptor -lopencv_ml -lopencv_objdetect -lopencv_optflow -lopencv_phase_unwrapping -lopencv_photo -lopencv_plot -lopencv_reg -lopencv_rgbd -lopencv_saliency -lopencv_shape -lopencv_stereo -lopencv_stitching -lopencv_structured_light -lopencv_superres -lopencv_surface_matching -lopencv_text -lopencv_video -lopencv_videoio -lopencv_videostab -lopencv_viz -lopencv_ximgproc -lopencv_xobjdetect -lopencv_xphoto
```



## VSCode

+ For the compiler (1b. includes)
  VSCode will generally automatically detect the include path


+ For the compiler (1.1. architecture) & For the linker (2. libs)

  + You need to create a `.vscode/tasks.json` and a
  `.vscode/launch.json` file in the project dir. VSCode will generally
  generate them automatically when asked by clicking on "Menu
  Terminal" > "Configure Tasks"/"Configure Default Build Task" and
  "Menu Run" > "Open Configurations"/"Add Configuration".

  + `tasks.json` (with '#' are parts you might have to change)
```
{
    "version": "2.0.0",
    "tasks": [
        {
            "type": "shell",
            "label": "Build",
            "command": "/usr/bin/g++",  ## path the the compiler
            "args": [
                "-march=haswell",       ## (1a) architecture that support SIMD
                "-g",
                "${file}",              ## cpp file to compile
                "-o",
                "${fileDirname}/${fileBasenameNoExtension}" ## output file
                "-L/usr/lib/x86_64-linux-gnu/",     ## (2) path where to find OpenCV's libs
                "-lopencv_aruco", "-lopencv_bgsegm", "-lopencv_bioinspired", "-lopencv_calib3d", "-lopencv_ccalib", "-lopencv_core", "-lopencv_datasets", "-lopencv_dpm", "-lopencv_face", "-lopencv_features2d", "-lopencv_flann", "-lopencv_freetype", "-lopencv_fuzzy", "-lopencv_hdf", "-lopencv_highgui", "-lopencv_imgcodecs", "-lopencv_imgproc", "-lopencv_line_descriptor", "-lopencv_ml", "-lopencv_objdetect", "-lopencv_optflow", "-lopencv_phase_unwrapping", "-lopencv_photo", "-lopencv_plot", "-lopencv_reg", "-lopencv_rgbd", "-lopencv_saliency", "-lopencv_shape", "-lopencv_stereo", "-lopencv_stitching", "-lopencv_structured_light", "-lopencv_superres", "-lopencv_surface_matching", "-lopencv_text", "-lopencv_video", "-lopencv_videoio", "-lopencv_videostab", "-lopencv_viz", "-lopencv_ximgproc", "-lopencv_xobjdetect", "-lopencv_xphoto"                  ## libs used in the project
            ],
            "options": {
                "cwd": "/usr/bin"
            },
            "problemMatcher": [],
            "group": "build"
        },
        {
            "type": "shell",
            "label": "Run",
            "command": "${fileDirname}/${fileBasenameNoExtension}",
            "problemMatcher": [],
            "group": "build"
        }
    ]
}
```

  + `launch.json` (you're not supposed to change anything there)
```
{
    "version": "0.2.0",
    "configurations": [
      {
        "name": "g++ build and debug active file",
        "type": "cppdbg",
        "request": "launch",
        "program": "${fileDirname}/${fileBasenameNoExtension}",  ## file to debug
        "args": [],
        "stopAtEntry": false,
        "cwd": "${workspaceFolder}",
        "environment": [],
        "externalConsole": false,
        "MIMode": "gdb",
        "setupCommands": [
          {
            "description": "Enable pretty-printing for gdb",
            "text": "-enable-pretty-printing",
            "ignoreFailures": true
          }
        ],
        "preLaunchTask": "Build",
        "miDebuggerPath": "/usr/bin/gdb"      ## path to the debugger (included with the compiler)
      }
    ]
  }
```

## Visual Studio [BEWARE: Create a Windows Console Application]

+ For the compiler (1.1. architecture)
  + Go to "Project" > "Properties" > "Configuration Properties" > "C/C++" > "Code Generation"
  + "Enable Enhanced Instruction Set" or append /arch:SSE (or /arch:SSE2) in "Command Line" > "Additional Options".

+ For the compiler (1b.includes)
  + Right Click on your *project* > Properties
  + In "Configuration", select "All Configurations"
  + In "C/C++" > "General" > "Additional Include Directories"

+ For the linker (2.libs)
  + Right Click on your *project* > Properties
  + In "Configuration", select "All Configurations"
  + In "Linker" > "Input" > "Additional Dependencies"



## Other Tips & Tricks

### Accessing the assembly code

- Add a Break Point where you want to get the assembly
- Run your application in debug mode until thi point
- When the debugger stops on the  Break Point, follow the steps below according to the tool you use.

+ QtCreator
  + At the bottom of the Debugger view, there's a panel with a table with the name of your file, a line number and nan address.
    [screenshot](../resources/figures/Qtcreator.png)
  + Right click on this line and select "Open Disassembler at 0x....."

+ Code::Blocks
  + Menu "Debug" > "Debugging Windows" > "Assembly"

+ Visual Studio
  + Right click in the "code window" > "Goto to disassembly" (Visual Studio)

+ VSCode
  + does not seem to support the feature
    + You're supposed to be able to use `-exec disassembly` in the "debugger console" but that did not work for me.
      https://github.com/Microsoft/vscode-cpptools/issues/206#issuecomment-245776970
    + Under Linux you could show disassembly with the external tool `nemiver`

### Using cmake
```
set(GCC_COVERAGE_COMPILE_FLAGS "-march=haswell")
set(CMAKE_CXX_FLAGS "${CMAKE_C_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}")
```
