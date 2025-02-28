# meta-ea-dx

## Preparations

### dx-rt-npu (Kernel driver)

The original tar.gz file must be renamed since underscore '_'
has a special meaning in Yocto.

```
mv dx_rt_npu_linux_driver_v1.3.1.tar.gz dx-rt-npu-1.3.1.tar.gz
```

### dx-rt (Runtime)

The original tar.gz file must be renamed since underscore '_'
has a special meaning in Yocto.

```
mv dx_rt_v2.6.3.tar.gz dx-rt-2.6.3.tar.gz
```

### dx-app (Example applications)

The original tar.gz file must be renamed since underscore '_'
has a special meaning in Yocto.

```
mv dx_app_1.0.6.tar.gz dx-app-1.0.6.tar.gz
```

### Put files on EA NAS

Due to licensing issues we cannot publish the source files. Instead,
the recepies have been setup to retrieve these from the EA NAS.
By default, it will use ```/mnt/uuu-storage/deepx``` as source path.
On the build servers this path is normally the same as 
```//192.168.6.2/products/Boards/COM_iMX_Common/mfgtools/no_backup/deepx```


### The python 'dx_engine' package

The 'dx_engine' python package can be installed on target by:

1. Download the dx-rt-2.6.3.tar.gz to target and extract it
2. cd dx_rt
3. sed -i 's/sudo //g' build.sh
4. sed -i 's;/usr/bin/aarch64-linux-gnu-;;g' cmake/toolchain.aarch64.cmake
5. cd extern
6. wget https://github.com/pybind/pybind11/archive/refs/heads/master.zip
7. unzip master.zip
8. mv pybind11-master/ pybind11
9. cd ..
10 ./build.sh --clean
11. cd python_package
12. pip install .

Step 10 produces the interesting file ``python_package/src/dx_engine/capi/_pydxrt.cpython-312-aarch64-linux-gnu.so``
which unfortunately cannot be crosscompiled in yocto (yet).

So the python3-dxengine package relies on it being precompiled. Do that by
1. Follow steps 1-5 above
2. Transfer it to the build server
3. Replace the content of python3-dxengine/files/prebuilt_lib_for_dxengine_2.6.3.zip with
   the newly built .so file. Remember to keep the folder structure of the zip file so that
   the file is placed correctly by the recipe.


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

