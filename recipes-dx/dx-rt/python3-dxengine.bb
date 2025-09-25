require dx-rt.inc

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

S = "${WORKDIR}/git/python_package"

inherit python_pep517 python3targetconfig
PEP517_BUILD_BACKEND = "scikit_build_core.build"

DEPENDS += "\
    cmake-native \
    ninja-native \
    python3-build-native \
    python3-installer-native \
    python3-wheel-native \
    python3-scikit-build-core-native \
    python3-setuptools-native \
    python3-pybind11-native \
    dx-rt \
    unzip-native \
"

# Also set common fallbacks (some versions check these)
export CMAKE = "${STAGING_BINDIR_NATIVE}/cmake"
export NINJA = "${STAGING_BINDIR_NATIVE}/ninja"
export CMAKE_GENERATOR = "Ninja"

do_compile:prepend() {
    # Remove Python 'cmake' and 'ninja' packages from THIS RECIPE'S native sysroot
    # so scikit-build-core can't pick their (missing) bundled binaries.
    for pkg in cmake ninja; do
        for pkgdir in "${RECIPE_SYSROOT_NATIVE}${libdir_native}"/python*/site-packages/"${pkg}"; do
            if [ -d "$pkgdir" ]; then
                bbnote "Removing task-local native Python package: $pkgdir"
                rm -rf "$pkgdir"
            fi
        done
    done
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}

    # Extract each wheel we built (usually just one) straight into site-packages
    for whl in ${WORKDIR}/dist/*.whl; do
        tmpdir="${WORKDIR}/wheel-unpack"
        rm -rf "$tmpdir"
        install -d "$tmpdir"
        unzip -q "$whl" -d "$tmpdir"
        cp -a "$tmpdir"/* ${D}${PYTHON_SITEPACKAGES_DIR}/
    done
}

INSANE_SKIP:${PN} += "already-stripped"

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/*"
RDEPENDS:${PN} += "python3-core"
