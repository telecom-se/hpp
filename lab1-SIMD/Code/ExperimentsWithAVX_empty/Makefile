SOURCES=$(wildcard *.cpp)

OPENCV_INC_DIR=/usr/include/opencv4/
OPENCV_LIB_DIR=/usr/lib/x86_64-linux-gnu/
OPENCV_LIBS=$(shell ls -C1 /usr/lib/x86_64-linux-gnu/libopencv_*.so | xargs -I{} basename {} | sed 's/^lib//g' | sed "s/[.]so//g" | xargs -I{} echo '-l{}' | tr '\n' ' ')

test:
	echo $(OPENCV_LIBS)

all: FusedMultAdd.exe
	true

clean:
	rm -f *.exe *.o

%.o: %.cpp
	gcc -c -o $@ $< \
		-march=haswell \
		-I/usr/lib/gcc/x86_64-linux-gnu/9/include/ -I/usr/include/c++/9 \
		-I$(OPENCV_INC_DIR)

%.exe: $(SOURCES:.cpp=.o)
	g++ -o $@ $^ \
		-L$(OPENCV_LIB_DIR) \
		$(OPENCV_LIBS)
