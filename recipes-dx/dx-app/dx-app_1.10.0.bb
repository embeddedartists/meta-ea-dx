SUMMARY = "DX User Application examples"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"
DEPENDS = "dx-rt opencv"

SRC_PATH ?= "/mnt/uuu-storage/deepx"
SRC_FILE ?= "dx-app-1.10.0.tar.gz"

SRC_URI = "file://${SRC_PATH}/${SRC_FILE} \
           file://0001-Changed-path-to-gen.h-created-by-dx-rt.patch \
           "

S = "${WORKDIR}/dx_app"

# Give a hint to the dx-app/CMakeLists.txt on where to find the /include/dxrt/gen.h
# file. This is the staging dir for the dx-app and the original CMakeLists.txt file
# uses DXRT_INSTALLED_DIR but that is never set and setting it here could affect
# other parts of the build process.
EXTRA_OECMAKE += "-DDXRT_GEN_FILE_ROOT_DIR=${STAGING_EXECPREFIXDIR}"

inherit cmake

FILES:${PN} = "/usr/bin/*"

