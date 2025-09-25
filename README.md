# meta-ea-dx

## How to use

Clone this repository into the `sources` directory.

```
cd <repo-dir>/sources
git clone https://github.com/embeddedartists/meta-ea-dx
```

Add the layer to the end of `conf/bblayers.conf`:

`BBLAYERS += " ${BSPDIR}/sources/meta-ea-dx "`

For a minimal build, add the following to `conf/local.conf`

```
IMAGE_INSTALL:append = " packagegroup-ea-dx-base"
```

Or for a more complete build with build tools, opencv and more, add the following to `conf/local.conf`

```
EXTRA_IMAGE_FEATURES += "package-management dev-pkgs"
IMAGE_INSTALL:append = " packagegroup-ea-dx-extended"
```

**NOTE**

Yocto does *split-packages* which results in multiple "versions" of the same package.
For example, dx-rt has the following:

* dx-rt (the default, binaries and libdxrt.so)
* dx-rt-dbg (a .debug folder is placed next to each of the binaries above and contains the debug version of that file)
* dx-rt-dev (places .h/.hpp/.cmake files under /usr/include and /usr/lib)
* dx-rt-src (places source code for the binaries and lib under /usr/src/debug/dx-rt/2.6.3)

The dx-app recipe has dx-app, dx-app-dbg and dx-app-src.

The dx-rt-npu recipe has dx-rt-npu, dx-rt-npu-dbg, dx-rt-npu-dev and dx-rt-npu-src.

The python3-dxengine recipe has no extra versions.


### Yocto distribution

The DX applications are using OpenCV in a way which requires GTK+ or
Cocoa support. This is not available in a `wayland` distribution.
Instead, use `xwayland` as when setting up your build.

```
DISTRO=fsl-imx-xwayland MACHINE=imx8mmea-ucom source ea-setup-release.sh -b 8mm-x
```


### Create a toolchain

After building the ea-image-base as normal, create a toolchain by running:

```
bitbake -c populate_sdk ea-image-base
```

This will add the dx-rt, kernel header files etc so that the toolchain can
be used outside of yocto to crosscompile e.g. dx-app for target.

