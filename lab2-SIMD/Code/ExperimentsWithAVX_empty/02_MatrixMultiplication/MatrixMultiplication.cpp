#include <iostream> // for cin/cout
#include <string>   // for std::strings


using namespace std;

const int ROWS1 = 4;
const int COLS1 = 6;
const int ROWS2 = COLS1;
const int COLS2 = 4;    // !!BEWARE!!: MUST BE A MULTIPLE OF REGITERS' SIZE !!!!

/* Init/Zeroes the matrix */
void initMatrix(float* mat, int rows, int cols, bool zero) {
    // TODO: implement
}

/* Display the matrix */
void printMatrix(string comment, float* mat, int rows, int cols) {
    // TODO: implement
}

/* Computes matrix mult without optimization. Assumes res is zeroed */
void multMatrix(float *mat1/*fr*irc*/, float *mat2/*irc*fc*/, float *res/*fr*fc*/, int finalRows, int intermRowsCols, int finalCols) {
    // TODO: implement
}

/* Computes matrix mult with SSE optimization. Assumes res is zeroed
Goal: find the correct for loop to optimize!
*/
void multMatrixSSE(float *mat1/*fr*irc*/, float *mat2/*irc*fc*/, float *res/*fr*fc*/, int finalRows, int intermRowsCols, int finalCols) {
    // TODO: implement
 }

/* Computes matrix mult with FMA optimization. Assumes res is zeroed */
void multMatrixFMA(float *mat1/*fr*irc*/, float *mat2/*irc*fc*/, float *res/*fr*fc*/, int finalRows, int intermRowsCols, int finalCols) {
    // TODO: implement
}



int main()
{
    // Initialises rand mat1 & mat2
    float *mat1 = new float[ROWS1*COLS1];
    initMatrix(mat1, ROWS1, COLS1, false);
    float *mat2 = new float[ROWS2*COLS2];
    initMatrix(mat2, ROWS2, COLS2, false);
    // Zeroes resu
    float *resu = new float[ROWS1*COLS2];
    initMatrix(resu, ROWS1, COLS2, true);

    // print initial matrices
    printMatrix("MAT1", mat1, ROWS1, COLS1);
    printMatrix("MAT2", mat2, ROWS2, COLS2);
    printMatrix("RESU", resu, ROWS1, COLS2);

    // TODO: implement

  return 0;
}
