SUMMARY = "DEEPX Runtime NPU linux driver"

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df0ebe3edba67d21cb2e798ef0ee2905"

SRC_PATH ?= "/mnt/uuu-storage/deepx"
SRC_FILE ?= "${BP}.tar.gz"

SRC_URI = "file://${SRC_PATH}/${SRC_FILE}"

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
