DESCRIPTION = "Add all packages required for a basic use of a DeepX M1 module"

inherit packagegroup

RDEPENDS:${PN} = " \
    dx-rt-npu \
    dx-rt \
    dx-app \
    gzip \
    python3-dxengine \
"

# the dx-* packages are obvious
# the gzip package is needed to get 'update-pciids' to work
