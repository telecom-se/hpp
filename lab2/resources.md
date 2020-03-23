# Resources utiles pour le TP sur SIMD

## Notion de scalaire

- scalar = 1 valeur seule
- vector = plusieurs valeurs


## Taille des types de données basiques

[source](https://stackoverflow.com/questions/14821738/size-of-basic-data-types-in-c)

| Type               | Taille               |
|--------------------|----------------------|
| sizeof(uchar/char) | 1 Ui64 (= 1 byte)    |
| sizeof(short)      | 2 Ui64 (= 2 bytes)   |
| sizeof(int)        | 4 Ui64 (= 4 bytes)(\*)|
| sizeof(float)      | 4 Ui64 (= 4 bytes)   |
| sizeof(long)       | 8 Ui64 (= 8 bytes)(\*)|
| sizeof(double)     | 8 Ui64 (= 8 bytes)   |

  - (\*)**ATTENTION** : toutes ces valeurs dépendent de la plateforme (par exemple, sur mon PC sizeof(int)=sizeof(long)=4bytes)

  - Note: En entrant le code `sizeof(<type>)` dans VisualStudio et en le survolant avec la souris, un ToolTip apparaît avec la valeur correspondante sur votre machine


## Nommage des intrinsics

[source](https://software.intel.com/en-us/cpp-compiler-developer-guide-and-reference-naming-and-usage-syntax) (=fonctions C traduites directement en 1 instruction ASM)

  - Nommage des types de données
    
	```c
	__m<register_length><scalar_type>         // NOTE: il y a 2 '_' pour les types de données```

  - Nommage des fonctions
    
	```c
	_mm<output_size>_<operation>_<input_type> // NOTE: il y a 1 seul '_' pour les fonctions```

   - `<input_type>` peut être :
   
    | `input_type` | définition |
    | ------------ | -----------|
    | `s`          | single-precision floating point|
	| `d`          | double-precision floating point|
    | `i<val>`     | signed `<val>`-bit integer (`val`=8,16,32)|
	| `u<val>`     | unsigned `<val>`-bit integer |

  - **ATTENTION**: toutes les combinaisons n'existent pas !!!

## Quels Headers C/C++ insérer pour accéder aux intrinsics

[source](https://stackoverflow.com/questions/11228855/header-files-for-x86-simd-intrinsics#11228864) ?

| Acronyme   | Nom                         | Processor            | Header          |
|------------|-----------------------------|----------------------|----------------|
| MMX/3DNow! | MultiMedia Extension        | Pentium MMX/K6       | `<mmintrin.h>`  |
| SSE        | Streaming SIMD Extension v1 | Pentium III          | `<xmmintrin.h>` |
| SSE2       | Streaming SIMD Extension v2 | Pentium 4            | `<emmintrin.h>` |
| SSE3       | Streaming SIMD Extension v3 | Pentium 4E           | `<pmmintrin.h>` (SSSE3: `<tmmintrin.h>`)|
| SSE4       | Streaming SIMD Extension v4 | Core 2 Duo (Penryn)  | `<smmintrin.h>` (SSE4.2: `<nmmintrin.h>`) |
| AVX        | Advanced Vector Extension   | Core i7 (Sandybridge)/Bulldozer | `<immintrin.h>` |

## Alignement mémoire 

- Quand on utilise les instructions vectorielles SSE/AVX, il est généralement nécessaire (ou, des fois, simplement plus rapide) que les données soient alignées en mémoire.

- Pour forcer l'alignement, on peut utiliser l'instruction `alignas` de C++11 :
    
	```c
	alignas(4) float a[4] = { 1.0f, 2.0f, 3.0f, 4.0f };```

