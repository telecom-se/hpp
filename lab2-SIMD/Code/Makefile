# DATE=$(shell date --iso-8601)

all: ExperimentsWithAVX_empty.zip

%.zip: $(@:.zip=)
#	7z a "$(DATE)-$@" "$(@:.zip=)"
	7z a "$@" "$(@:.zip=)"

#%.7z: $(@:.7z=)
#	7z a "$(DATE)-$@" "$(@:.7z=)"
