SUMMARY = "DX User Application examples"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"
DEPENDS = "dx-rt opencv"

SRC_URI = "git://github.com/DEEPX-AI/dx_app;branch=main;protocol=https"
SRCREV = "cab28ddbb7c87d4eec5f88d9bddaf5ba69fbf2f3"

SRC_URI += "file://0001-Changed-path-to-gen.h-created-by-dx-rt.patch \
            file://0001-use-installed-dataroot.patch \
           "

S = "${WORKDIR}/git"

# Give a hint to the dx-app/CMakeLists.txt on where to find the /include/dxrt/gen.h
# file. This is the staging dir for the dx-app and the original CMakeLists.txt file
# uses DXRT_INSTALLED_DIR but that is never set and setting it here could affect
# other parts of the build process.
EXTRA_OECMAKE += "-DDXRT_GEN_FILE_ROOT_DIR=${STAGING_EXECPREFIXDIR}"

inherit cmake

# Add prefix maps that also rewrite strings produced by macros like __FILE__
# Map both WORKDIR and S to a benign relative path so *no* host/build paths leak.
CFLAGS:append   = " -fdebug-prefix-map=${WORKDIR}=. -ffile-prefix-map=${WORKDIR}=. -fmacro-prefix-map=${WORKDIR}=."
CXXFLAGS:append = " -fdebug-prefix-map=${WORKDIR}=. -ffile-prefix-map=${WORKDIR}=. -fmacro-prefix-map=${WORKDIR}=."
CFLAGS:append   = " -ffile-prefix-map=${S}=. -fmacro-prefix-map=${S}=."
CXXFLAGS:append = " -ffile-prefix-map=${S}=. -fmacro-prefix-map=${S}=."

# Force CMake to actually use Yocto’s flags (some projects overwrite them)
EXTRA_OECMAKE += "\
  -DCMAKE_C_FLAGS:STRING='${CFLAGS} ${SELECTED_OPTIMIZATION}' \
  -DCMAKE_CXX_FLAGS:STRING='${CXXFLAGS} ${SELECTED_OPTIMIZATION}' \
  -DCMAKE_EXE_LINKER_FLAGS:STRING='${LDFLAGS}' \
  -DCMAKE_SHARED_LINKER_FLAGS:STRING='${LDFLAGS}' \
  -DCMAKE_EXPORT_COMPILE_COMMANDS=ON \
"

FILES:${PN} = "/usr/bin/*"
