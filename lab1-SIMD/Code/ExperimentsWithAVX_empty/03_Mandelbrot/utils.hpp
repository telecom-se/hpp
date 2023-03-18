#ifndef UTILS_HPP_INCLUDED
#define UTILS_HPP_INCLUDED

// Get AVX intrinsics
#include <immintrin.h>

typedef union {
  __m128 v;    // SSE 4 x float vector
  float f[4];  // scalar array of 4 floats
} m128_f32;
float m128_f32_get(__m128 v, unsigned int i);


typedef union {
  __m256i v;    // AVX 16 short vector
  unsigned short i[16];  // scalar array of 16 shorts
} m256_i16;
unsigned short m256i_i16_get(__m256i v, unsigned int i);

#endif // UTILS_HPP_INCLUDED
