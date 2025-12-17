SUMMARY = "DEEPX Runtime NPU linux driver"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_URI = "git://github.com/DEEPX-AI/dx_rt_npu_linux_driver;branch=main;protocol=https"
SRCREV = "a90cd9616f6ebe1e10feafb1371e4ca11f0c2c48"

S = "${WORKDIR}/git/modules"

PV = "1.8.0"

inherit module

DX_DEVICE="m1a"
DX_PCIE="deepx"

EXTRA_OEMAKE = "KERNEL_DIR=${STAGING_KERNEL_BUILDDIR} DEVICE=${DX_DEVICE} PCIE=${DX_PCIE}"
MODULES_INSTALL_TARGET = "install"

do_install:append () {
	# modprobe configuration
	install -d ${D}${sysconfdir}/modprobe.d
	install -m 0644 ${S}/dx_dma.conf ${D}${sysconfdir}/modprobe.d/dx_dma.conf

	# udev rules
	echo "#Change mode rules for DEEPX's PCIe driver" > "${S}/51-deepx-udev.rules"
	echo "SUBSYSTEM==\"dxrt\", MODE=\"0666\"" >> "${S}/51-deepx-udev.rules"

	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${S}/51-deepx-udev.rules ${D}${sysconfdir}/udev/rules.d/
}

FILES:${PN}="${sysconfdir}/modprobe.d/dx_dma.conf \
             ${sysconfdir}/udev/rules.d/51-deepx-udev.rules"
