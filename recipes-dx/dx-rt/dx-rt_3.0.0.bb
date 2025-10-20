SUMMARY = "DX Runtime"

require dx-rt.inc

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_URI += "file://0001-remove-invalid-dependencies.patch \
           file://0001-Changed-option-USE_SERVICE-from-ON-to-OFF.patch \
           "

S = "${WORKDIR}/git"


inherit cmake

EXTRA_OECMAKE += "\
  -DCMAKE_SKIP_RPATH=ON \
  -DCMAKE_BUILD_WITH_INSTALL_RPATH=OFF \
  -DCMAKE_INSTALL_RPATH= \
  -DCMAKE_INSTALL_RPATH_USE_LINK_PATH=OFF \
"

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

do_configure:prepend() {
    # make all install destinations relative
    # without this change there will be a Yocto QA Issue
    #   "Files/directories were installed but not shipped in any package"
    sed -i 's|DESTINATION[[:space:]]*${CMAKE_SOURCE_DIR}/bin/examples|DESTINATION bin/examples|g' \
        $(grep -rl 'DESTINATION[[:space:]]*${CMAKE_SOURCE_DIR}/bin/examples' ${S} || true)
}


