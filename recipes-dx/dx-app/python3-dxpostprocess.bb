SUMMARY = "DEEPX dx_app Python postprocess bindings"

require dx-app.inc

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

S = "${WORKDIR}/git"

inherit python_pep517 python3targetconfig
PEP517_BUILD_BACKEND = "scikit_build_core.build"

PEP517_SOURCE_PATH = "${S}/src/bindings/python/dx_postprocess"

DEPENDS += "\
    cmake-native \
    ninja-native \
    python3-installer-native \
    python3-wheel-native \
    python3-scikit-build-core-native \
    python3-setuptools-native \
    python3-pybind11-native \
    dx-rt \
"

RDEPENDS:${PN} += "\
    python3-core \
"

export PROJECT_ROOT = "${S}"
export CMAKE_EXECUTABLE = "${STAGING_BINDIR_NATIVE}/cmake"
export SKBUILD_CMAKE = "${STAGING_BINDIR_NATIVE}/cmake"
export SKBUILD_CMAKE_ARGS = "-DDXRT_LIB=${RECIPE_SYSROOT}/usr/lib/libdxrt.so"

# Upstream CMake adds -march=native, which is wrong for Yocto cross builds.
do_configure:prepend() {
    sed -i 's/ -march=native//g' \
        ${S}/src/bindings/python/dx_postprocess/CMakeLists.txt
}

FILES:${PN} += "\
    ${PYTHON_SITEPACKAGES_DIR}/*.so \
    ${PYTHON_SITEPACKAGES_DIR}/dx_postprocess-*.dist-info \
"

INSANE_SKIP:${PN} += "already-stripped"

