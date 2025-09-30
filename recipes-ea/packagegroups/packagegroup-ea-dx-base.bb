DESCRIPTION = "Add all packages required for a basic use of a DeepX M1 module"

inherit packagegroup

RDEPENDS:${PN} = " \
    dx-rt-npu \
    dx-rt \
    dx-rt-dev \
    dx-app \
    gzip \
"

# the dx-* packages are obvious except for dx-rt-dev which is needed when
# compiling dx-app as it provides the gen.h file that dx-app looks for
# the gzip package is needed to get 'update-pciids' to work
