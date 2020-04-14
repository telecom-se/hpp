#include "utils.hpp"

#include <cassert>

float m128_f32_get(__m128 v, unsigned int i)
{
    m128_f32 u;
    assert(i <= 3);
    u.v = v;
    return u.f[i];
}

unsigned short m256i_i16_get(__m256i v, unsigned int i)
{
    m256_i16 u;
    assert(i <= 16);
    u.v = v;
    return u.i[i];
}
