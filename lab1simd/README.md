# Module FISE2 - HPP 2018-2019

## Introduction to the course Lab1/SIMD

Dans ce cours, nous allons étudier la technologie SIMD ("Single Instruction Multiple Data), et plus particulièrement l'implémentation dans les architectures Intel : SSE/AVX.

Les transparent du cours sont accessibles ici: [lien vers les slides](./Slides/slides_SIMD.pdf)


## TP

### Resources/Rappels

- [Taille des différents types de données basiques](https://stackoverflow.com/questions/14821738/size-of-basic-data-types-in-c)

| Type               | Taille               |
|--------------------|----------------------|
| sizeof(uchar/char) | 1 Ui64 (= 1 byte)    |
| sizeof(short)      | 2 Ui64 (= 2 bytes)   |
| sizeof(int)        | 4 Ui64 (= 4 bytes)(*)|
| sizeof(float)      | 4 Ui64 (= 4 bytes)   |
| sizeof(long)       | 8 Ui64 (= 8 bytes)(*)|
| sizeof(double)     | 8 Ui64 (= 8 bytes)   |

  - (*)ATTENTION : toutes ces valeurs dépendent de la plateforme (par exemple, sur mon PC sizeof(int)=sizeof(long)=4bytes)

  - Note: En tappant le code sizeof(<type>) dans VisualStudio et en le survolant avec la souris, un ToolTip apparaît avec la valeur correspondante sur votre machine

- scalar = 1 valeur seule / vector = plusieurs valeurs

- [Nommage des intrinsics](https://software.intel.com/en-us/cpp-compiler-developer-guide-and-reference-naming-and-usage-syntax) (=fonctions C traduites directement en 1 instruction ASM)

  - Nommage des types de données
    
	`__m<register_length><scalar_type>         // NOTE: il y a 2 '_' pour les types de données`

  - Nommage des fonctions
    
	`_mm<output_size>_<operation>_<input_type> // NOTE: il y a 1 seul '_' pour les fonctions`

   - `<input_type>` peut être :
   
    | `input_type` |
    | ------------ | -----|
    | `s`          | single-precision floating point|
	| `d`          | double-precision floating point|
    | `i<val>`     | signed <val>-bit integer (val=8,16,32)|
	| `u<val>`     | unsigned <val>-bit integer |

  - ATTENTION: toutes les combinaisons n'existent pas!!!

- [Quels Headers C/C++ insérer pour accéder aux intrinsics](https://stackoverflow.com/questions/11228855/header-files-for-x86-simd-intrinsics#11228864) ?

| Header          | SIMD |
|-----------------|------|
| `<mmintrin.h>`  | MMX  |
| `<xmmintrin.h>` | SSE  |
| `<emmintrin.h>` | SSE2 |
| `<pmmintrin.h>` | SSE3 |
| `<immintrin.h>` | AVX  |

- Alignement mémoire : quand on utilise les instructions vectorielles SSE/AVX, il est généralement nécessaire (ou, des fois, simplement plus rapide) que les données soient alignées en mémoire.
  - Pour forcer l'alignement, on peut utiliser l'instruction `alignas` de C++11 :
    
	`alignas(4) float a[4] = { 1.0f, 2.0f, 3.0f, 4.0f };`


### Préparation du TP

- Téléchargez l'archive [ExperimentsWithAVX.zip](./Code/ExperimentsWithAVX_empty.zip)
- Décompressez-là sur un disque non-réseau
- Ouvrez le fichier `AVXEXperiments.sln` dans VisualStudio



### Exo1: Premiers pas avec SSE et AVX : projet "1_Basics"

- Préliminaires : choisissez ce projet comme projet de démarrage (Clic droit sur projet > "Set as Startup project")
- Buts :
  - Compiler et exécuter un premier code utilisant des instructions SSE/AVX.
  - Apprendre à rechercher dans la documentation Intel les bons intrinsics.
  - Comparer les performances.
- Dans cet exercice, vous allez implémenter une fonction qui prend trois entrées A,B,C et calcule A*B+C, de trois manières différentes : (1) en code C normal, (2) avec les optimisation SSE, (3) avec une instruction FMA
- On vous donne :
  - Une fonction `printRes()` pour afficher le résultat de vos calculs

> Task Utilisez les resources ci-dessus pour trouver le/les bons headers à inclure
> Task Utilisez la [documentation Intel](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) pour chercher les intrinsics SSE permettant (1) de charger 4 float dans un registre, (2) d'additionner 4 floats et (3) multiplier 4 floats
> Task Complétez le code proposé avec les intrinsics trouvés pour calculer a*b+c des trois façons demandées (C, SSE, FMA)
> Task À votre avis, à quoi peut servir une telle instruction a*b+c ?
> Task Utiliser une [high_resolution_clock](http://www.cplusplus.com/reference/chrono/high_resolution_clock/now/) pour évaluer le temps d'exécution des 3 fonctions
  - NOTE: Cette méthode très basique ne donnera jamais des temps *absolus* d'exécution corrects puisque, dans un OS multithreadé, un processus est régulièrement interrompu, pour une durée indéterminée, par des tâches plus importantes. Cependant, ce qui nous intéresse ici sont les temps d'exécution *relatifs* des différentes fonctions. Ainsi, en exécutant chaque function un grand nombre (par ex: 100) de fois et mesurant le temps d'exécution de l'ensemble, on "lisse" les temps d'interruptions et si les fonctions ont des temps d'exécution très différents, cela se reflètera sur les valeurs relatives (somme/moyenne) des temps d'exécution.
> Essayez d'expliquer les similarités/différences dans les temps d'exécution des trois functions, ainsi que les avantages/inconvénients des différentes façons de procéder




### Exo2: Premier cas concret : projet "2_MatrixMultiplication"
- Préliminaires : choisissez ce projet comme projet de démarrage (Clic droit sur projet > "Set as Startup project")
- Buts :
  - Employer les intrinsics dans un premier problème concret, en CLI
  - Comparer avec le code généré automatiquement par VisualStudio.
- Dans cet exercice, on vous propose d'utiliser les mêmes instructions que précédemment pour calculer le produit de deux matrices, de trois manières différentes : (1) en code C normal, (2) avec les optimisation SSE, (3) avec une instruction FMA
- On vous donne :
  - les constantes ROWS1/COLS1=dimensions de la première matrice, ROWS2/COLS2=dimensions de la seconde matrice. Bien entendu, pour pouvoir effectuer la multiplication, ROWS2=COLS1 et ROWS1/COLS2 sont les dimensions de la matrice résultante.
  - une structure de données particulière pour stocker les matrices, allouées dynamiquement, de manière continue en mémoire : il s'agit d'un vecteur à *une seule* dimension, où les lignes de la matrice sont stockées les unes dernière les autres. La traduction avec les tableaux à 2D habituels est très simple : on peut accéder à l'élément `a[i][j]` avec `a[i*nbCols(a)+j]`. 
- Intuitivement, à quels gains de performance pourrait-on s'attendre ?

> Task Utilisez les resources ci-dessus pour trouver le/les bons headers à inclure
> Task Écrivez une fonction pour remplir une matrice ayant ce format. Un booléen est fourni en entrée. S'il est à vrai, la matrice est remplie de 0, sinon elle est remplie de valeurs aléatoires.
> Task Écrivez une fonction pour afficher une telle matrice.
> Task Écrivez une fonction pour multiplier deux matrices et stocker le résultat dans une 3ème matrice passée en paramètre, sans chercher à optimiser pour le moment
  - NOTE: Vous devriez avoir 3 boucles for imbriquées
  - Optimisez cette fonction en utilisant SSE, puis en utilisant FMA
  - NOTE: Attention à choisir le bon axe d'optimisation (=la bonne boucle for) pour gagner au maximum en performance avec SSE/FMA (i.e. charger des données continues en mémoire) !
> Task Terminez le main() pour tester vos fonctions et vérifier que vous optenez bien les mêmes résultats selon les 3 méthodes.
> Task Regardez ce que le compilateur de VisualStudio avait généré dans le cas 1 et comparez à votre propre code :
  - Pour ce faite, ajouter un Break Point là où sont fait les calculs dans la version C "normale" (i.e. sans intrinsic)
  - Lancer le programme en mode déboggage
  - Quand ce dernier s'arrête sur le Break Point, faites un Clic Droit dans la fenêtre de code > Goto to disassembly
> Task Recherchez sur internet comment on pourrait demander à VisualStudio d'améliorer le code qu'il génère.
  - Tester ce que vous avez trouvé
> Task Si vous avez le temps, ajouter des instructions pour comparer les temps d'exécution sur de grosses matrices




### Exo3: Exemple concret n°1 : projet "3_MandelbrotSSEAVX"
- Préliminaires : choisissez ce projet comme projet de démarrage (Clic droit sur projet > "Set as Startup project")
- Buts :
  - Apprendre à coder le plus efficacement possible avec les nouvelles instructions "multimedia"
  - [Dessiner l'ensemble de Mandelbrot, selon l'agorithme "Escape Time"](https://en.wikipedia.org/wiki/Mandelbrot_set#Escape_time_algorithm)
- On vous donne :
  - Une fonction `mandelbrotCPU()` qui génère l'image de l'ensemble de Mandelbrot dans une version "propre" C++, utilisant des objets pour représenter les nombres complexes
  - Une fonction `displayImage()` pour afficher les images générées
  - Une fonction `main()` qui appelle les différentes fonction de génération de l'ensemble de Mandelbrot que vous allez écrire et les afficher à l'écran
  - NOTE: [le projet est configuré pour utiliser OpenCV](https://www.opencv-srf.com/2017/11/install-opencv-with-visual-studio.html)
    - Chercher si/où cette bibliothèque est installée
    - Modifier la configuration des variables d'environnement/du projet pour qu'il fonctionne

> Task Écrivez une version de la fonction en utilisant 2 `float`s à la place d'objets `<complex>` pour représenter les nombres complexes.
> Task Utilisez la [documentation Intel](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) pour ré-écrire cette version en utilisant au maximum des instructions SSE
> Task Utilisez la [documentation Intel](https://software.intel.com/sites/landingpage/IntrinsicsGuide/) pour ré-écrire cette version en utilisant au maximum des instructions AVX
> Task Utilisez le code de test donné dans `main()` pour tester vos fonctions
> Task Si vous avez le temps, ajouter des instructions pour comparer les temps de génération des images selon les différentes méthodes.




### Exo 4: Exemple concret n°2 : projet "4_ImageManipulation"
- Préliminaires : 
  - Choisissez ce projet comme projet de démarrage (Clic droit sur projet > "Set as Startup project")
  - Vérifiez que les images `"big_image1.jpg"` et `"big_image2.jpg"` se trouvent bien dans le même répertoire que la solution
- Buts :
  - Manipuler des images : remplir de 0 (noir), remplir d'une couleur donnée, fade to 0, fade to color, fade to another image
    Le fading est une transition linéaire et progressive d'une couleur vers une autre. Il fonctionne comme suit : `nouvelle_couleur = alpha*source_color + (1-alpha)*target_color`
  - Comparer les temps d'exécution avec/sans les instructions spéciales "multimédia"
- On vous donne :
  - Une classe ImageManipulation avec plusieurs méthodes "pure virtual" => à implémenter dans une classe dérivée de ImageManipulation
  - Un code "VisualTesting.cpp" pour tester vos méthodes visuellement : afficher les images
  - Un code "TimeTesting.cpp" pour exécuter et timer de multiples exécutions de vos méthodes (sorti du projet initialement, pour ne pas entrer en conflit avec le main de "VisualTesting.cpp")
  - Les images sont stockées dans le format d'OpenCV : `Mat` au format `CV32FC4` (4 channels de 1 float=32bits=4bytes) en mode RGBA
  - Pour vous simplifier la tâche on a défini les types suivants :
    - `chanType`  (=`float`)  : type pour stocker 1 channel d'un pixel
    - `pixelType` (=`Vec4f`)  : le format d'OpenCV pour représenter un pixel (4 floats)
-  - NOTE : pour accéder/affecter un pixel, on peut utiliser `cv::Mat::at<Vec4b>(line, col)` car elle retourne *reference* sur le pixel à (line, col)
    - `sseType`   (=`__m128`) : le format pour travailler avec des registres SSE
    - `avxType`   (=`__m256`) : le format pour travailler avec des registres AVX
  - Ainsi que les constantes suivantes :
    - `MAX_CHAN_TYPE`         : valeur de couleur maximale que l'on peut stocker dans le type de données utilisé pour stocker un channel (ici [0.0,1.0])
    - `NB_CHANTYPE_IN_128`    : le nombre d'éléments de type chanType que l'on peut stocker dans un registre SSE (`__m128`) = 128bits/`sizeof`(`float`=4bytes) = 4 `float`s = 4 channels = 1 pixel
    - `NB_CHANTYPE_IN_256`    : le nombre d'éléments de type chanType que l'on peut stocker dans un registre AVX (`__m256`) = 256bits/`sizeof`(`float`=4bytes) = 8 `float`s = 8 channels = 2 pixels
    - `MAX_TIMING_ITERATIONS` : le nombre de répétitions à effectuer pour timer chaque méthode
  - NOTE: [le projet est configuré pour utiliser OpenCV](https://www.opencv-srf.com/2017/11/install-opencv-with-visual-studio.html)

> Task Intuitivement, à quels gains de performance pourrait-on s'attendre ?
> Task Écrivez les méthodes `fillWithZero()`, `fillWithZeroOptimized()`, `fillWithZeroSSE()` et `fillWithZeroAVX()` pour remplir une image de 0.
  - `fillWithZeroOptimized()` doit manipuler directement le pointeur au lieu d'accéder à `img[i][j]`. On suppose que, comme dans l'exercice de multiplication de matrice, l'image est stockée dans un vecteur 1D où les lignes sont les unes derrière les autres.
> Task Écrivez les méthodes `fillWithColor()`, `fillWithColorOptimized()`, `fillWithColorSSE()` et `fillWithColorAVX()` pour remplir une image avec la couleur définie par `ImageManipulation::setTargetColor()`.
> Task Vérifier que vos codes fonctionnent avec le main() fournit dans VisualTesting.cpp
> Task Comparer les temps d'exécution des différentes méthodes. Que constatez-vous ?
  - Pour ce faire, exclure VisualTesting.cpp du projet et inclure TimeTesting.cpp 
> Task Écrivez les méthodes `fadeToZero()` et `fadeToZeroAVX()`, dont le but est d'effectuer une transition d'une couleur à une autre en fonction du paramètre défini par `ImageManipulation::setAlpha()`
  - Notre que le fading vers 0 revient simplement à multiplier la couleur actuelle par alpha.
> Task Écrivez les méthodes `fadeToColor()` et `fadeToColorAVX()`, qui transitionnent cette fois-ci vers la couleur donnée par `ImageManipulation::setTargetColor()` et utilise le même paramètre défini par `ImageManipulation::setAlpha()`
> Task Écrivez les méthodes `fadeToImage()` et `fadeToImageAVX()`, qui transitionnent chaque pixel d'une image source vers le pixel correspondant d'une image destination. Ces méthodes supposent que les 2 images sont de mêmes dimensions et utilisent `ImageManipulation::setAlpha()` et `ImageManipulation::setImageDest()`
> Task Que se passerait-il si le format d'image était CV32FC3?




### Exo5: Ouverture : Limites de l'approche SIMD
- Forts de votre expérience avec les nouvelles instructions "multi-média", listez quelques limites du parallélisme des données.
- Quelles seraient les caractéristiques d'un algorithme qu'il serait difficile d'optimiser avec SIMD ?
- En vous inspirant de [cette vidéo](https://www.youtube.com/watch?v=NmarI5ErisE) et des réponses ci-dessus, expliquez pourquoi est-il toujours nécessaire d'intervenir manuellement pour optimiser l'implémentation d'un algorithme avec SIMD/SSE/AVX

