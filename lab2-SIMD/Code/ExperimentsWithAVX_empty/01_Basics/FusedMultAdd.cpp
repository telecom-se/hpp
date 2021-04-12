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
  //  1. load data from memory into registers
  //  2. do computations (mult then add) on registers
  //  3. Store register back to memory
}

/* better optimization with FMA */
void fma(float a[4], float b[4], float c[4], float res[4]) {
  //  1. load data from memory into registers
  //  2. do computations (single fma) on registers
  //  3. Store register back to memory
}


/* displays the results & their timing */
void printRes(string title, float res[4]/*, high_resolution_clock::time_point time1, high_resolution_clock::time_point time2*/) {
    cout << title /*<< " (" << (time2 - time1).count() << "): "*/ << res[0] << " " << res[1] << " " << res[2] << " " << res[3] << endl;
}

int main() {
    alignas(4) float a[4] = { 1.0f, 2.0f, 3.0f, 4.0f };
    alignas(4) float b[4] = { 5.0f, 6.0f, 7.0f, 8.0f };
    alignas(4) float c[4] = { 9.0f, 10.0f, 11.0f, 12.0f };
    alignas(4) float res[4] = { 0.0f, 0.0f, 0.0f, 0.0f };

    //// Basic
    basic(a, b, c, res);
    printRes("BASE ", res/*, tp1, tp2*/);



    ////// SSE
    sse(a, b, c, res);
    printRes("SSE  ", res/*, tp1, tp2*/);



    /////// FMA
    fma(a, b, c, res);
    printRes("FMA  ", res/*, tp1, tp2*/);



    return 0;
}
