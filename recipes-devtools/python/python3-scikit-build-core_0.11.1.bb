SUMMARY = "PEP 517 backend for building C/C++/Cython extensions"
HOMEPAGE = "https://github.com/scikit-build/scikit-build-core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b4e748e5f102e31c9390dcd6fa66f09"

PYPI_PACKAGE = "scikit_build_core"
SRC_URI = "https://files.pythonhosted.org/packages/source/s/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"
SRC_URI[sha256sum] = "4e5988df5cd33f0bdb9967b72663ca99f50383c9bc21d8b24fa40c0661ae72b7"

SRC_URI += "file://0001-builder.py-Check-PYTHON_INCLUDE_DIR.patch"
SRC_URI[sha256sum] = "4e5988df5cd33f0bdb9967b72663ca99f50383c9bc21d8b24fa40c0661ae72b7"

inherit pypi python_pep517

# scikit-build-core builds itself with hatchling (per its own pyproject)
DEPENDS += "python3-hatchling-native python3-hatch-vcs-native"

# Some versions also need these at runtime; keep them handy if QA complains:
RDEPENDS:${PN} += "python3-packaging python3-pathspec python3-tomli"

# Make a -native variant available when something depends on it
BBCLASSEXTEND = "native nativesdk"