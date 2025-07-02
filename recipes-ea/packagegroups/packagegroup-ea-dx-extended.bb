DESCRIPTION = "Add all packages required for full use of a DeepX M1 module"

inherit packagegroup

# mandatory
RDEPENDS:${PN} = " \
    packagegroup-ea-dx-base \
"

RRECOMMENDS:${PN} = " \
    packagegroup-core-buildessential \
    python3-numpy \
    python3-six \
    python3-lxml \
    python3-pybind11 \
    cmake \
    \
    ${@bb.utils.contains('MACHINE', 'imx8mmea-ucom', 'packagegroup-fsl-opencv-imx', '', d)} \
    ${@bb.utils.contains('MACHINE', 'imx8mmea-som', 'packagegroup-fsl-opencv-imx', '', d)} \
    \
    ninja \
    \
    dx-rt-npu-dev \
    dx-rt-npu-src \
    dx-rt-src \
    dx-app-src \
"

# replaced opencv with packagegroup-fsl-opencv-imx to get apps, samples and python support

