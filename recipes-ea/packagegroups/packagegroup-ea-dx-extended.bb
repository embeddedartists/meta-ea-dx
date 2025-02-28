DESCRIPTION = "Add all packages required for full use of a DeepX M1 module"

inherit packagegroup

# mandatory
RDEPENDS:${PN} = " \
    packagegroup-ea-dx-base \
"

RRECOMMENDS:${PN} = " \
    packagegroup-core-buildessential \
    python3-numpy \
    cmake \
    \
    ${@bb.utils.contains('MACHINE', 'imx8mmea-ucom', 'packagegroup-fsl-opencv-imx', '', d)} \
    \
    python3-six \
    python3-lxml \
    ninja \
    python3-pybind11 \
"

# replaced opencv with packagegroup-fsl-opencv-imx to get apps, samples and python support

