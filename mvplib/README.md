# mvplib

包含mvp模式的各基类：

###关系:
- View层(一般是Activity和Fragment)：<br>
&emsp;&emsp;视图层，负责视图的展示处理。 视图层创建的时候，同时会创建一个presenter，并把自己跟presenter关联（以接口的形式，每个视图层都会implement一个视图层接口）。<br><br>
&emsp;&emsp;View层基类: MvpBaseActivity、MvpBaseFragment<br>
实现了一些view层通用的方法，例如showTipDialog、showToast。并且写好了在onCreate时创建presenter并关联，在onDestroy时与presenter解除关联。
<br>
&emsp;&emsp;View层接口基类： ViewBaseInterface

- Presenter层: <br>
&emsp;&emsp;逻辑层，负责逻辑业务，在需要操作视图的时候，通过关联了的视图层接口进行操作。<br><br>
&emsp;&emsp;逻辑层基类：MvpBasePresenter<br>
实现了弱引用关联视图、解除关联视图、获取关联的视图的方法 



-  Model层<br>
数据层


###其他：
为了方便各个library的相关构建版本统一，设置了一些通用的全局变量，关于build.gradle的设置，不懂的看如下：<br>
修改项目目录下gradle.properties文件,添加如下设置：
```
# 全局环境配置
ANDROID_BUILD_SDK_VERSION=25
ANDROID_BUILD_TOOLS_VERSION=25.0.3
ANDROID_BUILD_MIN_SDK_VERSION=9
ANDROID_BUILD_TARGET_SDK_VERSION=25
SUPPORT_LIBRARY=25.2.0
```

然后在library和主module的build.gradle文件下可以引用这些全局变量：
```
android {
    compileSdkVersion project.ANDROID_BUILD_SDK_VERSION as int
    buildToolsVersion project.ANDROID_BUILD_TOOLS_VERSION

    defaultConfig {
        minSdkVersion project.ANDROID_BUILD_MIN_SDK_VERSION as int
        targetSdkVersion project.ANDROID_BUILD_TARGET_SDK_VERSION as int
        ...
    }
    ...
}

dependencies {
    ...
    compile "com.android.support:appcompat-v7:${SUPPORT_LIBRARY}"  // 注意这里把原来的单引号改成双引号
    compile "com.android.support:recyclerview-v7:${SUPPORT_LIBRARY}"
}
```
