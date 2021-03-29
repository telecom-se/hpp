# Useful resources for SIMD labs

## Definition: What is a scalar?

- scalar = 1 single value
- vector = several values (in a continuous memory space, like an array[])


## Size of the basic data types

[source](https://stackoverflow.com/questions/14821738/size-of-basic-data-types-in-c)

| Type               | Taille               |
|--------------------|----------------------|
| sizeof(uchar/char) | 1 Ui64 (= 1 byte)    |
| sizeof(short)      | 2 Ui64 (= 2 bytes)   |
| sizeof(int)        | 4 Ui64 (= 4 bytes)(\*)|
| sizeof(float)      | 4 Ui64 (= 4 bytes)   |
| sizeof(long)       | 8 Ui64 (= 8 bytes)(\*)|
| sizeof(double)     | 8 Ui64 (= 8 bytes)   |

  - (\*)**ATTENTION** : these values *HIGHLY* depend on the plateform [hardware+compiler+OS] (for instance, on my PC sizeof(int)=sizeof(long)=4bytes)

  - NOTE: By typing `sizeof(<type>)` in Visual Studio and passing the mouse over the text, a ToolTip displays the corresponding value on your machine.


## Intrinsics names

[source](https://software.intel.com/en-us/cpp-compiler-developer-guide-and-reference-naming-and-usage-syntax)

  - Definition: intrinsic = C functions *directly* translated into a single ASM instruction


  - Data type names

    ```c
    __m<register_length><scalar_type>         // NOTE: 2 '_' for data types```

  - Function names

    ```c
    _mm<output_size>_<operation>_<input_type> // NOTE: only 1 single '_' for functions```

   - `<input_type>` can be:

    | `input_type` | definition |
    | ------------ | -----------|
    | `s`          | single-precision floating point|
    | `d`          | double-precision floating point|
    | `i<val>`     | signed `<val>`-bit integer (`val`=8,16,32)|
    | `u<val>`     | unsigned `<val>`-bit integer |

  - **ATTENTION**: NOT all the combination exist!!!

## Which C/C++ Header to insert to access what intrinsics?

[source](https://stackoverflow.com/questions/11228855/header-files-for-x86-simd-intrinsics#11228864) ?

| Acronym    | Name                        | Processor            | Header          |
|------------|-----------------------------|----------------------|----------------|
| MMX/3DNow! | MultiMedia Extension        | Pentium MMX/K6       | `<mmintrin.h>`  |
| SSE        | Streaming SIMD Extension v1 | Pentium III          | `<xmmintrin.h>` |
| SSE2       | Streaming SIMD Extension v2 | Pentium 4            | `<emmintrin.h>` |
| SSE3       | Streaming SIMD Extension v3 | Pentium 4E           | `<pmmintrin.h>` (SSSE3: `<tmmintrin.h>`)|
| SSE4       | Streaming SIMD Extension v4 | Core 2 Duo (Penryn)  | `<smmintrin.h>` (SSE4.2: `<nmmintrin.h>`) |
| AVX        | Advanced Vector Extension   | Core i7 (Sandybridge)/Bulldozer | `<immintrin.h>` |

## Memory Alignement

- When using MMX/SSE/AVX instructions, it is often required (at times, simply faster) to load from *aligned* memory space (i.e. continuous memory).

- To force the alignement, you can use the C++11 instruction `alignas`:

    ```c
    alignas(4) float a[4] = { 1.0f, 2.0f, 3.0f, 4.0f };```

  NOTE: There are other alignment instructions (that you may find in various forums), but they are often specific to a compiler. Thus, prefer the portable method above.

## Example usage of SIMD

- https://stackoverflow.blog/2020/07/08/improving-performance-with-simd-intrinsics-in-three-use-cases/
