#include <iostream>
#include <string>

#include <immintrin.h>

#include <chrono>

using namespace std;
using namespace std::chrono;

/* Code to optimize */
void basic(float a[4], float b[4], float c[4], float res[4]) {
  for (int i = 0 ; i < 4 ; i++)
    res[i] = a[i]*b[i] + c[i];
}

/* simple optimization with SSE mult+add */
void sse(float a[4], float b[4], float c[4], float res[4]) {
  __m128 regA = _mm_load_ps(a);
  __m128 regB = _mm_load_ps(b);
  __m128 regC = _mm_load_ps(c);

  regA = _mm_mul_ps(regA, regB);
  regA = _mm_add_ps(regA, regC);

  _mm_store_ps(res, regA);
}

/* simple optimization with SSE mult+add */
void sse2(float a[4], float b[4], float c[4], float res[4]) {
  __m128 regA = _mm_load_ps(a);
  __m128 regB = _mm_load_ps(b);
  __m128 regC = _mm_load_ps(c);

  for (int i=0 ; i<100000 ; i++) {
    regA = _mm_mul_ps(regA, regB);
    regA = _mm_add_ps(regA, regC);
  }

  _mm_store_ps(res, regA);
}


/* better optimization with FMA */
void fma(float a[4], float b[4], float c[4], float res[4]) {
  // TODO: implement
  __m128 regA = _mm_load_ps(a);
  __m128 regB = _mm_load_ps(b);
  __m128 regC = _mm_load_ps(c);

  regA = _mm_fmadd_ps(regA, regB, regC);

  _mm_store_ps(res, regA);
}

/* better optimization with FMA */
void fma2(float a[4], float b[4], float c[4], float res[4]) {
  // TODO: implement
  __m128 regA = _mm_load_ps(a);
  __m128 regB = _mm_load_ps(b);
  __m128 regC = _mm_load_ps(c);

  for (int i=0 ; i<100000 ; i++) {
    regA = _mm_fmadd_ps(regA, regB, regC);
  }

  _mm_store_ps(res, regA);
}


/* displays the results & their timing */
void printRes(string title, float res[4], high_resolution_clock::time_point time1, high_resolution_clock::time_point time2) {
  cout << title << " (" << (time2 - time1).count() << "): " << res[0] << " " << res[1] << " " << res[2] << " " << res[3] << endl;
}

int main() {
  alignas(4) float a[4] = { 1.0f, 2.0f, 3.0f, 4.0f };
  alignas(4) float b[4] = { 5.0f, 6.0f, 7.0f, 8.0f };
  alignas(4) float c[4] = { 9.0f, 10.0f, 11.0f, 12.0f };
  alignas(4) float res[4] = { 0.0f, 0.0f, 0.0f, 0.0f };

  //// Basic
  high_resolution_clock::time_point tp1 = high_resolution_clock::now();
  for (int i = 0 ; i < 100000 ; i++) {
    basic(a, b, c, res);
  }
  high_resolution_clock::time_point tp2 = high_resolution_clock::now();
  printRes("BASE ", res, tp1, tp2);


  // Trucs qui peuvent aller mal => crash de l'appli sans plus d'explications:
  // - Alignement : alignas(4) float a[4]; -> float __declspec(align(4)) a[4]; ?
  // - Stockage des tableaux "constants" sur la pile/tas (VC2015)
  // - Type cpu (-march haswell => -march native?)
  // - Temps d'execution à 0 => données déjà en cache (ou pas assez de tours de boucle) => clean projet / run in debug, then run normal again


  ////// SSE
  tp1 = high_resolution_clock::now();
  for (int i = 0 ; i < 100000 ; i++) {
    sse(a, b, c, res);
  }
  tp2 = high_resolution_clock::now();
  printRes("SSE  ", res, tp1, tp2);


  /////// FMA
  tp1 = high_resolution_clock::now();
  for (int i = 0 ; i < 100000 ; i++) {
    fma(a, b, c, res);
  }
  tp2 = high_resolution_clock::now();
  printRes("FMA  ", res, tp1, tp2);








  ////// SSEv2
  tp1 = high_resolution_clock::now();
  sse2(a, b, c, res);
  tp2 = high_resolution_clock::now();
  printRes("SSEv2", res, tp1, tp2);

  /////// FMAv2
  tp1 = high_resolution_clock::now();
  fma2(a, b, c, res);
  tp2 = high_resolution_clock::now();
  printRes("FMAv2", res, tp1, tp2);


  return 0;
}
