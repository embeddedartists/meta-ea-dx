SUMMARY = "DX Runtime"

require dx-rt.inc

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_URI += "file://0001-Remove-invalid-dependencies-3.1.patch \
            file://0002-Change-install-dir-for-dxrtd-3.1.patch \
            file://0003-Remove-invalid-install-dir-for-dxbench-and-dxtop.patch"


PV = "3.1.0"
S = "${WORKDIR}/git"

DEPENDS = "onnxruntime"

inherit cmake systemd

# onnxruntime headers are installed in onnxruntime sub-directory
CXXFLAGS:append = " -I${STAGING_INCDIR}/onnxruntime"

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
    # Only run sed if there are matches
    if grep -qrE 'DESTINATION[[:space:]]*\$\{CMAKE_SOURCE_DIR\}/bin/examples' "${S}"; then
        # NUL-separated to be safe with spaces
        grep -rlZE 'DESTINATION[[:space:]]*\$\{CMAKE_SOURCE_DIR\}/bin/examples' "${S}" \
        | xargs -0 sed -i -E \
          's|DESTINATION[[:space:]]*\$\{CMAKE_SOURCE_DIR\}/bin/examples|DESTINATION bin/examples|g'
        bbnote "Rewrote absolute DESTINATION to relative in example installs."
    else
        bbnote "No absolute DESTINATION paths found; nothing to rewrite."
    fi
}

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "dxrt.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/service/dxrt.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/dxrt.service"
