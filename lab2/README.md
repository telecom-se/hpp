# Module FISE2 - HPP 2018-2019

## Introduction to the SIMD Lab

In this Lab, we will study the SIMD technology ("Single Instruction Multiple Data), which allows to execute an  **identical** operation (add, substract...) on **several data** at the same time.

The slides for the Lecture part are available by following the links below:
- [Introduction slides](./Slides/slides_Intro.pdf)
- [SIMD slides](./Slides/slides_SIMD.pdf) (Based on the original work from Omur MULTU [slides](http://www.archive.ece.cmu.edu/~ece447/s15/doku.php?id=schedule) [videos](https://www.youtube.com/watch?v=zLP_X4wyHbY&list=PL5PHm2jkkXmi5CxxI7b3JCL1TWybTDtKq))
- [SSE/AVX slides](./Slides/slides_SSE.pdf) ([Based on the original work from Markus PÜSCHEL](http://www-oldurls.inf.ethz.ch/personal/markusp/teaching/263-2300-ETH-spring11/course.html))

In the Labs part, we will write & optimize source codes that use the Intel's architecture, thus implement the SSE/AVX variant of SIMD.

We will use C++ because it is easier to access low-level instructions in this language as compared to Java. Indeed, Java runs on the JVM, which abstracts the actual instruction set of the physical machine that we wnat to use now.

The following instructions are provided for VisualStudio, because you already worked with it and it offers a few facilities to write/debug/examine ASM code (to look for AVX instructions). But feel free to use other tools, like GCC + [GDB](https://darkdust.net/files/GDB%20Cheat%20Sheet.pdf) + [Code::Blocks]https://codeblocks.org/), e.g. under Linux. However, beware, even if the instruction set remains the same (because it is defined at processor level!), ASM code syntax (the way the instruction set is presented to humans) might differ with respect to the tools/disassembler you use.


## Lab

[Useful tips for the Lab](./resources.md)

### Preparation of the Lab

- Download the archive [ExperimentsWithAVX.zip](./Code/ExperimentsWithAVX_empty.zip)
- Decompress it on a non-network drive
- Open the file `AVXEXperiments.sln` in VisualStudio



### Ex1: First steps with SSE & AVX: project "1_Basics"

- Preliminaries: choose this project as the starting point (*Right click on project > "Set as Startup project"*).
- Goals:
  - Compile & execute your first code using SSE/AVX instructions.
  - Learn how to search the correct intrinsics in Intel's documentation.
  - Compare performances.
- In this exercise, you will implementer a function that takes 3 inputs a,b,c & computes `a*b+c`, in 3 different manners: (1) with "normal" C code, (2) with SSE optimizations, (3) with a FMA instruction.
- At your disposal:
  - A function `printRes()` to display the result of your executions.

> **Task** Use resources listed at the link above to find the correct headers to include.

> **Task** Use [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) to look for SSE intrinsics that allow (1) loading 4 floats in a register, (2) add 4 floats, (3) multiply 4 floats.

> **Task** Fill in the provided  codwith the correct intrinsics in order to compute `a*b+c` in the 3 different manners (C, SSE, FMA).

> **Task** In your opinion, why did the designer of SSE/AVX created an instruction `a*b+c`?

> **Task** Use & [`high_resolution_clock`](http://www.cplusplus.com/reference/chrono/high_resolution_clock/now/) to evaluate the execution time of these 3 functions
  - NOTE: As you've seen in previous Labs, when micro-benchmarking with JMH, this very basic method will never provide correct **absolute** execution times as, in a multithreaded OS, a process is frequently interrupted, for an undetermined time, by more important tasks. However, what we are interested in here are the **relative** execution time of the various functions. Thus, by executing each function a large number if times (e.g., 100) and measuring the total execution time, we "smooth" the interruptions and, if our functions have very different execution time, we will see it in the relative values (sum/average) of the execution times.

> **Task** Try to explain the similarities/differences in execution time of the 3 functions, as well as the pros/cons of these approches.




### Ex2: First concrete example: project "2_MatrixMultiplication"
- Preliminaries: choose this project as the starting project (*Right click on project > "Set as Startup project"*)
- Goals:
  - Use intrinsics in a first actual problem, in CLI.
  - Compare with the code generated automatically by VisualStudio.
- In this exercise, you ill use the same instructions as above, but in order to compute the multiplication of 2 matrices, in 3 different manners: (1) with "normal" C, (2) with SSE optimizations, (3) with a FMA instruction.
- At your disposal:
  - Consts ROWS1/COLS1=dimensions of the first matrix, ROWS2/COLS2=dimensions of the second matrix. Of course, in order to compute the final result, ROWS2=COLS1 et ROWS1/COLS2 are the dimensions of the resulting matrix.
  - A specific data structure to store the matrices (allocated dynamically), as continuous space in memory: it's a vector of **a single** dimension, where rows are stored one after the other. Teh translation into usual 2D arraysis pretty simple: to access element `a[i][j]` we use `a[i*nbCols(a)+j]`.
- Intuitively, what performance gains do you expect?

> **Task** Use above resources to find the correct headers to include

> **Task** Write a function to fill a matrix in the given format. A boolean value is provided as input.If true, the matrix is filled with 0, otherwise it is filled with random values.

> **Task** Write a function to print such a matrix.

> **Task** Wrtie a function to compute the multiplication if 2 matrices and store the result in a 3rd matrix, provided as parameter, *with no optimization for the moment*.
  - NOTE: You should end up with 3 interleaved loops
  - Optimize this function, using SSE, then FMA
  - **BEWARE**: Choose the correct optimization approach (=correct `for` loop) in order to gain as much performance as possible with SSE/FMA (i.e. load data in continuous memory)!

> **Task** Finish the `main()` function in order to teste your functions and verify that vous actually get the same & correct matrix multiplication results with the 3 methods.

> **Task** Have a look at what VisualStudio's compilater generated in the first case ("normal" C) and compare with your own code:
  - Add a Break Point where the computation are done in the "normal" C code (i.e. without intrinsic)
  - Run your application in debug mode
  - When the debugger stops on the  Break Point, *Right click in the code window > Goto to disassembly*

> **Task** Search the internet to find how to configure VisualStudio so that it directly optimizes the generated code.
  - Test the solution you just found

> **Task** (If time is still available) Add instructions to compare execution time on big matrices.




### Ex3: Concrete Example n°1: project "3_MandelbrotSSEAVX"
- Preliminaries: choose this project as the starting project (*Right click on project > "Set as Startup project"*).
- Goals:
  - Learn how to code more efficently with the new "multimedia" instructions.
  - [Draw the Mandelbrot set, using "Escape Time" algorithm](https://en.wikipedia.org/wiki/Mandelbrot_set#Escape_time_algorithm)
- At your disposal:
  - A function `mandelbrotCPU()` that generates the picture of the Mandelbrot set in a "clean" C++ code, using objects to represent complex numbers.
  - A function `displayImage()` to display the generated images.
  - A function `main()` that calls the various functions you will write to generate the Mandelbrot set and display them on screen.
  - NOTE: [The project is configured to use the OpenCV library](https://www.opencv-srf.com/2017/11/install-opencv-with-visual-studio.html).
    - Look where this library est installed.
    - Modify the environment variables/project's configuration so that is compiles & run.

> **Task** Write a version of the mandelbrot set generation function that uses 2 `float`s instead of `<complex>` objects to represent the complex numbers.

> **Task** Use the [Intel documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) to re-write a similar function using as much SSE instructions as possible.

> **Task** Use [Intel's documentation](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) to rewrite a similar function using as much AVX instructions as possible.

> **Task** Use the testing code provided in the `main()` function to teste your functions.

> **Task** (If time is still available) Add instructions to compare the image generation times wit the various methods.




### Ex4: Concrete Example n°2: project "4_ImageManipulation"
- Preliminaries:
  - Choose this project as the starting project (*Right click on project > "Set as Startup project"*).
  - Verify that images `"big_image1.jpg"` and `"big_image2.jpg"` are actually in the same directory as the solution.
- Goals:
  - Manipulate images: fill with 0 (black), fill with configurable color, fade to 0, fade to color, fade to another image.
    /Fading/ is a linear & progressive transition from une color towards another. It works as follows: `nouvelle_couleur = alpha*source_color + (1-alpha)*target_color`.
  - Compare execution times with/without "multimedia" instructions.
- At your disposal:
  - A class `ImageManipulation` with multiple "pure virtual" methods => you will implment these methods in a derived class `MyImageManipulation`.
  - `VisualTesting.cpp` file to test your methods visually: display the images.
  - `TimeTesting.cpp` file to execute & time multiples executions of your methods (NOTE: this file is initially "out" of the project, not to conflict with the `main()` in `VisualTesting.cpp`).
  - Images are stored in OpenCV's format: `Mat` using representation `CV32FC4` (4 channels of 1 float=32bits=4bytes) in RGBA mode.
  - To help you, we defined folowing types:
    - `chanType`  (=`float`)  : type to store 1 channel of a pixel.
    - `pixelType` (=`Vec4f`)  : OpenCV's format to represent a pixel (4 floats)
-  - NOTE: to access/set a pixel, you can use `cv::Mat::at<Vec4b>(line, col)` as it returns a **reference** on the pixel at coordinates (line, col).
    - `sseType`   (=`__m128`) : format to work with SSE registers.
    - `avxType`   (=`__m256`) : format to work with AVX registers.
  - And following consts:
    - `MAX_CHAN_TYPE`         : max value for the color that we can store in the data type used to store a channel (here [0.0,1.0])
    - `NB_CHANTYPE_IN_128`    : number of elements of type `chanType` that we can store in a SSE register (`__m128`) = 128bits/`sizeof`(`float`=4bytes) = 4 `float`s = 4 channels = 1 pixel
    - `NB_CHANTYPE_IN_256`    : number of elements of type `chanType` that we can store in a AVX register (`__m256`) = 256bits/`sizeof`(`float`=4bytes) = 8 `float`s = 8 channels = 2 pixels
    - `MAX_TIMING_ITERATIONS` : number of repetitions used to time each method
  - NOTE: [The project is configured to use the OpenCV library](https://www.opencv-srf.com/2017/11/install-opencv-with-visual-studio.html)

> **Task** Intuitively, what performance gains would you expect?

> **Task** Write methods `fillWithZero()`, `fillWithZeroOptimized()`, `fillWithZeroSSE()` and `fillWithZeroAVX()` to fill an image with 0s.
  - `fillWithZeroOptimized()` must directly manipulate the pointer instead of using `img[i][j]`. We assume that, as in the matrix multiplication exercise, the image is sotred in a 1D vector, where rows are stored one after the other.

> **Task** Write methods `fillWithColor()`, `fillWithColorOptimized()`, `fillWithColorSSE()` and `fillWithColorAVX()` to fill an image with the color previously stored with `ImageManipulation::setTargetColor()`.

> **Task** Verify that your codes work correctly with the `main()` in `VisualTesting.cpp`.

> **Task** Compare execution time of your various methods. What do you observe?
  - To achieve this, exclude `VisualTesting.cpp` from the project and include `TimeTesting.cpp`.

> **Task** Write methods `fadeToZero()` and `fadeToZeroAVX()`, which goal is to transition from one color to pure black according to previously set parameter `ImageManipulation::setAlpha()`.
  - Notre que le fading vers 0 revient simplement à multiplier la couleur actuelle par alpha.

> **Task** Write methods `fadeToColor()` and `fadeToColorAVX()`, that transition to the color previously set with `ImageManipulation::setTargetColor()` and use the same parameter `ImageManipulation::setAlpha()` as above.

> **Task** Write methods `fadeToImage()` and `fadeToImageAVX()`, that transition each pixel of one source image towards the corresponding pixel of a destination image. These methods assume that both images have the same dimensions and use `ImageManipulation::setAlpha()` and `ImageManipulation::setImageDest()`

> **Task** What would be the case if the image format would be CV32FC3?




### Ex5: Opening: Limits of SIMD
> **Task** Now that you are experts in "multimedia"/SIMD programming, give a list of some limits of data parallelization.

> **Task** What would be the  characteristics of an algorithm that would be hard to parallelize with SIMD?

> **Task** Based on [this video](https://www.youtube.com/watch?v=NmarI5ErisE) and above responses, explain why it is always necessary for a human developer to step in the optimization an algorithm with SIMD/SSE/AVX.
