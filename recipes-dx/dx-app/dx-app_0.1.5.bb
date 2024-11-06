SUMMARY = "DX User Application examples"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"
DEPENDS = "dx-rt opencv"

SRC_PATH ?= "/mnt/uuu-storage/deepx"
SRC_FILE ?= "dx-app-0.1.5.tar.gz"

SRC_URI = "file://${SRC_PATH}/${SRC_FILE}"
SRC_URI += "file://0001-fix-install-paths.patch"

S = "${WORKDIR}/dx_app"


inherit cmake

FILES:${PN} = "/opt/dxrt/*"
