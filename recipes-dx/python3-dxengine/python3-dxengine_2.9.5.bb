SUMMARY = "DX Engine Python3 Package"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://${WORKDIR}/dx_rt/LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_PATH ?= "/mnt/uuu-storage/deepx"
SRC_FILE ?= "dx-rt-2.9.5.tar.gz"

SRC_URI = "file://${SRC_PATH}/${SRC_FILE}"
SRC_URI += "file://prebuilt_lib_for_dxengine_2.9.5.zip"

S = "${WORKDIR}/dx_rt/python_package"

INSANE_SKIP:${PN} += "already-stripped"

inherit setuptools3

# WARNING: the following rdepends are determined through basic analysis of the
# python sources, and might not be 100% accurate.
RDEPENDS:${PN} += "python3-core dx-rt"
