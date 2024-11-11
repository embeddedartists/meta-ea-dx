# meta-ea-dx

## Preparations

### dx-rt-npu (Kernel driver)

The original tar.gz file must be restructured and renamed before used 
as source for the Yocto recipe. It must be renamed since underscore '_'
has a special meaning in Yocto and should not be used in file names.
It must be restructured to follow the expected folder structure
for kernel modules.

```
tar -xzvf dx_rt_npu_linux_driver_v1.0.7.tar.gz
cd dx_rt_npu_linux_driver/
mv modules/* .
rm -r modules
cd ..
mv dx_rt_npu_linux_driver dx-rt-npu-1.0.7
tar czf dx-rt-npu-1.0.7.tar.gz dx-rt-npu-1.0.7/
```

### dx-rt (Runtime)

The original tar.gz file must be renamed since underscore '_'
has a special meaning in Yocto.

```
mv dx_rt_v2.0.3.tar.gz dx-rt-2.0.3.tar.gz
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

## How to use

Clone this repository into the `sources` directory.

```
cd <repo-dir>/sources
git clone https://github.com/embeddedartists/meta-ea-dx
```

Add the layer to the end of `conf/bblayers.conf`:

`BBLAYERS += " ${BSPDIR}/sources/meta-ea-dx "`

Add the following to `conf/local.conf`

```
IMAGE_INSTALL:append = " dx-rt-npu"
IMAGE_INSTALL:append = " dx-rt"
IMAGE_INSTALL:append = " dx-app"
```

Add the following to `conf/local.conf` to get the `update-pciids` command to work

```
IMAGE_INSTALL:append = " gzip"
```

### Yocto distribution

The DX applications are using OpenCV in a way which requires GTK+ or 
Cocoa support. This is not available in a `wayland` distribution.
Instead, use `xwayland` as when setting up your build.

```
DISTRO=fsl-imx-xwayland MACHINE=imx8mmea-ucom source ea-setup-release.sh -b 8mm-x
```








