SUMMARY = "DX Runtime"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_PATH ?= "/mnt/uuu-storage/deepx"
SRC_FILE ?= "dx-rt-1.1.1.tar.gz"

SRC_URI = "file://${SRC_PATH}/${SRC_FILE}"
SRC_URI += "file://0001-remove-invalid-dependencies.patch"

S = "${WORKDIR}/dx_rt"


inherit cmake

FILES:${PN} = "${bindir}/*"

# The build contains unversioned libraries resulting in build error
# due to non-symlinks. The two below instructions solves this.
#
# Build errors
# - dx-rt-1.0.0-r0 do_package_qa: QA Issue: dx-rt rdepends on dx-rt-dev [dev-deps]
# - dx-rt-1.0.0-r0 do_package_qa: QA Issue: -dev package dx-rt-dev contains non-symlink
#      .so '/usr/lib/libdxrt.so' [dev-elf]
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so*"

